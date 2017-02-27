package my.epam.unit02.task04;

import my.epam.stationery.model.Stationery;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class App {
    public static void main(String[] args) {
        Stationery[] newbieSet = my.epam.unit02.task03.App.buildNewbieSet();

        System.out.println("Unsorted:");
        for (Stationery stationery : newbieSet) {
            System.out.println(stationery.getLabel() + " " + stationery.getPrice());
        }
        System.out.println("---------");

        Arrays.sort(newbieSet, (o1, o2) -> o2.getLabel().compareTo(o1.getLabel()));
        System.out.println("Sorted by label:");
        for (Stationery stationery : newbieSet) {
            System.out.println(stationery.getLabel() + " " + stationery.getPrice());
        }
        System.out.println("---------");

        Arrays.sort(newbieSet, (o1, o2) -> (int) (o1.getPrice() - o2.getPrice()));
        System.out.println("Sorted by price:");
        for (Stationery stationery : newbieSet) {
            System.out.println(stationery.getLabel() + " " + stationery.getPrice());
        }
        System.out.println("---------");

        Arrays.sort(newbieSet, (o1, o2) -> {
            int result = (int) (o1.getPrice() - o2.getPrice());
            if (result == 0) {
                result = o1.getLabel().compareTo(o2.getLabel());
            }
            return result;
        });
        System.out.println("Sorted by price and label:");
        for (Stationery stationery : newbieSet) {
            System.out.println(stationery.getLabel() + " " + stationery.getPrice());
        }
        System.out.println("---------");

    }
}
