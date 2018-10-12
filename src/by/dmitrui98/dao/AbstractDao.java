package by.dmitrui98.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Администратор on 10.10.2018.
 */
public abstract class AbstractDao<T> {
    public List<T> getAll() throws SQLException {
        throw new UnsupportedOperationException();
    }

    public T getById(int id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public void insert(T obj) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public void update(T obj) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public void delete(T obj) throws SQLException {
        throw new UnsupportedOperationException();
    }
}

