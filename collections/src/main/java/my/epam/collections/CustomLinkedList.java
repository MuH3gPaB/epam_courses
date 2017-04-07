package my.epam.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomLinkedList<E> implements CustomList<E> {
    private Node<E> head = new Node<>(null);
    private int size;

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
        return null;
    }

    @Override
    public boolean add(E t) {
        add(size(), t);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node<E> prev = head;
        Node<E> node = head.next;
        for (int i = 0; i < size(); i++) {
            if (checkEquality(o, node.value)) {
                prev.next = node.next;
                size--;
                return true;
            }

            prev = node;
            node = node.next;
        }
        return false;
    }

    @Override
    public void clear() {
        head = new Node<>(null);
        size = 0;
    }

    @Override
    public E get(int index) {
        rangeCheck(index, false);
        Node<E> node = head.next;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.value;
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index, false);
        Node<E> node = head.next;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        E tmp = node.value;
        node.value = element;
        return tmp;
    }

    @Override
    public void add(int index, E element) {
        rangeCheck(index, true);
        Node<E> prev = head;
        Node<E> node = prev.next;
        for (int i = 0; i < index; i++) {
            prev = node;
            node = node.next;
        }

        Node<E> newNode = new Node<>(element);
        prev.next = newNode;
        newNode.next = node;

        incrementSize();
    }

    @Override
    public E remove(int index) {
        rangeCheck(index, false);
        Node<E> prev = head;
        Node<E> node = head.next;
        for (int i = 0; i < index; i++) {
            prev = node;
            node = node.next;
        }

        prev.next = node.next;
        size--;
        return node.value;
    }

    @Override
    public int indexOf(Object o) {
        Node<E> node = head;
        for (int i = 0; i < size(); i++) {
            node = node.next;
            if (checkEquality(o, node.value)) return i;
        }
        return -1;
    }

    private class Node<E> {
        Node<E> next;
        E value;

        public Node(E value) {
            this.value = value;
        }

        private boolean hasNext() {
            return next != null;
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
            if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        } else {
            if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        }
    }

    private void incrementSize() {
        size++;
    }
}
