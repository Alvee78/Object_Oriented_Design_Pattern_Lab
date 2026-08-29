// ======================================================================
//  Control: BorrowController
//  Methods: issueBook, borrowBook, returnBook, updateBorrowStatus,
//           calculateOverdueDays, viewBorrowHistory
// ======================================================================

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BorrowController {

    private List<BorrowRecord> borrowRecords = new ArrayList<>();

    public boolean issueBook(Book book, User user, BorrowRecord record) {
        if (book.checkAvailability()) {
            book.decrementCopy();
            record.issueBook();
            borrowRecords.add(record);
            System.out.println("[BorrowController] '" + book.getTitle() +
                               "' issued to " + user.getName() + ".");
            return true;
        }
        System.out.println("[BorrowController] '" + book.getTitle() + "' not available.");
        return false;
    }

    public boolean borrowBook(Book book, User user, BorrowRecord record) {
        return issueBook(book, user, record);
    }

    public void returnBook(Book book, BorrowRecord record) {
        book.incrementCopy();   // ← triggers Observer notification
        record.returnBook();
        System.out.println("[BorrowController] '" + book.getTitle() + "' returned.");
    }

    public void updateBorrowStatus(BorrowRecord record, String status) {
        record.updateBorrowStatus(status);
    }

    public int calculateOverdueDays(BorrowRecord record) {
        int days = record.calculateOverdueDays();
        System.out.println("[BorrowController] Overdue days: " + days);
        return days;
    }

    public List<BorrowRecord> viewBorrowHistory(String userId) {
        List<BorrowRecord> history = borrowRecords.stream()
            .filter(r -> r.getUserId().equals(userId))
            .collect(Collectors.toList());
        System.out.println("\n[BorrowController] History for user '" + userId + "':");
        for (BorrowRecord r : history) System.out.println("  " + r);
        return history;
    }

    public List<BorrowRecord> getBorrowRecords() { return borrowRecords; }
}
