package org.application.repositories.custom;

import org.application.models.custom.RequestRecord;

import java.sql.SQLException;
import java.util.List;

public interface CustomRepo<T,ID> {
    void save(T record) throws SQLException;

    T get(ID id) throws SQLException;

    void delete(ID id) throws SQLException;

    List<T> getAll() throws SQLException;
}
