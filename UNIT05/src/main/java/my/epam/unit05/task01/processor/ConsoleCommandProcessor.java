package my.epam.unit05.task01.processor;

import my.epam.unit05.task01.MainAppClass;
import my.epam.unit05.task01.exceptions.AlreadyExistException;
import my.epam.unit05.task01.exceptions.DoesNotExistException;
import my.epam.unit05.task01.util.CustomExplorer;
import my.epam.unit05.task01.util.TextFileAppender;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;

public class ConsoleCommandProcessor extends CommandProcessor {
    private CustomExplorer explorer;
    private final static String LINE_SEPARATOR = System.getProperty("line.separator");

    public enum Commands {
        DIR,
        CD,
        EXIT,
        MKDIR,
        RMDIR,
        MKFILE,
        RMFILE,
        APPEND,
        READ,
        HELP;
    }

    public ConsoleCommandProcessor(OutputStream out, CustomExplorer explorer) throws IOException {
        super(out);
        this.explorer = explorer;
        printPreamble();
    }

    @Override
    public void proceed(String commandString) throws IOException {
        if (commandString.trim().isEmpty()) {
            printPreamble();
            return;
        }

        String[] commandStrings = commandString.trim().split(" ");
        String commandName = commandStrings[0].toUpperCase();
        try {
            Commands command = Commands.valueOf(Commands.class, commandName);
            String[] arguments = new String[commandStrings.length - 1];
            System.arraycopy(commandStrings, 1, arguments, 0, arguments.length);
            proceedCommand(command, arguments);
        } catch (IllegalArgumentException e) {
            proceedUnknownCommand(commandName);
        }
    }

    private void proceedUnknownCommand(String commandString) throws IOException {
        String output = "Unknown command [" + commandString + "]" + ". 'help' for command list.";
        printOut(output);
        printPreamble();
    }

    private void proceedCommand(Commands commands, String[] arguments) throws IOException {
        switch (commands) {
            case HELP:
                onHelp(arguments);
                break;
            case READ:
                onRead(arguments);
                break;
            case APPEND:
                onAppend(arguments);
                break;
            case MKFILE:
                onMkfile(arguments);
                break;
            case RMFILE:
                onRmfile(arguments);
                break;
            case MKDIR:
                onMkdir(arguments);
                break;
            case RMDIR:
                onRmdir(arguments);
                break;
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

    private void onHelp(String[] arguments) throws IOException {
        String[] commands = new String[Commands.values().length];
        commands[0] = "dir - list current folder";
        commands[1] = "cd - change folder";
        commands[2] = "exit - exit";
        commands[3] = "mkdir - make new dir";
        commands[4] = "rmdir - remove dir";
        commands[5] = "mkfile - make new file";
        commands[6] = "rmfile - remove file";
        commands[7] = "append - append new text to existing file";
        commands[8] = "read - read text file";
        commands[9] = "help - this help";
        printOut("Available commands:");
        arrayPrintOut(commands);
    }

    protected void onRead(String[] arguments) throws IOException {
        try {
            File file = explorer.getFile(arguments[0]);
            arrayPrintOut(Files.lines(file.toPath()).toArray(String[]::new));
        } catch (DoesNotExistException e) {
            printOut(e.getMessage());
            printPreamble();
        } catch (IOException e) {
            printOut("Error reading the file.");
            printPreamble();
        }
    }

    protected void onAppend(String[] arguments) throws IOException {
        try {
            File file = explorer.getFile(arguments[0]);
            TextFileAppender appender = TextFileAppender.getAppender(file);

            StringBuilder sb = new StringBuilder();
            Arrays.stream(arguments).skip(1).forEach((s) -> sb.append(s).append(" "));
            appender.append(sb.toString());
            printPreamble();
        } catch (DoesNotExistException e) {
            printOut(e.getMessage());
            printPreamble();
        } catch (IOException e) {
            printOut("File write error.");
            printPreamble();
        }
    }

    protected void onRmfile(String[] arguments) throws IOException {
        try {
            explorer.deleteFile(arguments[0]);
            printPreamble();
        } catch (DoesNotExistException e) {
            printOut(e.getMessage());
            printPreamble();
        }
    }

    protected void onMkfile(String[] arguments) throws IOException {
        try {
            explorer.createFile(arguments[0]);
            printPreamble();
        } catch (AlreadyExistException e) {
            printOut(e.getMessage());
            printPreamble();
        }
    }

    protected void onRmdir(String[] arguments) throws IOException {
        try {
            explorer.deleteFolder(arguments[0]);
            printPreamble();
        } catch (DoesNotExistException e) {
            printOut(e.getMessage());
            printPreamble();
        }
    }

    protected void onMkdir(String[] arguments) throws IOException {
        try {
            explorer.createFolder(arguments[0]);
            printPreamble();
        } catch (AlreadyExistException e) {
            printOut(e.getMessage());
            printPreamble();
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
            printOut(e.getMessage());
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
