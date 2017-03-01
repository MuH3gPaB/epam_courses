package my.epam.unit03.task01;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class CrazyLogger {
    private AbstractStringStorage stringStorage;

    private CrazyLogger(AbstractStringStorage stringStorage) {
        Objects.requireNonNull(stringStorage);
        this.stringStorage = stringStorage;
    }

    public void addMessage(String message) {
        stringStorage.addString(message);
    }

    public void showMessageTo(String pattern, OutputStream out) throws IOException {
        String founded = stringStorage.findOne(pattern);
        if (founded != null) {
            out.write(founded.getBytes());
        }
    }
}
