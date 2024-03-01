package org.example;

import org.entities.accounts.CreditAccount;
import org.entities.banks.Bank;
import org.entities.clients.Client;

public class Main {
    public static void main(String[] args) {
        Client pivozavrPivozavrov = new Client.ClientBuilder()
                .withName("Pivozavr")
                .withSurname("Pivozavrov")
                .withAddress("Pivnushka")
                .build();
        CreditAccount pivo = new CreditAccount(10000, Double.MAX_VALUE);
        pivozavrPivozavrov.addAccount(pivo);

        Bank pivoBank = new Bank(1000, 0.99, 150, 10000);
        pivoBank.addClient(pivozavrPivozavrov);

        System.out.println(pivozavrPivozavrov.isDoubtful());
    }
}
