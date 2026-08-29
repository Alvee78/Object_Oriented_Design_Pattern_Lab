// ======================================================================
//  Boundary: AdminDashboard
//  Methods: displayDashboard, manageUsers, manageLibrarians,
//           generateReports, logout
// ======================================================================

public class AdminDashboard {

    private Admin               admin;
    private Library             library;
    private UserController      userCtrl;
    private LibrarianController libCtrl;
    private ReportController    reportCtrl;
    private BorrowController    borrowCtrl;
    private FineController      fineCtrl;
    private LoginController     loginCtrl;

    public AdminDashboard(Admin admin, Library library,
                          UserController userCtrl,
                          LibrarianController libCtrl,
                          ReportController reportCtrl,
                          BorrowController borrowCtrl,
                          FineController fineCtrl,
                          LoginController loginCtrl) {
        this.admin      = admin;
        this.library    = library;
        this.userCtrl   = userCtrl;
        this.libCtrl    = libCtrl;
        this.reportCtrl = reportCtrl;
        this.borrowCtrl = borrowCtrl;
        this.fineCtrl   = fineCtrl;
        this.loginCtrl  = loginCtrl;
    }

    public void displayDashboard() { admin.viewDashboard(library); }

    public void manageUsers()      { userCtrl.manageUsers(); }

    public void manageLibrarians() { libCtrl.manageLibrarians(); }

    public void generateReports() {
        reportCtrl.generateBorrowReport(borrowCtrl.getBorrowRecords());
        reportCtrl.generateFineReport(fineCtrl.getFines());
        reportCtrl.generateInventoryReport(library.getInventory());
        reportCtrl.generateUserReport(userCtrl.getUsers());
    }

    public void logout() { loginCtrl.logout(admin); }
}
