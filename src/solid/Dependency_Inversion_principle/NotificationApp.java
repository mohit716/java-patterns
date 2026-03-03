// File: NotificationApp.java

interface MessageService {
    String getMessage();
}

class EmailService implements MessageService {
    public String getMessage() {
        return "Email message sent!";
    }
}

class NotificationManager {
    private final MessageService service;

    // Constructor injection
    public NotificationManager(MessageService service) {
        this.service = service;
    }

    public void notify() {
        System.out.println(service.getMessage());
    }
}

public class NotificationApp {
    public static void main(String[] args) {
        MessageService service = new EmailService();
        NotificationManager manager = new NotificationManager(service);
        manager.notify();
    }
}