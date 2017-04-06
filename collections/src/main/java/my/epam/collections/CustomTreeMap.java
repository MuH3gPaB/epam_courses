package my.epam.collections;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is implementation of SortedMap in educational purpose .
 * <p>
 * It stores key-value pairs into balanced red-black tree.
 * <p>
 * In case of educational purpose of this class, not complete
 * functionality of general TreeMap is supported.
 * <p>
 * For example, map returned by subMap method have unmodifiable,
 * and not backed to original map entrySet, keySet and values.
 * <p>
 * Some other features of original TreeMap may not be supported.
 * <p>
 * Null values is supported.
 * Null keys not allowed.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class CustomTreeMap<K, V> implements SortedMap<K, V> {
    private CustomNodeEntry<K, V> root;
    private Comparator<K> comparator;
    private int size = 0;

    /**
     * Create ney map, ordered by given Comparator{@literal <}Key{@literal >}.
     *
     * @param comparator comparator
     */
    public CustomTreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    /**
     * Create new map, ordered by Comparable.compareTo.
     * <p>
     * Keys should implement Comparable{@literal <}Key{@literal >}
     */
    public CustomTreeMap() {
        this(null);
    }

    /**
     * Get comparator of this map.
     *
     * @return comparator, or null is using Comparable{@literal <}Key{@literal >}
     */
    @Override
    public Comparator<? super K> comparator() {
        return comparator;
    }

    /**
     * Get subMap of this map by given bounds.
     * <p>
     * SubMap have unmodifiable and not backed to original map
     * entrySet, keySet, values.
     * <p>
     * SubMap is modifiable and backed to original map.
     * <p>
     * Given bounds(fromKey, toKey) should be in range of keys, represented
     * in this map.
     * It's means that:
     * fromKey {@literal >}= map.firstKey()
     * toKey {@literal <}= map.lastKey()
     * fromKey {@literal <} toKey
     * <p>
     * If some of this conditions is not correct IllegalArgumentException will be thrown.
     * <p>
     * If fromKey.equals(toKey) the empty map will be returned.
     *
     * @param fromKey start inclusive key
     * @param toKey   end exclusive key
     * @return subMap for given bounds
     * @throws IllegalArgumentException if any wrong arguments
     * @throws ClassCastException       if given keys could not be compared in this map
     * @see SubMap
     */
    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return new SubMap(fromKey, toKey);
    }

    /**
     * Get submap of (map.firstKey, toKey)
     *
     * @param toKey end exclusive key
     * @return subMap bounded by firstKey and toKey
     * @see CustomTreeMap.SubMap
     */
    @Override
    public SortedMap<K, V> headMap(K toKey) {
        return new SubMap(firstKey(), toKey);
    }

    /**
     * Get submap of (fromKey, map.lastKey)
     *
     * @param fromKey start inclusive key
     * @return subMap bounded by fromKey and lastKey
     * @see CustomTreeMap.SubMap
     */
    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        return new SubMap(fromKey, lastKey());
    }

    /**
     * Get first key of this map.
     *
     * @return first key
     * @throws NoSuchElementException if map is empty
     */
    @Override
    public K firstKey() {
        if (isEmpty()) throw new NoSuchElementException();
        return findMin(root).getKey();
    }

    /**
     * Get last key of this map.
     *
     * @return last key
     * @throws NoSuchElementException if map is empty
     */
    @Override
    public K lastKey() {
        if (isEmpty()) throw new NoSuchElementException();
        return findMax(root).getKey();
    }

    /**
     * Git size of this map.
     *
     * @return size, or Integer.MAX_VALUE if more elements was putted
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Check empty.
     *
     * @return true if map is empty, otherwise - false
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Check if this map contains given key.
     * <p>
     * Key should not be null.
     *
     * @param key key to be checked
     * @return true if map contains given key, otherwise - false
     * @throws NullPointerException if give key is null
     */
    @Override
    public boolean containsKey(Object key) {
        Objects.requireNonNull(key);
        return findNodeByKey(root, (K) key) != null;
    }

    /**
     * Check if this map contains given value.
     * <p>
     * Value may be null.
     *
     * @param value value to be checked
     * @return true if map contains value, otherwise - false
     */
    @Override
    public boolean containsValue(Object value) {
        return findNodeByValue(root, value) != null;
    }

    /**
     * Get mapped value by given key.
     * <p>
     * Key should not be null.
     *
     * @param key key to be find
     * @return value, mapped to given key, or null if absent
     * @throws NullPointerException if key is null
     */
    @Override
    public V get(Object key) {
        Objects.requireNonNull(key);
        CustomNodeEntry<K, V> nodeByKey = findNodeByKey(root, (K) key);
        return nodeByKey == null ? null : nodeByKey.getValue();
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
        CustomNodeEntry<K, V> oldValContainer = new CustomNodeEntry<>(null, null);
        root = putToNode(root, key, value, oldValContainer);
        root.parent = null;
        return oldValContainer.getValue();
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
        CustomNodeEntry<K, V> oldValContainer = new CustomNodeEntry<>(null, null);
        root = removeNode(root, (K) key, oldValContainer);
        if (root != null) root.parent = null;
        return oldValContainer.getValue();
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
        if (m.containsKey(null)) throw new NullPointerException();
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Unmap all values from current map.
     */
    @Override
    public void clear() {
        size = 0;
        root = null;
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

    /**
     * Get balance rate of inner tree of this map.
     * <p>
     * Balance rate shows proportion of count of left and right children of tree nodes.
     * <p>
     * Children of upper level (closure to root node) are most weight in balance rate.
     * <p>
     * Balance rate of balanced tree is close to 1.
     * If balance rate is close to 0, it means that left children count is greater then right.
     * If balance rate is more than 1, it means that right children count is greater then left.
     * <p>
     * Balance rate for empty tree is 1.
     *
     * @return balance rate.
     */
    public double getBalanceRate() {
        double left = 1;
        double right = 1;
        for (Entry<K, V> entry : entrySet()) {
            CustomNodeEntry nodeEntry = (CustomNodeEntry) entry;
            if (nodeEntry.left != null) left += getNodeWeight(nodeEntry);
            if (nodeEntry.right != null) right += getNodeWeight(nodeEntry);
        }
        return left / right;
    }

    class CustomNodeEntry<EK, EV> implements Map.Entry<EK, EV> {
        private CustomNodeEntry<EK, EV> right;
        private CustomNodeEntry<EK, EV> left;
        private CustomNodeEntry<EK, EV> parent;
        private boolean color = true;

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

        private EntrySetIterator() {
            current = findMin(root);
            lastReturned = null;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public IE next() {
            lastReturned = this.current;
            if (lastReturned == null) {
                throw new NoSuchElementException();
            }
            this.current = getNextEntry(this.current);
            return (IE) lastReturned;
        }

        @Override
        public void remove() {
            CustomTreeMap.this.remove(lastReturned.getKey());
        }

        CustomNodeEntry<K, V> getNextEntry(CustomNodeEntry<K, V> node) {
            if (node == null) return null;
            else {
                if (node.right != null) {
                    return findMin(node.right);
                } else {
                    CustomNodeEntry<K, V> parent = node.parent;
                    CustomNodeEntry<K, V> child = node;
                    while (parent != null && parent.left != child) {
                        child = parent;
                        parent = child.parent;
                    }
                    return parent;
                }
            }
        }
    }

    class SubMap extends CustomTreeMap<K, V> {
        final K fromKey;
        final K toKey;

        SubMap(K fromKey, K toKey) {
            Objects.requireNonNull(fromKey);
            Objects.requireNonNull(toKey);

            if (CustomTreeMap.this.isEmpty()) throw new IllegalArgumentException();

            if (CustomTreeMap.this.compare(fromKey, toKey) > 0) throw new IllegalArgumentException();
            if (CustomTreeMap.this.compare(fromKey, CustomTreeMap.this.firstKey()) < 0)
                throw new IllegalArgumentException();
            if (CustomTreeMap.this.compare(toKey, CustomTreeMap.this.lastKey()) > 0)
                throw new IllegalArgumentException();


            this.fromKey = fromKey;
            this.toKey = toKey;
        }

        @Override
        public int size() {
            int currentSize = 0;
            for (Entry<K, V> entry : CustomTreeMap.this.entrySet()) {
                if (validKey(entry.getKey())) currentSize++;
            }
            return currentSize;
        }

        @Override
        public V get(Object key) {
            if (validKey((K) key)) return CustomTreeMap.this.get(key);
            else return null;
        }

        @Override
        public V remove(Object key) {
            if (validKey((K) key)) return CustomTreeMap.this.remove(key);
            else return null;
        }

        @Override
        public V put(K key, V value) {
            if (validKey(key))
                return CustomTreeMap.this.put(key, value);
            else throw new IllegalArgumentException();
        }

        @Override
        public SortedMap<K, V> subMap(K fromKey, K toKey) {
            throw new UnsupportedOperationException();
        }

        @Override
        public SortedMap<K, V> headMap(K toKey) {
            throw new UnsupportedOperationException();
        }

        @Override
        public SortedMap<K, V> tailMap(K fromKey) {
            throw new UnsupportedOperationException();
        }

        @Override
        public K firstKey() {
            for (K key : CustomTreeMap.this.keySet()) {
                if (validKey(key)) {
                    return key;
                }
            }
            throw new NoSuchElementException();
        }

        @Override
        public K lastKey() {
            K lastValidKey = null;
            for (K key : CustomTreeMap.this.keySet()) {
                if (validKey(key)) {
                    lastValidKey = key;
                }
            }
            if (lastValidKey == null) throw new NoSuchElementException();
            else return lastValidKey;
        }

        @Override
        public boolean containsKey(Object key) {
            return validKey((K) key) && CustomTreeMap.this.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            Iterator<Entry<K, V>> iterator = CustomTreeMap.this.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();
                if (validKey(entry.getKey())) {
                    if (entry.getValue() == null) {
                        if (value == null) return true;
                    } else {
                        if (entry.getValue().equals(value)) return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void clear() {
            Iterator<K> iterator = CustomTreeMap.this.keySet().iterator();
            while (iterator.hasNext()) {
                K key = iterator.next();
                if (validKey(key)) iterator.remove();
            }
        }

        @Override
        public Set<K> keySet() {
            TreeSet<K> set = new TreeSet<>(CustomTreeMap.this::compare);

            CustomTreeMap.this.entrySet().stream()
                    .filter((e) -> validKey(e.getKey()))
                    .map(Entry::getKey)
                    .forEach(set::add);

            return Collections.unmodifiableSet(set);
        }

        @Override
        public Collection<V> values() {
            List<V> values = entrySet()
                    .stream()
                    .map(Entry::getValue)
                    .collect(Collectors.toList());

            return Collections.unmodifiableList(values);
        }

        @Override
        public Set<Entry<K, V>> entrySet() {
            TreeSet<Entry<K, V>> set = new TreeSet<>((o1, o2) -> CustomTreeMap.this.compare(o1.getKey(), o2.getKey()));

            CustomTreeMap.this.entrySet().stream()
                    .filter((e) -> validKey(e.getKey()))
                    .forEach(set::add);

            return Collections.unmodifiableSet(set);
        }

        @Override
        public Comparator<? super K> comparator() {
            return CustomTreeMap.this.comparator();
        }

        @Override
        public boolean isEmpty() {
            return size() == 0;
        }

        private boolean validKey(K key) {
            return compare(key, fromKey) >= 0 && compare(key, toKey) < 0;
        }
    }

    private void incrementSize() {
        size += size == Integer.MAX_VALUE ? 0 : 1;
    }

    private CustomNodeEntry<K, V> removeNode(CustomNodeEntry<K, V> node, K key, CustomNodeEntry<K, V> oldVal) {
        if (node == null) return null;
        if (compare(key, node.getKey()) > 0) node.right = removeNode(node.right, key, oldVal);
        else if (compare(key, node.getKey()) < 0) node.left = removeNode(node.left, key, oldVal);
        else {
            size--;
            oldVal.setValue(node.getValue());
            boolean leftExist = node.left != null;
            boolean rightExist = node.right != null;

            if (leftExist) {
                if (rightExist) {
                    CustomNodeEntry<K, V> tmp = findMin(node.right);
                    tmp.right = removeMin(node.right);
                    if (tmp.right != null) tmp.right.parent = tmp;
                    tmp.left = node.left;
                    if (tmp.left != null) tmp.left.parent = tmp;
                    tmp.parent = node.parent;
                    node = tmp;
                } else {
                    node.left.parent = node.parent;
                    return node.left;
                }
            } else {
                if (rightExist) {
                    node.right.parent = node.parent;
                    return node.right;
                } else {
                    return null;
                }
            }
        }

        if (!isRed(node.left) && isRed(node.right)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) node = flipColors(node);

        return node;
    }

    private CustomNodeEntry<K, V> findMin(CustomNodeEntry<K, V> node) {
        if (node.left == null) return node;
        else return findMin(node.left);
    }

    private CustomNodeEntry<K, V> findMax(CustomNodeEntry<K, V> node) {
        if (node.right == null) return node;
        else return findMax(node.right);
    }

    private CustomNodeEntry<K, V> removeMin(CustomNodeEntry<K, V> node) {
        if (node.left == null) {
            return node.right;
        } else {
            node.left = removeMin(node.left);
        }
        return node;
    }

    private CustomNodeEntry<K, V> findNodeByKey(CustomNodeEntry<K, V> node, K key) {
        if (node == null) return null;
        if (compare(key, node.getKey()) > 0) return findNodeByKey(node.right, key);
        else if (compare(key, node.getKey()) < 0) return findNodeByKey(node.left, key);
        else return node;
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

    private CustomNodeEntry<K, V> putToNode(CustomNodeEntry<K, V> node, K key, V value, CustomNodeEntry<K, V> oldVal) {
        if (node != null) {
            if (compare(key, node.getKey()) > 0) {
                CustomNodeEntry<K, V> newNode = putToNode(node.right, key, value, oldVal);
                node.right = newNode;
                newNode.parent = node;
            } else if (compare(key, node.getKey()) < 0) {
                CustomNodeEntry<K, V> newNode = putToNode(node.left, key, value, oldVal);
                node.left = newNode;
                newNode.parent = node;
            } else {
                oldVal.setValue(node.getValue());
                node.setValue(value);
            }

            if (!isRed(node.left) && isRed(node.right)) node = rotateLeft(node);
            if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
            if (isRed(node.left) && isRed(node.right)) node = flipColors(node);


            return node;
        } else {
            incrementSize();
            return new CustomNodeEntry<>(key, value);
        }
    }

    private int compare(K keyOne, K keyTwo) {
        if (comparator() == null) {
            // Cast both for checking.
            Comparable<K> keyOneComp = (Comparable<K>) keyOne;
            Comparable<K> keyTwoComp = (Comparable<K>) keyTwo;
            return keyOneComp.compareTo(keyTwo);
        } else {
            return comparator.compare(keyOne, keyTwo);
        }
    }

    private double getNodeWeight(CustomNodeEntry node) {
        int level = getNodeLevel(node);
        if (level < 9) {
            return Math.pow(10, 9 - level);
        } else {
            return 1;
        }
    }

    private int getNodeLevel(CustomNodeEntry node) {
        int level = 1;
        CustomNodeEntry parent = node.parent;
        while (parent != null) {
            level++;
            parent = parent.parent;
        }
        return level;
    }

    private boolean isRed(CustomNodeEntry node) {
        return node != null && node.color;
    }

    private CustomNodeEntry rotateLeft(CustomNodeEntry node) {
        if (node.right.color) {
            CustomNodeEntry tmp = node.right;

            tmp.parent = node.parent;
            if (tmp.left != null) tmp.left.parent = node;
            node.parent = tmp;

            node.right = tmp.left;
            tmp.left = node;
            tmp.color = node.color;
            node.color = true;
            return tmp;
        } else {
            return node;
        }
    }

    private CustomNodeEntry rotateRight(CustomNodeEntry node) {
        if (node.left.color) {
            CustomNodeEntry tmp = node.left;

            tmp.parent = node.parent;
            if (tmp.right != null) tmp.right.parent = node;
            node.parent = tmp;

            node.left = tmp.right;
            tmp.right = node;
            tmp.color = node.color;
            node.color = true;
            return tmp;
        } else {
            return node;
        }
    }

    private CustomNodeEntry flipColors(CustomNodeEntry node) {
        if (node.left.color && node.right.color) {
            node.color = true;
            node.left.color = false;
            node.right.color = false;
        }
        return node;
    }
}
