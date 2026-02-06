class Report:
    def __init__(self, title, content):
        self.title = title
        self.content = content
    def getTitle(self):
        return self.title
    def getContent(self):
        return self.content

class ReportValidator:
    def isValid(self, report):
        if report is None:
            return False
        if report.getTitle() is None or report.getTitle().strip() == '':
            return False
        if len(report.getTitle()) > 100:
            return False
        return True

class ReportFileWriter:
    def writeToFile(self, report, filename):
        with open(filename, 'w') as f:
            f.write(f'Report {report.getTitle()} | Content {report.getContent()}')

class ReportEmailSender:
    def send(self, report, toAddress):
        print(f'Report {report.getTitle()} has been sent to {toAddress}')

if __name__ == "__main__":
    report = Report("Monthly Sales", "Sales are up 10% this month.")
    validator = ReportValidator()
    if not validator.isValid(report):
        print("Report validation failed.")
        exit(1)
    print("Report validated.")
    fileWriter = ReportFileWriter()
    fileWriter.writeToFile(report, "report.txt")
    emailSender = ReportEmailSender()
    emailSender.send(report, "manager@company.com")

