package com.sample;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum ApplicationConfiguration {

    INSTANCE;

    public ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }

    public BankRepository createBankRepository() {
        return new BankRepositoryImpl();
    }

    public UnitOfWork createUnitOfWork() {
        return new InMemoryUnitOfWork();
    }

    public FundTransferService createService() {
        return new FundTransferService(createBankRepository(), createUnitOfWork());
    }

}