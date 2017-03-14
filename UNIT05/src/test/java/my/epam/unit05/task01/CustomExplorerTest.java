package my.epam.unit05.task01;

import my.epam.unit05.task01.exceptions.AlreadyExistException;
import my.epam.unit05.task01.exceptions.DoesNotExistException;
import my.epam.unit05.task01.util.CustomExplorer;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Test folder structure should be:
 * test-folder
 * |-folderOne
 * | |-subFolderOne
 * | | |-subFileTwo.txt
 * | |-subFileOne.txt
 * |-fileOne.txt
 */

public class CustomExplorerTest extends Assert {
    private static final String TEST_DIR_PATH = "./src/test/resources/my/epam/unit05/task01/test-folder";

    @Test
    public void createOnValidPath() {
        try {
            CustomExplorer explorer = new CustomExplorer(TEST_DIR_PATH);
            assertEquals("folderOne", explorer.getFoldersNames()[0]);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void createOnEmptyPathShouldBeOnClassPath() {
        try {
            CustomExplorer explorer = new CustomExplorer("");
            File cpFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getFile());
            assertEquals(cpFile.getParentFile(), explorer.getCurrentPath().getParentFile());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void createOnNullPathShouldBeOnClassPath() {
        try {
            CustomExplorer explorer = new CustomExplorer(null);
            File cpFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getFile());
            assertEquals(cpFile.getParentFile(), explorer.getCurrentPath().getParentFile());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void moveCurrentPathToExistingSubfolder() throws DoesNotExistException {
        CustomExplorer explorer = new CustomExplorer(TEST_DIR_PATH);
        explorer.goTo("folderOne");

        assertEquals("subFolderOne", explorer.getFoldersNames()[0]);
    }

    @Test(expected = DoesNotExistException.class)
    public void moveCurrentPathToNonExistingSubfolder() throws DoesNotExistException {
        CustomExplorer explorer = new CustomExplorer(TEST_DIR_PATH);
        explorer.goTo("unexisted_folder");
    }

    @Test
    public void moveCurrentPathToUpperLevel() throws DoesNotExistException {
        CustomExplorer explorer = new CustomExplorer(TEST_DIR_PATH);
        explorer.goTo("..");

        String[] splitedFolder = TEST_DIR_PATH.split("/");
        String expected = splitedFolder[splitedFolder.length - 1];

        assertEquals(expected, explorer.getFoldersNames()[0]);
    }

    @Test(expected = DoesNotExistException.class)
    public void moveCurrentPathToNull() throws DoesNotExistException {
        CustomExplorer explorer = new CustomExplorer(TEST_DIR_PATH);
        explorer.goTo(null);
    }

    @Test
    public void goToUpperFromRootShouldKeepCurrentPath(){
        CustomExplorer explorer = new CustomExplorer(TEST_DIR_PATH);
        while (explorer.getCurrentPath().getParentFile() != null){
            explorer.goToUpper();
        }

        File expected = explorer.getCurrentPath();
        explorer.goToUpper();
        File actual = explorer.getCurrentPath();

        assertEquals(expected, actual);
    }

    @Test
    public void getExistingFile() throws DoesNotExistException {
        CustomExplorer explorer = new CustomExplorer(TEST_DIR_PATH);

        File expected = new File(TEST_DIR_PATH+"/fileOne.txt");
        File actual = explorer.getFile("fileOne.txt");

        assertEquals(expected, actual);
    }

    @Test(expected = DoesNotExistException.class)
    public void getNotExistingFileShouldThrowNoSuchElement() throws DoesNotExistException {
        CustomExplorer explorer = new CustomExplorer(TEST_DIR_PATH);
        explorer.getFile("some_not_existing");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNullFileShouldThrowIAE() throws DoesNotExistException {
        CustomExplorer explorer = new CustomExplorer(TEST_DIR_PATH);
        explorer.getFile(null);
    }

    @Test
    public void fileCreateDeleteTest() throws IOException, AlreadyExistException, DoesNotExistException {
        CustomExplorer explorer = new CustomExplorer(TEST_DIR_PATH);
        String[] original = explorer.getFilesNames();

        String fileName = "new_file.txt";
        explorer.createFile(fileName);

        String[] afterCreatingExpected = new String[original.length+1];
        System.arraycopy(original, 0, afterCreatingExpected, 0, original.length);
        afterCreatingExpected[original.length] = fileName;

        assertArrayEquals(afterCreatingExpected, explorer.getFilesNames());

        explorer.deleteFile(fileName);

        assertArrayEquals(original, explorer.getFilesNames());
    }

    @Test
    public void folderCreateDeleteTest() throws IOException, AlreadyExistException, DoesNotExistException {
        CustomExplorer explorer = new CustomExplorer(TEST_DIR_PATH);
        String[] original = explorer.getFoldersNames();

        String folderName = "new_folder";
        explorer.createFolder(folderName);

        String[] afterCreatingExpected = new String[original.length+1];
        System.arraycopy(original, 0, afterCreatingExpected, 0, original.length);
        afterCreatingExpected[original.length] = folderName;

        assertArrayEquals(afterCreatingExpected, explorer.getFoldersNames());

        explorer.deleteFolder(folderName);

        assertArrayEquals(original, explorer.getFoldersNames());
    }
}