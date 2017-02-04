package my.epam.collections;


/**
 * Simple interface for integer set implementations
 */

public interface MyIntSet {
    /**
     * Add new value.
     *
     * @param value Value to be added
     */
    void add(int value);

    /**
     * Remove value.
     *
     * @param value Value to be removed
     */
    void remove(int value);

    /**
     * Check if set contains value.
     *
     * @param value Value to be checked
     * @return True if set contains value, otherwise - false.
     */
    boolean contains(int value);

    /**
     * Union of two sets.
     *
     * @param set Another set
     * @return Union set
     */
    MyIntSet union(MyIntSet set);

    /**
     * Intersection of two sets.
     *
     * @param set Another set
     * @return Intersection set
     */
    MyIntSet intersection(MyIntSet set);

    /**
     * Difference of two sets.
     *
     * @param set Another set
     * @return Difference set
     */
    MyIntSet difference(MyIntSet set);

    /**
     * Check if current set is subset of other set.
     *
     * @param set Another set
     * @return True if current set is subset of argument, otherwise - false
     */
    boolean isSubsetOf(MyIntSet set);

    /**
     * Convert set to array of values.
     *
     * @return Array of values
     */
    int[] toArray();

    /**
     * Get number of elements, stored in current set.
     *
     * @return Nuber of elements in set
     */
    int getSize();
}
