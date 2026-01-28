package singleton;

// Best-practice Singleton in Java: enum singleton
// - thread-safe by default
// - protects against reflection + serialization issues

enum AppConfig {
    INSTANCE; // the single instance

    private String env = "dev";

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}

public class Main {
    public static void main(String[] args) {
        AppConfig c1 = AppConfig.INSTANCE;
        AppConfig c2 = AppConfig.INSTANCE;

        c1.setEnv("prod");

        System.out.println("c1 env = " + c1.getEnv());
        System.out.println("c2 env = " + c2.getEnv());
        System.out.println("Same object? " + (c1 == c2));
    }
}
