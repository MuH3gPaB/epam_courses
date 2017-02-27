package my.epam.unit02.task05;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private String name;
    private Map<Discipline, Number> marks = new HashMap<>();

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public <T extends Number> void setMark(Discipline<T> discipline, T mark) {
        marks.put(discipline, mark);
    }

    public Discipline[] getDisciplines(){
        return marks.keySet().toArray(new Discipline[0]);
    }

    public <T extends Number> T getMark(Discipline<T> discipline){
        return (T) marks.get(discipline);
    }
}
