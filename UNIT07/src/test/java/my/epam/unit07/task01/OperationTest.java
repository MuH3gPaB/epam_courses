package my.epam.unit07.task01;

import my.epam.unit07.task01.model.Operation;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class OperationTest extends Assert {

    @Test
    public void parseValidString() throws ParseException {
        Operation expected = Operation.build(123123, Operation.OperationType.WITHDRAW, 100);
        Operation actual = Operation.parseOperation(expected.toString());

        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createOperationWithZeroAccIdShouldThrowsIAE() {
        Operation operation = Operation.build(0, Operation.OperationType.DEPOSIT, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createOperationWithZeroValueShouldThrowsIAE() {
        Operation operation = Operation.build(123123, Operation.OperationType.DEPOSIT, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createOperationWithNegativeAccIdShouldThrowsIAE() {
        Operation operation = Operation.build(-123, Operation.OperationType.DEPOSIT, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createOperationWithNegativeValueShouldThrowsIAE() {
        Operation operation = Operation.build(123123, Operation.OperationType.DEPOSIT, -100);
    }

    @Test(expected = ParseException.class)
    public void parseStringWithEmptyAccountIdShouldThrowsPE() throws ParseException {
        String opString = "Operation{accountId=, type=WITHDRAW, value=100}";
        Operation.parseOperation(opString);
    }

    @Test(expected = ParseException.class)
    public void parseStringWithEmptyTypeShouldThrowsPE() throws ParseException {
        String opString = "Operation{accountId=123112, type=, value=100}";
        Operation.parseOperation(opString);
    }

    @Test(expected = ParseException.class)
    public void parseStringWithEmptyValueShouldThrowsPE() throws ParseException {
        String opString = "Operation{accountId=123112, type=WITHDRAW, value=}";
        Operation.parseOperation(opString);
    }
}