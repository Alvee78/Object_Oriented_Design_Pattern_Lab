// ======================================================================
//  Entity: Book
//  Also implements BookSubject (Observer Pattern)
//
//  Attributes : bookID, title, author, ISBN, publisher, category,
//               shelfNumber, totalCopies, availableCopies, status
//  Methods    : addBook, updateBook, deleteBook, checkAvailability
//  Pattern    : Subject in Observer Pattern
// ======================================================================

import java.util.ArrayList;
import java.util.List;

/**
 * Entity: Book
 * Also acts as the Subject in the Observer Pattern.
 * When availableCopies increases (book returned), all registered
 * BookObservers (waiting users) are notified automatically.
 */
public class Book implements BookSubject {

    // ── Attributes ───────────────────────────────────────────────
    private String bookId;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private String category;
    private String shelfNumber;
    private int    totalCopies;
    private int    availableCopies;
    private String status;

    // ── Observer Pattern: list of registered observers ────────────
    private List<BookObserver> observers = new ArrayList<>();

    // ── Constructor ──────────────────────────────────────────────
    public Book(String bookId, String title, String author, String isbn,
                String publisher, String category,
                String shelfNumber, int totalCopies) {
        this.bookId          = bookId;
        this.title           = title;
        this.author          = author;
        this.isbn            = isbn;
        this.publisher       = publisher;
        this.category        = category;
        this.shelfNumber     = shelfNumber;
        this.totalCopies     = totalCopies;
        this.availableCopies = totalCopies;
        this.status          = "Available";
    }

    // ── Observer Pattern Methods ──────────────────────────────────

    @Override
    public void registerObserver(BookObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("[Observer] " + ((User) observer).getName() +
                               " subscribed to '" + title + "'.");
        }
    }

    @Override
    public void removeObserver(BookObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notify all waiting users that the book is now available.
     * Called automatically when availableCopies increases.
     */
    @Override
    public void notifyObservers() {
        if (!observers.isEmpty()) {
            System.out.println("[Observer] Notifying " + observers.size() +
                               " user(s) that '" + title + "' is now available.");
            for (BookObserver obs : observers) {
                obs.update(this);
            }
        }
    }

    // ── Entity Methods ────────────────────────────────────────────

    public void addBook(List<Book> inventory) {
        inventory.add(this);
        System.out.println("[Book] '" + title + "' added.");
    }

    public void updateBook(String field, Object value) {
        switch (field) {
            case "title":       this.title       = (String) value; break;
            case "author":      this.author      = (String) value; break;
            case "publisher":   this.publisher   = (String) value; break;
            case "category":    this.category    = (String) value; break;
            case "shelfNumber": this.shelfNumber = (String) value; break;
            case "totalCopies": this.totalCopies = (Integer) value; break;
        }
    }

    public void deleteBook(List<Book> inventory) {
        inventory.remove(this);
        System.out.println("[Book] '" + title + "' removed.");
    }

    public boolean checkAvailability() {
        boolean available = availableCopies > 0;
        this.status = available ? "Available" : "Not Available";
        return available;
    }

    // ── Borrow / Return Helpers ───────────────────────────────────

    public void decrementCopy() {
        if (availableCopies > 0) availableCopies--;
    }

    /**
     * Increment available copies and trigger Observer notification.
     */
    public void incrementCopy() {
        availableCopies++;
        if (availableCopies > 0) {
            notifyObservers();   // ← Observer Pattern trigger
        }
    }

    // ── Getters ───────────────────────────────────────────────────
    public String getBookId()          { return bookId; }
    public String getTitle()           { return title; }
    public String getAuthor()          { return author; }
    public String getIsbn()            { return isbn; }
    public String getPublisher()       { return publisher; }
    public String getCategory()        { return category; }
    public String getShelfNumber()     { return shelfNumber; }
    public int    getTotalCopies()     { return totalCopies; }
    public int    getAvailableCopies() { return availableCopies; }
    public String getStatus()          { return status; }

    @Override
    public String toString() {
        return "Book(id=" + bookId + ", title='" + title +
               "', author='" + author + "', avail=" +
               availableCopies + "/" + totalCopies + ")";
    }
}
