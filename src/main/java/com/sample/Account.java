package com.sample;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;
import static java.util.UUID.randomUUID;

public class Account {

    private UUID id;
    private String clientName;
    private BigDecimal balance;

    public Account(String clientName) {
        requireNonNull(clientName);

        this.clientName = clientName;
        id = randomUUID();
    }

    public Account(String clientName, BigDecimal initialBalance) {
        this(clientName);
        validateNegativeAmountOrElseThrow(initialBalance, IllegalArgumentException::new);
        this.balance = initialBalance;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getClientName() {
        return clientName;
    }

    public void deposit(BigDecimal amount) {
        this.balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        amount = balance.subtract(amount);
        validateNegativeAmountOrElseThrow(amount, IllegalArgumentException::new);
        this.balance = amount;
    }

    private void validateNegativeAmountOrElseThrow(BigDecimal amount, Supplier<RuntimeException> exceptionSupplier) {
        if (amount.signum() == -1) {
            throw exceptionSupplier.get();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
