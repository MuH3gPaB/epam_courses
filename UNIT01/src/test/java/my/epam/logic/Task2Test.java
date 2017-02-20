package my.epam.logic;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class Task2Test extends Assert {
    private static final Logger logger = Logger.getLogger(Task2Test.class);

    @BeforeClass
    public static void logStart(){
        logger.info("Task2 test started...");
    }

    @Test
    @Ignore
    public void getArrayIndex() throws Exception{
        try {
            Task2.getArrayIndex(0);
            Task2.getArrayIndex(-100);
            Task2.getArrayIndex(-0.001);
            Task2.getArrayIndex(0.001);
            Task2.getArrayIndex(0.9);
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

}