package my.epam.collections;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;
import java.util.*;

@FixMethodOrder
@RunWith(Parameterized.class)
public class CustomListTest extends Assert {

    private CustomList<Integer> list;

    @Parameterized.Parameters
    public static Object[] params() {
        return new Object[]{new CustomArrayList<Integer>()};//,
//                new CustomLinkedList<Integer>()};
    }

    public CustomListTest(CustomList<Integer> list) {
        this.list = list;
    }

    @Before
    public void setUp() {
        list.clear();
    }

    // SIZE ----------------------------------
    @Test
    public void sizeOfEmptyMapShouldBeZero() throws Exception {
        assertEquals(0, list.size());
    }

    @Test
    public void sizeShouldGrowOnAddingNewElement() throws Exception {
        list.add(1);
        int oldSize = list.size();

        list.add(2);
        int newSize = list.size();

        assertEquals(1, newSize - oldSize);
    }

    @Test
    public void sizeShouldReduceOnRemovingElement() throws Exception {
        list.add(10);
        int oldSize = list.size();
        list.remove(new Integer(10));
        int newSize = list.size();

        assertEquals(1, oldSize - newSize);
    }

    @Test
    @Ignore
    public void sizeShouldReturnIntMaxValueIfMapSizeMoreThenIntMaxValue() throws Exception {
        Field sizeField = list.getClass().getDeclaredField("size");
        sizeField.setAccessible(true);
        sizeField.set(list, Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, list.size());
        list.add(10);
        assertEquals(Integer.MAX_VALUE, list.size());
        sizeField.set(list, 0);
    }

    // IS_EMPTY ----------------------------------
    @Test
    public void isEmptyShouldReturnTrueOnEmptyList() throws Exception {
        assertTrue(list.isEmpty());
    }

    @Test
    public void isEmptyShouldReturnFalseOnNotEmptyList() throws Exception {
        list.add(10);
        assertFalse(list.isEmpty());
    }

    // CONTAINS ----------------------------------
    @Test
    public void containsShouldReturnFalseOnEmptyList() throws Exception {
        assertFalse(list.contains(10));
    }

    @Test
    public void containsShouldReturnTrueIfElementWasAdded() throws Exception {
        list.add(110);
        list.add(123);
        list.add(0);

        assertTrue(list.contains(110));
    }

    @Test
    public void containsShouldReturnFalseOnNullIfNullWasNotAdded() throws Exception {
        assertFalse(list.contains(null));
    }

    @Test
    public void containsShouldReturnTrueOnNullIfNullWasAdded() throws Exception {
        list.add(null);
        assertTrue(list.contains(null));
    }

    // ITERATOR ----------------------------------
    @Test
    public void iteratorShouldSupportForEachIteration() throws Exception {
        Integer[] expected = new Integer[]{10, 20, 30, 40};
        for(Integer value : expected){
            list.add(value);
        }

        int i = 0;

        for (Integer value : list) {
            assertEquals(expected[i++], value);
        }
    }

    @Test
    public void iteratorShouldSupportRemoveValues() throws Exception {
        Integer[] expected = new Integer[]{10, 20, 30, 40};
        for(Integer value : expected){
            list.add(value);
        }


        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }

        assertTrue(list.isEmpty());
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorNextShouldThrowNSEOnEmptyList() throws Exception {
        list.iterator().next();
    }

