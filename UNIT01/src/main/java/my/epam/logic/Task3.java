package my.epam.logic;

public class Task3 {
    private static final double MIN_STEP_VALUE = 0.00001;

    public static void getFunctionValue(double startIncl, double endIncl, double step) {
        if (Math.abs(step) < MIN_STEP_VALUE) {
            System.out.println("Absolute step value could not be less then " + MIN_STEP_VALUE);
            System.out.println();
            return;
        }

        if (startIncl > endIncl && step > 0) {
            System.out.println("Start > End. Step should be negative.");
            System.out.println();
            return;
        } else if (startIncl < endIncl && step < 0) {
            System.out.println("Start < End. Step should be positive.");
            System.out.println();
            return;
        }

        System.out.println("Calculating [start:" + startIncl + "; end:" + endIncl + "; step:" + step + "]...");
        if (step > 0) {
            for (double i = startIncl; i <= endIncl; i += step) {
                System.out.println(String.format("| %f | %f |", i, Math.tan(2 * i) - 3));
            }
        } else {
            for (double i = startIncl; i >= endIncl; i += step) {
                System.out.println(String.format("| %f | %f |", i, Math.tan(2 * i) - 3));
            }
        }
        System.out.println();
    }
}
