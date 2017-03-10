package my.epam.unit04.task04.dao;

/**
 * Interface for dao storing singe object.
 *
 * @param <T> Type of storing object.
 */

public interface SingleObjectDao<T> {
    T load();
    void save(T value);
}
