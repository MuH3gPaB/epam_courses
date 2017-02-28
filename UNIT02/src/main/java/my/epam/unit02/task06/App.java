package my.epam.unit02.task06;

public class App {
    public static void main(String[] args) {
        Submarine apl = new Submarine("Lenin");

        try {
            apl.go();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        apl.setEngine(apl.new Engine(1000, "KV20"));

        apl.go();
    }
}
