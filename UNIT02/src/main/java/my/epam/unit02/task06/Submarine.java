package my.epam.unit02.task06;

import my.epam.unit02.task07.Unit02;

@Unit02(taskNumber = 6)
public class Submarine {
    private Engine engine;
    private String name;

    public Submarine(String name) {
        this.name = name;
    }

    public void go() throws IllegalStateException {
        if(engine != null){
            System.out.println("Submarine " + name + " with engine " + engine.getName() + " start successful");
        } else {
            throw  new IllegalStateException("Submarine could not start without engine");
        }
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    class Engine{
        private final long power;
        private final String name;

        public Engine(long power, String name) {
            this.power = power;
            this.name = name;
        }

        public long getPower() {
            return power;
        }

        public String getName() {
            return name;
        }
    }
}
