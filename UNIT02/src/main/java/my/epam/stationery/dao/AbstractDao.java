package my.epam.stationery.dao;

public interface AbstractDao<T> {
        T[] getAll();
        T getById(long id);
        void remove(long id);
        void remove(T obj);
        long saveOrUpdate(T obj);
}
