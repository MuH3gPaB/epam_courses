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
            if (checkEquality(o, data[i])) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public boolean add(T t) {
        add(size(), t);
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
        int index = indexOf(o);
        if (index != -1) {
            remove(index);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void clear() {
        data = new Object[DEFAULT_INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public T get(int index) {
        rangeCheck(index, false);
        return (T) data[index];
    }

    @Override
    public T set(int index, T element) {
        rangeCheck(index, false);
        T tmp = (T) data[index];
        data[index] = element;
        return tmp;
    }

    @Override
    public void add(int index, T element) {
        rangeCheck(index, true);
        ensureCapacity(size + 1);
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        incrementSize();
    }

    @Override
    public T remove(int index) {
        rangeCheck(index, false);
        T element = get(index);
        System.arraycopy(data, index + 1, data, index, size() - index);
        size--;
        return element;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size(); i++) {
            if (checkEquality(o, data[i])) return i;
        }
        return -1;
    }

    private boolean checkEquality(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        } else {
            return o1.equals(o2);
        }
    }

    private void rangeCheck(int index, boolean sizeOk) {
        if (sizeOk) {
            if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        } else {
            if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        }
    }
}
