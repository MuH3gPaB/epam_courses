package my.epam.unit07.task01;

import java.util.Objects;

public class Operation {
    private long accountId;
    private OperatinType type;
    private long value;

    public Operation(long accountId, OperatinType type, long value) {
        Objects.requireNonNull(type, "Operation type should not be null.");
        if(accountId <= 0) throw new IllegalArgumentException("Negative or zero account id.");
        if(value <= 0) throw new IllegalArgumentException("Negative or zero operation value.");

        this.accountId = accountId;
        this.type = type;
        this.value = value;
    }

    public static Operation parseOperation(String string) {
        return null;
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
