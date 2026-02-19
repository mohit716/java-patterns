package solid.dependency_inversion_principle;

/**
 * Constructor injection: dependencies are passed in via the constructor.
 * The class does not create its own dependencies; the caller provides them.
 */

// Abstraction (the "wrap")
interface UserRepository {
    void save(String user);
    String find(String id);
}

// Concrete implementation 1
class DatabaseUserRepository implements UserRepository {
    @Override
    public void save(String user) {
        System.out.println("DB: saved " + user);
    }

    @Override
    public String find(String id) {
        return "User-" + id;
    }
}

// Concrete implementation 2 (e.g. for tests)
class InMemoryUserRepository implements UserRepository {
    private java.util.Map<String, String> store = new java.util.HashMap<>();

    @Override
    public void save(String user) {
        store.put(user, user);
        System.out.println("InMemory: saved " + user);
    }

    @Override
    public String find(String id) {
        return store.getOrDefault(id, null);
    }
}

// Service depends on abstraction; dependency is INJECTED via CONSTRUCTOR
class UserService {
    private final UserRepository repo;

    // Constructor injection: repo is provided from outside, not new'd here
    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public void register(String name) {
        repo.save(name);
    }

    public String getUser(String id) {
        return repo.find(id);
    }
}

public class ConstructorInjectionExample {
    public static void main(String[] args) {
        // Caller chooses which "raw ball" to pass (the wrap is UserRepository)
        UserService withDb = new UserService(new DatabaseUserRepository());
        withDb.register("Alice");

        UserService withMemory = new UserService(new InMemoryUserRepository());
        withMemory.register("Bob");
    }
}
