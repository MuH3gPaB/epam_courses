package my.epam.lambda_vs_anon;

public class Main {
    String name;

    public Main(String name) {
        this.name = name;
    }

    IA getLambda() {
        return () -> {
            System.out.println("I'm lambda");
        };
    }

    IA getAnonymous() {
        return new IA() {
            @Override
            public void method() {
                System.out.println("I'm anonymous");
            }
        };
    }

    static IA getIaA() {
        Main inst = new Main("Anonymous");
        return inst.getAnonymous();
    }

    static IA getIaL() {
        Main inst = new Main("Lambda");
        return inst.getLambda();
    }

    public static void main(String[] args) {
        IA a = getIaA();
        IA b = getIaL();

        for (int i = 0; i < 10; i++) {
            System.gc();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Killing main instance! ["+name+"]");
        super.finalize();
    }
}

interface IA {
    void method();
}