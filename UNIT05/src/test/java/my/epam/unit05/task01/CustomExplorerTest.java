package my.epam.unit05.task01;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.NoSuchElementException;

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
            assertEquals("folderOne", explorer.getDirsNames()[0]);
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
    public void moveCurrentPathToExistingSubfolder() {
        CustomExplorer explorer = new CustomExplorer(TEST_DIR_PATH);
        explorer.goTo("folderOne");

        assertEquals("subFolderOne", explorer.getDirsNames()[0]);
    }

    @Test(expected = NoSuchElementException.class)
    public void moveCurrentPathToNonExistingSubfolder() {
        CustomExplorer explorer = new CustomExplorer(TEST_DIR_PATH);
        explorer.goTo("unexisted_folder");
    }

    @Test
    public void moveCurrentPathToUpperLevel() {
        CustomExplorer explorer = new CustomExplorer(TEST_DIR_PATH);
        explorer.goTo("..");

        String[] splitedFolder = TEST_DIR_PATH.split("/");
        String expected = splitedFolder[splitedFolder.length - 1];

        assertEquals(expected, explorer.getDirsNames()[0]);
    }

    @Test(expected = NoSuchElementException.class)
    public void moveCurrentPathToNull(){
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
    public void getExistingFile(){
        CustomExplorer explorer = new CustomExplorer(TEST_DIR_PATH);

        File expected = new File(TEST_DIR_PATH+"/fileOne.txt");
        File actual = explorer.getFile("fileOne.txt");

        assertEquals(expected, actual);
    }

    @Test(expected = NoSuchElementException.class)
    public void getNotExistingFileShouldThrowNoSuchElement(){
        CustomExplorer explorer = new CustomExplorer(TEST_DIR_PATH);
        explorer.getFile("some_not_existing");
    }

    @Test(expected = NullPointerException.class)
    public void getNullFileShouldThrowNPE(){
        CustomExplorer explorer = new CustomExplorer(TEST_DIR_PATH);
        explorer.getFile(null);
    }
}