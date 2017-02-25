package my.epam.stationery.model;

import my.epam.stationery.entity.AbstractEntity;

import java.awt.*;

/**
 * Pen class.
 * <p>
 * Pen class represents simple instance of pen.
 * Pens are constant (immutable).
 * <p>
 * You can create new Pen with one of two constructors.
 * <p>
 * <blockquote><pre>
 * Pen penOne = new Pen(Color.black, Color.green, "BIC", Pen.BALLPOINT_PEN_TYPE, "RollerBall");
 * Pen penTwo = new Pen(Color.black, Color.green);
 * </pre></blockquote>
 * <p>
 * If use short constructor undefined fields set to default values.
 * Default values for brandName, type and label accessable as public constants.
 * <p>
 * Also, some types of pens are available as public String constants:
 * <blockquote><pre>
 * FOUNTAIN_PEN_TYPE
 * BALLPOINT_PEN_TYPE
 * FIBER_TIP_PEN_TYPE
 * ROLLER_BALL_PEN_TYPE
 * GEL_INK_PEN_TYPE
 * </pre></blockquote>
 * <p>
 * Three pens are available by default:
 * <blockquote><pre>
 * DEFAULT_BLACK_PEN
 * DEFAULT_BLUE_PEN
 * DEFAULT_RED_PEN
 * </pre></blockquote>
 *
 * @author M.Figurin
 */
public class Pen extends Stationery {
    private final Color brushColor;
    private final Color shellColor;

    public static final String FOUNTAIN_PEN_TYPE = "FOUNTAIN";
    public static final String BALLPOINT_PEN_TYPE = "BALLPOINT";
    public static final String FIBER_TIP_PEN_TYPE = "FIBER_TIP";
    public static final String ROLLER_BALL_PEN_TYPE = "ROLLER_BALL";
    public static final String GEL_INK_PEN_TYPE = "GEL_INK";

    public static final Pen DEFAULT_BLACK_PEN = new Pen(Color.BLACK, Color.BLACK, DEFAULT_BRAND_NAME, DEFAULT_TYPE, DEFAULT_LABEL);
    public static final Pen DEFAULT_BLUE_PEN = new Pen(Color.BLUE, Color.BLUE, DEFAULT_BRAND_NAME, DEFAULT_TYPE, DEFAULT_LABEL);
    public static final Pen DEFAULT_RED_PEN = new Pen(Color.RED, Color.RED, DEFAULT_BRAND_NAME, DEFAULT_TYPE, DEFAULT_LABEL);

    public Pen(Color brushColor, Color shellColor, String brandName, String type, String label) {
        super(brandName, type, label);
        if (brushColor == null || shellColor == null) {
            throw new IllegalArgumentException();
        }
        this.brushColor = brushColor;
        this.shellColor = shellColor;
    }

    /**
     * <u>Note:</u> If use this short constructor undefined fields set to default values.
     */

    public Pen(Color brushColor, Color shellColor) {
        this(brushColor, shellColor, DEFAULT_BRAND_NAME, DEFAULT_TYPE, DEFAULT_LABEL);
    }

    Pen(Long id, Color brushColor, Color shellColor, String brandName, String type, String label) {
        super(id, brandName, type, label);
        if (brushColor == null || shellColor == null) {
            throw new IllegalArgumentException();
        }
        this.brushColor = brushColor;
        this.shellColor = shellColor;
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
                && super.equals(pen);
    }

    @Override
    public int hashCode() {
        int result = brushColor.hashCode();
        result = 31 * result + shellColor.hashCode();
        result = 31 * result + super.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Pen{" +
                "brushColor=" + brushColor +
                ", shellColor=" + shellColor +
                '}' + super.toString();
    }

    public Color getBrushColor() {
        return brushColor;
    }

    public Color getShellColor() {
        return shellColor;
    }

    static class Entity implements AbstractEntity<Pen> {
        private Long id;
        private Color brushColor;
        private Color shellColor;
        private String brandName;
        private String type;
        private String label;

        public Entity() {
        }

        @Override
        public Pen build() {
            return new Pen(id, brushColor, shellColor, brandName, type, label);
        }
    }
}
