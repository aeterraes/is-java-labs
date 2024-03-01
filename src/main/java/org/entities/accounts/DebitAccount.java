package org.entities.accounts;

import org.entities.clients.Client;
import org.exceptions.BankException;

public class DebitAccount extends Account {
    private double interestRate;

    public DebitAccount(double interestRate) {
        super();
        this.interestRate = interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    public double getInterestRate() {return interestRate;}

    public void applyInterest(Client client) {
        double interest = getBalance() * interestRate;
        deposit(interest, client);
    }

    @Override
    public void withdraw(double amount, Client client) throws BankException {
        if (amount > getBalance()) {
            throw new BankException("Insufficient funds");
        }
        super.withdraw(amount, client);
    }

    @Override
    public void transfer(Account destination, double amount, Client client) throws BankException {
        if (amount > getBalance()) {
            throw new BankException("Insufficient funds");
        }
        super.transfer(destination, amount, client);
    }
}