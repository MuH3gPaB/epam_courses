package my.epam.stationery.entity;


import org.apache.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class EntityManager {
    private static Logger logger = Logger.getLogger(EntityManager.class.getName());
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("my.epam.stationery.entityMapping");

    private EntityManager() {
    }

    public static Class getEntity(Class clazz) {
        return getInnerEntity(clazz);
    }

    private static Class getInnerEntity(Class clazz) {
        Class result;
        try {
            result = Class.forName(clazz.getName() + "$Entity");
        } catch (ClassNotFoundException e) {
            result = getPropertiesEntity(clazz);
        }
        return result;
    }

    private static Class getPropertiesEntity(Class clazz) {
        Class result = null;
        try {
            result = Class.forName(resourceBundle.getString(clazz.getName()));
        } catch (MissingResourceException | ClassNotFoundException e) {
            logger.error("Could not load entity for class " + clazz.getName() + " from properties file.");
        }
        return result;
    }
}
