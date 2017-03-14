package my.epam.unit05.task01;

import my.epam.unit05.task01.processor.CommandProcessor;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public class MainAppClass extends Thread {
    private static Logger logger = Logger.getLogger(MainAppClass.class);

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
            try {
                processor.proceed(scanner.nextLine());
            }catch (IOException e){
                logger.error(e.getMessage());
            }
        }
    }

    public static void stopApp(){
        instance.running = false;
    }
}
