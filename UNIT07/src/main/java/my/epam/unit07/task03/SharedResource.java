package my.epam.unit07.task03;

import org.apache.log4j.Logger;

import java.util.*;

public class SharedResource {
    private static final Logger logger = Logger.getLogger(SharedResource.class);

    private long wdtLimit = 10;
    private volatile long timer = wdtLimit;
    private boolean noMoreData = false;

    private List<Integer> list;

    public SharedResource() {
        list = new ArrayList<>();
        runWdTimer();
    }

    public void setElement(Integer element) {
        list.add(element);
        resetWdt();
    }

    private void resetWdt() {
        this.timer = wdtLimit;
    }

    public Integer getElement() {
        if (noMoreData) throw new NoSuchElementException("No more data available.");

        if (list.size() > 0) {
            return list.remove(0);
        }
        return null;
    }

    private void runWdTimer() {
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                SharedResource.this.onWdtTick();
            }
        }, 0, 1000);
    }

    private void onWdtTick() {
        logger.info(String.format("[ WDT Status : %3d | Elements : %3d ]\n", timer, list.size()));
        if (timer > 0) {
            timer--;
        } else {
            onWdtDone();
        }
    }

    private synchronized void onWdtDone() {
        noMoreData = true;
        this.notifyAll();
    }

}

