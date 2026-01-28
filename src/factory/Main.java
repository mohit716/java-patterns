package factory;
// Factory Pattern = one place that decides "which object to create"
// Client (main) does NOT use `new Dog()` or `new Cat()` directly.
// It asks the Factory: "give me an Animal of type X".

interface Animal {
    void speak();
}

// Concrete classes (the real objects)
class Dog implements Animal {
    public void speak() { System.out.println("Woof"); }
}

class Cat implements Animal {
    public void speak() { System.out.println("Meow"); }
}

// Factory class (the creator)
class AnimalFactory {
    // Static factory method: hides object creation from the caller
    public static Animal create(String type) {
        // Normalize input
        String t = type.toLowerCase();

        if (t.equals("dog")) return new Dog();
        if (t.equals("cat")) return new Cat();

        // If the type is unknown, fail fast
        throw new IllegalArgumentException("Unknown animal: " + type);
    }
}

public class Main {
    public static void main(String[] args) {
        Animal a1 = AnimalFactory.create("dog");
        a1.speak();

        Animal a2 = AnimalFactory.create("cat");
        a2.speak();
    }
}
