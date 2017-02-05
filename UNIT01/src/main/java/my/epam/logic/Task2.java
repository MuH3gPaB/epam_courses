package my.epam.logic;

import java.util.NoSuchElementException;

public class Task2 {
    private static final int MAX_CYCLE_ITERATIONS = 1000;

    public static void getArrayIndex(double e) {
        if(e <= 0){
            System.out.println("No elements matches a[n] < " + e);
            return;
        }

        System.out.println("Looking for element matches a[n] < " + e);
        int n = 0;
        double a = 1;
        System.out.println("a[" + n + "] = " + a);
        while (a >= e) {
            n++;
            a = 1 / Math.pow((n + 1), 2);
            System.out.println("a[" + n + "] = " + a);
            if(n > MAX_CYCLE_ITERATIONS) throw new NoSuchElementException();
        }
        System.out.println("Element found at min index n = " + n);
        System.out.println();
    }
}
