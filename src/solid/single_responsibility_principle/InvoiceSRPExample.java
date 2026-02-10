// File: InvoiceSRPExample.java
// Goal: Show SRP (Single Responsibility Principle) in ONE file,
// while still keeping responsibilities separated into different classes.

import java.util.ArrayList;
import java.util.List;

public class InvoiceSRPExample {

    public static void main(String[] args) {

        // 1) Build an invoice (data)
        Invoice invoice = new Invoice("INV-1001");
        invoice.addItem(new LineItem("Notebook", 2, 5.50));
        invoice.addItem(new LineItem("Pen", 5, 1.20));

        // 2) Use a workflow coordinator service that delegates responsibilities
        InvoiceCalculator calculator = new InvoiceCalculator();
        InvoiceRepository repository = new InvoiceRepository();
        InvoiceEmailSender emailSender = new InvoiceEmailSender();

        InvoiceService service = new InvoiceService(calculator, repository, emailSender);

        // 3) Process invoice (calculate -> save -> send)
        service.process(invoice);

        System.out.println("\nDone. Final invoice object:");
        System.out.println(invoice);
    }
}

/*
SRP idea:
- InvoiceCalculator: only does math / business rule total calculation.
- InvoiceRepository: only "saves" invoices (persistence responsibility).
- InvoiceEmailSender: only "sends" invoices (communication responsibility).
- InvoiceService: coordinates the workflow (orchestration responsibility).
- Invoice / LineItem: data classes only.
*/

// -------------------- Coordinator (workflow) --------------------
class InvoiceService {

    private final InvoiceCalculator calculator;
    private final InvoiceRepository repository;
    private final InvoiceEmailSender emailSender;

    public InvoiceService(InvoiceCalculator calculator,
                          InvoiceRepository repository,
                          InvoiceEmailSender emailSender) {
        this.calculator = calculator;
        this.repository = repository;
        this.emailSender = emailSender;
    }

    public void process(Invoice invoice) {

        // Step 1) business logic
        double total = calculator.calculateTotal(invoice);
        invoice.setTotal(total);

        // Step 2) persistence
        repository.save(invoice);

        // Step 3) communication
        emailSender.send(invoice);
    }
}

// -------------------- Business logic only --------------------
class InvoiceCalculator {

    public double calculateTotal(Invoice invoice) {

        double sum = 0.0;

        for (LineItem item : invoice.getItems()) {
            sum += item.getUnitPrice() * item.getQuantity();
        }

        // You could imagine tax/discount rules here too.
        // The key point: if pricing rules change, ONLY this class changes.
        return sum;
    }
}

// -------------------- Persistence only --------------------
class InvoiceRepository {

    public void save(Invoice invoice) {
        // In real life: JDBC / JPA / Hibernate / etc.
        // Here: simple console output.

        System.out.println("Saving invoice to database...");
        System.out.println("Saved: " + invoice.getInvoiceNumber() + " with total $" + formatMoney(invoice.getTotal()));
    }

    private String formatMoney(double value) {
        return String.format("%.2f", value);
    }
}

// -------------------- Communication only --------------------
class InvoiceEmailSender {

    public void send(Invoice invoice) {
        // In real life: SMTP / SES / SendGrid / etc.
        // Here: simple console output.

        System.out.println("Sending invoice email...");
        System.out.println("To: customer@example.com");
        System.out.println("Subject: Your invoice " + invoice.getInvoiceNumber());
        System.out.println("Body: Total due = $" + String.format("%.2f", invoice.getTotal()));
    }
}

// -------------------- Data classes only --------------------
class Invoice {

    private final String invoiceNumber;
    private final List<LineItem> items;
    private double total; // calculated

    public Invoice(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        this.items = new ArrayList<>();
        this.total = 0.0;
    }

    public void addItem(LineItem item) {
        items.add(item);
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Invoice{number=").append(invoiceNumber).append(", items=\n");

        for (LineItem li : items) {
            sb.append("  ").append(li).append("\n");
        }

        sb.append("total=$").append(String.format("%.2f", total)).append("}");
        return sb.toString();
    }
}

class LineItem {

    private final String name;
    private final int quantity;
    private final double unitPrice;

    public LineItem(String name, int quantity, double unitPrice) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    @Override
    public String toString() {
        return "LineItem{name=" + name +
               ", qty=" + quantity +
               ", unitPrice=$" + String.format("%.2f", unitPrice) +
               "}";
    }
}
