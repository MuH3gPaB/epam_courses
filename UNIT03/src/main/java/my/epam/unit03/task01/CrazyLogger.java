package my.epam.unit03.task01;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public class CrazyLogger {
    private AbstractStringStorage stringStorage;

    public CrazyLogger(AbstractStringStorage stringStorage) {
        Objects.requireNonNull(stringStorage);
        this.stringStorage = stringStorage;
    }

    public void addMessage(String message) {
        stringStorage.addString(message);
    }

    public void showMessageTo(String pattern, OutputStream out) throws IOException {
        String founded = stringStorage.findOne(pattern);
        if (founded != null) {
            printOutMessage(founded, out);
        }
    }

    public void showMessagesTo(String pattern, OutputStream out) throws IOException {

    }

    public void showAllMessagesTo(OutputStream out) throws IOException {
        for (String message : stringStorage.getAll()) {
            printOutMessage(message, out);
        }
    }

    private void printOutMessage(String message, OutputStream out){
        PrintStream ps = new PrintStream(out, true);
        ps.println(message);
    }
}
