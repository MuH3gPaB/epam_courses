package my.epam.unit05.task01.processor;

import my.epam.unit05.task01.util.CustomExplorer;

import java.io.OutputStream;

public class ConsoleCommandProcessor extends CommandProcessor {
    private CustomExplorer explorer;

    public enum Commands {
        DIR("dir"),
        CD("cd"),
        EXIT("exit");

        String value;

        Commands(String value) {
            this.value = value;
        }
    }

    public ConsoleCommandProcessor(OutputStream out, CustomExplorer explorer) {
        super(out);
        this.explorer = explorer;
    }

    @Override
    public void proceed(String commandString) {
        try {
            Commands command = Commands.valueOf(Commands.class, commandString);
            proceedCommand(command);
        } catch (IllegalArgumentException e) {
            proceedUnknownCommand(commandString);
        }
    }

    private void proceedUnknownCommand(String commandString) {

    }

    private void proceedCommand(Commands commands) {
        switch (commands) {
            case DIR:
                break;
            case CD:
                break;
        }
    }


}
