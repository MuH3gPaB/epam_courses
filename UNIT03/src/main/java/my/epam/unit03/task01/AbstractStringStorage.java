package my.epam.unit03.task01;

public interface AbstractStringStorage {
    void addString(String string);
    String[] findAll(String pattern);
    String findOne(String pattern);
    String[] getAll();
    void clear();
}
