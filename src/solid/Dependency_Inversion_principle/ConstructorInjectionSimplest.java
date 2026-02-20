package solid.dependency_inversion_principle;

/**
 * Simplest constructor injection: one interface, one impl, one class that
 * receives the dependency via constructor (does not new it).
 */
interface Printer {
    void print(String s);
}

class ConsolePrinter implements Printer {
    @Override
    public void print(String s) {
        System.out.println(s);
    }
}

class Greeter {
    private final Printer printer;

    // Constructor injection: dependency passed in, not created here
    public Greeter(Printer printer) {
        this.printer = printer;
    }

    public void say(String msg) {
        printer.print(msg);
    }
}

public class ConstructorInjectionSimplest {
    public static void main(String[] args) {
        Greeter g = new Greeter(new ConsolePrinter());
        g.say("Hello");
    }
}
