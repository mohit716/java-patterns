package template;

// Template Method Pattern = base class defines the algorithm steps (fixed order).
// Subclasses override only specific steps.

abstract class DataProcessor {

    // Template method (the fixed algorithm)
    public final void run() {
        String data = read();
        String result = process(data);
        save(result);
    }

    // Steps (some fixed, some customizable)
    protected String read() {
        System.out.println("Reading data...");
        return "hello world";
    }

    // This step varies (subclasses must define)
    protected abstract String process(String data);

    protected void save(String result) {
        System.out.println("Saving result: " + result);
    }
}

// Concrete implementations
class UppercaseProcessor extends DataProcessor {
    protected String process(String data) {
        System.out.println("Processing: convert to UPPERCASE");
        return data.toUpperCase();
    }
}

class ReverseProcessor extends DataProcessor {
    protected String process(String data) {
        System.out.println("Processing: REVERSE string");
        StringBuilder sb = new StringBuilder(data);
        return sb.reverse().toString();
    }
}

public class Main {
    public static void main(String[] args) {
        DataProcessor p1 = new UppercaseProcessor();
        p1.run();

        System.out.println();

        DataProcessor p2 = new ReverseProcessor();
        p2.run();
    }
}
