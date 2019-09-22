package com.sample;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class JDBCUnitOfWork implements UnitOfWork {

    private Connection connection;

    public JDBCUnitOfWork(Connection connection) {
        Objects.requireNonNull(connection);

        this.connection = connection;
    }

    public void begin() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
