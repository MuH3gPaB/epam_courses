package my.epam.unit03.task01;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Simple logger class.
 *
 * Uses StringStorage implementations for storing log messages.
 *
 * Messages format looks like:
 * "dd-mm- YYYY : hh-mm – message"
 *
 * Messages should not be empty strings or null.
 *
 * You can print out all log, or some finded messages
 * to any OutputStream you like. Just use showMessages method.
 *
 * @see StringStorage
 */

public class CrazyLogger {
    private StringStorage stringStorage;

    public CrazyLogger(StringStorage stringStorage) {
        Objects.requireNonNull(stringStorage);
        this.stringStorage = stringStorage;
    }

    public void addMessage(String message) {
        if (message.isEmpty()) throw new IllegalArgumentException();
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
        String[] founded = stringStorage.findAll(pattern);
        if (founded != null) {
            for (String str : founded) {
                printOutMessage(str, out);
            }
        }
    }

    public void showAllMessagesTo(OutputStream out) throws IOException {
        for (String message : stringStorage.getAll()) {
            printOutMessage(message, out);
        }
    }

    public void clear(){
        stringStorage.clear();
    }

    private void printOutMessage(String message, OutputStream out) {
        PrintStream ps = new PrintStream(out, true);
        ps.println(message);
    }
}
