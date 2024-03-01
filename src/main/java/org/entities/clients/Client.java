package org.entities.clients;

import org.entities.accounts.Account;
import org.entities.banks.Bank;
import org.entities.banks.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client implements Observable {
    private final UUID id;
    private boolean doubtful;
    private final List<Account> accounts;
    private final List<String> bankNotifications;

    private Client(UUID id, String name, String surname, String passport, String address) {
        this.id = id;
        this.bankNotifications = new ArrayList<>();
        this.doubtful = false;
        this.accounts = new ArrayList<>();
    }

    public static class ClientBuilder {
        private final UUID id = UUID.randomUUID();
        private String name;
        private String surname;
        private String passport = "";
        private String address = "";

        public ClientBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ClientBuilder withSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public ClientBuilder withPassport(String passport) {
            this.passport = passport;
            return this;
        }

        public ClientBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Client build() {
            Client client = new Client(id, name, surname, passport, address);
            if (passport.isEmpty() || address.isEmpty()) {
                client.setDoubtful(true);
            }
            return client;
        }
    }

    public UUID getId() {
        return id;
    }

    public boolean isDoubtful() {
        return doubtful;
    }

    public void setDoubtful(boolean doubtful) {
        this.doubtful = doubtful;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }
    public void subscribe(Bank bank) {
        bank.addObserver(this);
    }

    public void unsubscribe(Bank bank) {
        bank.removeObserver(this);
    }

    public void update(String notification) {
        bankNotifications.add(notification);
    }

    public List<String> getAllNotifications() {
        return bankNotifications;
    }
}