package my.epam.unit07.task01.parallel;

import my.epam.unit07.task01.AccountsManager;
import my.epam.unit07.task01.model.Operation;

import java.util.ArrayList;
import java.util.Collections;

public class CommonParallelAccountingForTest {
    private AccountsManager manager;

    public CommonParallelAccountingForTest(AccountsManager manager) {
        this.manager = manager;
    }

    public void parallelValidOperations(long accountId) {
        ArrayList<Operation> operations = buildOperations(accountId);

        Collections.shuffle(operations);

        manager.performParallelOperations(operations);
    }

    public void parallelOneInvalidOperation(long accountId) {
        ArrayList<Operation> operations = buildOperations(accountId);

        long notExistingId = accountId + 1;
        Operation invalidOperation = Operation.build(notExistingId, Operation.OperationType.DEPOSIT, 100);
        operations.add(invalidOperation);
        Collections.shuffle(operations);

        manager.setAutoCreateAccounts(false);
        manager.performParallelOperations(operations);
    }

    private ArrayList<Operation> buildOperations(long accountId) {
        ArrayList<Operation> operations = new ArrayList<>();

        int operationsCount = 3000;
        for (int i = 1; i < operationsCount; i++) {
            operations.add(Operation.build(accountId, Operation.OperationType.DEPOSIT, 100 * i));
            operations.add(Operation.build(accountId, Operation.OperationType.WITHDRAW, 100 * i));
        }
        return operations;
    }

}
