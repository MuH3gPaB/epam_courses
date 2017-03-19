package my.epam.unit07.task01;

public enum OperatinType {
    DEPOSIT,
    WITHDRAW;

    public void apply(Account account){
        throw new UnsupportedOperationException();
    }
}
