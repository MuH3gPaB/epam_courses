package my.epam.unit05.task01;

import my.epam.unit05.task01.exceptions.AlreadyExistException;
import my.epam.unit05.task01.exceptions.DoesNotExistException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class CustomExplorer {

    private File currentPath;

    public CustomExplorer(String pathString) {
        if (pathString == null || pathString.isEmpty()) {
            currentPath = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getFile());
        } else {
            this.currentPath = new File(pathString);
        }
    }

    public String[] getFilesNames() {
        return getElementsNames(false);
    }

    public String[] getFoldersNames() {
        return getElementsNames(true);
    }

    public String[] getAllNames() {
        String[] files = getFilesNames();
        String[] dirs = getFoldersNames();

        String[] filesAndDirs = new String[files.length + dirs.length];
        System.arraycopy(dirs, 0, filesAndDirs, 0, dirs.length);
        System.arraycopy(files, 0, filesAndDirs, dirs.length, files.length);

        return filesAndDirs;
    }

    private String[] getElementsNames(boolean directories) {
        if (currentPath.isDirectory()) {
            File[] files = currentPath.listFiles((f) -> directories == f.isDirectory());
            if (files != null) {
                return Arrays.stream(files)
                        .map(File::getName)
                        .toArray(String[]::new);
            }
        }
        return new String[0];
    }

    public File getCurrentPath() {
        return currentPath;
    }

    public void goTo(String folder) throws DoesNotExistException {
        if ("..".equals(folder)) {
            goToUpper();
            return;
        }

        for (String dir : getFoldersNames()) {
            if (dir.equals(folder)) {
                currentPath = new File(currentPath.getPath() + File.separator + dir);
                return;
            }
        }
        throw new DoesNotExistException("Folder [" + folder + "] not found.");
    }

    public void goToUpper() {
        if (currentPath.getParentFile() != null) {
            currentPath = currentPath.getParentFile();
        }
    }

    public File getFile(String fileName) throws DoesNotExistException {
        if (fileName == null || fileName.isEmpty())
            throw new IllegalArgumentException("File name should not be empty.");

        for (String file : getFilesNames()) {
            if (file.equals(fileName)) {
                return new File(currentPath.getPath() + File.separator + file);
            }
        }
        throw new DoesNotExistException("File [" + fileName + "] not found.");
    }

    public void createFile(String fileName) throws AlreadyExistException, IOException {
        if (fileName == null || fileName.isEmpty())
            throw new IllegalArgumentException("File name should not be empty.");

        File file = new File(currentPath.getPath() + File.separator + fileName);

        if (!file.createNewFile())
            throw new AlreadyExistException("File [" + fileName + "] already exists.");
    }

    public void deleteFile(String fileName) throws DoesNotExistException, IOException {
        if (fileName == null || fileName.isEmpty())
            throw new IllegalArgumentException("File name should not be empty.");

        File file = getFile(fileName);

        if (!file.delete()) {
            throw new IOException("Could not delete file [" + fileName + "]");
        }
    }

    public void createFolder(String folderName) throws AlreadyExistException, IOException {
        if (folderName == null || folderName.isEmpty())
            throw new IllegalArgumentException("Folder name should not be empty.");

        File file = new File(currentPath.getPath() + File.separator + folderName);

        if (!file.mkdir())
            throw new AlreadyExistException("Folder [" + folderName + "] already exists.");
    }

    public void deleteFolder(String folderName) throws DoesNotExistException, IOException {
        if (folderName == null || folderName.isEmpty())
            throw new IllegalArgumentException("Folder name should not be empty.");

        File file = new File(currentPath.getPath() + File.separator + folderName);

        if(!file.exists()) throw new DoesNotExistException("Folder ["+folderName+"] does not exist.");

        if (!file.delete()) {
            throw new IOException("Could not delete folder [" + folderName + "]");
        }
    }
}
