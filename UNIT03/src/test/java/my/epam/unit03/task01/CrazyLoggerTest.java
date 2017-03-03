package my.epam.unit03.task01;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CrazyLoggerTest extends Assert{

    @Test
    public void addAndReadSimpleMessageShouldOk() throws IOException {
        CrazyLogger logger = new CrazyLogger(new StringBuilderStringStorage());

        String[] expected = {"Test message.", "Test message one."};
        logger.addMessage(expected[0]);
        logger.addMessage(expected[1]);

        String[] actual = readAllMessages(logger);

        String[] actualUnformatted = checkAndClearMessageHeader(actual);

        assertArrayEquals(expected, actualUnformatted);
    }

    @Test(expected = NullPointerException.class)
    public void addNullMessageShouldThrowsNPE() throws IOException {
        CrazyLogger logger = new CrazyLogger(new StringBuilderStringStorage());

        logger.addMessage(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addEmptyMessageShouldThrowsIAE() throws IOException {
        CrazyLogger logger = new CrazyLogger(new StringBuilderStringStorage());

        logger.addMessage("");
    }

    @Test
    public void addMessageWithLineSeparatorInsideShouldAddOneMultilineMessage() throws IOException {
        CrazyLogger logger = new CrazyLogger(new StringBuilderStringStorage());

        String separator = System.getProperty("line.separator");
        String expected = "One"+separator+"multiline"+separator+"message";
        logger.addMessage(expected);

        String actual = readAllMessages(logger)[0];

        assertEquals(expected, checkAndClearMessageHeader(actual));
    }

    @Test
    public void showSingleIfFoundMessageShouldOk() throws IOException {
        CrazyLogger logger = new CrazyLogger(new StringBuilderStringStorage());

        String expected = "Test message.";
        logger.addMessage(expected);
        logger.addMessage("Mysterious message");

        String actual = findOneMessage(logger, "Test");

        assertEquals(expected, checkAndClearMessageHeader(actual));
    }

    @Test
    public void showSomeMessagesIfFoundShouldOk() throws IOException {
        CrazyLogger logger = new CrazyLogger(new StringBuilderStringStorage());

        String[] expected = {"Test message one.", "Test message two."};
        logger.addMessage(expected[0]);
        logger.addMessage("Mysterious message");
        logger.addMessage(expected[1]);

        String[] actual = findAllMessages(logger, "Test");

        assertArrayEquals(expected, checkAndClearMessageHeader(actual));
    }

    @Test
    public void showNothingIfLogIsEmpty() throws IOException {
        CrazyLogger logger = new CrazyLogger(new StringBuilderStringStorage());

        String actualFindOne = findOneMessage(logger, "Some text");
        String[] actualFindAll = findAllMessages(logger, "Some text");
        String[] actualGetAll = readAllMessages(logger);

        // TODO: 03-Mar-17 Finish test 
    }

    @Test
    public void messageFormatCheckTest(){

    }

    private String[] checkAndClearMessageHeader(String[] message){
        String[] result = new String[message.length];
        for (int i = 0; i < message.length; i++) {
            result[i] = checkAndClearMessageHeader(message[i]);
        }
        return result;
    }

    private String checkAndClearMessageHeader(String message){
        checkMessageHeaderFormat();
        return message.substring(21);
    }

    private void checkMessageHeaderFormat() {
        assertTrue(true);
    }

    private String[] readAllMessages(CrazyLogger logger) throws IOException {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream(1024);
        logger.showAllMessagesTo(bOut);

        String buffer = bOut.toString();
        // TODO: HERE SHOULD BE REGEXP FOR HEADER!!!
        return buffer.split(System.getProperty("line.separator"));
    }

    private String[] findAllMessages(CrazyLogger logger, String pattern) throws IOException {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream(1024);
        logger.showMessagesTo(pattern, bOut);

        String buffer = bOut.toString();
        // TODO: HERE SHOULD BE REGEXP FOR HEADER!!!
        return buffer.split(System.getProperty("line.separator"));
    }

    private String findOneMessage(CrazyLogger logger, String pattern) throws IOException {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream(1024);
        logger.showMessageTo(pattern, bOut);

        return bOut.toString();
    }


}