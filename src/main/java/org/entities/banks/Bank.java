package org.entities.banks;

import org.entities.accounts.Account;
import org.entities.accounts.CreditAccount;
import org.entities.accounts.DebitAccount;
import org.entities.accounts.DepositAccount;
import org.entities.clients.Client;
import org.exceptions.BankException;
import org.models.AccountType;
import org.models.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Bank {
    private double transferLimit;
    private double interestRate;
    private double commission;
    private double creditLimit;
    private List<Client> clients;
    private List<Observable> observers;
    private List<Transaction> cancelledTransactions;

    public Bank(double transferLimit, double interestRate, double commission, double creditLimit) {
        this.transferLimit = transferLimit;
        this.interestRate = interestRate;
        this.commission = commission;
        this.creditLimit = creditLimit;
        this.clients = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.cancelledTransactions = new ArrayList<>();
    }

    public void setTransferLimit(double transferLimit, AccountType type) {
        this.transferLimit = transferLimit;
        notifyClients("Transfer limit changed to " + transferLimit, type);
    }

    public void setInterestRate(double interestRate, AccountType type) {
        this.interestRate = interestRate;
        notifyClients("Interest rate changed to " + interestRate, type);
    }

    public void setCommission(double commission, AccountType type) {
        this.commission = commission;
        notifyClients("Commission changed to " + commission, type);
    }

    public void addObserver(Observable observer) {
        observers.add(observer);
    }

    public void removeObserver(Observable observer) {
        observers.remove(observer);
    }

    private void notifyClients(String message, AccountType type) {
        for (Client client : clients) {
            for (Account account : client.getAccounts()) {
                if (type == AccountType.DEBIT && account instanceof DebitAccount) {
                    ((DebitAccount) account).setInterestRate(interestRate);
                    client.update(message);
                } else if (type == AccountType.CREDIT && account instanceof CreditAccount creditAccount) {
                    if (message.contains("Commission")) {
                        creditAccount.setCommission(commission);
                    } else if (message.contains("Credit limit")) {
                        creditAccount.setCreditLimit(creditLimit);
                    }
                    client.update(message);
                } else if (type == AccountType.DEPOSIT && account instanceof DepositAccount) {
                    ((DepositAccount) account).setInterestRate(interestRate);
                    client.update(message);
                }
            }
        }
    }

    public void addClient(Client client) {
        clients.add(client);
        client.subscribe(this);
    }

    public void removeClient(Client client) {
        clients.remove(client);
        client.unsubscribe(this);
    }

    public void cancelTransaction(Transaction transaction) throws BankException {
        if (!cancelledTransactions.contains(transaction)) {
            Account account = Objects.requireNonNull(getAccountByClientId(transaction.getClientId(), transaction.getDescription()));
            if (transaction.getAmount() < 0) {
                account.deposit(-transaction.getAmount(), Objects.requireNonNull(getClientById(transaction.getClientId())));
            } else {
                account.withdraw(transaction.getAmount(), Objects.requireNonNull(getClientById(transaction.getClientId())));
            }
            transaction.setDescription("Cancelled: " + transaction.getDescription());
            cancelledTransactions.add(transaction);
        }
    }

    private Account getAccountByClientId(UUID clientId, String transactionType) {
        for (Client client : clients) {
            if (client.getId().equals(clientId)) {
                for (Account account : client.getAccounts()) {
                    if (transactionType.equals("Withdrawal") && account instanceof DebitAccount) {
                        return account;
                    } else if (transactionType.equals("Deposit") && account instanceof DepositAccount) {
                        return account;
                    } else if (transactionType.startsWith("Transfer to") && account instanceof CreditAccount) {
                        return account;
                    }
                }
            }
        }
        return null;
    }

    private Client getClientById(UUID clientId) {
        for (Client client : clients) {
            if (client.getId().equals(clientId)) {
                return client;
            }
        }
        return null;
    }

    public double getTransferLimit() {
        return transferLimit;
    }
    public void processInternalTransfer(Account senderAccount, Account receiverAccount, double amount, Client senderClient, Client receiverClient) throws BankException {
        boolean senderDoubtful = senderClient.isDoubtful();
        boolean receiverDoubtful = receiverClient.isDoubtful();

        if (senderDoubtful || receiverDoubtful) {
            double senderTransferLimit = getTransferLimit();
            double receiverTransferLimit = getTransferLimit();

            if (senderDoubtful && amount > senderTransferLimit) {
                throw new BankException("Amount exceeds sender's transfer limit");
            }

            if (receiverDoubtful && amount > receiverTransferLimit) {
                throw new BankException("Amount exceeds receiver's transfer limit");
            }
        }

        if (senderAccount instanceof CreditAccount) {
            senderAccount.withdraw(amount, senderClient);
            ((CreditAccount) senderAccount).withdrawCommission(senderClient);
        } else {
            senderAccount.withdraw(amount, senderClient);
        }

        receiverAccount.deposit(amount, receiverClient);
    }
}

