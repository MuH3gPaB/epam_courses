package my.epam.unit03.task01;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * StringStorage implementation.
 *
 * Uses StringBuilder class for storing Strings.
 *
 */

public class StringBuilderStringStorage implements StringStorage {
    private static final char SEPARATOR = 1;
    private final StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void addString(String string) {
        if (string.isEmpty()) throw new IllegalArgumentException("String should not be empty.");
        stringBuilder.append(string).append(SEPARATOR);
    }

    @Override
    public String[] findAll(String pattern) {
        if (pattern.isEmpty()) throw new IllegalArgumentException("String should not be empty.");
        List<String> result = Arrays.stream(getAll())
                .filter((str) -> str.contains(pattern))
                .collect(Collectors.toList());
        return result.toArray(new String[0]);
    }

    @Override
    public String findOne(String pattern) {
        if (pattern.isEmpty()) throw new IllegalArgumentException("String should not be empty.");
        for (String str : getAll()) {
            if (str.contains(pattern)) return str;
        }
        return null;
    }

    @Override
    public String[] getAll() {
        String data = stringBuilder.toString();
        if(data.isEmpty()) return new String[0];
        return data.split("" + SEPARATOR);
    }

    @Override
    public void clear() {
        stringBuilder.setLength(0);
    }
}
