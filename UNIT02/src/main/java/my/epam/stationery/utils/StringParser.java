package my.epam.stationery.utils;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;

public class StringParser<T> {
    private Logger logger = Logger.getLogger(StringParser.class);
    private Class objClass;
    private final String SEPARATOR = ";";

    private StringParser(T obj) {
        this.objClass = obj.getClass();
    }

    public String parseTo(Object obj) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(objClass.getName()).append(SEPARATOR);
            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAccessible()) field.setAccessible(true);
                Object fieldValue = field.get(obj);
                sb.append(field.getName()).append("=");
                if (field.getType().isPrimitive() || field.getType().equals(String.class)) {
                    sb.append(fieldValue.toString());
                } else {
                    sb.append("{");
                    StringParser localParser = StringParser.getParser(fieldValue);
                    sb.append(localParser.parseTo(fieldValue));
                    sb.append("}");
                }
            }
        } catch (IllegalAccessException e) {
            logger.error("Error parsing object to string " + e.getMessage());
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public T parseFrom(String str) {
        T instance = null;
        try {
            instance = (T) objClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public static <E> StringParser<E> getParser(E obj) {
        return new StringParser<>(obj);
    }
}
