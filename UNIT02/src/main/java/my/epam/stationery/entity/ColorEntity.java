package my.epam.stationery.entity;

import java.awt.*;
import java.awt.color.ColorSpace;

public class ColorEntity implements AbstractEntity<Color>{
    public int value;
    public float frgbvalue[];
    public float fvalue[];
    public float falpha;
    public ColorSpace cs;

    public ColorEntity(){

    }

    @Override
    public Color build() {
        return new Color(value);
    }
}
