package my.epam.logic;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class Task4Test extends Assert {
    private static final Logger logger = Logger.getLogger(Task4Test.class);

    @BeforeClass
    public static void logStart() {
        logger.info("Task4 test started...");
    }

    @Test
    @Ignore
    public void getMaxPair() throws Exception {
        try {
            Task4.getMaxPair(new double[]{});
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }

        try {
            Task4.getMaxPair(new double[]{1, 2, 3});
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }

        double max = Task4.getMaxPair(new double[]{1, 2, 3, 4, 5, 6, 7, 8});
        assertEquals(9, max, 0.00001);

        max = Task4.getMaxPair(new double[]{1, -1});
        assertEquals(0, max, 0.00001);

        max = Task4.getMaxPair(new double[]{-10, 1, -1, -20});
        assertEquals(0, max, 0.00001);

        max = Task4.getMaxPair(new double[]{Double.POSITIVE_INFINITY, 0, 0, 0});
        assertEquals(Double.POSITIVE_INFINITY, max, 0.00001);

        try {
            Task4.getMaxPair(new double[]{Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY});
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }

        try {
            Task4.getMaxPair(new double[]{Double.NaN, 1, -1, Double.NaN});
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }

        try {
            Task4.getMaxPair(new double[]{1, Double.NaN, -1, Double.NaN});
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }

    }

}