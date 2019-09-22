package com.sample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

class JDBCUnitOfWorkTest {

    private JDBCUnitOfWork unitOfWork;
    private Connection connection;

    @BeforeEach
    void setUp() {
        connection = Mockito.mock(Connection.class);
        unitOfWork = new JDBCUnitOfWork(connection);
    }

    @Test
    void beginMustSetAutoCommitFalse() throws SQLException {
        unitOfWork.begin();

        verify(connection).setAutoCommit(false);
    }

    @Test
    void translateAllExceptionFromBeginToRuntimeException() throws SQLException {
        doThrow(SQLException.class).when(connection).setAutoCommit(false);

        assertThrows(RuntimeException.class, () -> unitOfWork.begin());
    }

    @Test
    void commitTransaction() throws SQLException {
        unitOfWork.commit();

        verify(connection).commit();
    }

    @Test
    void rollbackInCaseCommitFailed() throws SQLException {
        doThrow(SQLException.class).when(connection).commit();

        unitOfWork.commit();

        verify(connection).rollback();
    }
}