package my.epam.stationery.dao;

import java.util.List;

public interface AbstractDao<T> {
        List<T> getAll();
        T getById(long id);
        void remove(long id);
        void remove(T obj);
        long saveOrUpdate(T obj);
}
