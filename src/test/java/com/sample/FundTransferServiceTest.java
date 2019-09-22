package com.sample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.empty;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FundTransferServiceTest {
    private static final String SOURCE_CLIENT_NAME = "SourceClient LLC";
    private static final String TARGET_CLIENT_NAME = "TargetClient LLC";

    private FundTransferService service;
    private BankRepository bankRepository;
    private UnitOfWork unitOfWork;

    private UUID sourceId;
    private UUID targetId;


    @BeforeEach
    void setUp() {
        bankRepository = mock(BankRepository.class);
        unitOfWork = mock(UnitOfWork.class);
        service = new FundTransferService(bankRepository, unitOfWork);

        sourceId = randomUUID();
        targetId = randomUUID();
    }

    @Test
    void throwExceptionWhenSourceAccountNotPresent() {
        when(bankRepository.getAccountBy(eq(sourceId))).thenReturn(empty());

        assertThrows(IllegalArgumentException.class,
                () -> service.transfer(sourceId, targetId, new BigDecimal(100)));
    }

    @Test
    void throwExceptionWhenTargetAccountNotPresent() {
        Account client = createClient(SOURCE_CLIENT_NAME, new BigDecimal(10));
        when(bankRepository.getAccountBy(eq(sourceId))).thenReturn(Optional.of(client));
        when(bankRepository.getAccountBy(eq(targetId))).thenReturn(empty());

        assertThrows(IllegalArgumentException.class,
                () -> service.transfer(sourceId, targetId, new BigDecimal(100)));
    }

    @Test
    void transferIsWithdrawFirstAndThenDeposit() {
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);

        Account sourceAccount = createClient(SOURCE_CLIENT_NAME, new BigDecimal(100));
        Account targetAccount = createClient(TARGET_CLIENT_NAME, new BigDecimal(100));
        when(bankRepository.getAccountBy(eq(sourceId))).thenReturn(Optional.of(sourceAccount));
        when(bankRepository.getAccountBy(eq(targetId))).thenReturn(Optional.of(targetAccount));

        service.transfer(sourceId, targetId, new BigDecimal(10));

        InOrder inOrder = inOrder(unitOfWork, bankRepository);
        inOrder.verify(unitOfWork).begin();

        inOrder.verify(bankRepository, times(2)).update(accountCaptor.capture());
        inOrder.verify(unitOfWork).commit();

        List<Account> allValues = accountCaptor.getAllValues();
        assertThat(allValues.get(0).getBalance(), equalTo(new BigDecimal(90)));
        assertThat(allValues.get(1).getBalance(), equalTo(new BigDecimal(110)));
    }

    private Account createClient(String clientName, BigDecimal balance) {
        return new Account(clientName, balance);
    }

}