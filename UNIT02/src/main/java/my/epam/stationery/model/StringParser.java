package my.epam.stationery.model;

import my.epam.stationery.entity.AbstractEntity;
import my.epam.stationery.entity.EntityManager;
import org.apache.log4j.Logger;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class StringParser<T> {
    private Logger logger = Logger.getLogger(StringParser.class);
    private Class objClass;
    private static char ROOT_SEPARATOR = 1;
    private int depth = 5;

    public StringParser(Class objClass) {
        if (!isPrimitiveOrBoxed(objClass) && !objClass.equals(String.class) && !objClass.isArray()) {
            if (EntityManager.getEntity(objClass) == null) {
                throw new IllegalArgumentException("Could not found entity for class " + objClass.getName());
            }
        }
        this.objClass = objClass;
    }

    public String parseTo(T obj) {
        return parseToBySeparator(obj, ROOT_SEPARATOR, null);
    }

    public String parseToWithId(T obj, Long id){
        return parseToBySeparator(obj, ROOT_SEPARATOR, id);
    }

    private String parseToBySeparator(Object obj, char separator, Long id) {
        if (isPrimitiveOrBoxed(objClass) || objClass.equals(String.class)) {
            return parsePrimitive(obj, separator);
        }

        if (objClass.isArray()) {
            return parseArray(obj, separator);
        }

        StringBuilder sb = new StringBuilder();
        try {
            sb.append("class=").append(objClass.getName()).append(separator);

            Field[] fields = getAllNonStaticDeclaredFields(objClass);
            for (Field field : fields) {
                Object fieldValue = field.get(obj);
                sb.append(field.getName()).append("={");

                if(id != null && field.getName().equals("id") && field.getType().equals(Long.class)){
                    sb.append(id).append("}").append(separator);
                    continue;
                }

                if (fieldValue == null) {
                    sb.append("null}").append(separator);
                    continue;
                }

                if (isPrimitiveOrBoxed(field.getType()) || field.getType().equals(String.class)) {
                    sb.append(fieldValue.toString()).append("}").append(separator);
                } else if (field.getType().isArray()) {
                    appendArray(separator, sb, field.getType(), fieldValue).append("}").append(separator);
                } else {
                    appendObject(separator, sb, field, fieldValue).append("}").append(separator);
                }
            }
        } catch (IllegalAccessException e) {
            logger.error("Error parsing object to string " + e.getMessage());
        }
        return sb.toString();
    }

    private String parseArray(Object obj, char separator) {
        StringBuilder sb = new StringBuilder();
        sb.append("class=").append(objClass.getName()).append(separator).append("{");
        appendArray((char) (separator + 1), sb, objClass, obj).append("}").append(separator);
        return sb.toString();
    }

    private StringBuilder appendObject(char separator, StringBuilder sb, Field field, Object fieldValue) {
        if (separator - ROOT_SEPARATOR > depth) {
            sb.append(fieldValue.toString()).append("}");
        } else {
            StringParser localParser = new StringParser(field.getType());
            sb.append(localParser.parseToBySeparator(fieldValue, (char) (separator + 1), null));
        }
        return sb;
    }

    private StringBuilder appendArray(char separator, StringBuilder sb, Class fieldClass, Object fieldValue) {
        if (separator - ROOT_SEPARATOR > depth) sb.append("null");
        else {
            Class elementClass = fieldClass.getComponentType();
            StringParser localParser = new StringParser(elementClass);

            Object[] array = (Object[]) fieldValue;
            for (Object object : array) {
                sb.append(localParser.parseToBySeparator(object, (char) (separator + 1), null)).append(separator);
            }
        }
        return sb;
    }

    private Field[] getAllNonStaticDeclaredFields(Class objClass) {
        ArrayList<Field> fields = new ArrayList<>();
        Class clazz = objClass;
        do {
            Stream.of(clazz.getDeclaredFields())
                    .forEach((field) -> {
                        if (!Modifier.isStatic(field.getModifiers())) {
                            if (!field.isAccessible()) field.setAccessible(true);
                            fields.add(field);
                        }
                    });
            clazz = clazz.getSuperclass();
        } while (!clazz.equals(Object.class));
        return fields.toArray(new Field[0]);
    }

    private String parsePrimitive(Object obj, char separator) {
        return objClass.getName() + separator + obj.toString();
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public T parseFrom(String str) {
        return parseFromBySeparator(str, ROOT_SEPARATOR);
    }

    private boolean isPrimitiveOrBoxed(Class clazz) {
        return clazz.isPrimitive() ||
                clazz.equals(Byte.class) ||
                clazz.equals(Short.class) ||
                clazz.equals(Integer.class) ||
                clazz.equals(Long.class) ||
                clazz.equals(Float.class) ||
                clazz.equals(Double.class) ||
                clazz.equals(Character.class) ||
                clazz.equals(Boolean.class);
    }

    private T parseFromBySeparator(String str, char separator) throws IllegalArgumentException {
        try {
            if (isPrimitiveOrBoxed(objClass) || objClass.equals(String.class)) {
                return parsePrimitive(str, separator);
            }

            if (objClass.isArray()) {
                return parseArray(objClass.getComponentType(), str, separator);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Could not parse string = " + str + " Exception = " + e.getMessage());
            throw new IllegalArgumentException();
        }

        String sepString = "" + separator;
        String[] strings = str.split(sepString);
        AbstractEntity<T> entityObject;
        try {
            Class realObjClass = parseClass(strings[0]);
            Class entityClass = EntityManager.getEntity(realObjClass);

            entityObject = (AbstractEntity<T>) entityClass.newInstance();

            for (String localString : strings) {

                String fieldName = parseFieldName(localString);

                if (fieldName.equals("class")) continue;

                Field field = entityClass.getDeclaredField(fieldName);
                if (!field.isAccessible()) field.setAccessible(true);

                if (isPrimitiveOrBoxed(field.getType()) || field.getType().equals(String.class)) {
                    parseAndSetPrimitive(entityObject, field, localString);
                } else if (field.getType().isArray()) {
                    parseAndSetArray(separator, entityObject, field, localString);
                } else {
                    parseAndSetObject(separator, entityObject, localString, field);
                }
            }
        } catch (NoSuchFieldException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            logger.error("Could not parse string = " + str + " Exception = " + e.getMessage());
            throw new IllegalArgumentException();
        }
        return entityObject.build();
    }

    private T parseArray(Class type, String str, char separator) {
        if (!str.contains("" + separator)) return null;
        String strWithoutClassName = str.split("" + separator)[1];
        String strWithoutBrackets = strWithoutClassName.substring(1, strWithoutClassName.length() - 1);
        String[] effStrings = strWithoutBrackets.split("" + (char) (separator + 1));
        StringParser localParser = new StringParser(type);
        Object[] result = (Object[]) Array.newInstance(type, effStrings.length);
        for (int i = 0; i < effStrings.length; i++) {
            result[i] = localParser.parseFromBySeparator(effStrings[i], (char) (separator + 2));
        }
        return (T) result;
    }

    private T parsePrimitive(String str, char separator) throws InstantiationException, IllegalAccessException {
        str = str.substring(str.indexOf(separator) + 1);
        return (T) parseValue(str, objClass);
    }

    private void parseAndSetObject(char separator, AbstractEntity<T> entityObject, String localString, Field field)
            throws IllegalAccessException {
        String fieldName = field.getName();
        if (separator - ROOT_SEPARATOR < depth) {
            String toParse = localString.substring(fieldName.length() + 2, localString.length() - 1);
            if (toParse.equals("null")) {
                field.set(entityObject, null);
            } else {
                StringParser localParser = new StringParser(field.getType());
                field.set(entityObject, localParser.parseFromBySeparator(toParse, (char) (separator + 1)));
            }
        } else {
            field.set(entityObject, null);
        }
    }

    private void parseAndSetArray(char separator, AbstractEntity<T> entityObject, Field field, String str)
            throws IllegalAccessException {
        Object value = parseArray(field.getType().getComponentType(), str, separator);
        field.set(entityObject, value);
    }

    private void parseAndSetPrimitive(AbstractEntity<T> entityObject, Field field, String str)
            throws IllegalAccessException, InstantiationException {
        field.set(entityObject, parseValue(str.substring(str.indexOf('=') + 2, str.length() - 1), field.getType()));
    }

    private Object parseValue(String str, Class type) throws IllegalAccessException, InstantiationException {
        if (str.equals("null")) return null;
        Object result = null;
        switch (type.getName()) {
            case "byte":
            case "java.lang.Byte":
                result = Byte.parseByte(str);
                break;
            case "short":
            case "java.lang.Short":
                result = Short.parseShort(str);
                break;
            case "int":
            case "java.lang.Integer":
                result = Integer.parseInt(str);
                break;
            case "long":
            case "java.lang.Long":
                result = Long.parseLong(str);
                break;
            case "double":
            case "java.lang.Double":
                result = Double.parseDouble(str);
                break;
            case "float":
            case "java.lang.Float":
                result = Float.parseFloat(str);
                break;
            case "char":
            case "java.lang.Character":
                result = str.charAt(0);
                break;
            case "boolean":
            case "java.lang.Boolean":
                result = Boolean.parseBoolean(str);
                break;
            case "java.lang.String":
                result = str;
                break;
            default:
                result = null;
        }
        return result;
    }

    private String parseFieldName(String str) {
        return str.substring(0, str.indexOf('='));
    }

    private Class parseClass(String str) throws ClassNotFoundException {
        String className = str.substring(str.indexOf('=') + 1);
        return Class.forName(className);
    }

    public Class getCurrentObjectClass(){
        return objClass;
    }
}
