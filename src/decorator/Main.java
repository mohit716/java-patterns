package decorator;

// Decorator Pattern = wrap an object to add new behavior/features,
// without changing the original object’s class.

// Component
interface Coffee {
    String getDescription();
    int cost();
}

// Concrete Component (base object)
class SimpleCoffee implements Coffee {
    public String getDescription() { return "Coffee"; }
    public int cost() { return 50; }
}

// Decorator base class (also implements Coffee)
abstract class CoffeeDecorator implements Coffee {
    protected Coffee inner; // the wrapped coffee

    public CoffeeDecorator(Coffee inner) {
        this.inner = inner;
    }

    public String getDescription() { return inner.getDescription(); }
    public int cost() { return inner.cost(); }
}

// Concrete Decorators (wrappers)
class Milk extends CoffeeDecorator {
    public Milk(Coffee inner) { super(inner); }

    public String getDescription() {
        return inner.getDescription() + " + Milk";
    }

    public int cost() {
        return inner.cost() + 10;
    }
}

class Sugar extends CoffeeDecorator {
    public Sugar(Coffee inner) { super(inner); }

    public String getDescription() {
        return inner.getDescription() + " + Sugar";
    }

    public int cost() {
        return inner.cost() + 5;
    }
}

class WhippedCream extends CoffeeDecorator {
    public WhippedCream(Coffee inner) { super(inner); }

    public String getDescription() {
        return inner.getDescription() + " + WhippedCream";
    }

    public int cost() {
        return inner.cost() + 15;
    }
}

public class Main {
    public static void main(String[] args) {
        // Base coffee
        Coffee c1 = new SimpleCoffee();
        System.out.println(c1.getDescription() + " = " + c1.cost());

        // Wrap it with Milk
        Coffee c2 = new Milk(new SimpleCoffee());
        System.out.println(c2.getDescription() + " = " + c2.cost());

        // Wrap multiple times (this is the point)
        Coffee c3 = new WhippedCream(new Sugar(new Milk(new SimpleCoffee())));
        System.out.println(c3.getDescription() + " = " + c3.cost());
    }
}
