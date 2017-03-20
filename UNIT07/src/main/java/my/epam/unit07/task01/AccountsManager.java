package my.epam.unit07.task01;

import my.epam.unit07.task01.model.Account;
import my.epam.unit07.task01.model.Operation;
import org.apache.log4j.Logger;

import java.util.*;

public class AccountsManager {
    private static Logger logger = Logger.getLogger(AccountsManager.class);

    protected Map<Long, Account> accounts = new HashMap<>();
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
        checkAccount(accountId, autoCreateAccounts);

        Account account = accounts.get(accountId);
        operation.apply(account);
    }

    public void performParallelOperations(ArrayList<Operation> operations){
        throw new UnsupportedOperationException("Method should be overrided.");
    }

    protected void checkAccount(long accountId, boolean create) {
        if (!accounts.containsKey(accountId)) {
            if (create) {
                accounts.put(accountId, new Account(accountId, 0));
            } else {
                throw new IllegalArgumentException("Account with id [" + accountId + "] does not exist.");
            }
        }
    }

    public void performOperations(ArrayList<Operation> operations) {
        operations.forEach(this::performOperation);
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
