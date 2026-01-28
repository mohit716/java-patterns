package mediator;

import java.util.ArrayList;
import java.util.List;

// Mediator Pattern = objects communicate through a mediator
// instead of calling each other directly.

interface ChatMediator {
    void register(User user);
    void send(String message, User from);
}

// Concrete Mediator
class ChatRoom implements ChatMediator {
    private final List<User> users = new ArrayList<>();

    public void register(User user) {
        users.add(user);
    }

    public void send(String message, User from) {
        // deliver message to everyone except sender
        for (User u : users) {
            if (u != from) {
                u.receive(from.getName() + ": " + message);
            }
        }
    }
}

// Colleague
abstract class User {
    protected final ChatMediator mediator;
    protected final String name;

    public User(ChatMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public String getName() { return name; }

    public void send(String message) {
        mediator.send(message, this);
    }

    public abstract void receive(String message);
}

// Concrete Colleague
class BasicUser extends User {
    public BasicUser(ChatMediator mediator, String name) {
        super(mediator, name);
    }

    public void receive(String message) {
        System.out.println(name + " received -> " + message);
    }
}

public class Main {
    public static void main(String[] args) {
        ChatMediator room = new ChatRoom();

        User mohit = new BasicUser(room, "Mohit");
        User aman  = new BasicUser(room, "Aman");
        User riya  = new BasicUser(room, "Riya");

        room.register(mohit);
        room.register(aman);
        room.register(riya);

        mohit.send("Hi everyone!");
        aman.send("Yo Mohit!");
    }
}
