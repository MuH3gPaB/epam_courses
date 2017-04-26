package my.epam.collections;


/**
 * This small sketch class was create during exam in Epam (java se course).
 *
 * @param <T>
 */
public class BinaryTree<T extends Comparable<T>> {

    private Node root;

    public void add(T element) {
        root = add1(root, element);
    }

    private Node add1(Node node, T element) {
        if (node == null) return new Node(element);

        if (compare(element, node.element) > 0) node.right = add1(node.right, element);
        else node.left = add1(node.left, element);

        return node;
    }

    private int compare(T o1, T o2) {
        return o1.compareTo(o2);
    }

    public Node getRoot() {
        return root;
    }

    class Node {
        Node left;
        Node right;
        T element;

        public Node(T element) {
            this.element = element;
        }

        public boolean hasLeft() {
            return left != null;
        }

        public boolean hasRight() {
            return right != null;
        }
    }


}
