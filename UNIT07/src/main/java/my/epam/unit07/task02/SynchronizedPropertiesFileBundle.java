package my.epam.unit07.task02;

import my.epam.unit05.task02.PropertiesFileBundle;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SynchronizedPropertiesFileBundle extends PropertiesFileBundle {
    private static Map<File, PropertiesFileBundle> bundles = new HashMap<>();

    protected SynchronizedPropertiesFileBundle(Map<String, Object> properties) {
        super(properties);
    }

    public static PropertiesFileBundle getBundle(File file) {
        if (bundles.containsKey(file)) {
            return bundles.get(file);
        } else {
            PropertiesFileBundle bundle = PropertiesFileBundle.getBundle(file);
            bundles.put(file, bundle);
            return bundle;
        }
    }
}
