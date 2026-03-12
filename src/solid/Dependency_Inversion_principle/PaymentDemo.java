// the method inside this must be implemented by someone implementing this interface
interface PaymentMethod {
    void pay(int amount);
}

// pay method prints Cash payment of: amount
class CashPayment implements PaymentMethod {
    public void pay(int amount) {
        System.out.println("Cash payment of: $" + amount);
    }
}

// pay method prints Card payment of: amount
class CardPayment implements PaymentMethod {
    public void pay(int amount) {
        System.out.println("Card payment of: $" + amount);
    }
}

// below class has one job:
// it stores interface type reference
// receives type-agnostic object in constructor
// and calls agnostic.pay method inside another method
// so this class does not care whether payment is cash or card
class PaymentService {
    private PaymentMethod method;

    public PaymentService(PaymentMethod method) {
        this.method = method;
    }

    public void makePayment(int amount) {
        method.pay(amount);
    }
}

public class PaymentDemo {
    public static void main(String[] args) {
        PaymentMethod method = new CashPayment();
        PaymentService service = new PaymentService(method);
        service.makePayment(500);
    }
}

/*
Summary:
This design makes the three classes other than PaymentDemo
more stable / less affected by change.

Because in our version:

CashPayment only knows how to do cash payment

CardPayment only knows how to do card payment

PaymentService only knows:
"I use something that can make payment"

PaymentDemo decides which actual payment type to plug in
*/