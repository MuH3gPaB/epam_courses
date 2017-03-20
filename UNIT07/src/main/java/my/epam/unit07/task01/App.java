package my.epam.unit07.task01;


import my.epam.unit07.task01.model.Operation;
import my.epam.unit07.task01.parallel.ConcurrentAccountsManager;
import my.epam.unit07.task01.parallel.ParallelAccountsManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    private static Logger logger = Logger.getLogger(App.class);

    private static final String OPERATIONS_FILE = "/my/epam/unit07/task01/operations.txt";

    public static void main(String[] args) throws IOException {
        List<Operation> operations = loadOperations();

        AccountsManager accountsManager = new AccountsManager();
        ParallelAccountsManager parallelAccountsManager = new ParallelAccountsManager();
        ConcurrentAccountsManager concurrentAccountsManager = new ConcurrentAccountsManager();

        long accountManagerTime = getPerformTime(accountsManager, operations);
        printOutResult(accountsManager, accountManagerTime);

        long parallelAccountManagerTime = getPerformTime(parallelAccountsManager, operations);
        printOutResult(parallelAccountsManager, parallelAccountManagerTime);

        long concurrentAccountManagerTime = getPerformTime(concurrentAccountsManager, operations);
        printOutResult(concurrentAccountsManager, concurrentAccountManagerTime);
    }

    private static PrintStream printOutResult(AccountsManager accountsManager, long accountManagerTime) {
        return System.out.printf("%30s { operations : %6d time : %8d ms}\n", accountsManager.getClass().getSimpleName(), accountsManager.getOperationsCount(), accountManagerTime);
    }

    private static long getPerformTime(AccountsManager manager, List<Operation> operations) {
        long start = System.currentTimeMillis();
        manager.performOperations(operations);
        return System.currentTimeMillis() - start;
    }

    private static List<Operation> loadOperations() throws IOException {
        String fileName = App.class.getResource(OPERATIONS_FILE).getFile();
        Path filePath = new File(fileName).toPath();
        List<Operation> operations = Files
                .lines(filePath)
                .map((str) -> {
                    try {
                        return Operation.parseOperation(str);
                    } catch (ParseException e) {
                        logger.warn(e.getMessage());
                        return null;
                    }
                })
                .filter((o) -> o != null)
                .collect(Collectors.toList());

        return multiplyAndShuffle(operations, 5000);
    }

    private static ArrayList<Operation> multiplyAndShuffle(List<Operation> operations, int mulCount) {
        ArrayList<Operation> allOperations = new ArrayList<>();
        for (int i = 0; i < mulCount; i++) {
            allOperations.addAll(operations);
        }
        Collections.shuffle(allOperations);
        return allOperations;
    }
}
