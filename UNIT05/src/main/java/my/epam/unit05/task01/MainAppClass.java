package my.epam.unit05.task01;

import my.epam.unit05.task01.processor.CommandProcessor;

import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public class MainAppClass extends Thread {

    private static MainAppClass instance;
    private final Scanner scanner;
    private final CommandProcessor processor;

    private boolean running = false;

    private MainAppClass(CommandProcessor processor, InputStream in) {
        Objects.requireNonNull(processor);
        Objects.requireNonNull(in);
        this.processor = processor;
        this.scanner = new Scanner(in);
        start();
    }

    public static void buildApp(CommandProcessor processor, InputStream in) {
        if (instance == null) {
            synchronized (MainAppClass.class) {
                if (instance == null) {
                    instance = new MainAppClass(processor, in);
                }
            }
        }
    }


    @Override
    public void run() {
        running = true;
        while (running) {
            System.out.println(scanner.nextLine());
        }
    }
}
