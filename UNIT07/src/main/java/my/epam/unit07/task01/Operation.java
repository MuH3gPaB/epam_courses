package my.epam.unit07.task01;

import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Operation {
    private long accountId;
    private OperationType type;
    private long value;

    private final static String OPERATION_STRING_REGEXP = "(^Operation *\\{ *accountId=\\d+, *type=\\w+, *value=\\d+ *\\}$)";

    private Operation() {
    }

    public static Operation build(long accountId, OperationType type, long value) {
        Objects.requireNonNull(type, "Operation type should not be null.");
        if (accountId <= 0) throw new IllegalArgumentException("Negative or zero account id.");
        if (value <= 0) throw new IllegalArgumentException("Negative or zero operation value.");

        Operation operation = new Operation();
        operation.accountId = accountId;
        operation.type = type;
        operation.value = value;
        return operation;
    }

    public static Operation parseOperation(String string) throws ParseException {
        string = string.trim();

        if (!string.matches(OPERATION_STRING_REGEXP)) {
            throw new ParseException("Operation parse error on string [" + string + "]", 0);
        }

        String accountIdString = extractValue("accountId", string);
        String operationTypeString = extractValue("type", string);
        String valueString = extractValue("value", string);

        long accountId = Long.parseLong(accountIdString);
        OperationType type = OperationType.valueOf(operationTypeString);
        long value = Long.parseLong(valueString);

        return Operation.build(accountId, type, value);
    }

    private static String extractValue(String valName, String string) throws ParseException {
        Matcher matcher = Pattern.compile(valName + "=.*?(?=,|\\})").matcher(string);
        if (matcher.find()) {
            return matcher.group().split("=")[1];
        } else {
            throw new ParseException("Value extraction error on value [" + valName + "] on string [" + string + "].", 0);
        }
    }

    @Override
    public String toString() {
        return "Operation{" +
                "accountId=" + accountId +
                ", type=" + type +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        if (accountId != operation.accountId) return false;
        if (value != operation.value) return false;
        return type == operation.type;

    }

    @Override
    public int hashCode() {
        int result = (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (int) (value ^ (value >>> 32));
        return result;
    }
}
