package my.epam.unit05.task01;

import java.io.File;
import java.util.Arrays;
import java.util.NoSuchElementException;

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

    public String[] getDirsNames() {
        return getElementsNames(true);
    }

    public String[] getAllNames() {
        String[] files = getFilesNames();
        String[] dirs = getDirsNames();

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

    public void goTo(String folder) {
        if ("..".equals(folder)) {
            goToUpper();
            return;
        }

        for (String dir : getDirsNames()) {
            if (dir.equals(folder)) {
                currentPath = new File(currentPath.getPath() + File.separator + dir);
                return;
            }
        }
        throw new NoSuchElementException("Folder [" + folder + "] not found.");
    }

    public void goToUpper() {
        if (currentPath.getParentFile() != null) {
            currentPath = currentPath.getParentFile();
        }
    }
}
