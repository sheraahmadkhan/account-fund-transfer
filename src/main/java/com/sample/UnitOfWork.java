package com.sample;


//TODO: Introduce a new method doInTransaction(...) to handle all consumers parameters from single point.
public interface UnitOfWork {

    void begin();

    void commit();

    void rollback();
}
