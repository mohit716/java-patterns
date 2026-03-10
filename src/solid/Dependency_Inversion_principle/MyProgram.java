interface MessageSender {
    void send(String msg);
}

class EmailSender implements MessageSender {
    public void send(String msg) {
        System.out.println("Email: " + msg);
    }
}

class SMSsender implements MessageSender {
    public void send(String msg) {
        System.out.println("SMS: " + msg);
    }
}

class NotificationService {
    private MessageSender sender;

    public NotificationService(MessageSender sender) {
        this.sender = sender;
    }

    public void notifyUser(String msg) {
        sender.send(msg);
    }
}

public class MyProgram {
    public static void main(String[] args) {
        MessageSender sender = new EmailSender();
        NotificationService service = new NotificationService(sender);
        service.notifyUser("Order placed");
    }
}