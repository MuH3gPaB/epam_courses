package my.epam.collections;

import java.util.*;

public class CustomTreeMap<K, V> implements SortedMap<K, V> {
    private CustomNodeEntry<K, V> root;
    private Comparator<K> comparator;
    private int size = 0;

    public CustomTreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    public CustomTreeMap() {
        this(null);
    }

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
        Objects.requireNonNull(key);
        Comparable<K> keyComp = (Comparable<K>) key;
        return findNodeByKey(root, keyComp) != null;
    }

    private CustomNodeEntry<K, V> findNodeByKey(CustomNodeEntry<K, V> node, Comparable<K> keyComp) {
        if (node == null) return null;
        if (keyComp.compareTo(node.getKey()) > 0) return node.right;
        else if (keyComp.compareTo(node.getKey()) < 0) return node.left;
        else return node;
    }

    @Override
    public boolean containsValue(Object value) {
        return findNodeByValue(root, value) != null;
    }

    private CustomNodeEntry<K, V> findNodeByValue(CustomNodeEntry<K, V> node, Object value) {
        if (node == null) return null;
        if (node.getValue() == null) {
            if (value == null) {
                return node;
            }
        } else {
            if (node.getValue().equals(value)) {
                return node;
            }
        }

        CustomNodeEntry<K, V> left = findNodeByValue(node.left, value);
        CustomNodeEntry<K, V> right = findNodeByValue(node.right, value);

        if (left == null) return right;
        else return left;
    }

    @Override
    public V get(Object key) {
        Objects.requireNonNull(key);
        Comparable<K> compKey = (Comparable<K>) key;
        CustomNodeEntry<K, V> nodeByKey = findNodeByKey(root, compKey);
        return nodeByKey == null ? null : nodeByKey.getValue();
    }

    @Override
    public V put(K key, V value) {
        Objects.requireNonNull(key);
        CustomNodeEntry<K, V> oldValContainer = new CustomNodeEntry<>(null, null);
        if (comparator == null) {
            Comparable<K> compKey = (Comparable<K>) key;
            root = putToNode(root, compKey, value, oldValContainer);
        }
        return oldValContainer.getValue();
    }

    private CustomNodeEntry<K, V> putToNode(CustomNodeEntry<K, V> node, Comparable<K> key, V value, CustomNodeEntry<K, V> oldVal) {
        if (node != null) {
            if (key.compareTo(node.getKey()) > 0) node.right = putToNode(node.right, key, value, oldVal);
            else if (key.compareTo(node.getKey()) < 0) node.left = putToNode(node.right, key, value, oldVal);
            else {
                oldVal.setValue(node.getValue());
                node.setValue(value);
            }
            return node;
        } else {
            incrementSize();
            return new CustomNodeEntry<K, V>((K) key, value);
        }
    }

    @Override
    public V remove(Object key) {
        Objects.requireNonNull(key);
        Comparable<K> keyComp = (Comparable<K>) key;
        root = removeNode(root, keyComp);
        return null;
    }

    private CustomNodeEntry<K, V> removeNode(CustomNodeEntry<K, V> node, Comparable<K> keyComp) {
        if (node == null) return null;
        if (keyComp.compareTo(node.getKey()) > 0) node.right = removeNode(node.right, keyComp);
        else if (keyComp.compareTo(node.getKey()) < 0) node.left = removeNode(node.left, keyComp);
        else {
            size--;
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            CustomNodeEntry<K, V> tmp = node;
            node = findMin(tmp);
            node.right = removeMin(tmp);
            node.left = tmp.left;
        }
        return node;
    }

    private CustomNodeEntry<K, V> findMin(CustomNodeEntry<K, V> node) {
        if (node.left == null) return node;
        else return findMin(node.left);
    }


    private CustomNodeEntry<K, V> removeMin(CustomNodeEntry<K, V> node) {
        if (node.left == null) return node.right;
        node.left = removeMin(node.left);
        return node;
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

    class CustomNodeEntry<EK, EV> implements Map.Entry<EK, EV> {
        private CustomNodeEntry<EK, EV> right;
        private CustomNodeEntry<EK, EV> left;

        private final EK key;
        private EV value;

        public CustomNodeEntry(EK key, EV value) {
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

            CustomNodeEntry<?, ?> that = (CustomNodeEntry<?, ?>) o;

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
        CustomNodeEntry<K, V> current;
        CustomNodeEntry<K, V> lastReturned;
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

        CustomNodeEntry<K, V> getNextEntry(CustomNodeEntry<K, V> entry) {
            return null;
        }
    }

    private void incrementSize() {
        size += size == Integer.MAX_VALUE ? 0 : 1;
    }
}
