# Python implementation of OrderExample (SRP): each class has one job.

class Order:
    """Responsibility: store order details."""
    def __init__(self, order_id, customer_id, total, item_count):
        self.id = order_id
        self.customer_id = customer_id
        self.total = total
        self.item_count = item_count

    def get_id(self):
        return self.id
    def get_customer_id(self):
        return self.customer_id
    def get_total(self):
        return self.total
    def get_item_count(self):
        return self.item_count


class OrderValidator:
    """Responsibility: validate order (business rules)."""
    def is_valid(self, order):
        if order is None:
            return False
        if not order.get_id() or not str(order.get_id()).strip():
            return False
        if not order.get_customer_id() or not str(order.get_customer_id()).strip():
            return False
        if order.get_total() <= 0:
            return False
        if order.get_item_count() <= 0:
            return False
        return True


class OrderStorage:
    """Responsibility: persist order to a file."""
    def save(self, order):
        path = f"order_{order.get_id()}.txt"
        content = (
            f"Order {order.get_id()} | Customer {order.get_customer_id()} | "
            f"Total {order.get_total():.2f} | Items {order.get_item_count()}"
        )
        with open(path, "w") as f:
            f.write(content)
        print(f"Order saved to: {path}")


class OrderNotifier:
    """Responsibility: notify customer about order."""
    def send_confirmation(self, order):
        print("--- Confirmation (simulated) ---")
        print(f"To customer: {order.get_customer_id()}")
        print(f"Order {order.get_id()} confirmed. Total: {order.get_total()}, Items: {order.get_item_count()}")
        print("--- Sent ---")


if __name__ == "__main__":
    order = Order("ORD-001", "cust-42", 99.50, 3)

    validator = OrderValidator()
    if not validator.is_valid(order):
        print("Order validation failed.")
        exit(1)
    print("Order validated.")

    storage = OrderStorage()
    storage.save(order)

    notifier = OrderNotifier()
    notifier.send_confirmation(order)
