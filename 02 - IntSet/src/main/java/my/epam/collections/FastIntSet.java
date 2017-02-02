package my.epam.collections;

/**
 * Created by M.Figurin on 01-Feb-17.
 */
public class FastIntSet implements MyIntSet {

    private long[] data;
    private int size;

    public FastIntSet() {
        this(new long[]{});
        size = 0;
    }

    private FastIntSet(long[] data) {
        this.data = new long[data.length];
        System.arraycopy(data, 0, this.data, 0, data.length);
    }

    @Override
    public void add(int value) {
        if (!contains(value)) {
            int reqDataLength = getReqDataLength(value);
            if (reqDataLength > data.length) {
                setDataLength(reqDataLength);
            }
            data[reqDataLength - 1] = data[reqDataLength - 1] | (1L << value % 64);
            size += 1;
        }
    }

    @Override
    public void remove(int value) {
        if (contains(value)) {
            int reqDataLength = getReqDataLength(value);
            if (reqDataLength > data.length) return;
            long mask = ~(1L << value % 64);
            data[reqDataLength - 1] = data[reqDataLength - 1] & mask;
            size -= 1;
        }
    }

    @Override
    public boolean contains(int value) {
        int reqDataLength = getReqDataLength(value);
        if (data.length < reqDataLength) return false;
        long mask = 1L << value % 64;
        return (data[reqDataLength - 1] & mask) == mask;
    }

    @Override
    public MyIntSet union(MyIntSet set) {
        FastIntSet result = new FastIntSet();
        if (set instanceof FastIntSet) {
            FastIntSet fset = (FastIntSet) set;
            long[] dataOne;
            long[] dataTwo;
            if (data.length > fset.data.length) {
                dataOne = data;
                dataTwo = fset.data;
            } else {
                dataOne = fset.data;
                dataTwo = data;
            }

            long[] newData = new long[dataOne.length];

            for (int i = 0; i < dataOne.length; i++) {
                if (i < dataTwo.length) {
                    newData[i] = dataOne[i] | dataTwo[i];
                } else {
                    newData[i] = dataOne[i];
                }
            }

            result = new FastIntSet(newData);

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
        for (int i = 0; i < data.length; i++) {
            int[] vals = getMarketBits(data[i]);
            for (int val : vals) {
                val += 64 * i;
                result[count] = val;
                count++;
            }
        }
        return result;
    }

    private int[] getMarketBits(long value) {
        if(value == 0L) return new int[0];
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
            if (data.length < fset.data.length) {
                dataOne = data;
                dataTwo = fset.data;
            } else {
                dataOne = fset.data;
                dataTwo = data;
            }

            long[] newData = new long[dataOne.length];

            for (int i = 0; i < dataOne.length; i++) {
                newData[i] = dataOne[i] & dataTwo[i];
            }

            result = new FastIntSet(newData);

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
            if (data.length > fset.data.length) {
                dataOne = data;
                dataTwo = fset.data;
            } else {
                dataOne = fset.data;
                dataTwo = data;
            }

            long[] newData = new long[dataOne.length];

            for (int i = 0; i < dataOne.length; i++) {
                if (i < dataTwo.length) {
                    newData[i] = dataOne[i] ^ dataTwo[i];
                } else {
                    newData[i] = dataOne[i];
                }
            }

            result = new FastIntSet(newData);

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
            if(this.data.length > fset.data.length) return false;

            for (int i = 0; i < this.data.length; i++) {
                if((this.data[i] & fset.data[i]) != this.data[i]) return false;
            }

        } else {
            for(int val : this.toArray()){
                if(!set.contains(val)) return false;
            }
        }
        return true;
    }

    private void setDataLength(int dataLength) {
        long[] newData = new long[dataLength];
        System.arraycopy(data, 0, newData, 0, data.length);
        data = newData;
    }

    private int getReqDataLength(int value) {
        return value / 64 + 1;
    }
}
