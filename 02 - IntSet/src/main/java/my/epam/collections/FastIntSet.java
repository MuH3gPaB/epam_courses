package my.epam.collections;

/**
 *
 * Experimental implementation of integer set collection.
 *
 * Stores values as bits indexes of long value.
 * Use two arrays of longs for store positive and negative integer values.
 *
 * <p>Requiered heap memory for one instance = 8 bytes * (Max positive +  |Min negative|)</p>
 *
 */
public class FastIntSet implements MyIntSet {


    private long[] posData;
    private long[] negData;
    private int size;

    /**
     * Create empty FastIntSet
     */
    public FastIntSet() {
        this(new long[]{}, new long[]{});
        size = 0;
    }

    private FastIntSet(long[] posData, long[] negData) {
        this.posData = new long[posData.length];
        System.arraycopy(posData, 0, this.posData, 0, posData.length);
        this.negData = new long[negData.length];
        System.arraycopy(negData, 0, this.negData, 0, negData.length);

    }

    /**
     * Add <code>int</code> value to set.
     *
     * Do nothing if current set already contains value.
     *
     * @param value value to be added
     */
    @Override
    public void add(int value) {
        if (!contains(value)) {
            long[] workData = (value >= 0) ? posData : negData;
            int reqDataLength = getReqDataLength(Math.abs(value));
            if (reqDataLength > workData.length) {
                workData = setDataLength((value >= 0), reqDataLength);
            }
            workData[reqDataLength - 1] = workData[reqDataLength - 1] | (1L << Math.abs(value) % 64);
            size += 1;
        }
    }

    /**
     * Remove <code>int</code> value from set.
     *
     * Do nothing if current set does not contains value.
     *
     * @param value
     */
    @Override
    public void remove(int value) {
        if (contains(value)) {
            long[] workData = (value >= 0) ? posData : negData;
            int reqDataLength = getReqDataLength(Math.abs(value));
            long mask = ~(1L << Math.abs(value) % 64);
            workData[reqDataLength - 1] = workData[reqDataLength - 1] & mask;
            size -= 1;
        }
    }

    /**
     * Check if current set contains <code>int</code> value
     *
     * @param value
     *
     * @return True if set contains value, otherwise - false.
     */
    @Override
    public boolean contains(int value) {
        long[] workData = (value >= 0) ? posData : negData;
        int reqDataLength = getReqDataLength(Math.abs(value));
        if (workData.length < reqDataLength) return false;
        long mask = 1L << Math.abs(value) % 64;
        return (workData[reqDataLength - 1] & mask) == mask;
    }

    /**
     * Union of two sets.
     *
     * Works faster with FastIntSet as argument.
     *
     * @param set May be any implementation of MyIntSet
     *
     * @return Union set as FastIntSet
     */

    @Override
    public MyIntSet union(MyIntSet set) {
        FastIntSet result = new FastIntSet();
        if (set instanceof FastIntSet) {
            FastIntSet fset = (FastIntSet) set;

            long[] newPosData = dataUnion(this.posData, fset.posData);
            long[] newNegData = dataUnion(this.negData, fset.negData);

            result = new FastIntSet(newPosData, newNegData);

        } else {
            for (int val : set.toArray()) {
                result.add(val);
            }
            for (int val : this.toArray()) {
                result.add(val);
            }
        }
        return result;
    }

    /**
     * Convert set to array of <code>int</code> values.
     *
     * @return
     */
    @Override
    public int[] toArray() {
        int[] result = new int[size];
        int count = 0;
        for (int i = 0; i < posData.length; i++) {
            int[] vals = getMarketBits(posData[i]);
            for (int val : vals) {
                val += 64 * i;
                result[count] = val;
                count++;
            }
        }

        for (int i = 0; i < negData.length; i++) {
            int[] vals = getMarketBits(negData[i]);
            for (int val : vals) {
                val += 64 * i;
                val = -val;
                result[count] = val;
                count++;
            }
        }
        return result;
    }

