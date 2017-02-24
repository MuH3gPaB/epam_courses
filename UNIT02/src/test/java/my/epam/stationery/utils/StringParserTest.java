package my.epam.stationery.utils;

import my.epam.stationery.entity.PenEntity;
import my.epam.stationery.model.Pen;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringParserTest {
    @Test
    public void stringParseTo() throws Exception {
        String str = "hello";
        StringParser<String> strParser = new StringParser<>(String.class);
        assertEquals("java.lang.String;hello", strParser.parseTo(str));
    }

    @Test
    public void penParseTo() throws Exception {
        Pen pen = Pen.DEFAULT_BLACK_PEN;
        StringParser<Pen> penParser = new StringParser<>(Pen.class);
        assertEquals("class=my.epam.stationery.model.Pen\u0001brushColor={class=java.awt.Color\u0002value={-16777216}" +
                        "\u0002frgbvalue={null}\u0002fvalue={null}\u0002falpha={0.0}\u0002cs={null}\u0002}" +
                        "\u0001shellColor={class=java.awt.Color\u0002value={-16777216}\u0002frgbvalue={null}" +
                        "\u0002fvalue={null}\u0002falpha={0.0}\u0002cs={null}\u0002}\u0001",
                penParser.parseTo(pen));
    }

    @Test
    public void parseFrom() throws Exception {
        Pen pen = Pen.DEFAULT_BLACK_PEN;
        StringParser<Pen> penParser = new StringParser<>(Pen.class);
        String parsed = penParser.parseTo(pen);
        Pen unparsed = penParser.parseFrom(parsed);
        assertEquals(pen, unparsed);
    }

}