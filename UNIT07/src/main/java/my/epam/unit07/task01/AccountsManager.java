package my.epam.unit07.task01;

import org.apache.log4j.Logger;

import java.util.*;

public class AccountsManager {
    private static Logger logger = Logger.getLogger(AccountsManager.class);

    private Map<Long, Account> accounts = new HashMap<>();
    private boolean autoCreateAccounts = true;

    public AccountsManager(Map<Long, Account> accounts) {
        this.accounts = accounts;
    }

    public AccountsManager() {
        this(new HashMap<>());
    }

    public long addAccount(long balance) {
        Random random = new Random();
        long accountId;
        do {
            accountId = Math.abs(random.nextLong());
        } while (accounts.containsKey(accountId));
        accounts.put(accountId, new Account(accountId, balance));
        return accountId;
    }

    public void removeAccount(Account account) {
        if (accounts.containsKey(account.getId())) {
            accounts.remove(account.getId());
        } else {
            throw new IllegalArgumentException("Account with id [" + account.getId() + "] not found.");
        }
    }

    public void performOperation(Operation operation) {
        long accountId = operation.getAccountId();
        if (!accounts.containsKey(accountId)) {
            if (autoCreateAccounts) {
                accounts.put(accountId, new Account(accountId, 0));
            } else {
                throw new IllegalArgumentException("Account with id [" + accountId + "] does not exist.");
            }
        }

        Account account = accounts.get(accountId);
        operation.apply(account);
    }

    public void performOperations(ArrayList<Operation> operations) {
        operations.forEach(this::performOperation);
    }

    public void performParallelOperations(ArrayList<Operation> operations) {
        int allJob = operations.size();
        int threadsCount = calculateThreadsCount(allJob);
        int operatorsJob = allJob / threadsCount;
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadsCount; i++) {
            int fromIndex = i * operatorsJob;
            int toIndex = (i + 1) * operatorsJob;
            if (i == threadsCount - 1) {
                toIndex = operations.size();
            }
            List<Operation> forOperator = operations.subList(fromIndex, toIndex);

            Runnable operator = () -> {
                for (Operation operation : forOperator) {
                    AccountsManager.this.performOperation(operation);
                }
            };

            Thread thread = new Thread(operator, "Operator" + i);
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                logger.warn("Parallel operations interrupted.");
            }
        }
    }

    private int calculateThreadsCount(int opl) {
        int threadsCount = 8;
        while (opl / threadsCount < 5 && threadsCount != 1) {
            threadsCount--;
        }
        return threadsCount;
    }

    public boolean isAutoCreateAccounts() {
        return autoCreateAccounts;
    }

    public void setAutoCreateAccounts(boolean autoCreateAccounts) {
        this.autoCreateAccounts = autoCreateAccounts;
    }

    public long getAccountBalance(long accountId) {
        if (accounts.containsKey(accountId)) {
            return accounts.get(accountId).getBalance();
        } else {
            throw new IllegalArgumentException("Account wiht id [" + accountId + "] not found.");
        }
    }
}
