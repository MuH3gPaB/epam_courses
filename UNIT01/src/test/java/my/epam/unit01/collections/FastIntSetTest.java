package my.epam.unit01.collections;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

public class FastIntSetTest extends Assert {
    private static final Logger logger = Logger.getLogger(FastIntSetTest.class);

    @BeforeClass
    public static void logStart(){
        logger.info("FastIntSet test started...");
    }

    @Test
    public void add() throws Exception {
        logger.info("   Testing FastIntSet.add...");
        int valuesCount = 200;

        int[] values = new int[valuesCount];
        FastIntSet set = new FastIntSet();
        Random generator = new Random();
        for (int i = 0; i < valuesCount; i++) {
            int value = generator.nextInt();
            set.add(value);
            set.add(value);
            assertTrue("Fails on add value = " + value, set.contains(value));
            values[i] = value;
        }

        for (int value : values) {
            assertTrue("Value = " + value + " not found.", set.contains(value));
        }
    }

    @Test
    public void remove() throws Exception {
        logger.info("   Testing FastIntSet.remove...");
        FastIntSet set = new FastIntSet();

        int[] checkedValues = {0, 5, 65536, Integer.MAX_VALUE, 123123};

        for (int value : checkedValues) {
            set.add(value);
            assertTrue("Fails on add value = " + value, set.contains(value));
        }


        for (int i = 0; i < checkedValues.length; i++) {
            int value = checkedValues[i];
            set.remove(value);
            assertFalse("Fails on remove value = " + value, set.contains(value));
            try {
                set.remove(value);
            } catch (Exception ex) {
                fail("Removed absent exception.");
            }

            // Check if other elements was deleted
            for (int j = i + 1; j < checkedValues.length; j++) {
                assertTrue("Wrong value deleted = " + checkedValues[j] + ", expected = " + value, set.contains(checkedValues[j]));
            }
        }
    }

    @Test
    public void contains() throws Exception {
        logger.info("   Testing FastIntSet.contains...");
        int valuesCount = 200;

        int[] values = new int[valuesCount];
        FastIntSet set = new FastIntSet();
        Random generator = new Random();
        for (int i = 0; i < valuesCount; i++) {
            int value = generator.nextInt();
            set.add(value);
            assertTrue("Fails on add value = " + value, set.contains(value));
            values[i] = value;
        }

        for (int value : values) {
            set.remove(value);
            assertFalse("Wrong contains value = " + value, set.contains(value));
        }
    }

    @Test
    public void getSize() throws Exception {
        logger.info("   Testing FastIntSet.getSize...");
        FastIntSet set = new FastIntSet();
        assertEquals("Zero set size fails.", 0, set.getSize());

        Random generator = new Random();
        int valuesCount = generator.nextInt(1000);
        for (int i = 0; i < valuesCount; i++) {
            set.add(i);
        }
        assertEquals("Wrong set size.", valuesCount, set.getSize());
    }

    @Test
    public void toArray() throws Exception {
        logger.info("   Testing FastIntSet.toArray...");
        int valuesCount = 200;

        FastIntSet set = new FastIntSet();
        assertEquals("Empty set to array fails.", 0, set.toArray().length);

        set.add(1212);
        assertEquals("One element set to array fails.", 1, set.toArray().length);

        set.remove(1212);
        assertEquals("After remove empty set to array fails.", 0, set.toArray().length);

        Random generator = new Random();
        for (int i = 0; i < valuesCount; i++) {
            int value = generator.nextInt();
            set.add(value);
        }

        int[] array = set.toArray();

        for (int val : array) {
            assertTrue("Value = " + val + " not found.", set.contains(val));
        }
    }

    @Test
    public void union() throws Exception {
        logger.info("   Testing FastIntSet.union...");
        int valuesCount = 20;

        int[] valuesOne = new int[valuesCount];
        int[] valuesTwo = new int[valuesCount];
        FastIntSet setOne = new FastIntSet();
        FastIntSet setTwo = new FastIntSet();
        Random generator = new Random();
        for (int i = 0; i < valuesCount; i++) {
            int value = generator.nextInt();
            setOne.add(value);
            valuesOne[i] = value;

            value = generator.nextInt();
            setTwo.add(value);
            valuesTwo[i] = value;
        }

        FastIntSet resultSet = (FastIntSet) setOne.union(setTwo);
        for (int value : valuesOne) {
            assertTrue("Value = " + value + " from first set not found.", resultSet.contains(value));
        }

        for (int value : valuesTwo) {
            assertTrue("Value = " + value + " from second set not found.", resultSet.contains(value));
        }

        // Self union check
        FastIntSet selfUnionSet = (FastIntSet) setOne.union(setOne);
        for (int val : selfUnionSet.toArray()) {
            assertTrue("Self union unexpected value = " + val, setOne.contains(val));
        }

        for (int val : setOne.toArray()) {
            assertTrue("Self union cant find value = " + val, selfUnionSet.contains(val));
        }

        // Empty set union check
        FastIntSet emptySet = new FastIntSet();
        assertEquals("Empty union check fails.", setOne.getSize(), emptySet.union(setOne).getSize());

    }

