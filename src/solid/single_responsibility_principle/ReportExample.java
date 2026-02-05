package solid.single_responsibility_principle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// ---------------------------------------------------------------------------
// SRP Example 1: Report — each class has one job. All in one file for convenience.
// ---------------------------------------------------------------------------

// report has two private variables , one constructor that i call variable setters
// it also has two getters for the variables
// i think once its set it cant be changed
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
// the above all does is enable a support for multivariable named report(in proper lanbuage called class) 
// we didnt created a vallidator function in report because it will violate the single responsibility principle
// the below only checks if above multivariable/class object  is valid or not
class ReportValidator {
    public boolean isValid(Report report) {
        if (report == null) return false;
        if (report.getTitle() == null || report.getTitle().trim().isEmpty()) return false;
        if (report.getContent() == null || report.getContent().trim().isEmpty()) return false;
        if (report.getTitle().length() > 200) return false;
        return true;
    }
}
// now the report had content might be provided from the main method when report was created
// we didnt extracted the content in report class because it will violate the single responsibility principle
// we extracted the content in the below class because it is a separate responsibility
class ReportFileWriter {
    public void writeToFile(Report report, String filename) throws IOException {
        Path path = Paths.get(filename);
        String content = report.getTitle() + "\n---\n" + report.getContent();
        Files.writeString(path, content);
        System.out.println("Report written to: " + path.toAbsolutePath());
    }
}
// now the report is passed and toaddress is provided from the main method
// we didnt receive those to things or atleast toaddress in the report class because it will violate the single responsibility principle
// and we can observe it will increase constructor complexity and will make the class more complex and harder to understand
// and we extracted the toaddress in the below class because it is a separate responsibility
//send report to address is a separate responsibility
class ReportEmailSender {
    public void send(Report report, String toAddress) {
        System.out.println("--- Email (simulated) ---");
        System.out.println("To: " + toAddress);
        System.out.println("Subject: " + report.getTitle());
        System.out.println("Body: " + report.getContent());
        System.out.println("--- Sent ---");
    }
}
// this is like the main class in which we create the report object and pass it to the validator, file writer and email sender
public class ReportExample {
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
