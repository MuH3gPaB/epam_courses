package my.epam.logic;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class Task3Test extends Assert{
    private static final Logger logger = Logger.getLogger(Task3Test.class);

    static {
        BasicConfigurator.configure();
    }


    @BeforeClass
    public static void logStart(){
        logger.info("Task3 test started...");
    }


    @Test
    @Ignore
    public void getFunctionValue() throws Exception {
        Task3.getFunctionValue(0, 10, 1);

        // Zero step test
        Task3.getFunctionValue(0, 0, 0);

        // Start = end = 0 test
        Task3.getFunctionValue(0, 0, 0.001);

        // Start > end test
        Task3.getFunctionValue(10, 0, 1);
        Task3.getFunctionValue(10, 0, -1);

        // Negatives test
        Task3.getFunctionValue(-10, 0, 1);
        Task3.getFunctionValue(-20, -10, 1);
        Task3.getFunctionValue(-10, -20, 1);
        Task3.getFunctionValue(-10, -20, -1);
    }

}