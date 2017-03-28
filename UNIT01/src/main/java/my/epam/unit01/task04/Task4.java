package my.epam.unit01.task04;

public class Task4 {

    public static double getMaxPair(double[] data){
        if(data.length == 0 || data.length%2 != 0){
            throw new IllegalArgumentException("Wrong data array length = " + data.length);
        }

        double maxValue = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < data.length / 2; i++) {
            double val = data[i] + data[data.length-1-i];
            if(Double.isNaN(val)) throw new IllegalArgumentException("Double.NaN is not allowed.");
            if(val > maxValue) maxValue = val;
        }

        return maxValue;
    }
}
