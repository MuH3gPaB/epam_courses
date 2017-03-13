package my.epam.unit05.task02;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class PropertiesFileReaderTest extends Assert {

    @Test
    public void existingFileWithValidProperties() {
        File propFile = new File(getClass().getResource("/my/epam/unit05/task02/test-properties-01.properties").getFile());
        PropertiesFileBundle bundle = PropertiesFileBundle.getBundle(propFile);

        assertEquals("text", bundle.getString("text_propery"));
        assertEquals(10.12, bundle.getObject("double_property"));
        assertEquals(11, bundle.getObject("int_property"));
    }

    @Test
    public void fileDoesNotExistShouldNotThrowException() {
        try {
            File propFile = new File("");
            PropertiesFileBundle bundle = PropertiesFileBundle.getBundle(propFile);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void fileDoesNotExistShouldCreateEmptyBundle() {
        File propFile = new File("");
        PropertiesFileBundle bundle = PropertiesFileBundle.getBundle(propFile);
        assertEquals(0, bundle.size());
    }

    @Test
    public void getStringArrayTest() {
        File propFile = new File(getClass().getResource("/my/epam/unit05/task02/test-properties-02.properties").getFile());
        PropertiesFileBundle bundle = PropertiesFileBundle.getBundle(propFile);

        String[] expected = {"element1", "element2"};
        String[] actual = bundle.getStringArray("string_array");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void noSuchPropertyShouldNotThrowException() {
        File propFile = new File("/my/epam/unit05/task02/test-properties-02.properties");
        PropertiesFileBundle bundle = PropertiesFileBundle.getBundle(propFile);

        try {
            bundle.getString("");
            bundle.getObject("");
            bundle.getStringArray("");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}