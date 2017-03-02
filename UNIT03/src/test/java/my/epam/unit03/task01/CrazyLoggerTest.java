package my.epam.unit03.task01;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class CrazyLoggerTest extends Assert{

    @Test
    public void addSimpleMessageShouldOk() throws IOException {
        CrazyLogger logger = new CrazyLogger(new StringBuilderStringStorage());
        logger.addMessage("Test message.");
        logger.addMessage("Test message one.");

        
        logger.showAllMessagesTo(System.out);

    }

    @Test(expected = NullPointerException.class)
    public void addNullMessageShouldThrowNPE(){

    }

    @Test(expected = IllegalArgumentException.class)
    public void addEmptyMessageShouldThrowIAE(){

    }

    @Test
    public void showSingleIfFoundMessageShouldOk(){

    }

    @Test
    public void showSomeMessagesIfFoundShouldOk(){

    }

    @Test
    public void showAllMessagesShouldOk(){

    }

    @Test
    public void showNothingIfLogIsEmpty(){

    }

    @Test
    public void messageFormatCheckTest(){

    }

    private boolean messageFormatCheck(String message){
        return true;
    }



}