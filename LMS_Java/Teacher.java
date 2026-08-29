// ======================================================================
//  Entity: Teacher
//  Also implements BookObserver (Observer Pattern)
//
//  Attributes : teacherID, name, email, department, phone, password
//  Methods    : login, logout, searchBook, borrowBook, returnBook,
//               viewBorrowHistory, viewFine
//  Pattern    : Concrete Observer in Observer Pattern
// ======================================================================

import java.util.ArrayList;
import java.util.List;

/**
 * Entity: Teacher
 * Implements BookObserver — receives notification when a reserved book
 * becomes available.
 */
public class Teacher extends User implements BookObserver {

    private String teacherId;
    private String department;
    private List<BorrowRecord> borrowHistory = new ArrayList<>();
    private List<Fine>         fines         = new ArrayList<>();

    public Teacher(String teacherId, String name, String email,
                   String department, String phone, String password) {
        super(teacherId, name, email, phone, password);
        this.teacherId  = teacherId;
        this.department = department;
    }

    // ── Observer Pattern: update() ────────────────────────────────
    @Override
    public void update(Book book) {
        System.out.println("[Notification -> Dr. " + name + "] '" +
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
        System.out.println("[Teacher] Search '" + query + "': " +
                           results.size() + " result(s).");
        return results;
    }

    public boolean borrowBook(Book book, BorrowRecord record) {
        if (book.checkAvailability()) {
            book.decrementCopy();
            borrowHistory.add(record);
            System.out.println("[Teacher] " + name + " borrowed '" + book.getTitle() + "'.");
            return true;
        }
        System.out.println("[Teacher] '" + book.getTitle() + "' not available.");
        return false;
    }

    public void returnBook(BorrowRecord record) {
        record.returnBook();
        System.out.println("[Teacher] " + name + " returned book (ID=" +
                           record.getBorrowId() + ").");
    }

    public void viewBorrowHistory() {
        System.out.println("\n[Teacher] Borrow History for " + name + ":");
        for (BorrowRecord r : borrowHistory) System.out.println("  " + r);
    }

    public void viewFine() {
        System.out.println("\n[Teacher] Fines for " + name + ":");
        for (Fine f : fines) System.out.println("  " + f);
    }

    // ── Getters ───────────────────────────────────────────────────
    public String getTeacherId()             { return teacherId; }
    public String getDepartment()            { return department; }
    public List<BorrowRecord> getBorrowHistory() { return borrowHistory; }
}
