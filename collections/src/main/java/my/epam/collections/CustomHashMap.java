package my.epam.collections;

import java.util.*;

public class CustomHashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_BUCKETS_COUNT = 16;
    private CustomEntry<K, V>[] bucketsHeads = produceBuckets(DEFAULT_BUCKETS_COUNT);

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        Objects.requireNonNull(key);
        int bucketNumber = getBucketNumber(key);
        CustomEntry<K, V> node = bucketsHeads[bucketNumber];
        if (node.hasNext()) {
            do {
                node = node.next;
                if (node.key.equals(key)) {
                    return true;
                }
            } while (node.hasNext());
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < bucketsHeads.length; i++) {
            CustomEntry<K, V> node = bucketsHeads[i];
            if (node.hasNext()) {
                do {
                    node = node.next;
                    if (node.getValue() == null) {
                        if (value == null) return true;
                    } else if (node.getValue().equals(value)) {
                        return true;
                    }
                } while (node.hasNext());
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        Objects.requireNonNull(key);
        int bucketNumber = getBucketNumber(key);
        CustomEntry<K, V> node = bucketsHeads[bucketNumber];
        if (node.hasNext()) {
            do {
                node = node.next;
                if (node.key.equals(key)) {
                    return node.value;
                }
            } while (node.hasNext());
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        Objects.requireNonNull(key);

        int bucketNumber = getBucketNumber(key);
        CustomEntry<K, V> head = bucketsHeads[bucketNumber];

        if (!head.hasNext()) {
            head.next = new CustomEntry<>(key, value);
        } else {
            CustomEntry<K, V> node = head;

            do {
                node = node.next;
                if (node.getKey().equals(key)) {
                    V tmp = node.getValue();
                    node.setValue(value);
                    return tmp;
                }
            } while (node.hasNext());

            node.next = new CustomEntry<>(key, value);
        }
        size++;
        return null;
    }

    @Override
    public V remove(Object key) {
        Objects.requireNonNull(key);
        int bucketNumber = getBucketNumber(key);
        CustomEntry<K, V> head = bucketsHeads[bucketNumber];
        if (head.hasNext()) {
            CustomEntry<K, V> node = head;
            do {
                if (node.next.key.equals(key)) {
                    V tmp = node.next.value;
                    node.next = node.next.next;
                    size--;
                    return tmp;
                }
                node = node.next;
            } while (node.hasNext());
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        this.bucketsHeads = new CustomEntry[DEFAULT_BUCKETS_COUNT];
        this.size = 0;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    private int getBucketNumber(Object key) {
        return Math.abs(key.hashCode() % bucketsHeads.length);
    }

    private CustomEntry<K, V>[] produceBuckets(int bucketsCount) {
        CustomEntry<K, V>[] entries = new CustomEntry[bucketsCount];
        for (int i = 0; i < bucketsCount; i++) {
            entries[i] = new CustomEntry<>(null, null);
        }
        return entries;
    }

    class CustomEntry<K, V> implements Map.Entry<K, V> {
        private CustomEntry<K, V> next;

        private final K key;
        private V value;

        public CustomEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public boolean hasNext() {
            return next != null;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V tmp = this.value;
            this.value = value;
            return tmp;
        }
    }

    class KeySet<K> extends AbstractSet<K> {

        @Override
        public Iterator<K> iterator() {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }
    }

    class Values<V> implements Collection<V> {

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<V> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(V v) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends V> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }
    }
}
