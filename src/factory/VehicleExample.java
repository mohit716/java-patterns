package factory;

// Same Factory idea, different domain: Vehicle instead of Animal.
// Client asks VehicleFactory for a vehicle by type; no "new Car()" / "new Bike()" in main.

interface Vehicle {
    void drive();
}

class Car implements Vehicle {
    public void drive() { System.out.println("Car: driving on road"); }
}

class Bike implements Vehicle {
    public void drive() { System.out.println("Bike: pedaling"); }
}

class VehicleFactory {
    public static Vehicle create(String type) {
        String t = type.toLowerCase();
        if (t.equals("car")) return new Car();
        if (t.equals("bike")) return new Bike();
        throw new IllegalArgumentException("Unknown vehicle: " + type);
    }
}

public class VehicleExample {
    public static void main(String[] args) {
        Vehicle v1 = VehicleFactory.create("car");  //create is a static method i.e it is called on the class itself not on an instance of the class
        v1.drive();

        Vehicle v2 = VehicleFactory.create("bike");
        v2.drive();
    }
}
