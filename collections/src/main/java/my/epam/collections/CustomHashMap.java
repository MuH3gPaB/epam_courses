package my.epam.collections;

import java.util.*;

public class CustomHashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_BUCKETS_COUNT = 16;
    private CustomEntry<K, V>[] bucketsHeads = produceBuckets(DEFAULT_BUCKETS_COUNT);

    private int capacity = DEFAULT_BUCKETS_COUNT;
    private final float loadFactor = 0.75F;
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
        for (CustomEntry<K, V> bucketsHead : bucketsHeads) {
            CustomEntry<K, V> node = bucketsHead;
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

        checkOverflow();
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
        incrementSize();
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
        Objects.requireNonNull(m);
        if (m.containsKey(null)) throw new NullPointerException();
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        this.bucketsHeads = new CustomEntry[DEFAULT_BUCKETS_COUNT];
        this.size = 0;
    }

    @Override
    public Set<K> keySet() {
        return new KeySet<>();
    }

    @Override
    public Collection<V> values() {
        return new Values<>();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new EntrySet<>();
    }

    class CustomEntry<EK, EV> implements Map.Entry<EK, EV> {
        private CustomEntry<EK, EV> next;

        private final EK key;
        private EV value;

        public CustomEntry(EK key, EV value) {
            this.key = key;
            this.value = value;
        }

        public boolean hasNext() {
            return next != null;
        }

        @Override
        public EK getKey() {
            return key;
        }

        @Override
        public EV getValue() {
            return value;
        }

        @Override
        public EV setValue(EV value) {
            EV tmp = this.value;
            this.value = value;
            return tmp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CustomEntry<?, ?> that = (CustomEntry<?, ?>) o;

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
                throw new NoSuchElementException();
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
                throw new NoSuchElementException();
            }
            return (IV) lastReturned.getValue();
        }
    }

    class EntrySetIterator<IE> implements Iterator<IE> {
        CustomEntry<K, V> current;
        CustomEntry<K, V> lastReturned;
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
                throw new NoSuchElementException();
            }
            return (IE) lastReturned;
        }

        @Override
        public void remove() {
            CustomHashMap.this.remove(lastReturned.getKey());
        }

        CustomEntry<K, V> getNextEntry(CustomEntry<K, V> entry) {
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

    private CustomEntry<K, V>[] produceBuckets(int bucketsCount) {
        CustomEntry<K, V>[] entries = new CustomEntry[bucketsCount];
        for (int i = 0; i < bucketsCount; i++) {
            entries[i] = new CustomEntry<>(null, null);
        }
        return entries;
    }

    private void checkOverflow() {
        if (size + 1 > capacity * loadFactor) {
            capacity = capacity * 2;
            rebuildMap(capacity);
        }
    }

    private void rebuildMap(int capacity) {
        CustomEntry<K, V>[] entries = entrySet().toArray(new CustomEntry[0]);
        bucketsHeads = produceBuckets(capacity);
        size = 0;
        for (CustomEntry<K, V> entry : entries) {
            put(entry.getKey(), entry.getValue());
        }
    }
}
