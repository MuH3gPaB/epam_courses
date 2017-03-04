package my.epam.unit03.task01;

/**
 * String storage interface.
 *
 * Used by some loggers or other tasks that does not
 * requires modification or removing strings from storage.
 *
 */
public interface StringStorage {
    void addString(String string);
    String[] findAll(String pattern);
    String findOne(String pattern);
    String[] getAll();
    void clear();
}
