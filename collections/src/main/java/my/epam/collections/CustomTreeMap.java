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
        return comparator;
    }

    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return new SubMap(fromKey, toKey);
    }

    @Override
    public SortedMap<K, V> headMap(K toKey) {
        return new SubMap(firstKey(), toKey);
    }

    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        return new SubMap(fromKey, lastKey());
    }

    @Override
    public K firstKey() {
        if (isEmpty()) throw new NoSuchElementException();
        return findMin(root).getKey();
    }

    @Override
    public K lastKey() {
        if (isEmpty()) throw new NoSuchElementException();
        return findMax(root).getKey();
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
        return findNodeByKey(root, (K) key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return findNodeByValue(root, value) != null;
    }

    @Override
    public V get(Object key) {
        Objects.requireNonNull(key);
        CustomNodeEntry<K, V> nodeByKey = findNodeByKey(root, (K) key);
        return nodeByKey == null ? null : nodeByKey.getValue();
    }

    @Override
    public V put(K key, V value) {
        Objects.requireNonNull(key);
        CustomNodeEntry<K, V> oldValContainer = new CustomNodeEntry<>(null, null);
        root = putToNode(root, key, value, oldValContainer);
        return oldValContainer.getValue();
    }

    @Override
    public V remove(Object key) {
        Objects.requireNonNull(key);
        CustomNodeEntry<K, V> oldValContainer = new CustomNodeEntry<>(null, null);
        root = removeNode(root, (K) key, oldValContainer);
        return oldValContainer.getValue();
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
        size = 0;
        root = null;
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

    public double getBalanceRate() {
        double left = 0;
        double right = 0;
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
                if (compare(entry.getKey(), fromKey) >= 0 && compare(entry.getKey(), toKey) < 0) currentSize++;
            }
            return currentSize;
        }

        @Override
        public V get(Object key) {
            if (compare((K) key, fromKey) >= 0 && compare((K) key, toKey) < 0) return CustomTreeMap.this.get(key);
            else return null;
        }

        @Override
        public V remove(Object key) {
            if (compare((K) key, fromKey) >= 0 && compare((K) key, toKey) < 0) return CustomTreeMap.this.remove(key);
            else return null;
        }

        @Override
        public V put(K key, V value) {
            if (compare((K) key, fromKey) >= 0 && compare((K) key, toKey) < 0)
                return CustomTreeMap.this.put(key, value);
            else throw new IllegalArgumentException();
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
            boolean leftExist = !(node.left == null);
            boolean rightExist = !(node.right == null);

            if (leftExist) {
                if (rightExist) {
                    CustomNodeEntry<K, V> tmp = node;
                    node = findMin(tmp);
                    node.right = removeMin(tmp);
                    node.left = tmp.left;
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
        if (node.left == null) return node.right;
        node.left = removeMin(node.left);
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
        if (comparator == null) {
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
