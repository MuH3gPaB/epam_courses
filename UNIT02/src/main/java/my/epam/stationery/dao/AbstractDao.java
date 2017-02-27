package my.epam.stationery.dao;

import java.util.List;
import java.util.Map;

public interface AbstractDao<T> {
        List<T> getAll();
        T getById(long id);
        void remove(long id);
        void remove(T obj);
        long saveOrUpdate(T obj);
        T saveOrUpdateAndReturn(T obj);
        List<T> findBy(Map<String, String> valMap);
}
