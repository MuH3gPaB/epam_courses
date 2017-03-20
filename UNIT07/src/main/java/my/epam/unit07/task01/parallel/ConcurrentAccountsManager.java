package my.epam.unit07.task01.parallel;

import my.epam.unit07.task01.AccountsManager;
import my.epam.unit07.task01.model.Account;
import my.epam.unit07.task01.model.Operation;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentAccountsManager extends AccountsManager {
    private static Logger logger = Logger.getLogger(ConcurrentAccountsManager.class);

    @Override
    public void performOperation(Operation operation) {
        long accountId = operation.getAccountId();
        checkAccount(accountId, isAutoCreateAccounts());

        Account account = accounts.get(accountId);

        operation.apply(account);
    }

    public void performParallelOperations(ArrayList<Operation> operations) {
        List<Runnable> jobs = buildJobs(operations);
        doJobs(jobs);
    }

    private void doJobs(List<Runnable> jobs) {
        ExecutorService ex = Executors.newCachedThreadPool();
        jobs.forEach(ex::execute);
        ex.shutdown();
    }

    private List<Runnable> buildJobs(ArrayList<Operation> operations) {
        Runnable[] jobs = operations.stream()
                .map(operation -> (Runnable) () -> this.performOperation(operation))
                .toArray(Runnable[]::new);

        return Arrays.asList(jobs);
    }
}
