package org.entities.accounts;

import org.entities.clients.Client;
import org.exceptions.BankException;

import java.time.LocalDate;
public class DepositAccount extends Account {
    private LocalDate paymentDate;
    private double interestRate;

    public DepositAccount(double initialAmount) {
        super();
        setInterestRateByInitialAmount(initialAmount);
        calculateDate();
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    private void setInterestRateByInitialAmount(double amount) {
        double MIN_AMOUNT = 50000;
        double MEDIUM_AMOUNT = 100000;
        if (amount < MIN_AMOUNT) {
            this.interestRate = 0.03;
        } else if (amount < MEDIUM_AMOUNT) {
            this.interestRate = 0.035;
        } else {
            this.interestRate = 0.04;
        }
    }

    private void calculateDate() {
        this.paymentDate = LocalDate.now().plusYears(1);
    }

    @Override
    public void withdraw(double amount, Client client) throws BankException {
        if (LocalDate.now().isBefore(paymentDate)) {
            throw new BankException("Withdrawal isn't allowed until payment date expires");
        }
        super.withdraw(amount, client);
    }

    @Override
    public void transfer(Account destination, double amount, Client client) throws BankException {
        if (LocalDate.now().isBefore(paymentDate)) {
            throw new BankException("Transfer isn't allowed until payment date expires");
        }
        super.transfer(destination, amount, client);
    }
}