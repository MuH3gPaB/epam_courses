package my.epam.unit07.task01;


import my.epam.unit07.task01.model.Operation;
import my.epam.unit07.task01.parallel.ConcurrentAccountsManager;
import my.epam.unit07.task01.parallel.ParallelAccountsManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    private static Logger logger = Logger.getLogger(App.class);

    private static final String OPERATIONS_FILE = "/my/epam/unit07/task01/operations.txt";

    public static void main(String[] args) throws IOException {
        List<Operation> operations = loadOperations();

        long accountManagerTime = getPerformTime(new AccountsManager(), operations);
        long parallelAccountManagerTime = getPerformTime(new ParallelAccountsManager(), operations);
        long concurrentAccountManagerTime = getPerformTime(new ConcurrentAccountsManager(), operations);

        System.out.println("AccountManager time = " + accountManagerTime);
        System.out.println("ParallelAccountManager time = " + parallelAccountManagerTime);
        System.out.println("ConcurrentAccountManager time = " + concurrentAccountManagerTime);
    }

    private static long getPerformTime(AccountsManager manager, List<Operation> operations) {
        long start = System.currentTimeMillis();
        manager.performOperations(operations);
        return System.currentTimeMillis() - start;
    }

    private static List<Operation> loadOperations() throws IOException {
        Path filePath = Paths.get(OPERATIONS_FILE);
        Stream<String> lines = Files.lines(filePath);

        return lines
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
    }
}
