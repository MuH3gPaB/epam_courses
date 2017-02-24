package my.epam.stationery.utils;

import my.epam.stationery.entity.AbstractEntity;
import my.epam.stationery.entity.EntityManager;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class StringParser<T> {
    private Logger logger = Logger.getLogger(StringParser.class);
    private Class objClass;
    private static char ROOT_SEPARATOR = 1;
    private int depth = 2;

    public StringParser(Class objClass) {
        if (!objClass.isPrimitive() && !objClass.equals(String.class)) {
            if (EntityManager.getEntity(objClass) == null) {
                throw new IllegalArgumentException("Could not found entity for class " + objClass.getName());
            }
        }
        this.objClass = objClass;
    }

    public String parseTo(T obj) {
        return parseToBySeparator(obj, ROOT_SEPARATOR);
    }

    private String parseToBySeparator(Object obj, char separator) {
        if (objClass.isPrimitive() || objClass.equals(String.class)) {
            return parsePrimitive(obj);
        }
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("class=").append(objClass.getName()).append(separator);
            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    if (!field.isAccessible()) field.setAccessible(true);
                    Object fieldValue = field.get(obj);
                    sb.append(field.getName()).append("={");
                    if (fieldValue == null) {
                        sb.append("null}");
                        sb.append(separator);
                        continue;
                    }
                    if (field.getType().isPrimitive() || field.getType().equals(String.class)) {
                        sb.append(fieldValue.toString()).append("}");
                        sb.append(separator);
                    } else if (field.getType().isArray()) {
                        System.out.println("array");
                    } else {
                        if (separator - ROOT_SEPARATOR > depth) {
                            sb.append(fieldValue.toString()).append("}");
                        } else {
                            StringParser localParser = new StringParser(field.getType());
                            sb.append(localParser.parseToBySeparator(fieldValue, (char) (separator + 1)));
                            sb.append("}").append(separator);
                        }
                    }
                }
            }
        } catch (
                IllegalAccessException e)

        {
            logger.error("Error parsing object to string " + e.getMessage());
        }
        return sb.toString();
    }

    private String parsePrimitive(Object obj) {
        return objClass.getName() + ";" + obj.toString();
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public T parseFrom(String str) {
        return parseFromBySeparator(str, ROOT_SEPARATOR);
    }

    private T parseFromBySeparator(String str, char separator) throws IllegalArgumentException {
        String sepString = "" + separator;
        String[] strings = str.split(sepString);
        Class realObjClass;
        AbstractEntity<T> object;
        try {
            realObjClass = EntityManager.getEntity(parseClass(strings[0]));
            object = (AbstractEntity<T>) realObjClass.newInstance();
            for (String localString : strings) {
                String fieldName = parseFieldName(localString);
                if (fieldName.equals("class")) continue;
                Field field = realObjClass.getDeclaredField(fieldName);
                if (field.getType().isPrimitive() || field.getType().equals(String.class)) {
                    field.set(object, parseValue(localString, field.getType()));
                } else if (field.getType().isArray()) {
                    System.out.println("array");
                } else {
                    if (separator - ROOT_SEPARATOR < depth) {
                        String toParse = localString.substring(fieldName.length() + 2, localString.length() - 1);
                        if (toParse.equals("null")) {
                            field.set(object, null);
                        } else {
                            StringParser localParser = new StringParser(field.getType());
                            field.set(object, localParser.parseFromBySeparator(toParse, (char) (separator + 1)));
                        }
                    } else {
                        field.set(object, null);
                    }
                }
            }
        } catch (NoSuchFieldException
                | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            logger.error("Could not parse string = " + str + " Exception = " + e.getMessage());
            throw new IllegalArgumentException();
        }
        return object.build();
    }

    private Object parseValue(String str, Class type) throws IllegalAccessException, InstantiationException {
        String effStr = str.substring(str.indexOf('=') + 2, str.length() - 1);
        Object result = null;
        switch (type.getName()) {
            case "byte":
                result = Byte.parseByte(effStr);
                break;
            case "short":
                result = Short.parseShort(effStr);
                break;
            case "int":
                result = Integer.parseInt(effStr);
                break;
            case "long":
                result = Long.parseLong(effStr);
                break;
            case "double":
                result = Double.parseDouble(effStr);
                break;
            case "float":
                result = Float.parseFloat(effStr);
                break;
            case "char":
                result = effStr.charAt(0);
                break;
            case "boolean":
                result = Boolean.parseBoolean(effStr);
                break;
            case "String":
                result = effStr;
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
}
