package org.entities.accounts;

import org.entities.clients.Client;
import org.exceptions.BankException;
import org.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private double balance;
    private final List<Transaction> transactionHistory;

    public Account() {
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
    }

    public void withdraw(double amount, Client client) throws BankException {
        if (amount > balance) {
            throw new BankException("Insufficient funds");
        }
        balance -= amount;
        transactionHistory.add(new Transaction(client.getId(), -amount, "Withdrawal"));
    }

    public void deposit(double amount, Client client) {
        balance += amount;
        transactionHistory.add(new Transaction(client.getId(), amount, "Deposit"));
    }

    public void transfer(Account destination, double amount, Client client) throws BankException {
        if (amount > balance) {
            throw new BankException("Insufficient funds");
        }
        withdraw(amount, client);
        destination.deposit(amount, client);
        transactionHistory.add(new Transaction(client.getId(), -amount, "Transfer to " + destination.getClass().getSimpleName()));
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}