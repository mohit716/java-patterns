package singleton;

public class DatabaseConnection {
    // 1. Private static variable to hold the one and only instance 
    private static DatabaseConnection instance = new DatabaseConnection();

    // 2. Private constructor prevents anyone else from saying "new DatabaseConnection()" 
    private DatabaseConnection() {
        System.out.println("Connecting to the Database...");
    }

    // 3. Public static method to give the user the single instance 
    public static DatabaseConnection getInstance() {
        return instance;
    }

    // An example of a "normal" method you might use later
    public void executeQuery(String query) {
        System.out.println("Executing: " + query);
    }
}
class Main {
    public static void main(String[] args) {
        // This would cause a COMPILE ERROR:
        // DatabaseConnection db = new DatabaseConnection();

        // Instead, we call the getInstance() method: [cite: 1]
        DatabaseConnection connection1 = DatabaseConnection.getInstance();
        connection1.executeQuery("SELECT * FROM users");

        // If we try to get another one...
        DatabaseConnection connection2 = DatabaseConnection.getInstance();
        
        // Both connection1 and connection2 are actually the exact same object!
        System.out.println(connection1 == connection2); // This will print: true
    }
}