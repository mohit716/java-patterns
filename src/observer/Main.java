package observer;

import java.util.ArrayList;
import java.util.List;

// Observer = gets notified when subject updates
interface Subscriber {
    void update(String channelName, String videoTitle);
}

// Concrete Observer
class UserSubscriber implements Subscriber {
    private final String name;

    public UserSubscriber(String name) {
        this.name = name;
    }

    public void update(String channelName, String videoTitle) {
        System.out.println(name + " got notification: " + channelName
                + " uploaded '" + videoTitle + "'");
    }
}

// Subject = the thing being observed
class Channel {
    private final String name;
    private final List<Subscriber> subscribers = new ArrayList<>();

    public Channel(String name) {
        this.name = name;
    }

    public void subscribe(Subscriber s) {
        subscribers.add(s);
    }

    public void unsubscribe(Subscriber s) {
        subscribers.remove(s);
    }

    // When something happens, notify everyone
    public void uploadVideo(String title) {
        System.out.println("\nChannel '" + name + "' uploaded: " + title);
        notifySubscribers(title);
    }

    private void notifySubscribers(String videoTitle) {
        for (Subscriber s : subscribers) {
            s.update(name, videoTitle);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Channel ch = new Channel("Mohit Tech");

        Subscriber a = new UserSubscriber("Aman");
        Subscriber b = new UserSubscriber("Riya");
        Subscriber c = new UserSubscriber("Jay");

        ch.subscribe(a);
        ch.subscribe(b);
        ch.subscribe(c);

        ch.uploadVideo("Observer Pattern in 5 minutes");

        // Unsubscribe one user
        ch.unsubscribe(b);

        ch.uploadVideo("Factory vs Builder explained");
    }
}
