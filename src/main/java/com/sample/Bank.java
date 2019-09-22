package com.sample;

import java.util.Collection;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static java.util.UUID.randomUUID;

public class Bank {
    private UUID id;
    private String name;
    private Collection<Account> accounts;

    public Bank(String name, Collection<Account> accounts) {
        requireNonNull(name);

        this.name = name;
        this.id = randomUUID();
        this.accounts = accounts;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Account> getAccounts() {
        return accounts;
    }
}
