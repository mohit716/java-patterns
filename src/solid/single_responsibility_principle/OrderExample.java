package solid.single_responsibility_principle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// ---------------------------------------------------------------------------
// SRP Example 2: Order processing — each class has one job.
// ---------------------------------------------------------------------------
// class Order's responsibility is to store the order details
class Order {
    private final String id;
    private final String customerId;
    private final double total;
    private final int itemCount;

    public Order(String id, String customerId, double total, int itemCount) {
        this.id = id;
        this.customerId = customerId;
        this.total = total;
        this.itemCount = itemCount;
    }

    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public double getTotal() { return total; }
    public int getItemCount() { return itemCount; }
}
// class OrderValidator's responsibility is to validate the order 
// the getters we set above were useful to get the values of the variables in the order class to OrderValidator class
// and validate the order based on the business rules
/** One job: validate order (business rules). Changes only when validation rules change. */
class OrderValidator {
    public boolean isValid(Order order) {
        if (order == null) return false;
        if (order.getId() == null || order.getId().trim().isEmpty()) return false;
        if (order.getCustomerId() == null || order.getCustomerId().trim().isEmpty()) return false;
        if (order.getTotal() <= 0) return false;
        if (order.getItemCount() <= 0) return false;
        return true;
    }
}
// class OrderStorage's responsibility is to persist the order to a file
// files.writeString is used to write the order details to a file that can be found in the project directory
/** One job: persist order. Changes only when storage (file/DB) changes. */
class OrderStorage {
    public void save(Order order) throws IOException {
        Path path = Paths.get("order_" + order.getId() + ".txt");
        String content = String.format("Order %s | Customer %s | Total %.2f | Items %d",
                order.getId(), order.getCustomerId(), order.getTotal(), order.getItemCount());
        Files.writeString(path, content);
        System.out.println("Order saved to: " + path.toAbsolutePath());
    }
}

/** One job: notify about order. Changes only when notification channel (email/SMS) changes. */
class OrderNotifier {
    public void sendConfirmation(Order order) {
        System.out.println("--- Confirmation (simulated) ---");
        System.out.println("To customer: " + order.getCustomerId());
        System.out.println("Order " + order.getId() + " confirmed. Total: " + order.getTotal() + ", Items: " + order.getItemCount());
        System.out.println("--- Sent ---");
    }
}

public class OrderExample {
    public static void main(String[] args) throws IOException {
        Order order = new Order("ORD-001", "cust-42", 99.50, 3);

        OrderValidator validator = new OrderValidator();
        if (!validator.isValid(order)) {
            System.out.println("Order validation failed.");
            return;
        }
        System.out.println("Order validated.");

        OrderStorage storage = new OrderStorage();
        storage.save(order);

        OrderNotifier notifier = new OrderNotifier();
        notifier.sendConfirmation(order);
    }
}
