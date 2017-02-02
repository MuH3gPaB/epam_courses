package my.epam.collections;

/**
 * Created by M.Figurin on 01-Feb-17.
 */
public class SmallIntSet implements MyIntSet{
    @Override
    public void add(int value) {

    }

    @Override
    public void remove(int value) {

    }

    @Override
    public boolean contains(int value) {
        return false;
    }

    @Override
    public MyIntSet union(MyIntSet set) {
        return null;
    }

    @Override
    public MyIntSet intersection(MyIntSet set) {
        return null;
    }

    @Override
    public MyIntSet difference(MyIntSet set) {
        return null;
    }

    @Override
    public boolean isSubsetOf(MyIntSet set) {
        return false;
    }

    @Override
    public int[] toArray() {
        return new int[0];
    }

    @Override
    public int getSize() {
        return 0;
    }
}
