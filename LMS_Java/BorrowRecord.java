// ======================================================================
//  Entity: BorrowRecord
//  Attributes : borrowID, userID, bookID, issueDate, dueDate,
//               returnDate, borrowStatus
//  Methods    : issueBook, returnBook, updateBorrowStatus, calculateOverdueDays
// ======================================================================

import java.time.LocalDate;

public class BorrowRecord {

    public static final int LOAN_DAYS = 14; // default 2-week loan

    private String    borrowId;
    private String    userId;
    private String    bookId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String    borrowStatus;

    public BorrowRecord(String borrowId, String userId, String bookId,
                        LocalDate issueDate) {
        this.borrowId    = borrowId;
        this.userId      = userId;
        this.bookId      = bookId;
        this.issueDate   = issueDate;
        this.dueDate     = issueDate.plusDays(LOAN_DAYS);
        this.returnDate  = null;
        this.borrowStatus = "Issued";
    }

    public void issueBook() {
        this.borrowStatus = "Issued";
        System.out.println("[BorrowRecord] Book issued. Due: " + dueDate);
    }

    public void returnBook() {
        this.returnDate   = LocalDate.now();
        this.borrowStatus = "Returned";
        System.out.println("[BorrowRecord] Book returned on " + returnDate + ".");
    }

    public void updateBorrowStatus(String status) {
        this.borrowStatus = status;
    }

    public int calculateOverdueDays() {
        LocalDate checkDate = (returnDate != null) ? returnDate : LocalDate.now();
        long days = java.time.temporal.ChronoUnit.DAYS.between(dueDate, checkDate);
        return (int) Math.max(0, days);
    }

    // ── Getters / Setters ──────────────────────────────────────────
    public String    getBorrowId()    { return borrowId; }
    public String    getUserId()      { return userId; }
    public String    getBookId()      { return bookId; }
    public LocalDate getIssueDate()   { return issueDate; }
    public LocalDate getDueDate()     { return dueDate; }
    public LocalDate getReturnDate()  { return returnDate; }
    public String    getStatus()      { return borrowStatus; }
    public void      setStatus(String s) { this.borrowStatus = s; }

    @Override
    public String toString() {
        return "BorrowRecord(id=" + borrowId + ", book=" + bookId +
               ", status=" + borrowStatus + ", due=" + dueDate + ")";
    }
}
