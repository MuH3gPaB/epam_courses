package my.epam.unit05.task02;

import org.apache.log4j.Logger;
import sun.reflect.Reflection;
import sun.util.ResourceBundleEnumeration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PropertiesFileBundle extends ResourceBundle {
    private static final PropertiesFileBundle EMPTY_PROPERTIES_HOLDER = new PropertiesFileBundle(new HashMap<>());

    private static Logger logger = Logger.getLogger(PropertiesFileBundle.class);

    private Map<String, Object> properties;

    private PropertiesFileBundle(Map<String, Object> properties) {
        this.properties = properties;
    }

    public static PropertiesFileBundle getBundle(File file) {
        try {
            Path path = file.toPath();
            Map<String, Object> values = new HashMap<>();

            Files.lines(path).forEach((s) -> {
                parseStringToMap(values, s);
            });

            return new PropertiesFileBundle(values);
        } catch (IOException e) {
            logger.error("Could not read the file [" + file.getName() + "]. " + e.getMessage());
        }
        return EMPTY_PROPERTIES_HOLDER;
    }

    @Override
    protected Object handleGetObject(String key) {
        if (properties.containsKey(key)) {
            return properties.get(key);
        } else {
            logger.error("No such property [" + key + "]");
            return null;
        }
    }

    @Override
    public Enumeration<String> getKeys() {
        ResourceBundle parent = this.parent;
        return new ResourceBundleEnumeration(properties.keySet(),
                (parent != null) ? parent.getKeys() : null);
    }

    private static void parseStringToMap(Map<String, Object> values, String s) {
        try {
            String[] pair = checkPropertyLine(s);
            Object value = parseValue(pair[1]);
            values.put(pair[0], value);
        } catch (IllegalArgumentException e) {
            logger.warn("Parse error at line: [" + s + "]");
        }
    }

    private static Object parseValue(String valueString) {
        Object value;
        if (valueString.matches("(^[0-9]{1,9}[.][0-9]{1,20}$)")) {
            value = Double.parseDouble(valueString);
        } else if (valueString.matches("(^[0-9]{1,18}$)")) {
            value = Integer.parseInt(valueString);
        } else {
            value = valueString;
        }
        return value;
    }

    private static String[] checkPropertyLine(String s) throws IllegalArgumentException {
        String[] pair = s.split("=");
        if (pair.length != 2) throw new IllegalArgumentException("Wrong line format. Should be one '=' symbol inside.");
        return pair;
    }

}
