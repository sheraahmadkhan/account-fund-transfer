package com.sample;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    void assignNewIdToAccountUponCreation() {

        String clientName = randomUUID().toString();
        Account account = new Account(clientName);

        assertThat(account.getId(), notNullValue());
        assertThat(account.getClientName(), equalTo(clientName));
    }

    @Test
    void initializeWithBalanceUponCreation() {
        BigDecimal initialBalance = new BigDecimal(10);
        Account account = new Account(randomUUID().toString(), initialBalance);

        assertThat(account.getId(), notNullValue());
        assertThat(account.getBalance(), equalTo(initialBalance));
    }

    @Test
    void initialBalanceMustNotBeNegative() {
        BigDecimal initialBalance = new BigDecimal(-10);

        assertThrows(IllegalArgumentException.class,
                () -> new Account(randomUUID().toString(), initialBalance));
    }

    @Test
    void depositMustAddToBalance() {
        BigDecimal initialBalance = new BigDecimal(100);
        Account account = new Account(randomUUID().toString(), initialBalance);

        account.deposit(new BigDecimal(100));

        assertThat(account.getBalance(), equalTo(new BigDecimal(200)));
    }

    @Test
    void withdrawMustSubtractFromBalance() {
        BigDecimal initialBalance = new BigDecimal(100);
        Account account = new Account(randomUUID().toString(), initialBalance);

        account.withdraw(new BigDecimal(10));

        assertThat(account.getBalance(), equalTo(new BigDecimal(90)));
    }

    @Test
    void withdrawAllBalance() {
        BigDecimal initialBalance = new BigDecimal(100);
        Account account = new Account(randomUUID().toString(), initialBalance);

        account.withdraw(new BigDecimal(100));

        assertThat(account.getBalance(), equalTo(new BigDecimal(0)));
    }

    @Test
    void withdrawMoreThanAvailableBalanceResultsToException() {
        BigDecimal initialBalance = new BigDecimal(100);
        Account account = new Account(randomUUID().toString(), initialBalance);

        assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(new BigDecimal(1000)));
    }
}