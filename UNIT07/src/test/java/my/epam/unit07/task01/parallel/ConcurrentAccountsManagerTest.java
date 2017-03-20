package my.epam.unit07.task01.parallel;

import my.epam.unit07.task01.model.Operation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class ConcurrentAccountsManagerTest extends Assert{
    private ConcurrentAccountsManager manager;

    @Before
    public void initTest() {
        manager = new ConcurrentAccountsManager();
    }

    @Test
    public void parallelOperationsPerformTest() {
        long accountId = manager.addAccount(0);
        ArrayList<Operation> operations = new ArrayList<>();

        int operationsCount = 3000;
        for (int i = 1; i < operationsCount; i++) {
            operations.add(Operation.build(accountId, Operation.OperationType.DEPOSIT, 100 * i));
            operations.add(Operation.build(accountId, Operation.OperationType.WITHDRAW, 100 * i));
        }

        Collections.shuffle(operations);

        manager.performParallelOperations(operations);

        assertEquals(0, manager.getAccountBalance(accountId));
    }
}