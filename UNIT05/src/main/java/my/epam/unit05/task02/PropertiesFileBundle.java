package my.epam.unit05.task02;

import org.apache.log4j.Logger;
import sun.util.ResourceBundleEnumeration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
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
            logger.warn("Could not read the file [" + file.getName() + "]. " + e.getMessage());
        }
        return EMPTY_PROPERTIES_HOLDER;
    }

    public int size() {
        return properties.entrySet().size();
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

    private boolean isStringArrayRequired() {
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            return e.getStackTrace()[3].getMethodName().equals("getStringArray");
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
            if (values.containsKey(pair[0])) {
                Object newValue = addElementTo(values.get(pair[0]), value);
                values.put(pair[0], newValue);
            } else {
                values.put(pair[0], value);
            }
        } catch (IllegalArgumentException e) {
            logger.warn("Parse error at line: [" + s + "]" + e.getMessage());
        }
    }

    private static Object addElementTo(Object o, Object value) {
        if (o.getClass().isArray()) {
            checkClassesEquality(o.getClass().getComponentType(), value.getClass());
            Object[] objArray = (Object[]) o;
            Object[] newArray = (Object[]) Array.newInstance(value.getClass(), objArray.length + 1);
            System.arraycopy(objArray, 0, newArray, 0, objArray.length);
            newArray[objArray.length] = value;
            return newArray;
        } else {
            checkClassesEquality(o.getClass(), value.getClass());
            Object[] newArray = (Object[]) Array.newInstance(o.getClass(), 2);
            newArray[0] = o;
            newArray[1] = value;
            return newArray;
        }
    }

    private static void checkClassesEquality(Class o, Class value) {
        if (!o.equals(value))
            throw new IllegalArgumentException("Two properties with different types: " +
                    "[" + o.getClass().getComponentType() + "] [" + value.getClass() + "]");
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
