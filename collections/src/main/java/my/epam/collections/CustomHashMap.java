package my.epam.collections;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Custom implementation of Map interface.
 * <p>
 * This Map uses inner hash table to store entries.
 * <p>
 * 'Null' key not allowed.
 * Support 'null' values.
 *
 * @param <K> key
 * @param <V> value
 */


public class CustomHashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_BUCKETS_COUNT = 16;
    private CustomEntry[] bucketsHeads;

    private int capacity;
    private final float loadFactor = 0.75F;
    private int size = 0;

    /**
     * Default constructor creates default empty Map.
     * <p>
     * Default capacity = 16.
     */
    public CustomHashMap() {
        this(DEFAULT_BUCKETS_COUNT);
    }

    /**
     * Creates empty Map with given initial capacity.
     *
     * @param capacity initial capacity
     */

    public CustomHashMap(int capacity) {
        this.capacity = capacity;
        bucketsHeads = produceBuckets(capacity);
    }

    /**
     * Get current size of the Map.
     *
     * @return current size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Check if current Map is empty.
     *
     * @return true if map is empty otherwise - false
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Check if current map contains given key.
     * <p>
     * Key should not be null.
     *
     * @param key key for check
     * @return true if key mapped, otherwise - false
     */
    @Override
    public boolean containsKey(Object key) {
        Objects.requireNonNull(key);
        int bucketNumber = getBucketNumber(key);
        CustomEntry node = bucketsHeads[bucketNumber];
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

    /**
     * Check if current map contains given value.
     * <p>
     * Iterate over hole map, and checks every value on
     * equals to given.
     * <p>
     * On first founded equals value returns true.
     * <p>
     * Accept null value.
     *
     * @param value value to be checked
     * @return true if found, otherwise - false
     */
    @Override
    public boolean containsValue(Object value) {
        for (CustomEntry bucketsHead : bucketsHeads) {
            CustomEntry node = bucketsHead;
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

    /**
     * Get value mapped to given key.
     * <p>
     * Given key should not be null.
     *
     * @param key key to be found
     * @return value if found or null
     */
    @Override
    public V get(Object key) {
        Objects.requireNonNull(key);
        int bucketNumber = getBucketNumber(key);
        CustomEntry node = bucketsHeads[bucketNumber];
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

    /**
     * Map given value to given key.
     * <p>
     * Key should not be null.
     * Value may be null.
     * <p>
     * If another value was already mapped to given key,
     * old value will be override to new one.
     * <p>
     * In this case, old value will be returned.
     *
     * @param key   key to be mapped
     * @param value value to be mapped
     * @return null, or old value if was mapped
     */
    @Override
    public V put(K key, V value) {
        Objects.requireNonNull(key);

        checkOverflow();
        int bucketNumber = getBucketNumber(key);
        CustomEntry head = bucketsHeads[bucketNumber];

        if (!head.hasNext()) {
            head.next = new CustomEntry(key, value);
        } else {
            CustomEntry node = head;

            do {
                node = node.next;
                if (node.getKey().equals(key)) {
                    V tmp = node.getValue();
                    node.setValue(value);
                    return tmp;
                }
            } while (node.hasNext());

            node.next = new CustomEntry(key, value);
        }
        incrementSize();
        return null;
    }

    /**
     * Unmap given key if was mapped.
     * <p>
     * Key should not be null.
     * Return null if no value was mapped, otherwise - value was mapped.
     * <p>
     * It's no way to check if null value was mapped, or no value.
     *
     * @param key key to be unmapped
     * @return value that was mapped, or null if no value was mapped.
     */
    @Override
    public V remove(Object key) {
        Objects.requireNonNull(key);
        int bucketNumber = getBucketNumber(key);
        CustomEntry head = bucketsHeads[bucketNumber];
        if (head.hasNext()) {
            CustomEntry node = head;
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

    /**
     * Put all values from given map to current.
     * <p>
     * Given map should not contains null keys.
     * <p>
     * If given map contains keys that already mapped in current map,
     * mapped value will be override by values from given map.
     *
     * @param m map with values
     * @throws NullPointerException if given map contains null keys
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Objects.requireNonNull(m);
        if (m.containsKey(null)) throw new NullPointerException("Map should not contain null keys.");
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Unmap all values from current map.
     */
    @Override
    public void clear() {
        this.bucketsHeads = produceBuckets(capacity);
        this.size = 0;
    }

    /**
     * Get set of keys of the current map.
     *
     * @return keySet
     */
    @Override
    public Set<K> keySet() {
        return new KeySet<>();
    }

    /**
     * Get values collection.
     *
     * @return values
     */
    @Override
    public Collection<V> values() {
        return new Values<>();
    }

    /**
     * Get set of entries.
     *
     * @return entries set
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        return new EntrySet<>();
    }

    class CustomEntry implements Map.Entry<K, V> {
        private CustomEntry next;

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CustomEntry that = (CustomEntry) o;

            if (key != null ? !key.equals(that.key) : that.key != null) return false;
            return value != null ? value.equals(that.value) : that.value == null;

        }

        @Override
        public int hashCode() {
            int result = key != null ? key.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }
    }

    class KeySet<KK> extends AbstractSet<KK> {

        @Override
        public boolean contains(Object o) {
            return CustomHashMap.this.containsKey(o);
        }

        @Override
        public boolean remove(Object o) {
            V value = CustomHashMap.this.remove(o);
            return value != null;
        }

        @Override
        public Iterator<KK> iterator() {
            return CustomHashMap.this.new KeySetIterator<>();
        }

        @Override
        public int size() {
            return CustomHashMap.this.size();
        }
    }

    class Values<VV> extends AbstractCollection<VV> {

        @Override
        public boolean contains(Object o) {
            return CustomHashMap.this.containsValue(o);
        }

        @Override
        public Iterator<VV> iterator() {
            return new ValuesIterator<>();
        }

        @Override
        public int size() {
            return CustomHashMap.this.size();
        }
    }

    class EntrySet<EK> extends AbstractSet<EK> {

        @Override
        public boolean contains(Object o) {
            Objects.requireNonNull(o);
            if (!(o instanceof Map.Entry)) throw new ClassCastException();
            Entry entry = (Entry) o;
            if (CustomHashMap.this.containsKey(entry.getKey())) {
                V value = CustomHashMap.this.get(entry.getKey());
                if (value == null) {
                    return entry.getValue() == null;
                } else {
                    return value.equals(entry.getValue());
                }
            }
            return false;
        }

        @Override
        public boolean remove(Object o) {
            Objects.requireNonNull(o);
            if (!(o instanceof Map.Entry)) throw new ClassCastException();
            Entry entry = (Entry) o;
            V value = CustomHashMap.this.remove(entry.getKey());
            return value != null;
        }

        @Override
        public Iterator<EK> iterator() {
            return CustomHashMap.this.new EntrySetIterator<>();
        }

        @Override
        public int size() {
            return CustomHashMap.this.size();
        }
    }

    class KeySetIterator<IK> extends EntrySetIterator<IK> {

        KeySetIterator() {
            current = getNextEntry(CustomHashMap.this.bucketsHeads[currentBucket]);
        }

        @Override
        public IK next() {
            lastReturned = this.current;
            this.current = getNextEntry(this.current);
            if (lastReturned == null) {
                throw new NoSuchElementException("No more elements for iteration.");
            }
            return (IK) lastReturned.getKey();
        }
    }

    class ValuesIterator<IV> extends EntrySetIterator<IV> {

        ValuesIterator() {
            current = getNextEntry(CustomHashMap.this.bucketsHeads[currentBucket]);
        }

        @Override
        public IV next() {
            lastReturned = this.current;
            this.current = getNextEntry(this.current);
            if (lastReturned == null) {
                throw new NoSuchElementException("No more elements for iteration.");
            }
            return (IV) lastReturned.getValue();
        }
    }

    class EntrySetIterator<IE> implements Iterator<IE> {
        CustomEntry current;
        CustomEntry lastReturned;
        int currentBucket = 0;

        EntrySetIterator() {
            current = getNextEntry(CustomHashMap.this.bucketsHeads[currentBucket]);
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public IE next() {
            lastReturned = this.current;
            this.current = getNextEntry(this.current);
            if (lastReturned == null) {
                throw new NoSuchElementException("No more elements for iteration.");
            }
            return (IE) lastReturned;
        }

        @Override
        public void remove() {
            CustomHashMap.this.remove(lastReturned.getKey());
        }

        CustomEntry getNextEntry(CustomEntry entry) {
            if (entry.hasNext()) {
                return entry.next;
            } else {
                currentBucket++;
                if (currentBucket == CustomHashMap.this.bucketsHeads.length) {
                    return null;
                } else {
                    return getNextEntry(CustomHashMap.this.bucketsHeads[currentBucket]);
                }
            }
        }
    }

    private int getBucketNumber(Object key) {
        return key.hashCode() & (bucketsHeads.length - 1);
    }

    private void incrementSize() {
        this.size += (this.size == Integer.MAX_VALUE) ? 0 : 1;
    }

    @SuppressWarnings(value = "unchecked")
    private CustomEntry[] produceBuckets(int bucketsCount) {
        CustomEntry[] entries = (CustomEntry[]) Array.newInstance(CustomEntry.class, bucketsCount);
        for (int i = 0; i < bucketsCount; i++) {
            entries[i] = new CustomEntry(null, null);
        }
        return entries;
    }

    private void checkOverflow() {
        if (size + 1 > capacity * loadFactor) {
            capacity = capacity * 2;
            rebuildMap(capacity);
        }
    }

    @SuppressWarnings(value = "unchecked")
    private void rebuildMap(int capacity) {
        Object[] entries = entrySet().toArray();
        bucketsHeads = produceBuckets(capacity);
        size = 0;
        for (Object obj : entries) {
            Entry<K, V> entry = (Entry<K, V>) obj;
            put(entry.getKey(), entry.getValue());
        }
    }
}
