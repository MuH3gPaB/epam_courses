package my.epam.stationery.model;

import my.epam.stationery.StationeryManager;
import my.epam.stationery.model.Pen;
import my.epam.stationery.model.StringParser;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringParserTest {
    @Test
    public void stringParseTo() throws Exception {
        String str = "hello";
        StringParser<String> strParser = new StringParser<>(String.class);
        assertEquals("java.lang.String\u0001hello", strParser.parseTo(str));
    }

    @Test
    public void stringParseFrom() {
        String str = "hello";
        StringParser<String> strParser = new StringParser<>(String.class);
        String parsed = strParser.parseTo(str);
        String unparsed = strParser.parseFrom(parsed);
        assertEquals(str, unparsed);
    }

    @Test
    public void integerParseFrom() {
        Integer val = 123;
        StringParser<Integer> strParser = new StringParser<>(Integer.class);
        String parsed = strParser.parseTo(val);
        Integer unparsed = strParser.parseFrom(parsed);
        assertEquals(val, unparsed);
    }

    @Test
    public void booleanParseFrom() {
        StringParser<Boolean> strParser = new StringParser<>(Boolean.class);
        String parsed = strParser.parseTo(true);
        Boolean unparsed = strParser.parseFrom(parsed);
        assertEquals(true, unparsed);
    }

    @Test
    public void parseFrom() throws Exception {
        Pen pen = Pen.DEFAULT_BLACK_PEN;
        StringParser<Pen> penParser = new StringParser<>(Pen.class);
        String parsed = penParser.parseTo(pen);
        Pen unparsed = penParser.parseFrom(parsed);
        assertEquals(pen, unparsed);
    }

    @Test
    public void stringsArrayParseTest() {
        String[] strings = {"hello", "from", "array"};
        StringParser<String[]> strParser = new StringParser<>(String[].class);
        String parsed = strParser.parseTo(strings);
        String[] unparsed = strParser.parseFrom(parsed);
        assertArrayEquals(strings, unparsed);
    }

    @Test
    public void penArrayParseTest() {
        Pen[] pens = {Pen.DEFAULT_RED_PEN, Pen.DEFAULT_BLUE_PEN, Pen.DEFAULT_BLACK_PEN};
        StringParser<Pen[]> strParser = new StringParser<>(Pen[].class);
        String parsed = strParser.parseTo(pens);
        Pen[] unparsed = strParser.parseFrom(parsed);
        assertArrayEquals(pens, unparsed);
    }

    @Test
    public void parseWithIdTest(){
        Pen pen = Pen.DEFAULT_BLACK_PEN;
        StringParser<Pen> parser = new StringParser<>(Pen.class);
        String parsed = parser.parseToWithId(pen, 123L);
        Pen unparsed = parser.parseFrom(parsed);
        assertEquals(new Long(123), unparsed.getId());
    }

    @Test
    public void stationeryManagerParseTest(){
        StationeryManager manager = new StationeryManager();
        StringParser<StationeryManager> parser = new StringParser<>(StationeryManager.class);
        String parsed = parser.parseTo(manager);
        System.out.println(parsed);
    }

}