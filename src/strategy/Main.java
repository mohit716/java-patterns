package strategy;

// Strategy Pattern = choose an algorithm/behavior at runtime
// without changing the Context class.

interface PaymentStrategy {
    void pay(int amount);
}

// Concrete strategies
class CashPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid $" + amount + " using CASH");
    }
}

class CreditCardPayment implements PaymentStrategy {
    private String cardLast4;

    public CreditCardPayment(String cardLast4) {
        this.cardLast4 = cardLast4;
    }

    public void pay(int amount) {
        System.out.println("Paid $" + amount + " using CREDIT CARD (**** " + cardLast4 + ")");
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    public void pay(int amount) {
        System.out.println("Paid $" + amount + " using PAYPAL (" + email + ")");
    }
}

// Context (uses a strategy)
class PaymentProcessor {
    private PaymentStrategy strategy;

    public PaymentProcessor(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void checkout(int amount) {
        // Context doesn’t know WHICH payment it is.
        // It just calls the strategy.
        strategy.pay(amount);
    }
}

public class Main {
    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor(new CashPayment());
        processor.checkout(100);

        processor.setStrategy(new CreditCardPayment("1234"));
        processor.checkout(250);

        processor.setStrategy(new PayPalPayment("mohit@example.com"));
        processor.checkout(75);
    }
}
