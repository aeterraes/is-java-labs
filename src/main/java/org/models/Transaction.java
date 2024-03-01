package org.models;

import java.util.UUID;

public class Transaction {
    private final UUID clientId;
    private final double amount;
    private String description;

    public Transaction(UUID clientId, double amount, String description) {
        this.clientId = clientId;
        this.amount = amount;
        this.description = description;
    }

    public UUID getClientId() {
        return clientId;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

