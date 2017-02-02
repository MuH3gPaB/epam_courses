package my.epam.collections;

/**
 * Created by M.Figurin on 01-Feb-17.
 */
public class FastIntSet implements MyIntSet {

    private long[] posData;
    private long[] negData;
    private int size;

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

    @Override
    public void add(int value) {
        if (!contains(value)) {
            long[] workData = (value >= 0) ? posData : negData;
            int reqDataLength = getReqDataLength(Math.abs(value));
            if (reqDataLength > workData.length) {
                workData = setDataLength((value >= 0), reqDataLength);
            }
            workData[reqDataLength - 1] = workData[reqDataLength - 1] | (1L << value % 64);
            size += 1;
        }
    }

    @Override
    public void remove(int value) {
        if (contains(value)) {
            long[] workData = (value >= 0) ? posData : negData;
            int reqDataLength = getReqDataLength(Math.abs(value));
            long mask = ~(1L << value % 64);
            workData[reqDataLength - 1] = workData[reqDataLength - 1] & mask;
            size -= 1;
        }
    }

    @Override
    public boolean contains(int value) {
        long[] workData = (value >= 0) ? posData : negData;
        int reqDataLength = getReqDataLength(Math.abs(value));
        if (workData.length < reqDataLength) return false;
        long mask = 1L << value % 64;
        return (workData[reqDataLength - 1] & mask) == mask;
    }

    @Override
    public MyIntSet union(MyIntSet set) {
        FastIntSet result = new FastIntSet();
        if (set instanceof FastIntSet) {
            FastIntSet fset = (FastIntSet) set;
            long[] dataOne;
            long[] dataTwo;
            if (posData.length > fset.posData.length) {
                dataOne = posData;
                dataTwo = fset.posData;
            } else {
                dataOne = fset.posData;
                dataTwo = posData;
            }

            long[] newData = new long[dataOne.length];

            for (int i = 0; i < dataOne.length; i++) {
                if (i < dataTwo.length) {
                    newData[i] = dataOne[i] | dataTwo[i];
                } else {
                    newData[i] = dataOne[i];
                }
            }

            result = new FastIntSet(newData, null);

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
                val *= -1;
                result[count] = val;
                count++;
            }
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

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public MyIntSet intersection(MyIntSet set) {
        FastIntSet result = new FastIntSet();
        if (set instanceof FastIntSet) {
            FastIntSet fset = (FastIntSet) set;
            long[] dataOne;
            long[] dataTwo;
            if (posData.length < fset.posData.length) {
                dataOne = posData;
                dataTwo = fset.posData;
            } else {
                dataOne = fset.posData;
                dataTwo = posData;
            }

            long[] newData = new long[dataOne.length];

            for (int i = 0; i < dataOne.length; i++) {
                newData[i] = dataOne[i] & dataTwo[i];
            }

            result = new FastIntSet(newData, null);

        } else {
            for (int val : set.toArray()) {
                if (this.contains(val)) {
                    result.add(val);
                }
            }
        }
        return result;
    }

    @Override
    public MyIntSet difference(MyIntSet set) {
        FastIntSet result = new FastIntSet();
        if (set instanceof FastIntSet) {
            FastIntSet fset = (FastIntSet) set;
            long[] dataOne;
            long[] dataTwo;
            if (posData.length > fset.posData.length) {
                dataOne = posData;
                dataTwo = fset.posData;
            } else {
                dataOne = fset.posData;
                dataTwo = posData;
            }

            long[] newData = new long[dataOne.length];

            for (int i = 0; i < dataOne.length; i++) {
                if (i < dataTwo.length) {
                    newData[i] = dataOne[i] ^ dataTwo[i];
                } else {
                    newData[i] = dataOne[i];
                }
            }

            result = new FastIntSet(newData, null);

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

    @Override
    public boolean isSubsetOf(MyIntSet set) {
        if (set instanceof FastIntSet) {
            FastIntSet fset = (FastIntSet) set;
            if (this.posData.length > fset.posData.length) return false;

            for (int i = 0; i < this.posData.length; i++) {
                if ((this.posData[i] & fset.posData[i]) != this.posData[i]) return false;
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
}
