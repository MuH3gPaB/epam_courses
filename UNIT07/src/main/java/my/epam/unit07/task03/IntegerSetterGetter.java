package my.epam.unit07.task03;

import org.apache.log4j.Logger;

import java.util.NoSuchElementException;
import java.util.Random;

public class IntegerSetterGetter extends Thread {
    private static final Logger logger = Logger.getLogger(IntegerSetterGetter.class);

    private final SharedResource resource;
    private boolean running;

    private Random rand = new Random();

    public IntegerSetterGetter(ThreadGroup group, String name, SharedResource resource) {
        super(group, name);
        this.resource = resource;
    }

    public void stopThread() {
        running = false;
    }

    public void run() {
        running = true;
        try {
            while (running) {
                synchronized (resource) {
                    Thread me = currentThread();

                    int action = rand.nextInt(1000);
                    if (imLastWriter() || action % 2 == 0) {
                        me.setName("wrt" + me.getName());
                        setIntegersIntoResource();
                    } else {
                        me.setName("rdr" + me.getName());
                        getIntegersFromResource();
                    }

                    me.setName(me.getName().substring(3));
                }
            }
            logger.info("Поток " + getName() + " завершил работу.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            logger.info("Поток " + getName() + " получил уведомлене об отсутствии данных и завершил работу.");
        }
    }

    private boolean imLastWriter() {
        ThreadGroup group = currentThread().getThreadGroup();
        Thread[] threads = new Thread[group.activeCount() + 10];
        group.enumerate(threads);
        int nonReadersCount = 0;
        for (Thread thread : threads) {
            if (thread != null && !thread.getName().startsWith("rdr")) {
                nonReadersCount++;
            }
        }
        return nonReadersCount == 1;
    }

    private void getIntegersFromResource() throws InterruptedException {
        logger.info("Поток " + getName() + " хочет извлечь число.");
        Integer number = resource.getElement();
        while (number == null) {
            logger.info("Поток " + getName() + " ждет пока очередь заполнится.");
            resource.wait();
            logger.info("Поток " + getName() + " возобновил работу.");
            number = resource.getElement();
        }
        logger.info("Поток " + getName() + " извлек число " + number);
    }

    private void setIntegersIntoResource() throws InterruptedException {
        Integer number = rand.nextInt(500);
        resource.setElement(number);
        logger.info("Поток " + getName() + " записал число " + number);
        resource.notify();
    }
}
