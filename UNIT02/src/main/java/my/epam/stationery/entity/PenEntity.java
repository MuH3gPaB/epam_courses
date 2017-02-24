package my.epam.stationery.entity;

import my.epam.stationery.model.Pen;


import java.awt.*;

public class PenEntity implements AbstractEntity<Pen>{
    public Color brushColor;
    public Color shellColor;
    private String brandName;
    private String type;
    private String label;

    public PenEntity(){}

    @Override
    public Pen build() {
        return new Pen(brushColor, shellColor, brandName, type, label);
    }
}
