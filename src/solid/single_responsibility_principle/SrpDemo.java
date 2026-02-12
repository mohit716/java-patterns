// srp simple example
public class SrpDemo {
    public static void main(String[] args) {
        User u = new User("Mohit");
        new UserPrinter().print(u);
    }
}

class User {
    String name;
    User(String name) { this.name = name; }
}

class UserPrinter {
    void print(User u) { System.out.println("User: " + u.name); }
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
UserPrinter only prints.

that is what we did in the code (uncommented)

*/