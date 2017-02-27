package my.epam.unit02.task03;

import my.epam.stationery.model.Pen;
import my.epam.stationery.model.Pencil;
import my.epam.stationery.model.Stationery;

import java.awt.Color;

public class App {
    public static Stationery[] buildNewbieSet() {
        Stationery[] newbieSet = new Stationery[4];

        Pen penBlue = new Pen(Color.BLUE, Color.BLUE, "BIC", Pen.GEL_INK_PEN_TYPE, "BluePen");
        penBlue.setPrice(999);

        Pen penRed = new Pen(Color.RED, Color.RED, "Parker", Pen.ROLLER_BALL_PEN_TYPE, "RedPen");
        penRed.setPrice(999);

        Pencil pencil = new Pencil(Color.GRAY, "NoName", Pencil.SIMPLE_PENCIL_TYPE, "Pencil", 10L);
        pencil.setPrice(100);

        Stationery clip = new Stationery("NoName", "clip", "Clip");
        clip.setPrice(10);

        newbieSet[0] = penBlue;
        newbieSet[1] = penRed;
        newbieSet[2] = clip;
        newbieSet[3] = pencil;

        return newbieSet;
    }
}

