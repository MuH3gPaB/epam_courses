package my.epam.unit07.task01.parallel;

import my.epam.unit07.task01.AccountsManager;
import my.epam.unit07.task01.model.Account;
import my.epam.unit07.task01.model.Operation;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentAccountsManager extends AccountsManager {
    private static Logger logger = Logger.getLogger(ConcurrentAccountsManager.class);
    private Map<Long, Lock> locks = new HashMap<>();

    @Override
    public void performOperation(Operation operation) {
        long accountId = operation.getAccountId();
        checkAccount(accountId, isAutoCreateAccounts());

        Account account = accounts.get(accountId);
        Lock lock = getLock(accountId);

        try {
            if (lock.tryLock(10, TimeUnit.SECONDS)) {
                operation.apply(account);
                operationsCount++;
                lock.unlock();
            }
        } catch (InterruptedException e) {
            logger.warn("Job interrupted. On [" + operation + "]");
            lock.unlock();
        }
    }

    @Override
    public void performParallelOperations(List<Operation> operations) {
        List<Runnable> jobs = buildJobs(operations);
        doJobs(jobs);
    }

    private void doJobs(List<Runnable> jobs) {
        ExecutorService ex = Executors.newCachedThreadPool();
        jobs.forEach(ex::execute);
        ex.shutdown();
    }

    private List<Runnable> buildJobs(List<Operation> operations) {
        Runnable[] jobs = operations.stream()
                .map(operation -> (Runnable) () -> {
                    try {
                        this.performOperation(operation);
                    } catch (IllegalArgumentException e) {
                        logger.warn(e.getMessage());
                    }
                })
                .toArray(Runnable[]::new);

        return Arrays.asList(jobs);
    }

    private Lock getLock(Long accountId) {
        if (!locks.containsKey(accountId)) {
            locks.put(accountId, new ReentrantLock());
        }

        return locks.get(accountId);
    }
}
