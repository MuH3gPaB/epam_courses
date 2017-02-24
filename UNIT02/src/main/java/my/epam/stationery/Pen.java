package my.epam.stationery;

import java.awt.*;

/**
 * Created by M.Figurin on 22-Feb-17.
 */
public class Pen {
    private final Color brushColor;
    private final Color shellColor;
    private final String brandName;
    private final String type;
    private final String label;

    public static final String FOUNTAIN_PEN_TYPE = "FOUNTAIN";
    public static final String BALLPOINT_PEN_TYPE = "BALLPOINT";
    public static final String FIBER_TIP_PEN_TYPE = "FIBER_TIP";
    public static final String ROLLER_BALL_PEN_TYPE = "ROLLER_BALL";
    public static final String GEL_INK_PEN_TYPE = "GEL_INK";

    public static final String DEFAULT_BRAND_NAME = "UNBRANDED";
    public static final String DEFAULT_TYPE = "UNDEFINED";
    public static final String DEFAULT_LABEL = "";

    public static final Pen DEFAULT_BLACK_PEN = new Pen(Color.BLACK, Color.BLACK, DEFAULT_BRAND_NAME, DEFAULT_TYPE, DEFAULT_LABEL);
    public static final Pen DEFAULT_BLUE_PEN = new Pen(Color.BLUE, Color.BLUE, DEFAULT_BRAND_NAME, DEFAULT_TYPE, DEFAULT_LABEL);
    public static final Pen DEFAULT_RED_PEN = new Pen(Color.RED, Color.RED, DEFAULT_BRAND_NAME, DEFAULT_TYPE, DEFAULT_LABEL);


    public Pen(Color brushColor, Color shellColor, String brandName, String type, String label) {
        if (brushColor == null || shellColor == null) {
            throw new IllegalArgumentException();
        }
        this.brushColor = brushColor;
        this.shellColor = shellColor;
        this.brandName = brandName == null ? DEFAULT_BRAND_NAME : brandName;
        this.type = type == null ? DEFAULT_TYPE : type;
        this.label = label == null ? DEFAULT_LABEL : label;
    }

    public Pen(Color brushColor, Color shellColor) {
        this(brushColor, shellColor, DEFAULT_BRAND_NAME, DEFAULT_TYPE, DEFAULT_LABEL);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null
                || getClass() != o.getClass())
            return false;

        Pen pen = (Pen) o;

        return brushColor.equals(pen.brushColor)
                && shellColor.equals(pen.shellColor)
                && brandName.equals(pen.brandName)
                && type.equals(pen.type)
                && label.equals(pen.label);

    }

    @Override
    public int hashCode() {
        int result = brushColor.hashCode();
        result = 31 * result + shellColor.hashCode();
        result = 31 * result + brandName.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + label.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Pen{" +
                "brushColor=" + brushColor +
                ", shellColor=" + shellColor +
                ", brandName='" + brandName + '\'' +
                ", type='" + type + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

    public Color getBrushColor() {
        return brushColor;
    }

    public Color getShellColor() {
        return shellColor;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }
}
