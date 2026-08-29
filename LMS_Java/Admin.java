// ======================================================================
//  Entity: Admin
//  Attributes : adminID, name, email, password
//  Methods    : manageUsers, manageLibrarians, generateReports, viewDashboard
// ======================================================================

import java.util.List;

public class Admin extends User {

    private String adminId;

    public Admin(String adminId, String name, String email, String password) {
        super(adminId, name, email, "N/A", password);
        this.adminId = adminId;
    }

    public void manageUsers(List<User> users) {
        System.out.println("\n[Admin] All Users (" + users.size() + "):");
        for (User u : users) System.out.println("  " + u);
    }

    public void manageLibrarians(List<Librarian> librarians) {
        System.out.println("\n[Admin] All Librarians (" + librarians.size() + "):");
        for (Librarian l : librarians) System.out.println("  " + l);
    }

    public void generateReports(Report report) {
        report.generateBorrowReport();
        report.generateFineReport();
        report.generateInventoryReport();
        report.generateUserReport();
    }

    public void viewDashboard(Library library) {
        System.out.println("\n[Admin] Dashboard");
        System.out.println("  Library : " + library.getLibraryName());
        System.out.println("  Address : " + library.getAddress());
        System.out.println("  Contact : " + library.getContactNumber());
        System.out.println("  Hours   : " + library.getOpeningHours());
        int total = 0;
        for (Book b : library.getInventory()) total += b.getAvailableCopies();
        System.out.println("  Books Available: " + total);
    }

    public String getAdminId() { return adminId; }
}
