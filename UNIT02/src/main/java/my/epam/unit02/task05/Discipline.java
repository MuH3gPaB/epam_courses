package my.epam.unit02.task05;

public class Discipline<T extends Number> {
    public static final Discipline<Long> MATH = new Discipline<>("Math");
    public static final Discipline<Long> HISTORY = new Discipline<>("History");
    public static final Discipline<Double> PHYSICS = new Discipline<>("Physics");

    private String name;

    private Discipline(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
