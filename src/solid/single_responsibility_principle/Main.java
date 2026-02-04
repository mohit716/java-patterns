package solid.single_responsibility_principle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// ---------------------------------------------------------------------------
// SRP: Each class has one job. All in one file for convenience.
// ---------------------------------------------------------------------------

class Report {
    private final String title;
    private final String content;

    public Report(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}

class ReportValidator {
    public boolean isValid(Report report) {
        if (report == null) return false;
        if (report.getTitle() == null || report.getTitle().trim().isEmpty()) return false;
        if (report.getContent() == null || report.getContent().trim().isEmpty()) return false;
        if (report.getTitle().length() > 200) return false;
        return true;
    }
}

class ReportFileWriter {
    public void writeToFile(Report report, String filename) throws IOException {
        Path path = Paths.get(filename);
        String content = report.getTitle() + "\n---\n" + report.getContent();
        Files.writeString(path, content);
        System.out.println("Report written to: " + path.toAbsolutePath());
    }
}

class ReportEmailSender {
    public void send(Report report, String toAddress) {
        System.out.println("--- Email (simulated) ---");
        System.out.println("To: " + toAddress);
        System.out.println("Subject: " + report.getTitle());
        System.out.println("Body: " + report.getContent());
        System.out.println("--- Sent ---");
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        Report report = new Report("Monthly Sales", "Sales are up 10% this month.");

        ReportValidator validator = new ReportValidator();
        if (!validator.isValid(report)) {
            System.out.println("Report validation failed.");
            return;
        }
        System.out.println("Report validated.");

        ReportFileWriter fileWriter = new ReportFileWriter();
        fileWriter.writeToFile(report, "report.txt");

        ReportEmailSender emailSender = new ReportEmailSender();
        emailSender.send(report, "manager@company.com");
    }
}
