package solid.dependency_inversion_principle;

// DIP: AlertService depends on MessageSender (interface), not on EmailSender or SmsSender.
// We pass the implementation from outside; we can swap without changing AlertService.

interface MessageSender {
    void send(String msg);
}

class EmailSender implements MessageSender {
    @Override
    public void send(String msg) {
        System.out.println("[EMAIL] " + msg);
    }
}

class SmsSender implements MessageSender {
    @Override
    public void send(String msg) {
        System.out.println("[SMS] " + msg);
    }
}

// Depends on abstraction (MessageSender), not on concrete classes.
class AlertService {
    private final MessageSender sender;

    public AlertService(MessageSender sender) {
        this.sender = sender;
    }

    public void alert(String msg) {
        sender.send(msg);
    }
}

public class DIPExample {
    public static void main(String[] args) {
        // Use email
        AlertService withEmail = new AlertService(new EmailSender());
        withEmail.alert("Hello via email");

        // Swap to SMS — no change to AlertService
        AlertService withSms = new AlertService(new SmsSender());
        withSms.alert("Hello via SMS");
    }
}