    @Test
    public void intersection() throws Exception {
        logger.info("   Testing FastIntSet.intersection...");
        int valuesCount = 20;

        int[] valuesOne = new int[valuesCount];
        int[] valuesTwo = new int[valuesCount];
        FastIntSet setOne = new FastIntSet();
        FastIntSet setTwo = new FastIntSet();
        Random generator = new Random();
        for (int i = 0; i < valuesCount; i++) {
            int value = generator.nextInt();
            setOne.add(value);
            valuesOne[i] = value;

            value = generator.nextInt();
            setTwo.add(value);
            valuesTwo[i] = value;
        }

        FastIntSet resultSet = (FastIntSet) setOne.intersection(setTwo);

        for (int value : valuesOne) {
            assertEquals("Value = " + value + " not found in intersection result", setTwo.contains(value), resultSet.contains(value));
        }

        for (int value : valuesTwo) {
            assertEquals("Value = " + value + " not found in intersection result", setOne.contains(value), resultSet.contains(value));
        }

        // Self intersection check
        FastIntSet selfIntersectionSet = (FastIntSet) setOne.intersection(setOne);
        for (int val : selfIntersectionSet.toArray()) {
            assertTrue("Self intersection unexpected value = " + val, setOne.contains(val));
        }

        for (int val : setOne.toArray()) {
            assertTrue("Self intersection cant find value = " + val, selfIntersectionSet.contains(val));
        }

        // Empty set union check
        FastIntSet emptySet = new FastIntSet();
        assertEquals("Empty intersection check fails.", 0, emptySet.intersection(setOne).getSize());
    }

    @Test
    public void difference() throws Exception {
        logger.info("   Testing FastIntSet.difference...");
        int valuesCount = 20;

        int[] valuesOne = new int[valuesCount];
        int[] valuesTwo = new int[valuesCount];
        FastIntSet setOne = new FastIntSet();
        FastIntSet setTwo = new FastIntSet();
        Random generator = new Random();
        for (int i = 0; i < valuesCount; i++) {
            int value = generator.nextInt();
            setOne.add(value);
            valuesOne[i] = value;

            value = generator.nextInt();
            setTwo.add(value);
            valuesTwo[i] = value;
        }

        FastIntSet resultSet = (FastIntSet) setOne.difference(setTwo);

        for (int value : valuesOne) {
            assertNotEquals("Value = " + value + " not found in intersection result", setTwo.contains(value), resultSet.contains(value));
        }

        for (int value : valuesTwo) {
            assertNotEquals("Value = " + value + " not found in intersection result", setOne.contains(value), resultSet.contains(value));
        }

        FastIntSet resultEmptySet = (FastIntSet) setOne.difference(setOne);
        assertEquals("Self difference fails.", 0, resultEmptySet.getSize());

    }

    @Test
    public void isSubsetOf() throws Exception {
        logger.info("   Testing FastIntSet.isSubsetOf...");
        int valuesCount = 20;
        int subsetCount = 10;
        int missedValues[] = new int[subsetCount / 2];

        FastIntSet setOne = new FastIntSet();
        FastIntSet setTwo = new FastIntSet();
        Random generator = new Random();
        for (int i = 0; i < valuesCount; i++) {
            int value = generator.nextInt();
            setOne.add(value);
            if (i < subsetCount) {
                setTwo.add(value);
                if (i % 2 == 0) missedValues[i / 2] = value;
            }
        }

        assertTrue("Subset error.", setTwo.isSubsetOf(setOne));
        for (int val : missedValues) {
            setOne.remove(val);
        }

        assertFalse("Subset error.", setTwo.isSubsetOf(setOne));
        assertTrue("Self subset fails.", setTwo.isSubsetOf(setTwo));

        // simple test for negatives
        {
            FastIntSet s1 = new FastIntSet();
            FastIntSet s2 = new FastIntSet();
            int[] v1 = {-1, 0};
            int[] v2 = {-2, 0, 1, 2};
            for (int v : v1) s1.add(v);
            for (int v : v2) s2.add(v);

            assertFalse("Negatives test fails", s1.isSubsetOf(s2));
        }

        // Set with removed endings test
        {
            FastIntSet s1 = new FastIntSet();
            FastIntSet s2 = new FastIntSet();
            int[] v1 = {0, 1, Integer.MAX_VALUE};
            int[] v2 = {0, 1, 2};
            for (int v : v1) s1.add(v);
            for (int v : v2) s2.add(v);

            assertFalse("Big subset data test fails", s1.isSubsetOf(s2));
            s1.remove(Integer.MAX_VALUE);
            assertTrue("Removed endings test fails", s1.isSubsetOf(s2));

        }

    }
}
