import org.entities.accounts.Account;
import org.entities.accounts.CreditAccount;
import org.entities.accounts.DebitAccount;
import org.entities.accounts.DepositAccount;
import org.entities.banks.Bank;
import org.entities.clients.Client;
import org.exceptions.BankException;
import org.models.Transaction;
import org.services.CentralBank;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankTests {

    @Test
    void internalTransferBobrClientTest() {
        Client kurwaBobr = new Client.ClientBuilder()
                .withName("Kurwa")
                .withSurname("Bobr")
                .withPassport("POLISHKURRRWA")
                .build();

        DebitAccount debitAccount = new DebitAccount(0.05);
        debitAccount.deposit(1000, kurwaBobr);

        DepositAccount depositAccount = new DepositAccount(1000);
        depositAccount.deposit(1000, kurwaBobr);

        kurwaBobr.addAccount(debitAccount);
        kurwaBobr.addAccount(depositAccount);

        Bank bank = new Bank(10000, 0.03, 5, 100500);
        bank.addClient(kurwaBobr);

        double amount = 500;
        try {
            bank.processInternalTransfer(debitAccount, depositAccount, amount, kurwaBobr, kurwaBobr);
        } catch (BankException e) {
            fail(e.getMessage());
        }

        assertEquals(500, debitAccount.getBalance());
        assertEquals(1500, depositAccount.getBalance());
    }

    @Test
    void internalTransferBetweenTwoClientsTest() {
        Client emhyrVarEmreis = new Client.ClientBuilder()
                .withName("Emhyr")
                .withSurname("var Emreis")
                .build();

        Client stefanSkellen = new Client.ClientBuilder()
                .withName("Stefan")
                .withSurname("Skellen")
                .build();

        DebitAccount emhyrAccount = new DebitAccount(0.05);
        emhyrAccount.deposit(1000, emhyrVarEmreis);

        DebitAccount stefanAccount = new DebitAccount(0.05);
        stefanAccount.deposit(1000, stefanSkellen);

        emhyrVarEmreis.addAccount(emhyrAccount);
        stefanSkellen.addAccount(stefanAccount);

        Bank bank = new Bank(10000, 0.03, 5, 100500);
        bank.addClient(emhyrVarEmreis);
        bank.addClient(stefanSkellen);

        double amount = 500;
        try {
            bank.processInternalTransfer(emhyrAccount, stefanAccount, amount, emhyrVarEmreis, stefanSkellen);
        } catch (BankException e) {
            fail(e.getMessage());
        }

        assertEquals(500, emhyrAccount.getBalance());
        assertEquals(1500, stefanAccount.getBalance());
    }

    @Test
    void externalTransferBetweenDifferentBanksTest() {
        Client kurwaBobr = new Client.ClientBuilder()
                .withName("Kurwa")
                .withSurname("Bobr")
                .withAddress("POLISH RIVER")
                .build();

        Client emhyrVarEmreis = new Client.ClientBuilder()
                .withName("Emhyr")
                .withSurname("var Emreis")
                .build();

        DebitAccount kurwaAccount = new DebitAccount(0.05);
        kurwaAccount.deposit(1000, kurwaBobr);

        DebitAccount emhyrAccount = new DebitAccount(0.05);
        emhyrAccount.deposit(1000, emhyrVarEmreis);

        kurwaBobr.addAccount(kurwaAccount);
        emhyrVarEmreis.addAccount(emhyrAccount);

        Bank kurwaBank = new Bank(10000, 0.03, 5, 100500);
        Bank emhyrBank = new Bank(10000, 0.03, 5, 100500);

        CentralBank centralBank = CentralBank.getInstance();
        centralBank.registerBank(kurwaBank);
        centralBank.registerBank(emhyrBank);

        kurwaBank.addClient(kurwaBobr);
        emhyrBank.addClient(emhyrVarEmreis);

        double amount = 500;
        try {
            centralBank.processExternalTransfer(kurwaBank, emhyrBank, kurwaAccount, emhyrAccount, amount, kurwaBobr, emhyrVarEmreis);
        } catch (BankException e) {
            fail(e.getMessage());
        }

        assertEquals(500, kurwaAccount.getBalance());
        assertEquals(1500, emhyrAccount.getBalance());
    }
    @Test
    void creditAccountTransferBetweenClientsTest() {
        Client Findabair = new Client.ClientBuilder()
                .withName("Francesca")
                .withSurname("Findabair")
                .build();

        Client Emean = new Client.ClientBuilder()
                .withName("Ida")
                .withSurname("Emean")
                .build();

        CreditAccount francesca = new CreditAccount(100500, 10);
        CreditAccount ida = new CreditAccount(1005000, 10);

        Findabair.addAccount(francesca);
        Emean.addAccount(ida);

        Bank bank = new Bank(10000, 0.03, 5, 100500);
        bank.addClient(Findabair);
        bank.addClient(Emean);

        double initial = 3000;
        francesca.deposit(initial, Findabair);
        ida.deposit(initial, Emean);

        double amount = 1000;

        try {
            bank.processInternalTransfer(francesca, ida, amount, Findabair, Emean);
        } catch (BankException e) {
            fail(e.getMessage());
        }

        double expectedFrancescaBalance = initial - amount - 10;
        double expectedIdaBalance = initial + amount;

        assertEquals(expectedFrancescaBalance, francesca.getBalance());
        assertEquals(expectedIdaBalance, ida.getBalance());
    }

    @Test
    void cancelTransactionTest() {
        Client kurwaBobr = new Client.ClientBuilder()
                .withName("Kurwa")
                .withSurname("Bobr")
                .build();

        Client pingwinPingwin = new Client.ClientBuilder()
                .withName("Pingwin")
                .withSurname("Pingwin")
                .build();

        DebitAccount kurwaDebitAccount = new DebitAccount(0.05);
        DebitAccount pingwinDebitAccount = new DebitAccount(0.05);

        kurwaBobr.addAccount(kurwaDebitAccount);
        pingwinPingwin.addAccount(pingwinDebitAccount);

        kurwaDebitAccount.deposit(2000, kurwaBobr);
        pingwinDebitAccount.deposit(2000, pingwinPingwin);

        Bank bank = new Bank(10000, 0.04, 4, 100500);
        bank.addClient(kurwaBobr);
        bank.addClient(pingwinPingwin);

        double amount = 500;

        try {
            bank.processInternalTransfer(kurwaDebitAccount, pingwinDebitAccount, amount, kurwaBobr, pingwinPingwin);
            bank.processInternalTransfer(kurwaDebitAccount, pingwinDebitAccount, amount, kurwaBobr, pingwinPingwin);
        } catch (BankException e) {
            fail(e.getMessage());
        }

        assertEquals(1000, kurwaDebitAccount.getBalance());
        assertEquals(3000, pingwinDebitAccount.getBalance());

        List<Transaction> history = kurwaDebitAccount.getTransactionHistory();
        assertEquals(3, history.size());

        Transaction lastTransaction = history.get(2);
        try {
            bank.cancelTransaction(lastTransaction);
        } catch (BankException e) {
            fail(e.getMessage());
        }

        assertEquals(1500, kurwaDebitAccount.getBalance());

        history = kurwaDebitAccount.getTransactionHistory();
        assertEquals(4, history.size());
        assertEquals("Cancelled: Withdrawal", history.get(2).getDescription());
    }

    @Test
    void depositAccountTransactionHistoryTest() {
        DepositAccount depositAccount = new DepositAccount(50000);
        Client sabrina = new Client.ClientBuilder().withName("Sabrina").withSurname("Glevissig").build();
        depositAccount.deposit(999, sabrina);
        assertEquals(0.035, depositAccount.getInterestRate());

        List<Transaction> history = depositAccount.getTransactionHistory();
        assertEquals(1, history.size());
        Transaction transaction = history.get(0);
        assertEquals(sabrina.getId(), transaction.getClientId());
        assertEquals(999, transaction.getAmount());
        assertEquals("Deposit", transaction.getDescription());
    }
}
