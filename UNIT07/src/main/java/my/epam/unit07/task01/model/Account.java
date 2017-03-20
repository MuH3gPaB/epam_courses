package my.epam.unit07.task01.model;

/**
 * This class represent bank account.
 * <p>
 * It use just two fields: id, balance.
 * <p>
 * Two simple operations supported: increase, decrease.
 */

public class Account {

    private long balance;
    private final long id;

    public Account(long id, long balance) {
        this.balance = balance;
        this.id = id;
    }

    public void increase(long value) {
        this.balance += value;
    }

    public void decrease(long value) {
        this.balance -= value;
    }

    public long getBalance() {
        return balance;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (balance != account.balance) return false;
        return id == account.id;

    }

    @Override
    public int hashCode() {
        int result = (int) (balance ^ (balance >>> 32));
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }
}