    /**
     * Get number of elements, stored in set.
     *
     * @return
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Intersection of two sets.
     *
     * Works faster with FastIntSet as argument.
     *
     * @param set May be any implementation of MyIntSet
     *
     * @return Intersection set as FastIntSet
     */
    @Override
    public MyIntSet intersection(MyIntSet set) {
        FastIntSet result = new FastIntSet();
        if (set instanceof FastIntSet) {
            FastIntSet fset = (FastIntSet) set;

            long[] newPosData = dataIntersection(this.posData, fset.posData);
            long[] newNegData = dataIntersection(this.negData, fset.negData);

            result = new FastIntSet(newPosData, newNegData);
        } else {
            for (int val : set.toArray()) {
                if (this.contains(val)) {
                    result.add(val);
                }
            }
        }
        return result;
    }

    /**
     * Difference of two sets.
     *
     * Works faster with FastIntSet as argument.
     *
     * @param set May be any implementation of MyIntSet
     *
     * @return Difference set as FastIntSet
     */
    @Override
    public MyIntSet difference(MyIntSet set) {
        FastIntSet result = new FastIntSet();
        if (set instanceof FastIntSet) {
            FastIntSet fset = (FastIntSet) set;

            long[] newPosData = dataDifference(this.posData, fset.posData);
            long[] newNegData = dataDifference(this.negData, fset.negData);

            result = new FastIntSet(newPosData, newNegData);

        } else {
            for (int val : set.toArray()) {
                if (!this.contains(val)) {
                    result.add(val);
                }
            }
            for (int val : this.toArray()) {
                if (!set.contains(val)) {
                    result.add(val);
                }
            }
        }
        return result;
    }

    /**
     * Check if current set is a subset of another.
     *
     * Works faster with FastIntSet as argument.
     *
     * @param set May be any implementation of MyIntSet
     *
     * @return True if current set is subset of argument, otherwise - false
     */
    @Override
    public boolean isSubsetOf(MyIntSet set) {
        if (set instanceof FastIntSet) {
            FastIntSet fset = (FastIntSet) set;
            for (int i = 0; i < this.posData.length; i++) {
                if (i < fset.posData.length) {
                    if ((this.posData[i] & fset.posData[i]) != this.posData[i]) return false;
                } else {
                    if (this.posData[i] != 0) return false;
                }
            }

            for (int i = 0; i < this.negData.length; i++) {
                if (i < fset.negData.length) {
                    if ((this.negData[i] & fset.negData[i]) != this.negData[i]) return false;
                } else {
                    if(this.negData[i] != 0) return false;
                }

            }

        } else {
            for (int val : this.toArray()) {
                if (!set.contains(val)) return false;
            }
        }
        return true;
    }

    private long[] setDataLength(boolean positive, int dataLength) {
        long[] newData = new long[dataLength];
        long[] workData = (positive) ? posData : negData;
        System.arraycopy(workData, 0, newData, 0, workData.length);
        if (positive) {
            posData = newData;
        } else {
            negData = newData;
        }
        return newData;
    }

    private int getReqDataLength(int value) {
        return value / 64 + 1;
    }

    private long[] dataUnion(long[] data1, long[] data2){
        int length = Math.max(data1.length, data2.length);
        long[] result = new long[length];
        for (int i = 0; i < length; i++) {
            long val1 = 0;
            long val2 = 0;
            if(i < data1.length) val1 = data1[i];
            if(i < data2.length) val2 = data2[i];
            result[i] = val1 | val2;
        }
        return result;
    }

    private long[] dataIntersection(long[] data1, long[] data2){
        int length = Math.min(data1.length, data2.length);
        long[] result = new long[length];
        for (int i = 0; i < length; i++) {
            long val1 = 0;
            long val2 = 0;
            if(i < data1.length) val1 = data1[i];
            if(i < data2.length) val2 = data2[i];
            result[i] = val1 & val2;
        }
        return result;
    }

    private long[] dataDifference(long[] data1, long[] data2){
        int length = Math.max(data1.length, data2.length);
        long[] result = new long[length];
        for (int i = 0; i < length; i++) {
            long val1 = 0;
            long val2 = 0;
            if(i < data1.length) val1 = data1[i];
            if(i < data2.length) val2 = data2[i];
            result[i] = val1 ^ val2;
        }
        return result;
    }

    private int[] getMarketBits(long value) {
        if (value == 0L) return new int[0];
        int[] res = new int[64];
        int counter = 0;
        for (int i = 0; i < 64; i++) {
            if ((value & 1) == 1) {
                res[counter] = i;
                counter++;
            }
            value = value >> 1;
        }

        int[] result = new int[counter];
        System.arraycopy(res, 0, result, 0, counter);
        return result;
    }
}
