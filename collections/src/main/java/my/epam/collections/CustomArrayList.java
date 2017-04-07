package my.epam.collections;

import java.util.Iterator;
import java.util.Objects;

public class CustomArrayList<T> implements CustomList<T> {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private int size;
    private Object[] data = new Object[DEFAULT_INITIAL_CAPACITY];

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size(); i++) {
            if (o == null) {
                if (data[i] == null) {
                    return true;
                }
            } else if (o.equals(data[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public boolean add(T t) {
        ensureCapacity(size + 1);
        data[size] = t;
        incrementSize();
        return true;
    }

    private void ensureCapacity(int capacity) {
        if (data.length < capacity) {
            Object[] tmp = new Object[Math.max((data.length * 2) / 3, capacity)];
            System.arraycopy(data, 0, tmp, 0, size);
            data = tmp;
        }
    }

    private void incrementSize() {
        size++;
    }

    @Override
    public boolean remove(Object o) {
        size--;
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public void add(int index, T element) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }
}
