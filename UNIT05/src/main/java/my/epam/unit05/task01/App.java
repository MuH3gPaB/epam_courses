package my.epam.unit05.task01;

import my.epam.unit05.task01.processor.ConsoleCommandProcessor;
import my.epam.unit05.task01.util.CustomExplorer;

public class App {

    public static void main(String[] args) {
        MainAppClass.buildApp(new ConsoleCommandProcessor(System.out, new CustomExplorer("./")), System.in);
    }
}
