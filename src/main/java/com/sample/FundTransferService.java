package com.sample;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.function.Supplier;

public class FundTransferService {

    private final BankRepository bankRepository;
    private final UnitOfWork unitOfWork;

    public FundTransferService(BankRepository bankRepository, UnitOfWork unitOfWork) {
        this.bankRepository = bankRepository;
        this.unitOfWork = unitOfWork;
    }

    public void transfer(UUID source, UUID target, BigDecimal amount) {
        Account sourceAccount = findAccountOrElse(source, IllegalArgumentException::new);
        Account targetAccount = findAccountOrElse(target, IllegalArgumentException::new);

        TransactionalCallback callback = () -> {
            unitOfWork.begin();
            try {
                withdraw(amount, sourceAccount);
                deposit(amount, targetAccount);
            } finally {
                unitOfWork.commit();
            }
        };
        callback.doInTransaction();
    }

    private Account findAccountOrElse(UUID source, Supplier<IllegalArgumentException> exception) {
        return bankRepository.getAccountBy(source)
                .orElseThrow(exception);
    }

    private void deposit(BigDecimal amount, Account targetAccount) {
        targetAccount.deposit(amount);
        bankRepository.update(targetAccount);
        System.out.println(">> Deposit: " + targetAccount);
    }

    private void withdraw(BigDecimal amount, Account sourceAccount) {
        sourceAccount.withdraw(amount);
        bankRepository.update(sourceAccount);
        System.out.println(">> Withdraw : " + sourceAccount);
    }
}
