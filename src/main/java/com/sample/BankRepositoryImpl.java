package com.sample;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BankRepositoryImpl implements BankRepository {

    private Map<UUID, Account> accounts = new ConcurrentHashMap<>();

    public BankRepositoryImpl() {
        List<Account> accountList = init();
        accounts.put(accountList.get(0).getId(), accountList.get(0));
        accounts.put(accountList.get(1).getId(), accountList.get(1));
    }

    @Override
    public Optional<Account> getAccountBy(UUID accountNumber) {
        Account account = accounts.getOrDefault(accountNumber, null);
        return Optional.ofNullable(account);
    }

    @Override
    public void update(Account account) {
        accounts.put(account.getId(), account);
    }
}
