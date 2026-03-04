// File: NotificationApp.java
// this is the interface that will be implemented by the concrete classes
interface MessageService {
    String getMessage();
}
// email service implements the message service interface means it will have the getMessage method
class EmailService implements MessageService {
    public String getMessage() {
        return "Email message sent!";
    }
}
// this is the orchestrator class that will use the message service to send the message
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
// this is the main class that will use the notification manager to send the message
public class NotificationApp {
    public static void main(String[] args) {
        MessageService service = new EmailService();
        NotificationManager manager = new NotificationManager(service);
        manager.notify();
    }
}