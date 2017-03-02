package my.epam.unit03.task01;

import java.util.Arrays;

public class StringBuilderStringStorage implements AbstractStringStorage{
    private static final char SEPARATOR = 1;
    private final StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void addString(String string) {
        stringBuilder.append(string).append(SEPARATOR);
    }

    @Override
    public String[] findAll(String pattern) {
        return (String[]) Arrays.stream(getAll())
                .filter((str) -> str.contains(pattern))
                .toArray();
    }

    @Override
    public String findOne(String pattern) {
        return null;
    }

    @Override
    public String[] getAll() {
        return new String[0];
    }

    @Override
    public void clear() {

    }
}
