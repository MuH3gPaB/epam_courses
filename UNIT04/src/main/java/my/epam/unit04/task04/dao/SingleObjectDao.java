package my.epam.unit04.task04.dao;

public interface SingleObjectDao<T> {
    T load();
    void save(T value);
}
