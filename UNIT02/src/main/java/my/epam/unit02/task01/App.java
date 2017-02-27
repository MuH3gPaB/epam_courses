package my.epam.unit02.task01;

import my.epam.stationery.model.Pen;

import java.awt.*;

public class App {
    public static void main(String[] args) {
        Pen pen1 = Pen.DEFAULT_BLUE_PEN;
        pen1.setPrice(100);

        Pen pen2 = new Pen(Color.BLACK, Color.CYAN, "BIC", Pen.GEL_INK_PEN_TYPE, "GelInk");
        pen2.setPrice(999);

        System.out.println(pen1);
        System.out.println(pen2);
    }
}
