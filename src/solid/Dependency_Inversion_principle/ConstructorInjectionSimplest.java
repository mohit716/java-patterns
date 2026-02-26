package solid.dependency_inversion_principle;

/**
 * Simplest constructor injection: one interface, one impl, one class that
 * receives the dependency via constructor (does not new it).
 */

// Abstraction (the "wrap")  // so like printer has one method print(String s) that must be implemented by the subclasses
interface Printer {
    void print(String s);
}

// it is the implementation of the interface
// whatever you give to the printer, it will print it
// so we can create like this: Printer printer = new ConsolePrinter(); printer.print("Hello");
// console printer implements it and prints the string to the console rather than directly having print method 
class ConsolePrinter implements Printer {
    @Override
    public void print(String s) {
        System.out.println(s);
    }
}
// it has a data member of the interface type
// and it is initialized via constructor
// so we can create like this: Greeter greeter = new Greeter(new ConsolePrinter()); greeter.say("Hello");
// so we can say that the Greeter class is dependent on the Printer interface
// and it is injected via constructor
// so we can create like this: Greeter greeter = new Greeter(new ConsolePrinter()); greeter.say("Hello");  
// this class will use the printer to print the message 
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
// this is the main class that will use the greeter to print the message using Printer class that implements the interface
// so we can create like this: Greeter g = new Greeter(new ConsolePrinter()); g.say("Hello");
// this class will use the printer to print the message 
// so when g is created, the printer is passed in and it is used to print the message
public class ConstructorInjectionSimplest {
    public static void main(String[] args) {
        Greeter g = new Greeter(new ConsolePrinter());
        g.say("Hello");
    }
}
