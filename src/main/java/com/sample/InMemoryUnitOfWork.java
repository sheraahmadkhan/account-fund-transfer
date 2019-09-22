package com.sample;

import java.util.concurrent.locks.ReentrantLock;

public class InMemoryUnitOfWork implements UnitOfWork {
    private ReentrantLock locksmith;

    public InMemoryUnitOfWork() {
        this.locksmith = new ReentrantLock();
    }

    @Override
    public void begin() {
        if (isPending()) {
            locksmith.unlock();
        }
        this.locksmith.lock();
    }

    private boolean isPending() {
        return this.locksmith.isLocked();
    }


    @Override
    public void commit() {
        this.locksmith.unlock();
    }

    @Override
    public void rollback() {
        this.locksmith.unlock();
    }
}
