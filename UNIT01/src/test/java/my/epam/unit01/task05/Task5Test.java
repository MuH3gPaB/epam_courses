package my.epam.unit01.task05;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class Task5Test extends Assert {
    private static final Logger logger = Logger.getLogger(Task5Test.class);

    @BeforeClass
    public static void logStart() {
        logger.info("Task5 test started...");
    }


    @Test
    @Ignore
    public void getMatrix() throws Exception {
        try {
            Task5.getMatrix(-10);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }

        try {
            Task5.getMatrix(0);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }

        Task5.printMatrix(Task5.getMatrix(9));
    }

}