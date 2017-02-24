package my.epam.stationery.dao;

import my.epam.stationery.model.Stationery;

/**
 * Created by Muxa on 24.02.2017.
 */
public interface AbstractDao<T> {
        T[] getAll();
        T getById(long id);
        void remove(long id);
        void remove(T obj);
        void saveOrUpdate(T obj);
}
