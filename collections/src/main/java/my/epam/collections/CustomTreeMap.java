package my.epam.collections;

import java.util.*;

public class CustomTreeMap<K, V> implements SortedMap<K, V> {
    private CustomTreeEntry<K, V> root;
    private int size = 0;


    @Override
    public Comparator<? super K> comparator() {
        return null;
    }

    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return null;
    }

    @Override
    public SortedMap<K, V> headMap(K toKey) {
        return null;
    }

    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        return null;
    }

    @Override
    public K firstKey() {
        return null;
    }

    @Override
    public K lastKey() {
        return null;
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
    public boolean containsKey(Object key) {
        return root.getKey().equals(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return root.getValue().equals(value);
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        if(root == null) root = new CustomTreeEntry<>(key, value);
        incrementSize();
        return null;
    }

    @Override
    public V remove(Object key) {
        size--;
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

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

    class CustomTreeEntry<EK, EV> implements Map.Entry<EK, EV> {
        private CustomTreeEntry<EK, EV> right;
        private CustomTreeEntry<EK, EV> left;

        private final EK key;
        private EV value;

        public CustomTreeEntry(EK key, EV value) {
            this.key = key;
            this.value = value;
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

            CustomTreeEntry<?, ?> that = (CustomTreeEntry<?, ?>) o;

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
            return CustomTreeMap.this.containsKey(o);
        }

        @Override
        public boolean remove(Object o) {
            V value = CustomTreeMap.this.remove(o);
            return value != null;
        }

        @Override
        public Iterator<KK> iterator() {
            return CustomTreeMap.this.new KeySetIterator<>();
        }

        @Override
        public int size() {
            return CustomTreeMap.this.size();
        }
    }

    class Values<VV> extends AbstractCollection<VV> {

        @Override
        public boolean contains(Object o) {
            return CustomTreeMap.this.containsValue(o);
        }

        @Override
        public Iterator<VV> iterator() {
            return new ValuesIterator<>();
        }

        @Override
        public int size() {
            return CustomTreeMap.this.size();
        }
    }

    class EntrySet<EK> extends AbstractSet<EK> {

        @Override
        public boolean contains(Object o) {
            Objects.requireNonNull(o);
            if (!(o instanceof Map.Entry)) throw new ClassCastException();
            Entry entry = (Entry) o;
            if (CustomTreeMap.this.containsKey(entry.getKey())) {
                V value = CustomTreeMap.this.get(entry.getKey());
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
            V value = CustomTreeMap.this.remove(entry.getKey());
            return value != null;
        }

        @Override
        public Iterator<EK> iterator() {
            return CustomTreeMap.this.new EntrySetIterator<>();
        }

        @Override
        public int size() {
            return CustomTreeMap.this.size();
        }
    }

    class KeySetIterator<IK> extends EntrySetIterator<IK> {

        KeySetIterator() {

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
        CustomTreeEntry<K, V> current;
        CustomTreeEntry<K, V> lastReturned;
        int currentBucket = 0;

        EntrySetIterator() {
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
            CustomTreeMap.this.remove(lastReturned.getKey());
        }

        CustomTreeEntry<K, V> getNextEntry(CustomTreeEntry<K, V> entry) {
            return null;
        }
    }

    private void incrementSize() {
        size += size == Integer.MAX_VALUE ? 0 : 1;
    }
}
