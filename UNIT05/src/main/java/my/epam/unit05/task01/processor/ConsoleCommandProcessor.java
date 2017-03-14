package my.epam.unit05.task01.processor;

import my.epam.unit05.task01.MainAppClass;
import my.epam.unit05.task01.exceptions.DoesNotExistException;
import my.epam.unit05.task01.util.CustomExplorer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class ConsoleCommandProcessor extends CommandProcessor {
    private CustomExplorer explorer;
    private final static String LINE_SEPARATOR = System.getProperty("line.separator");

    public enum Commands {
        DIR("dir"),
        CD("cd"),
        EXIT("exit");

        String value;

        Commands(String value) {
            this.value = value;
        }
    }

    public ConsoleCommandProcessor(OutputStream out, CustomExplorer explorer) throws IOException {
        super(out);
        this.explorer = explorer;
        printPreamble();
    }

    @Override
    public void proceed(String commandString) throws IOException {
        try {
            String[] commandStrings = commandString.trim().split(" ");
            Commands command = Commands.valueOf(Commands.class, commandStrings[0].toUpperCase());
            String[] arguments = new String[commandStrings.length - 1];
            System.arraycopy(commandStrings, 1, arguments, 0, arguments.length);
            proceedCommand(command, arguments);
        } catch (IllegalArgumentException e) {
            proceedUnknownCommand(commandString);
        }
    }

    private void proceedUnknownCommand(String commandString) throws IOException {
        String output = "Unknown command [" + commandString + "]" + ". 'help' for command list.";
        printOut(output);
        printPreamble();
    }

    private void proceedCommand(Commands commands, String[] arguments) throws IOException {
        switch (commands) {
            case DIR:
                onDir(arguments);
                break;
            case CD:
                onCD(arguments);
                break;
            case EXIT:
                onExit(arguments);
        }
    }

    protected void onExit(String[] arguments) {
        MainAppClass.stopApp();
    }

    protected void onCD(String[] arguments) throws IOException {
        try {
            explorer.goTo(arguments[0]);
            printPreamble();
        } catch (DoesNotExistException e) {
            printOut("Folder [" + arguments[0] + "] does not exist.");
            printPreamble();
        }
    }

    protected void onDir(String[] arguments) throws IOException {
        arrayPrintOut(explorer.getAllNames());
    }

    private void arrayPrintOut(String[] strings) throws IOException {
        for (String str : strings) {
            printOut(str);
        }
        printPreamble();
    }

    private void printOut(String string) throws IOException {
        out.write((string + LINE_SEPARATOR).getBytes(Charset.defaultCharset()));
        out.flush();
    }

    private void printPreamble() throws IOException {
        out.write((explorer.getCurrentPath().getCanonicalPath() + "\\>").getBytes(Charset.defaultCharset()));
    }

}