    // ADD ----------------------------------
    @Test
    public void addShouldAddValidElement() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);

        assertTrue(list.contains(10));
        assertTrue(list.contains(20));
        assertTrue(list.contains(30));
    }

    @Test
    public void addShouldReturnTrueAfterAdding() throws Exception {
        assertTrue(list.add(10));
    }

    @Test
    public void addShouldSupportAdding100ElementsOneByOne() throws Exception {
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        assertEquals(100, list.size());
    }

    // REMOVE ----------------------------------
    @Test
    public void removeShouldRemoveElementFromList() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);

        list.remove(new Integer(20));

        assertFalse(list.contains(20));
    }

    @Test
    public void removeShouldRemoveOnlyFirstOccuranceOfGivenElementFromList() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(20);

        list.remove(new Integer(20));

        assertTrue(list.contains(20));
        assertEquals(3, list.size());
    }

    @Test
    public void removeShouldReturnTrueIfElementWasRemoved() throws Exception {
        list.add(10);
        assertTrue(list.remove(new Integer(10)));
    }

    @Test
    public void removeShouldReturnFalseIfElementWasNotFound() throws Exception {
        assertFalse(list.remove(new Integer(10)));
    }

    @Test
    public void removeShouldSupportRemovingNullFromList() throws Exception {
        list.add(null);
        assertTrue(list.remove(null));
        assertFalse(list.contains(null));
    }

    @Test
    public void removeShouldReturnFalseOnNullIfItWasNotAdded() throws Exception {
        assertFalse(list.remove(null));
    }

    @Test
    public void removeShouldPlaceNextElementOnPositionOfRemoved() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);

        list.remove(new Integer(20));
        assertEquals(new Integer(30), list.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void afterRemovingLastElementGettingOnItIndexShouldThrowIOOB() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);

        list.remove(30);
        list.get(2);
    }

    // CLEAR ----------------------------------
    @Test
    public void clearShouldMakeListEmpty() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);

        list.clear();

        assertTrue(list.isEmpty());
    }

    @Test
    public void clearShouldBeOkOnEmptyList() throws Exception {
        list.clear();
        assertTrue(list.isEmpty());
    }

    // GET_BY_INDEX ----------------------------------
    @Test
    public void getShouldReturnElementByIndex() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(new Integer(20), list.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getShouldThrowIOOBOnEmptyList() throws Exception {
        list.get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getShouldThrowIOOBOnIndexGreaterThenListSize() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);
        list.get(4);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getShouldThrowIOOBOnIndexEqualsToListSize() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);
        list.get(3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getShouldThrowIOOBOnNegativeIndex() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);
        list.get(-4);
    }

    // SET_BY_INDEX ----------------------------------
    @Test
    public void setShouldReplaceElementOnGivenPosition() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);

        list.set(1, 40);
        assertEquals(new Integer(40), list.get(1));
    }

    @Test
    public void setShouldReturnReplacedElement() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(new Integer(20), list.set(1, 40));
    }

    @Test
    public void setShouldBeOkOnSettingNull() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);

        list.set(1, null);
        assertNull(list.get(1));
    }

    @Test
    public void setShouldBeOkOnReturningNull() throws Exception {
        list.add(10);
        list.add(null);
        list.add(30);

        assertNull(list.set(1, 20));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setShouldThrowIOOBOnEmptyList() throws Exception {
        list.set(0, 10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setShouldThrowIOOBOnIndexGreaterThenListSize() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);
        list.set(4, 10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setShouldThrowIOOBOnIndexEqualsToListSize() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);
        list.set(3, 10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setShouldThrowIOOBOnNegativeIndex() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);
        list.set(-4, 2);
    }

    // ADD_TO_INDEX ----------------------------------
    @Test
    public void addToIndexShouldInsertElementToGivenIndex() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);

        list.add(1, 40);

        assertEquals(new Integer(40), list.get(1));
    }

    @Test
    public void addToIndexShouldShiftNextElementsToRightPosition() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);

        list.add(1, 40);

        assertEquals(new Integer(30), list.get(3));
    }

    @Test
    public void addToIndexShouldBeOkOnAddingNull() throws Exception {
        list.add(10);
        list.add(0, null);

        assertNull(list.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addToIndexShouldThrowIOOBOnEmptyList() throws Exception {
        list.add(1, 10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addToIndexShouldThrowIOOBOnIndexGreaterThenListSize() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(4, 10);
    }

    @Test
    public void addToIndexShouldBeOkOnIndexEqualsToListSize() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(3, 10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addToIndexShouldThrowIOOBOnNegativeIndex() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(-4, 2);
    }

    // REMOVE_BY_INDEX ----------------------------------
    @Test
    public void removeByIndexShouldRemoveElementByGivenIndex() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);

        list.remove(1);

        assertFalse(list.contains(20));
    }

    @Test
    public void removeByIndexShouldShiftNextElementsToLeftPosition() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);

        list.remove(1);

        assertEquals(new Integer(30), list.get(1));
    }

    @Test
    public void removeByIndexShouldBeOkOnRemovingNull() throws Exception {
        list.add(10);
        list.add(null);

        list.remove(1);
        assertFalse(list.contains(null));
    }

    @Test
    public void removeByIndexShouldReturnRemovedElement() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(new Integer(20), list.remove(1));
    }

    @Test
    public void removeByIndexShouldBeOkOnReturningNull() throws Exception {
        list.add(10);
        list.add(null);
        list.add(30);

        assertNull(list.remove(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeByIndexShouldThrowIOOBOnEmptyList() throws Exception {
        list.remove(1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeByIndexShouldThrowIOOBOnIndexGreaterThenListSize() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);
        list.remove(4);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeByIndexShouldThrowIOOBOnIndexEqualsToListSize() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);
        list.remove(3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeByIndexShouldThrowIOOBOnNegativeIndex() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);
        list.remove(-4);
    }

    // INDEX_OF ----------------------------------
    @Test
    public void indexOfShouldReturnIndexOfFirstOccuranceOfGivenElement() throws Exception {
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(20);

        assertEquals(1, list.indexOf(20));
    }

    @Test
    public void indexOfShouldReturnNegative1IfElementNotFound() throws Exception {
        list.add(10);
        list.add(20);

        assertEquals(-1, list.indexOf(30));
    }

    @Test
    public void indexOfShouldFoundIndexOfNullElementIfItWasAdded(){
        list.add(10);
        list.add(20);
        list.add(null);
        list.add(30);

        assertEquals(2, list.indexOf(null));
    }

    @Test
    public void indexOfShouldReturnNegative1OnNullIfItWasNotAdded(){
        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(-1, list.indexOf(null));
    }
}
