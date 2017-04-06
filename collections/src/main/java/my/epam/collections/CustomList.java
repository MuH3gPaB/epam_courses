package my.epam.collections;

import java.util.Iterator;

public interface CustomList<T> {

    int size();

    boolean isEmpty();

    boolean contains(Object o);

    Iterator<T> iterator();

    boolean add(T t);

    boolean remove(Object o);

    void clear();

    T get(int index);

    T set(int index, T element);

    void add(int index, T element);

    T remove(int index);

    int indexOf(Object o);
}
