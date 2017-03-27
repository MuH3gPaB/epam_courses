package my.epam.collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNull.notNullValue;

@FixMethodOrder
public class CustomHashMapTest extends Assert {
    private Map<Integer, String> m;

    @Before
    public void createMapInstance() {
        m = new CustomHashMap<>();
    }

    @Test
    public void testThatWeCanCreate() {
        assertThat(m, is(notNullValue()));
    }

    @Test
    public void testThatNewMapIsEmpty() {
        assertThat(m.isEmpty(), is(true));
    }

    @Test
    public void testThatOnNewMapContainKeyMethodReturnFalseForAnyObject() {
        assertThat(m.containsKey(1), is(false));
    }

    @Test
    public void testThatWeCanPutKeyValuePairAndCanCheckIt() {
        m.put(3, "abc");
        assertThat(m.containsKey(3), is(true));
    }

    @Test(expected = NullPointerException.class)
    public void testThatWeCanNotPutNullKey() {
        m.put(null, "abc");
    }

    @Test
    public void testThatWeCanPutNullValue() {
        m.put(12, null);
        assertThat(m.get(12), is(nullValue()));
    }

    @Test
    public void testThatMapCanPutPairWithKeyThatAlreadyPresented() {
        m.put(10, "ten");
        m.put(10, "ten");
        assertThat(m.containsKey(10), is(true));
    }

    @Test
    public void testThatMapCanContainsKeysWithSameHashCode() {
        class SameObj {
            private int val;

            SameObj(int val) {
                this.val = val;
            }

            public int hashCode() {
                return 1234;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                SameObj sameObj = (SameObj) o;

                return val == sameObj.val;

            }
        }
        Map<SameObj, String> map = new CustomHashMap<>();
        SameObj obj1 = new SameObj(10);
        SameObj obj2 = new SameObj(20);
        map.put(obj1, "obj1");
        map.put(obj2, "obj2");

        assertNotEquals(map.get(obj1), map.get(obj2));
    }

    @Test(expected = NullPointerException.class)
    public void testThatContainsKeyMethodThrowsExceptionOnNullKey() {
    }

    @Test
    public void testContainsValueMethodWorksProperlyOn() {
    }

    @Test
    public void testContainsValueMethodWorksProperlyOnNullInputValue() {
        m.put(1, null);
        assertTrue(m.containsValue(null));
    }


    @Test
    public void testThatMapCalculateItsSizeProperly() {
        m.put(1, "dff");
        m.put(2, "ddd");

        assertEquals(2, m.size());
    }
}