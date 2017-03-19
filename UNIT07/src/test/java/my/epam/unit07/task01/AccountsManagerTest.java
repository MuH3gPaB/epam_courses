package my.epam.unit07.task01;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class AccountsManagerTest extends Assert {

    AccountsManager manager;

    @Before
    public void initTest() {
        manager = new AccountsManager();
    }

    @Test
    public void performWithdrawOperationWhenBalanceMoreThenWithdraw() {
        int balance = 200;
        long accountId = manager.addAccount(balance);

        int toWithdraw = 100;
        Operation withdraw = Operation.build(accountId, OperationType.WITHDRAW, toWithdraw);

        manager.performOperation(withdraw);

        assertEquals(balance - toWithdraw, manager.getAccountBalance(accountId));
    }

    @Test
    public void performWithdrawOperationWhenBalanceLessThenWithdraw() {
        int balance = 100;
        long accountId = manager.addAccount(balance);

        int toWithdraw = 200;
        Operation withdraw = Operation.build(accountId, OperationType.WITHDRAW, toWithdraw);

        manager.performOperation(withdraw);

        assertEquals(balance - toWithdraw, manager.getAccountBalance(accountId));
    }

    @Test
    public void performDepositOperation() {
        int balance = 100;
        long accountId = manager.addAccount(balance);

        int toDeposit = 200;
        Operation deposit = Operation.build(accountId, OperationType.DEPOSIT, toDeposit);

        manager.performOperation(deposit);

        assertEquals(balance + toDeposit, manager.getAccountBalance(accountId));
    }

    @Test
    public void parallelOperationsPerformTest() {
        long accountId = manager.addAccount(0);
        ArrayList<Operation> operations = new ArrayList<>();

        int operationsCount = 300;
        for (int i = 1; i < operationsCount; i++) {
            operations.add(Operation.build(accountId, OperationType.DEPOSIT, 100 * i));
            operations.add(Operation.build(accountId, OperationType.WITHDRAW, 100 * i));
        }

        Collections.shuffle(operations);

        manager.performParallelOperations(operations);

        assertEquals(0, manager.getAccountBalance(accountId));
    }
}