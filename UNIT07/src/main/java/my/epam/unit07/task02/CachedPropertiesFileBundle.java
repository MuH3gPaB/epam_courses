package my.epam.unit07.task02;

import my.epam.unit05.task02.PropertiesFileBundle;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper on PropertiesFileBundle class.
 * <p>
 * If PropertiesFileBundle was once loaded, it stores into
 * CachedPropertiesFileBundle inner cache.
 * <p>
 * If any thread invokes getBundle(File file), it get value
 * from cache, or, if no value found, load it from file.
 *
 */
public class CachedPropertiesFileBundle {
    private static Map<File, PropertiesFileBundle> bundles = new HashMap<>();

    /**
     * Get bundle from cache, or read new bundle if not found.
     *
     * @param file Properties file for bundle
     * @return Loaded bundle
     */
    public static PropertiesFileBundle getBundle(File file) {
        if (!bundles.containsKey(file)) {
            synchronized (CachedPropertiesFileBundle.class) {
                if (!bundles.containsKey(file)) {
                    PropertiesFileBundle bundle = PropertiesFileBundle.getBundle(file);
                    bundles.put(file, bundle);
                }
            }
        }

        return bundles.get(file);
    }

    /**
     * Clear bundles cache.
     */
    
    public static synchronized void clearBundlesCache() {
        bundles = new HashMap<>();
    }
}
