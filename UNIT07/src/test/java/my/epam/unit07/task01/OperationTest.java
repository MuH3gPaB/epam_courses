package my.epam.unit07.task01;

import org.junit.Assert;
import org.junit.Test;

public class OperationTest extends Assert {

    @Test
    public void parseValidString(){
        String opString = "Operation{accountId=123123, type=withdraw, value=100}";

        Operation expected = new Operation(123123L, OperatinType.WITHDRAW, 100L);

    }
}