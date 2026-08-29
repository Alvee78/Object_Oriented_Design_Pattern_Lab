// ======================================================================
//  Control: ReportController
//  Methods: generateBorrowReport, generateFineReport,
//           generateInventoryReport, generateUserReport
// ======================================================================

import java.util.List;

public class ReportController {

    private Report report;

    public ReportController(Report report) {
        this.report = report;
    }

    public void generateBorrowReport(List<BorrowRecord> records) {
        report.setBorrowRecords(records);
        report.generateBorrowReport();
    }

    public void generateFineReport(List<Fine> fines) {
        report.setFines(fines);
        report.generateFineReport();
    }

    public void generateInventoryReport(List<Book> inventory) {
        report.setInventory(inventory);
        report.generateInventoryReport();
    }

    public void generateUserReport(List<User> users) {
        report.setUsers(users);
        report.generateUserReport();
    }
}
