package my.epam.collections;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Random;

public class SkipList<T extends Comparable> implements CustomList<T> {
    private int size;
    private Node head = new Node(null);

    public SkipList() {
        head.addNextOnLvl(0, null);
    }

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
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public boolean add(T t) {

        int level = 0;
        insertOnLevel(t, level);

        Random random = new Random();

        while (random.nextInt() % 2 == 0 && level < head.nexts.length) {
            level++;
            insertOnLevel(t, level);

            if (level == head.nexts.length - 1) {
                addNewTopLevel();
            }
        }

        incrementSize();
        return false;
    }

    private void addNewTopLevel() {

    }

    private void insertOnLevel(T t, int level) {
        Node current = head;
        while (current.nexts[level] != null && current.nexts[level].element.compareTo(t) <= 0) {
            current = current.nexts[level];
        }
        Node newNode = new Node(t);
        newNode.addNextOnLvl(level, current.nexts[level]);
        current.nexts[level] = newNode;
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
        incrementSize();

    }

    @Override
    public T remove(int index) {
        size--;
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    private void incrementSize() {
        size += (size == Integer.MAX_VALUE) ? 0 : 1;
    }

    private class Node {
        private Node[] nexts = (Node[]) Array.newInstance(Node.class, 1);
        private T element;

        private Node(T element) {
            this.element = element;
        }

        private boolean hasNext(int lvl) {
            return nexts.length < lvl && nexts[lvl] != null;
        }

        private void addNextOnLvl(int lvl, Node next) {
            if (nexts.length <= lvl) enlargeNexts(lvl);
            nexts[lvl] = next;
        }

        private void removeNextOnLvl(int lvl) {
            if (nexts.length > lvl) {
                nexts[lvl] = nexts[lvl].nexts[lvl];
            }
        }

        private void enlargeNexts(int lvl) {
            Node[] newNexts = (Node[]) Array.newInstance(Node.class, lvl);
            System.arraycopy(nexts, 0, newNexts, 0, nexts.length);
            nexts = newNexts;
        }
    }

}
