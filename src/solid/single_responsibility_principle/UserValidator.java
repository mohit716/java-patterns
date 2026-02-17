// // BAD: Not SRP (one class does many unrelated jobs)
// class UserServiceBad {

//     // 1) validation + 2) saving + 3) emailing + 4) printing/logging
//     public void registerUser(String email, String password) {

//         // Validation responsibility
//         if (email == null || !email.contains("@")) {
//             System.out.println("Invalid email");
//             return;
//         }
//         if (password == null || password.length() < 6) {
//             System.out.println("Password too short");
//             return;
//         }

//         // "Database" responsibility (pretend)
//         System.out.println("Saving user to database: " + email);

//         // Email responsibility (pretend)
//         System.out.println("Sending welcome email to: " + email);

//         // Logging/printing responsibility
//         System.out.println("User registration successful: " + email);
//     }
// }


// GOOD: SRP (each class has ONE job)
class UserValidator {
    public boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }

    public boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
}

class UserRepository {
    public void saveUser(String email, String password) {
        // pretend database save
        System.out.println("Saving user to database: " + email);
    }
}

class EmailService {
    public void sendWelcomeEmail(String email) {
        // pretend email send
        System.out.println("Sending welcome email to: " + email);
    }
}

class LoggerService {
    public void log(String message) {
        System.out.println(message);
    }
}

class UserServiceGood {
    private final UserValidator validator = new UserValidator();
    private final UserRepository repo = new UserRepository();
    private final EmailService emailService = new EmailService();
    private final LoggerService logger = new LoggerService();

    // Now UserServiceGood has ONE main job: "coordinate registration"
    public void registerUser(String email, String password) {

        if (!validator.isValidEmail(email)) {
            logger.log("Invalid email");
            return;
        }

        if (!validator.isValidPassword(password)) {
            logger.log("Password too short");
            return;
        }

        repo.saveUser(email, password);
        emailService.sendWelcomeEmail(email);
        logger.log("User registration successful: " + email);
    }
}
