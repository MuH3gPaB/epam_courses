package my.epam.collections;

/**
 * Created by M.Figurin on 01-Feb-17.
 */
public interface MyIntSet {
    void add(int value);
    void remove(int value);
    boolean contains(int value);
    MyIntSet union(MyIntSet set);
    MyIntSet intersection(MyIntSet set);
    MyIntSet difference(MyIntSet set);
    boolean isSubsetOf(MyIntSet set);
    int[] toArray();
    int getSize();
}
