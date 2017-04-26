package my.epam.collections;

import org.junit.Assert;
import org.junit.Test;

public class BinaryTreeTest extends Assert {

    @Test
    public void testThatAddWorksProperlyWithSmallTree() throws Exception {
        BinaryTree<Integer> intTree = new BinaryTree<>();

        intTree.add(10);
        intTree.add(1);
        intTree.add(0);
        intTree.add(-20);
        intTree.add(123);
        intTree.add(Integer.MAX_VALUE);

        BinaryTree<Integer>.Node root = intTree.getRoot();

        assertEquals(new Integer(10), root.element);
        assertEquals(new Integer(1), root.left.element);
        assertEquals(new Integer(123), root.right.element);
    }


}