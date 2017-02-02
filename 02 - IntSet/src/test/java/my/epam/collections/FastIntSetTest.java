package my.epam.collections;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

/**
 * Created by M.Figurin on 01-Feb-17.
 */
public class FastIntSetTest extends Assert {


    @Test
    public void add() throws Exception {
        int valuesCount = 200;
//        int valueLimit = Integer.MAX_VALUE;

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
        FastIntSet set = new FastIntSet();
        Random generator = new Random();
        int valuesCount = generator.nextInt(1000);
        for (int i = 0; i < valuesCount; i++) {
            set.add(i);
        }
        assertEquals("Wrong set size.", valuesCount, set.getSize());
    }

    @Test
    public void toArray() throws Exception {
        int valuesCount = 200;
        int valueLimit = Integer.MAX_VALUE;

        int[] values = new int[valuesCount];
        FastIntSet set = new FastIntSet();

        Random generator = new Random();
        for (int i = 0; i < valuesCount; i++) {
            int value = generator.nextInt(valueLimit);
            set.add(value);
            values[i] = value;
        }

        int[] array = set.toArray();

        for (int val : array) {
            assertTrue("Value = " + val + " not found.", set.contains(val));
        }
    }

    @Test
    public void union() throws Exception {
        int valuesCount = 200;
        int valueLimit = Integer.MAX_VALUE;

        int[] valuesOne = new int[valuesCount];
        int[] valuesTwo = new int[valuesCount];
        FastIntSet setOne = new FastIntSet();
        FastIntSet setTwo = new FastIntSet();
        Random generator = new Random();
        for (int i = 0; i < valuesCount; i++) {
            int value = generator.nextInt(valueLimit);
            setOne.add(value);
            valuesOne[i] = value;

            value = generator.nextInt(valueLimit);
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
    }

    @Test
    public void intersection() throws Exception {
        int valuesCount = 200;
        int valueLimit = Integer.MAX_VALUE;

        int[] valuesOne = new int[valuesCount];
        int[] valuesTwo = new int[valuesCount];
        FastIntSet setOne = new FastIntSet();
        FastIntSet setTwo = new FastIntSet();
        Random generator = new Random();
        for (int i = 0; i < valuesCount; i++) {
            int value = generator.nextInt(valueLimit);
            setOne.add(value);
            valuesOne[i] = value;

            value = generator.nextInt(valueLimit);
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
    }

    @Test
    public void difference() throws Exception {
        int valuesCount = 200;
        int valueLimit = Integer.MAX_VALUE;

        int[] valuesOne = new int[valuesCount];
        int[] valuesTwo = new int[valuesCount];
        FastIntSet setOne = new FastIntSet();
        FastIntSet setTwo = new FastIntSet();
        Random generator = new Random();
        for (int i = 0; i < valuesCount; i++) {
            int value = generator.nextInt(valueLimit);
            setOne.add(value);
            valuesOne[i] = value;

            value = generator.nextInt(valueLimit);
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

    }

    @Test
    public void isSubsetOf() throws Exception {
        int valuesCount = 20;
        int subsetCount = 10;
        int missedValues[] = new int[subsetCount/2];

        FastIntSet setOne = new FastIntSet();
        FastIntSet setTwo = new FastIntSet();
        Random generator = new Random();
        for (int i = 0; i < valuesCount; i++) {
            int value = generator.nextInt();
            setOne.add(value);
            if (i < subsetCount) {
                setTwo.add(value);
                if(i%2 == 0) missedValues[i/2] = value;
            }
        }

        assertTrue("Subset error.", setTwo.isSubsetOf(setOne));
        for(int val : missedValues){
            setOne.remove(val);
        }

        assertFalse("Subset error. Missed value = ", setTwo.isSubsetOf(setOne));
    }

}
