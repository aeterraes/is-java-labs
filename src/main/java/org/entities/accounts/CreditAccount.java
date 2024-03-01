package org.entities.accounts;

import org.entities.clients.Client;
import org.exceptions.BankException;

public class CreditAccount extends Account {
    private double creditLimit;
    private double commission;

    public CreditAccount(double creditLimit, double commission) {
        super();
        this.creditLimit = creditLimit;
        this.commission = commission;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getCommission() {
        return commission;
    }

    @Override
    public void withdraw(double amount, Client client) throws BankException {
        if (amount > getBalance() + creditLimit) {
            throw new BankException("Exceeds credit limit");
        }
        super.withdraw(amount, client);
        if (getBalance() < 0) {
            super.withdraw(commission, client);
        }
    }

    @Override
    public void transfer(Account destination, double amount, Client client) throws BankException {
        if (amount > getBalance() + creditLimit) {
            throw new BankException("Exceeds credit limit for transfer");
        }
        super.transfer(destination, amount, client);
        if (getBalance() < 0) {
            super.withdraw(commission, client);
        }
    }
    public void withdrawCommission(Client client) throws BankException {
        super.withdraw(commission, client);
    }
}
