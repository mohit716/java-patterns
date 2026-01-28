package state;

// State Pattern = an object changes its behavior when its internal state changes.
// Instead of if/else, we delegate to a State object.

interface OrderState {
    void pay(Order order);
    void ship(Order order);
}

// Context
class Order {
    private OrderState state;

    public Order() {
        this.state = new NewState(); // initial state
        System.out.println("Order created: NEW");
    }

    void setState(OrderState state) {
        this.state = state;
    }

    public void pay() {
        state.pay(this);
    }

    public void ship() {
        state.ship(this);
    }
}

// Concrete states
class NewState implements OrderState {
    public void pay(Order order) {
        System.out.println("Payment received. Order is now PAID.");
        order.setState(new PaidState());
    }

    public void ship(Order order) {
        System.out.println("Cannot ship. Order is not paid yet.");
    }
}

class PaidState implements OrderState {
    public void pay(Order order) {
        System.out.println("Already paid. No action.");
    }

    public void ship(Order order) {
        System.out.println("Shipping order. Order is now SHIPPED.");
        order.setState(new ShippedState());
    }
}

class ShippedState implements OrderState {
    public void pay(Order order) {
        System.out.println("Order already shipped. Payment step is done.");
    }

    public void ship(Order order) {
        System.out.println("Already shipped. No action.");
    }
}

public class Main {
    public static void main(String[] args) {
        Order order = new Order();

        order.ship(); // should fail (not paid)
        order.pay();  // becomes paid
        order.pay();  // already paid
        order.ship(); // becomes shipped
        order.ship(); // already shipped
    }
}
