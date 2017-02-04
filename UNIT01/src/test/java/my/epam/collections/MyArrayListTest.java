package my.epam.collections;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

public class MyArrayListTest extends Assert {
    private static final Logger logger = Logger.getLogger(MyArrayListTest.class);

    static {
        BasicConfigurator.configure();
    }

    @BeforeClass
    public static void logStart(){
        logger.info("MyArrayList test started...");
    }

    @Test
    public void sort() throws Exception {
        logger.info("   Testing MyArrayListTest.sort...");
        final int[] ints = {12, 0, -13, 666, 2, 56, 56, 56, 120, -1, 1, 0, Integer.MAX_VALUE, Integer.MIN_VALUE};
        final int[] expected = Arrays.copyOf(ints, ints.length);
        Arrays.sort(expected);

        final MyArrayList list = new MyArrayList(ints);

        list.sort();

        for (int i = 0; i < expected.length; i++) {
            assertEquals("i = " + i, expected[i], list.get(i));
        }
    }

    @Test
    public void binarySearch() {
        logger.info("   Testing MyArrayListTest.binarySearch...");

        int[] ints = {Integer.MIN_VALUE, -13, -1, 0, 1, 2, 12, 56, 56, 56, 120, 666, Integer.MAX_VALUE};
        MyArrayList arrayList = new MyArrayList(ints);
        arrayList.sort();

        assertEquals(5, arrayList.binarySearch(2));
        assertEquals(12, arrayList.binarySearch(Integer.MAX_VALUE));
        assertEquals(0, arrayList.binarySearch(Integer.MIN_VALUE));


        ints = new int[]{-13, -1, 0, 1, 2, 12, 56, 56, 56, 120, 666};
        arrayList = new MyArrayList(ints);

        assertEquals(-12, arrayList.binarySearch(Integer.MAX_VALUE));
        assertEquals(-1, arrayList.binarySearch(Integer.MIN_VALUE));


        assertEquals(-7, arrayList.binarySearch(28));
        assertEquals(-6, arrayList.binarySearch(10));
    }

    @Test
    public void binarySearchCycl() {
        logger.info("   Testing MyArrayListTest.binarySearchCycl...");
        int[] ints = {Integer.MIN_VALUE, -13, -1, 0, 1, 2, 12, 56, 56, 56, 120, 666, Integer.MAX_VALUE};
        MyArrayList arrayList = new MyArrayList(ints);
        arrayList.sort();

        assertEquals(5, arrayList.binarySearchCycl(2));
        assertEquals(12, arrayList.binarySearchCycl(Integer.MAX_VALUE));
        assertEquals(0, arrayList.binarySearchCycl(Integer.MIN_VALUE));


        ints = new int[]{-13, -1, 0, 1, 2, 12, 56, 56, 56, 120, 666};
        arrayList = new MyArrayList(ints);

        assertEquals(-12, arrayList.binarySearchCycl(Integer.MAX_VALUE));
        assertEquals(-1, arrayList.binarySearchCycl(Integer.MIN_VALUE));


        assertEquals(-7, arrayList.binarySearch(28));
        assertEquals(-6, arrayList.binarySearch(10));
    }

}