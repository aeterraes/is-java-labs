package org.services;

import org.entities.accounts.Account;
import org.entities.banks.Bank;
import org.entities.clients.Client;
import org.exceptions.BankException;

import java.util.ArrayList;
import java.util.List;

public class CentralBank {
    private static CentralBank instance;
    private List<Bank> registeredBanks;

    private CentralBank() {
        this.registeredBanks = new ArrayList<>();
    }

    public static CentralBank getInstance() {
        if (instance == null) {
            instance = new CentralBank();
        }
        return instance;
    }

    public void registerBank(Bank bank) {
        registeredBanks.add(bank);
    }

    public void deregisterBank(Bank bank) {
        registeredBanks.remove(bank);
    }

    public List<Bank> getRegisteredBanks() {
        return registeredBanks;
    }
    public void processExternalTransfer(Bank senderBank, Bank receiverBank, Account senderAccount, Account receiverAccount, double amount, Client senderClient, Client receiverClient) throws BankException {
        if (!registeredBanks.contains(senderBank) || !registeredBanks.contains(receiverBank)) {
            throw new BankException("One or both banks aren't registered in the central bank.");
        }

        boolean senderDoubtful = senderClient.isDoubtful();
        boolean receiverDoubtful = receiverClient.isDoubtful();

        if (senderDoubtful || receiverDoubtful) {
            double senderTransferLimit = senderBank.getTransferLimit();
            double receiverTransferLimit = receiverBank.getTransferLimit();

            if ((senderDoubtful && amount > senderTransferLimit) || (receiverDoubtful && amount > receiverTransferLimit)) {
                throw new BankException("Amount exceeds transfer limit");
            }
        }

        senderAccount.withdraw(amount, senderClient);
        receiverAccount.deposit(amount, receiverClient);
    }

}
