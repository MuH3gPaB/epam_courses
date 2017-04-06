package my.epam.collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import java.util.Objects;

@FixMethodOrder
@RunWith(Parameterized.class)
public class CustomListTest extends Assert {

    private List<Integer> list;

    @Parameterized.Parameters
    public static Object[] params() {
        return new Object[]{new CustomArrayList<Integer>(), new CustomLinkedList<Integer>()};
    }

    public CustomListTest(List<Integer> list) {
        this.list = list;
    }

    @Before
    public void setUp() {
        list.clear();
    }

    @Test
    public void firstTest() throws Exception {
        assertTrue(list != null);
    }
}
