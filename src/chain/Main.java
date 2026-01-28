package chain;

// Chain of Responsibility = request goes through multiple handlers in order.
// Each handler can block (stop) or pass to the next one.

class Request {
    String user;
    String token;     // pretend auth token
    int requestsMade; // pretend rate limit counter

    Request(String user, String token, int requestsMade) {
        this.user = user;
        this.token = token;
        this.requestsMade = requestsMade;
    }
}

// Base Handler
abstract class Handler {
    protected Handler next;

    public Handler setNext(Handler next) {
        this.next = next;
        return next; // allows chaining setup: a.setNext(b).setNext(c)
    }

    public boolean handle(Request req) {
        // Default behavior: pass to next if exists
        if (next == null) return true;
        return next.handle(req);
    }
}

// Handler 1: Authentication
class AuthHandler extends Handler {
    public boolean handle(Request req) {
        if (req.token == null || req.token.isEmpty()) {
            System.out.println("AuthHandler: BLOCKED (missing token)");
            return false;
        }
        System.out.println("AuthHandler: OK");
        return super.handle(req);
    }
}

// Handler 2: Validation
class ValidationHandler extends Handler {
    public boolean handle(Request req) {
        if (req.user == null || req.user.trim().isEmpty()) {
            System.out.println("ValidationHandler: BLOCKED (missing user)");
            return false;
        }
        System.out.println("ValidationHandler: OK");
        return super.handle(req);
    }
}

// Handler 3: Rate Limit
class RateLimitHandler extends Handler {
    private final int limit;

    RateLimitHandler(int limit) {
        this.limit = limit;
    }

    public boolean handle(Request req) {
        if (req.requestsMade >= limit) {
            System.out.println("RateLimitHandler: BLOCKED (limit reached)");
            return false;
        }
        System.out.println("RateLimitHandler: OK");
        return super.handle(req);
    }
}

// Final Handler: Business logic
class BusinessHandler extends Handler {
    public boolean handle(Request req) {
        System.out.println("BusinessHandler: SUCCESS for user=" + req.user);
        return true;
    }
}

public class Main {
    public static void main(String[] args) {

        // Build the chain: Auth -> Validation -> RateLimit -> Business
        Handler auth = new AuthHandler();
        Handler validate = new ValidationHandler();
        Handler rateLimit = new RateLimitHandler(3);
        Handler business = new BusinessHandler();

        auth.setNext(validate).setNext(rateLimit).setNext(business);

        System.out.println("---- Request 1 (good) ----");
        Request r1 = new Request("mohit", "token123", 0);
        auth.handle(r1);

        System.out.println("\n---- Request 2 (missing token) ----");
        Request r2 = new Request("mohit", "", 0);
        auth.handle(r2);

        System.out.println("\n---- Request 3 (rate limited) ----");
        Request r3 = new Request("mohit", "token123", 3);
        auth.handle(r3);
    }
}
