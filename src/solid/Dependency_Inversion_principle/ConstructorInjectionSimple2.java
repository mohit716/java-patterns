package solid.dependency_inversion_principle;

/**
 * Another simple constructor injection: a lamp that uses any "switch" (abstraction).
 * The Lamp does not create the switch; the caller injects it.
 */
interface Switch {
    void turnOn();
    void turnOff();
}

class RealSwitch implements Switch {
    @Override
    public void turnOn() {
        System.out.println("Light ON");
    }

    @Override
    public void turnOff() {
        System.out.println("Light OFF");
    }
}

class Lamp {
    private final Switch sw;

    public Lamp(Switch sw) {
        this.sw = sw;
    }

    public void on() {
        sw.turnOn();
    }

    public void off() {
        sw.turnOff();
    }
}

public class ConstructorInjectionSimple2 {
    public static void main(String[] args) {
        Lamp lamp = new Lamp(new RealSwitch());
        lamp.on();
        lamp.off();
    }
}
