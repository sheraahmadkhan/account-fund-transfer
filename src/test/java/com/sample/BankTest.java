package com.sample;

import org.junit.jupiter.api.Test;

import static java.util.Collections.emptySet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

class BankTest {

    @Test
    void mustInitializeWithUniqueIdUponCreation() {
        Bank bank = new Bank("Fraud LLC", emptySet());

        assertThat(bank.getId(), notNullValue());
    }

    @Test
    void mustInitializeWithNotNullValue() {
        String name = "Fraud LLC";
        Bank bank = new Bank(name, emptySet());

        assertThat(bank.getName(), equalTo(name));
    }
}