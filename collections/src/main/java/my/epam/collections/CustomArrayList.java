package my.epam.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Custom simple implementation of ArrayList.
 * <p>
 * This class stores elements as generic array.
 *
 * @param <E> type of stored elements
 * @see CustomList
 */

public class CustomArrayList<E> implements CustomList<E> {
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
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomListIterator();
    }

    @Override
    public boolean add(E t) {
        add(size(), t);
        return true;
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
    public E get(int index) {
        rangeCheck(index, false);
        return (E) data[index];
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index, false);
        E tmp = (E) data[index];
        data[index] = element;
        return tmp;
    }

    @Override
    public void add(int index, E element) {
        rangeCheck(index, true);
        ensureCapacity(size + 1);
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        incrementSize();
    }

    @Override
    public E remove(int index) {
        rangeCheck(index, false);
        E element = get(index);
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

    private class CustomListIterator implements Iterator<E> {
        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size();
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException("No more elements for iteration.");
            return (E) data[cursor++];
        }

        @Override
        public void remove() {
            CustomArrayList.this.remove(--cursor);
        }
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
            if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index : " + index + ", Size : " + size);
        } else {
            if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index : " + index + ", Size : " + size);
        }
    }

    private void ensureCapacity(int capacity) {
        if (data.length < capacity) {
            Object[] tmp = new Object[Math.max((data.length * 2) / 3, capacity)];
            System.arraycopy(data, 0, tmp, 0, size);
            data = tmp;
        }
    }

    private void incrementSize() {
        this.size += (this.size == Integer.MAX_VALUE) ? 0 : 1;
    }
}
