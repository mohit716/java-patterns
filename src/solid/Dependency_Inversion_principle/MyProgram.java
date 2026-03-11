// the method inside this must be implemented by someone implementing this interface
interface MessageSender {
    void send(String msg);
}

// send method prints email: "whatever"
class EmailSender implements MessageSender {
    public void send(String msg) {
        System.out.println("Email: " + msg);
    }
}
// SMS method prints SMS: "whatever"
class SMSsender implements MessageSender {
    public void send(String msg) {
        System.out.println("SMS: " + msg);
    }
}
// below class has one job interface's instance creation (it use interface/parent for creation)
// we did is that created interface object ,receiving type agnostic argument in constructor
// and called aganostic.send method under another method of this class
// so method to call another's method
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

/*
Summary:
This design makes the three classes other than MyProgram
more stable / less affected by change.

Because in our version:

EmailSender only knows how to send email

SMSsender only knows how to send SMS

NotificationService only knows “I use something that can send messages”

MyProgram decides which actual sender to plug in
*/
