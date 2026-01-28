package adapter;

// Adapter Pattern = make an existing class work with a required interface
// by wrapping it and translating method calls.

interface Logger {
    void log(String message);
}

// This is our "client" code that expects Logger
class App {
    private final Logger logger;

    public App(Logger logger) {
        this.logger = logger;
    }

    public void run() {
        logger.log("App started");
        logger.log("Doing work...");
        logger.log("App finished");
    }
}

// Imagine this is a third-party / old library class.
// We cannot change its code.
class LegacyFileLogger {
    public void writeToFile(String text) {
        System.out.println("LEGACY FILE LOGGER: " + text);
    }
}

// Adapter: makes LegacyFileLogger look like Logger
class LegacyFileLoggerAdapter implements Logger {
    private final LegacyFileLogger legacy;

    public LegacyFileLoggerAdapter(LegacyFileLogger legacy) {
        this.legacy = legacy;
    }

    // Translate log(...) into writeToFile(...)
    public void log(String message) {
        legacy.writeToFile("[LOG] " + message);
    }
}

public class Main {
    public static void main(String[] args) {
        // We have a legacy logger (wrong interface)
        LegacyFileLogger legacy = new LegacyFileLogger();

        // Wrap it in an adapter (now it matches Logger)
        Logger logger = new LegacyFileLoggerAdapter(legacy);

        // App only knows Logger, not LegacyFileLogger
        App app = new App(logger);
        app.run();
    }
}
