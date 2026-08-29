// ======================================================================
//  Entity: Student
//  Also implements BookObserver (Observer Pattern)
//
//  Attributes : studentID, name, email, department, phone, password
//  Methods    : login, logout, searchBook, borrowBook, returnBook,
//               viewBorrowHistory, viewFine
//  Pattern    : Concrete Observer in Observer Pattern
// ======================================================================

import java.util.ArrayList;
import java.util.List;

/**
 * Entity: Student
 * Implements BookObserver — receives notification when a reserved book
 * becomes available.
 */
public class Student extends User implements BookObserver {

    private String studentId;
    private String department;
    private List<BorrowRecord> borrowHistory = new ArrayList<>();
    private List<Fine>         fines         = new ArrayList<>();

    public Student(String studentId, String name, String email,
                   String department, String phone, String password) {
        super(studentId, name, email, phone, password);
        this.studentId  = studentId;
        this.department = department;
    }

    // ── Observer Pattern: update() ────────────────────────────────
    /**
     * Called by Book (Subject) when a copy becomes available.
     */
    @Override
    public void update(Book book) {
        System.out.println("[Notification -> " + name + "] The book '" +
                           book.getTitle() + "' is now available. Please visit the library!");
    }

    // ── Entity Methods ────────────────────────────────────────────

    public List<Book> searchBook(String query, List<Book> bookList) {
        List<Book> results = new ArrayList<>();
        for (Book b : bookList) {
            if (b.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                b.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                results.add(b);
            }
        }
        System.out.println("[Student] Search '" + query + "': " +
                           results.size() + " result(s).");
        return results;
    }

    public boolean borrowBook(Book book, BorrowRecord record) {
        if (book.checkAvailability()) {
            book.decrementCopy();
            borrowHistory.add(record);
            System.out.println("[Student] " + name + " borrowed '" + book.getTitle() + "'.");
            return true;
        }
        System.out.println("[Student] '" + book.getTitle() + "' not available.");
        return false;
    }

    public void returnBook(BorrowRecord record) {
        record.returnBook();
        System.out.println("[Student] " + name + " returned book (ID=" +
                           record.getBorrowId() + ").");
    }

    public void viewBorrowHistory() {
        System.out.println("\n[Student] Borrow History for " + name + ":");
        if (borrowHistory.isEmpty()) {
            System.out.println("  No records.");
        } else {
            for (BorrowRecord r : borrowHistory) System.out.println("  " + r);
        }
    }

    public void viewFine() {
        System.out.println("\n[Student] Fines for " + name + ":");
        if (fines.isEmpty()) {
            System.out.println("  No fines.");
        } else {
            for (Fine f : fines) System.out.println("  " + f);
        }
    }

    // ── Getters ───────────────────────────────────────────────────
    public String getStudentId()           { return studentId; }
    public String getDepartment()          { return department; }
    public List<BorrowRecord> getBorrowHistory() { return borrowHistory; }
}
