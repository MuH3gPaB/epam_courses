package my.epam.unit07.task01;

public enum OperationType {
    DEPOSIT {
        @Override
        public void apply(Account account, Long value) {
            account.increase(value);
        }
    },

    WITHDRAW {
        @Override
        public void apply(Account account, Long value) {
            account.decrease(value);
        }
    };

    public void apply(Account account, Long value) {
        throw new UnsupportedOperationException();
    }
}
