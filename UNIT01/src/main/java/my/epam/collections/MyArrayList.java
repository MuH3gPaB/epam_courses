package my.epam.collections;

/**
 * Experimental arrayList implementation.
 *
 */

import java.util.Arrays;
import java.util.NoSuchElementException;

public class MyArrayList {
    private int[] data;
    private int size;

    public MyArrayList(int[] data) {
        this.data = Arrays.copyOf(data, data.length);
        size = data.length;
    }

    public MyArrayList() {
        data = new int[10];
        size = 0;
    }

    public void add(int value) {
        ensureCapasity(size + 1);
        data[size] = value;
        size += 1;
    }

    public int get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }

        return data[i];
    }

    public int getSize() {
        return size;
    }

    public int maxValueInefficient() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return maxValueRec(data, 0, size);
    }

    private int maxValueRec(int[] data, int startInclusive, int endExlusive) {
        final int length = endExlusive - startInclusive;

        if (length == 1) {
            return data[startInclusive];
        } else if (length == 0) {
            return Integer.MIN_VALUE;
        }

        final int mid = startInclusive + length / 2;
        return Math.max(
                maxValueRec(data, startInclusive, mid),
                maxValueRec(data, mid, endExlusive)
        );
    }

    public void sort() {
        mergeSort(data, 0, getSize(), new int[getSize()]);
    }


    /**
     * Expects collection to be sorted.
     *
     * @param value value to find in collection
     * @return index of the value or -indexToInsert - 1
     */
    public int binarySearch(int value) {
        return binarySearcher(data, 0, data.length, value);
    }

    private static int binarySearcher(int[] data, int startInclusive, int endExclsive, int value) {
        int mid = (endExclsive - startInclusive) / 2 + startInclusive;

        if (startInclusive == endExclsive) return -startInclusive - 1;
        if (data[mid] == value) return mid;

        if (value > data[mid]) {
            startInclusive = mid + 1;
        } else {
            endExclsive = mid;
        }

        return binarySearcher(data, startInclusive, endExclsive, value);
    }

    public int binarySearchCycl(int value) {
        int startInclusive = 0;
        int endExclsive = data.length;

        for (int i = 0; i < 32; i++) {
            int mid = (endExclsive - startInclusive) / 2 + startInclusive;

            if (startInclusive == endExclsive) return -startInclusive - 1;
            if (data[mid] == value) return mid;

            if (value > data[mid]) {
                startInclusive = mid + 1;
            } else {
                endExclsive = mid;
            }
        }
        throw new NoSuchElementException();
    }

    private static void mergeSort(int[] data, int startInclusive, int endExclusive, int[] free) {
        final int length = endExclusive - startInclusive;
        if (length <= 1) {
            return;
        }

        final int mid = startInclusive + length / 2;

        mergeSort(data, startInclusive, mid, free);
        mergeSort(data, mid, endExclusive, free);

        merger(data, startInclusive, mid, endExclusive, free);
    }

    private static void merger(int[] data, int startInclusive, int mid, int endExclusive, int[] free) {
        System.arraycopy(data, startInclusive, free, startInclusive, endExclusive - startInclusive);

        int i = startInclusive;
        int j = mid;
        for (int k = startInclusive; k < endExclusive; k++) {
            if (i >= mid) data[k] = free[j++];
            else if (j >= endExclusive) data[k] = free[i++];
            else if (free[i] < free[j]) data[k] = free[i++];
            else data[k] = free[j++];
        }
    }

    private void ensureCapasity(int requiredCapacity) {
        if (requiredCapacity <= getCapacity()) {
            return;
        }
        final int newCapasity = Math.max(requiredCapacity, (getCapacity() * 3) / 2 + 1);
        data = Arrays.copyOf(data, newCapasity);
    }

    private int getCapacity() {
        return data.length;
    }
}
