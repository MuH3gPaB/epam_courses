package my.epam.unit07.task01.parallel;

import my.epam.unit07.task01.AccountsManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConcurrentAccountsManagerTest extends Assert {
    private AccountsManager manager;
    private CommonParallelAccountingForTest common;

    @Before
    public void initTest() {
        manager = new ConcurrentAccountsManager();
        common = new CommonParallelAccountingForTest(manager);
    }

    @Test
    public void parallelValidOperationsTest() {
        long accountId = manager.addAccount(0);
        common.parallelValidOperations(accountId);
        assertEquals(0, manager.getAccountBalance(accountId));
    }

    @Test
    public void parallelOneInvalidOperationShouldCompleteAllValids() {
        long accountId = manager.addAccount(0);
        common.parallelOneInvalidOperation(accountId);
        assertEquals(0, manager.getAccountBalance(accountId));
    }
}