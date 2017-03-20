package my.epam.unit07.task01;

import my.epam.unit07.task01.model.Operation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class AccountsManagerTest extends Assert {

    private AccountsManager manager;

    @Before
    public void initTest() {
        manager = new AccountsManager();
    }

    @Test
    public void performWithdrawOperationWhenBalanceMoreThenWithdraw() {
        int balance = 200;
        long accountId = manager.addAccount(balance);

        int toWithdraw = 100;
        Operation withdraw = Operation.build(accountId, Operation.OperationType.WITHDRAW, toWithdraw);

        manager.performOperation(withdraw);

        assertEquals(balance - toWithdraw, manager.getAccountBalance(accountId));
    }

    @Test
    public void performWithdrawOperationWhenBalanceLessThenWithdraw() {
        int balance = 100;
        long accountId = manager.addAccount(balance);

        int toWithdraw = 200;
        Operation withdraw = Operation.build(accountId, Operation.OperationType.WITHDRAW, toWithdraw);

        manager.performOperation(withdraw);

        assertEquals(balance - toWithdraw, manager.getAccountBalance(accountId));
    }

    @Test
    public void performDepositOperation() {
        int balance = 100;
        long accountId = manager.addAccount(balance);

        int toDeposit = 200;
        Operation deposit = Operation.build(accountId, Operation.OperationType.DEPOSIT, toDeposit);

        manager.performOperation(deposit);

        assertEquals(balance + toDeposit, manager.getAccountBalance(accountId));
    }


}