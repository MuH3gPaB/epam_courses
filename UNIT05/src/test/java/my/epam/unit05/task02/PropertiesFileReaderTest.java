package my.epam.unit05.task02;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.PropertyResourceBundle;

public class PropertiesFileReaderTest extends Assert {

    @Test
    public void existingFileWithValidProperties() {
        File propFile = new File(getClass().getResource("/my/epam/unit05/task02/test-properties-01.properies").getFile());
        PropertiesFileBundle bundle = PropertiesFileBundle.getBundle(propFile);

        assertEquals("text", bundle.getString("text_propery"));
        assertEquals(10.12, bundle.getObject("double_property"));
        assertEquals(11, bundle.getObject("int_property"));
    }

}