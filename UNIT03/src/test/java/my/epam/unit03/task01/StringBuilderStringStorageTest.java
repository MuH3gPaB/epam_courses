package my.epam.unit03.task01;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringBuilderStringStorageTest {
    @Test
    public void addNormalStringShouldOk() throws Exception {
        StringBuilderStringStorage storage = new StringBuilderStringStorage();

        String expected = "Test message";
        storage.addString(expected);
        String actual = storage.findOne(expected);

        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void addNullStringsShouldThrowNPE() throws Exception {
        StringBuilderStringStorage storage = new StringBuilderStringStorage();

        storage.addString(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addEmptyStringsShouldThrowIAE() throws Exception {
        StringBuilderStringStorage storage = new StringBuilderStringStorage();

        storage.addString("");
    }

    @Test
    public void findAllCorrectPresentStringsShouldFind() throws Exception {
        StringBuilderStringStorage storage = new StringBuilderStringStorage();

        String[] expected = new String[2];
        expected[0] = "Test message one";
        expected[1] = "Test message two";
        storage.addString(expected[0]);
        storage.addString(expected[1]);
        storage.addString("Mysterious string");

        String[] actual = storage.findAll("Test");

        assertArrayEquals(expected, actual);
    }

    @Test
    public void findAllCorrectAbsentStringsShouldReturnEmptyArray() throws Exception {
        StringBuilderStringStorage storage = new StringBuilderStringStorage();

        String[] expected = new String[0];

        storage.addString("Test message one");
        storage.addString("Test message two");

        String[] actual = storage.findAll("noway");

        assertArrayEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void findAllNullStringShouldThrowNPE() throws Exception {
        StringBuilderStringStorage storage = new StringBuilderStringStorage();

        storage.addString("Test message one");
        storage.addString("Test message two");

        storage.findAll(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAllEmptyStringShouldThrowIAE() throws Exception {
        StringBuilderStringStorage storage = new StringBuilderStringStorage();

        storage.addString("Test message one");
        storage.addString("Test message two");

        storage.findAll("");
    }

    @Test
    public void findOneCorrectPresentStringShouldFindFirst() throws Exception {
        StringBuilderStringStorage storage = new StringBuilderStringStorage();

        String expected = "Test message One";
        storage.addString(expected);
        storage.addString("Test message Two");
        storage.addString("Mysterious string");

        String actual = storage.findOne("Test");

        assertEquals(expected, actual);
    }

    @Test
    public void findOneCorrectAbsentStringsShouldReturnNull() throws Exception {
        StringBuilderStringStorage storage = new StringBuilderStringStorage();

        storage.addString("Test message one");
        storage.addString("Test message two");

        String actual = storage.findOne("noway");

        assertNull(actual);
    }

    @Test(expected = NullPointerException.class)
    public void findOneNullStringShouldThrowNPE() throws Exception {
        StringBuilderStringStorage storage = new StringBuilderStringStorage();

        storage.addString("Test message one");
        storage.addString("Test message two");

        storage.findOne(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findOneEmptyStringShouldThrowIAE() throws Exception {
        StringBuilderStringStorage storage = new StringBuilderStringStorage();

        storage.addString("Test message one");
        storage.addString("Test message two");

        storage.findOne("");
    }

    @Test
    public void getAllIfEmptyShouldReturnEmptyArray() throws Exception {
        StringBuilderStringStorage storage = new StringBuilderStringStorage();

        assertArrayEquals(new String[0], storage.getAll());
    }

    @Test
    public void getAllShouldReturnAllStringsArray() throws Exception {
        StringBuilderStringStorage storage = new StringBuilderStringStorage();

        String[] expected = new String[2];
        expected[0] = "Test message one";
        expected[1] = "Test message two";
        storage.addString(expected[0]);
        storage.addString(expected[1]);

        String[] actual = storage.getAll();

        assertArrayEquals(expected, actual);
    }

    @Test
    public void clearIfStorageIsEmptyShouldOk() throws Exception {
        StringBuilderStringStorage storage = new StringBuilderStringStorage();

        try {
            storage.clear();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void clearIfStorageNotEmptyShouldOk() throws Exception {
        StringBuilderStringStorage storage = new StringBuilderStringStorage();

        storage.addString("Test message one");
        storage.addString("Test message two");

        assertEquals(2, storage.getAll().length);

        storage.clear();

        assertArrayEquals(new String[0], storage.getAll());
    }

}