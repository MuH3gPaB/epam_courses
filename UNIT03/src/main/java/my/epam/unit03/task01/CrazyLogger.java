package my.epam.unit03.task01;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class CrazyLogger {
    private AbstractStringStorage stringStorage;

    public CrazyLogger(AbstractStringStorage stringStorage) {
        Objects.requireNonNull(stringStorage);
        this.stringStorage = stringStorage;
    }

    public void addMessage(String message) {
        String messageWithHeader = addHeader(message);
        stringStorage.addString(messageWithHeader);
    }

    private String addHeader(String message) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY : HH-mm");
        String header = formatter.format(localDateTime);

        return header + " - " + message;
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
