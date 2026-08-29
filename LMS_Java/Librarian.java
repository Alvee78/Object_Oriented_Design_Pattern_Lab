// ======================================================================
//  Entity: Librarian
//  Attributes : librarianID, name, email, phone, password
//  Methods    : addBook, updateBook, deleteBook, issueBook,
//               receiveReturnedBook, manageUsers
// ======================================================================

import java.util.List;

public class Librarian extends User {

    private String librarianId;

    public Librarian(String librarianId, String name, String email,
                     String phone, String password) {
        super(librarianId, name, email, phone, password);
        this.librarianId = librarianId;
    }

    public void addBook(Book book, List<Book> inventory) {
        inventory.add(book);
        System.out.println("[Librarian] Book '" + book.getTitle() + "' added.");
    }

    public void updateBook(Book book, String field, Object value) {
        book.updateBook(field, value);
        System.out.println("[Librarian] Book '" + book.getTitle() +
                           "' field '" + field + "' updated.");
    }

    public void deleteBook(Book book, List<Book> inventory) {
        book.deleteBook(inventory);
        System.out.println("[Librarian] Book '" + book.getTitle() + "' deleted.");
    }

    public boolean issueBook(Book book, User user, BorrowRecord record) {
        if (book.checkAvailability()) {
            book.decrementCopy();
            record.setStatus("Issued");
            System.out.println("[Librarian] Issued '" + book.getTitle() +
                               "' to " + user.getName() +
                               ". Due: " + record.getDueDate());
            return true;
        }
        System.out.println("[Librarian] Cannot issue. '" + book.getTitle() + "' unavailable.");
        return false;
    }

    public void receiveReturnedBook(Book book, BorrowRecord record) {
        book.incrementCopy();   // ← triggers Observer notification
        record.returnBook();
        System.out.println("[Librarian] Received returned book '" + book.getTitle() + "'.");
    }

    public void manageUsers(List<User> users) {
        System.out.println("\n[Librarian] Registered Users (" + users.size() + "):");
        for (User u : users) System.out.println("  " + u);
    }

    public String getLibrarianId() { return librarianId; }
}
