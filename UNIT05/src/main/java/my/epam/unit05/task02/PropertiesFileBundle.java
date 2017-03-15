package my.epam.unit05.task02;

import org.apache.log4j.Logger;
import sun.util.ResourceBundleEnumeration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Properties file custom resource bundle.
 * <p>
 * Reads properties file to inner map.
 * <p>
 * Does not throws any exceptions if file or
 * property not found. Instead of exception, write
 * warning into log, and return empty value.
 */

public class PropertiesFileBundle extends ResourceBundle {
    private static final PropertiesFileBundle EMPTY_PROPERTIES_HOLDER = new PropertiesFileBundle(new HashMap<>());

    private static Logger logger = Logger.getLogger(PropertiesFileBundle.class);

    private Map<String, Object> properties;

    private PropertiesFileBundle(Map<String, Object> properties) {
        this.properties = properties;
    }

    /**
     * General PropertiesFileBundle fabric method.
     *
     * @param file Properties file.
     * @return New instance of PropertiesFileBundle initialized with values from file, OR
     * empty PropertiesFileBundle if file not exist, or any errors while parsing.
     */
    public static PropertiesFileBundle getBundle(File file) {

        Map<String, Object> values = readValues(file);

        if (!values.isEmpty()) {
            return new PropertiesFileBundle(values);
        } else {
            return EMPTY_PROPERTIES_HOLDER;
        }
    }

    private static Map<String, Object> readValues(File file) {
        Path path = file.toPath();
        Map<String, Object> values = new HashMap<>();
        try {
            Files.lines(path).forEach((s) -> {
                parseStringToMap(values, s);
            });
        } catch (IOException e) {
            logger.warn("Could not read the file [" + file.getName() + "]. " + e.getMessage());
            return Collections.emptyMap();
        }
        return values;
    }

    /**
     * Get size of current properties bundle.
     *
     * @return Number of elements stored in bundle.
     */
    public int size() {
        return properties.entrySet().size();
    }

    /**
     * Get keys enumeration.
     *
     * @return Keys enumeration.
     */
    @Override
    public Enumeration<String> getKeys() {
        ResourceBundle parent = this.parent;
        return new ResourceBundleEnumeration(properties.keySet(),
                (parent != null) ? parent.getKeys() : null);
    }

    @Override
    protected Object handleGetObject(String key) {
        if (properties.containsKey(key)) {
            return properties.get(key);
        } else {
            logger.warn("No such property [" + key + "]");
            if (isStringArrayRequired()) return new String[0];
            else return "";
        }
    }

    private static void parseStringToMap(Map<String, Object> values, String s) {
        try {
            String[] pair = checkPropertyLine(s);
            Object value = parseValue(pair[1]);
            values.put(pair[0], value);
        } catch (IllegalArgumentException e) {
            logger.warn("Parse error at line: [" + s + "]" + e.getMessage());
        }
    }

    private static Object parseValue(String valueString) {
        Object value;
        if (valueString.matches("(^[0-9]{1,9}[.][0-9]{1,20}$)")) {
            value = Double.parseDouble(valueString);
        } else if (valueString.matches("(^[0-9]{1,18}$)")) {
            value = Integer.parseInt(valueString);
        } else {
            String[] values = valueString.split(",");
            if (values.length == 1) return values[0];
            return values;
        }
        return value;
    }

    private static String[] checkPropertyLine(String s) throws IllegalArgumentException {
        String[] pair = s.split("=");
        if (pair.length != 2) throw new IllegalArgumentException("Wrong line format. Should be one '=' symbol inside.");
        return pair;
    }

    private boolean isStringArrayRequired() {
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            return e.getStackTrace()[3].getMethodName().equals("getStringArray");
        }
    }
}
