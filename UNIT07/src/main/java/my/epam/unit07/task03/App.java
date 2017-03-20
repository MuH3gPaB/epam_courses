package my.epam.unit07.task03;

import java.util.ArrayList;

public class App {

    public static void main(String[] args) throws InterruptedException {
        SharedResource res = new SharedResource();
        ThreadGroup group = new ThreadGroup("IntSettersGetters");

        ArrayList<IntegerSetterGetter> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            threads.add(new IntegerSetterGetter(group, "" + i, res));
        }

        threads.forEach(Thread::start);

        Thread.sleep(1000);

        threads.forEach(IntegerSetterGetter::stopThread);

        for (Thread thread : threads) {
            thread.join();
        }
    }
}

