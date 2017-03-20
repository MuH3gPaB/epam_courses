package my.epam.unit07.task01.parallel;

import my.epam.unit07.task01.AccountsManager;
import my.epam.unit07.task01.model.Account;
import my.epam.unit07.task01.model.Operation;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ParallelAccountsManager extends AccountsManager {
    private static Logger logger = Logger.getLogger(ParallelAccountsManager.class);

    @Override
    public void performOperation(Operation operation) {
        long accountId = operation.getAccountId();
        checkAccount(accountId, isAutoCreateAccounts());

        Account account = accounts.get(accountId);

        synchronized (account) {
            operation.apply(account);
            operationsCount++;
        }
    }

    @Override
    public void performParallelOperations(List<Operation> operations) {

        List<Thread> threads = buildThreads(operations);

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                logger.warn("Parallel operations interrupted.");
            }
        }
    }

    private List<Thread> buildThreads(List<Operation> operations) {
        List<Thread> threads = new ArrayList<>();

        int allJob = operations.size();
        int threadsCount = calculateThreadsCount(allJob);
        int operatorsJob = allJob / threadsCount;

        for (int i = 0; i < threadsCount; i++) {
            int fromIndex = i * operatorsJob;
            int toIndex = (i + 1) * operatorsJob;
            if (i == threadsCount - 1) {
                toIndex = allJob;
            }
            List<Operation> forOperator = operations.subList(fromIndex, toIndex);

            threads.add(runOperator(i, forOperator));
        }
        return threads;
    }

    private Thread runOperator(int i, List<Operation> forOperator) {
        Runnable operator = () -> forOperator.forEach((operation) -> {
            try {
                this.performOperation(operation);
            } catch (IllegalArgumentException e) {
                logger.warn(e.getMessage());
            }
        });

        Thread thread = new Thread(operator, "Operator" + i);
        thread.start();
        return thread;
    }

    private int calculateThreadsCount(int opl) {
        int threadsCount = 8;
        while (opl / threadsCount < 5 && threadsCount != 1) {
            threadsCount--;
        }
        return threadsCount;
    }


}
