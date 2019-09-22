package com.sample;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Arrays.asList;

public interface BankRepository {
    Optional<Account> getAccountBy(UUID accountNumber);

    void update(Account account);

    default List<Account> init() {
        Account account1 = new Account("Client-1", new BigDecimal(200));
        Account account2 = new Account("Client-2", new BigDecimal(100));

        System.out.println(">> Initialized " + account1);
        System.out.println(">> Initialized " + account2);

        return asList(account1, account2);
    }
}
