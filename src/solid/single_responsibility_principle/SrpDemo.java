package solid.single_responsibility_principle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// SRP: User = data. UserStorage = persist to file. UserPrinter = print to console.
public class SrpDemo {
    public static void main(String[] args) throws IOException {
        User u = new User("Mohit");

        UserStorage storage = new UserStorage();
        storage.save(u);
        System.out.println("Saved.");

        new UserPrinter().print(u);

        User loaded = storage.load();
        if (loaded != null) {
            System.out.print("Loaded from file: ");
            new UserPrinter().print(loaded);
        }
    }
}

class User {
    String name;
    User(String name) { this.name = name; }
    String getName() { return name; }
}

/** One job: persist User to file. */
class UserStorage {
    private static final String FILENAME = "user.txt";

    public void save(User u) throws IOException {
        Files.writeString(Paths.get(FILENAME), u.getName());
    }

    public User load() throws IOException {
        Path path = Paths.get(FILENAME);
        if (!Files.exists(path)) return null;
        String name = Files.readString(path);
        return new User(name);
    }
}

class UserPrinter {
    void print(User u) { System.out.println("User: " + u.getName()); }
}


/* 
below is a bad eg
class User {
    String name;

    User(String name) {
        this.name = name;
    }

    void printUser() {   // <-- extra responsibility (printing)
        System.out.println("User: " + name);
    }
}
This class both stores user data and prints it.




SRP (split responsibilities)

User only holds data.
UserStorage only persists to file (save/load).
UserPrinter only prints.

That is what we did in the code (uncommented).

*/