package my.epam.unit05.task01;

import my.epam.unit05.task01.processor.ConsoleCommandProcessor;
import my.epam.unit05.task01.util.CustomExplorer;
import org.apache.log4j.Logger;

import java.io.IOException;

public class App {
    private static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {
        try {
            MainAppClass.buildApp(new ConsoleCommandProcessor(System.out, new CustomExplorer("")), System.in);
        } catch (IOException e) {
            logger.error(e.getMessage());
            System.exit(1);
        }
    }
}
