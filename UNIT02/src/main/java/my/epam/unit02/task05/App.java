package my.epam.unit02.task05;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Student vova = new Student("Vova");

        vova.setMark(Discipline.HISTORY, 10L);
        vova.setMark(Discipline.PHYSICS, 9.5D);
        vova.setMark(Discipline.MATH, 13L);

        List<Number> marks = new ArrayList<>();
        System.out.println("Disciplines:");
        for (Discipline discipline : vova.getDisciplines()) {
            System.out.println(discipline.getName());
            marks.add(vova.getMark(discipline));
        }
        System.out.println("---------");

        System.out.println("Unsorted:");
        for (Number mark : marks) {
            System.out.println(mark);
        }
        System.out.println("---------");

        marks.sort((o1, o2) -> {
            Double d1 = o1.doubleValue();
            Double d2 = o2.doubleValue();
            Double error = 1e-10;
            if (Math.abs(d2 - d1) < error) return 0;
            else return (int) Math.signum(d2 - d1);
        });
        System.out.println("Sorted:");
        for (Number mark : marks) {
            System.out.println(mark);
        }
        System.out.println("---------");


    }
}
