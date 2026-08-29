// ======================================================================
//  Boundary: StudentDashboard
//  Methods: displayStudentProfile, searchBook, borrowBook, returnBook,
//           viewBorrowHistory, viewFine, logout
// ======================================================================

import java.util.List;

public class StudentDashboard {

    private Student          student;
    private Library          library;
    private BorrowController borrowCtrl;
    private FineController   fineCtrl;
    private LoginController  loginCtrl;

    public StudentDashboard(Student student, Library library,
                            BorrowController borrowCtrl,
                            FineController fineCtrl,
                            LoginController loginCtrl) {
        this.student    = student;
        this.library    = library;
        this.borrowCtrl = borrowCtrl;
        this.fineCtrl   = fineCtrl;
        this.loginCtrl  = loginCtrl;
    }

    public void displayStudentProfile() {
        System.out.println("\n[StudentDashboard] Profile");
        System.out.println("  ID         : " + student.getStudentId());
        System.out.println("  Name       : " + student.getName());
        System.out.println("  Email      : " + student.getEmail());
        System.out.println("  Department : " + student.getDepartment());
        System.out.println("  Phone      : " + student.getPhone());
    }

    public List<Book> searchBook(String query) {
        List<Book> results = library.searchBook(query);
        System.out.println("[StudentDashboard] Results:");
        for (Book b : results) System.out.println("  " + b);
        return results;
    }

    public boolean borrowBook(Book book, BorrowRecord record) {
        return borrowCtrl.borrowBook(book, student, record);
    }

    public void returnBook(Book book, BorrowRecord record) {
        borrowCtrl.returnBook(book, record);
    }

    public void viewBorrowHistory() {
        borrowCtrl.viewBorrowHistory(student.getStudentId());
    }

    public void viewFine() {
        fineCtrl.viewFine(student.getStudentId(), borrowCtrl.getBorrowRecords());
    }

    public void logout() { loginCtrl.logout(student); }
}
