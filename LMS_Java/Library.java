// ======================================================================
//  Singleton Pattern — Library Entity
//
//  Ensures only ONE Library instance exists in the entire application.
//
//  Attributes : libraryID, libraryName, address, contactNumber, openingHours
//  Methods    : searchBook, manageInventory, updateLibraryInformation
//  Pattern    : Singleton (private constructor + static getInstance())
// ======================================================================

import java.util.ArrayList;
import java.util.List;

/**
 * Entity: Library
 *
 * SINGLETON PATTERN:
 * - Private static field `instance` holds the single Library object.
 * - Private constructor prevents external instantiation with `new Library(...)`.
 * - Public static `getInstance(...)` creates the object on first call,
 *   and returns the same object on every subsequent call.
 *
 * Reason: A real library system has exactly one central Library managing
 *         all inventory, users, and operations. Multiple instances would
 *         lead to inconsistent state.
 */
public class Library {

    // ── Singleton: private static instance ──────────────────────────
    private static Library instance = null;

    // ── Attributes ──────────────────────────────────────────────────
    private String libraryId;
    private String libraryName;
    private String address;
    private String contactNumber;
    private String openingHours;
    private List<Book> inventory = new ArrayList<>();

    // ── Singleton: private constructor ──────────────────────────────
    private Library(String libraryId, String libraryName, String address,
                    String contactNumber, String openingHours) {
        this.libraryId     = libraryId;
        this.libraryName   = libraryName;
        this.address       = address;
        this.contactNumber = contactNumber;
        this.openingHours  = openingHours;
        System.out.println("[Singleton] Library instance created: " + libraryName);
    }

    // ── Singleton: public static access point ───────────────────────
    /**
     * Returns the single Library instance.
     * Creates it on the first call (lazy initialization).
     *
     * @param libraryId     used only on first call to initialize
     * @param libraryName   used only on first call to initialize
     * @param address       used only on first call to initialize
     * @param contactNumber used only on first call to initialize
     * @param openingHours  used only on first call to initialize
     */
    public static Library getInstance(String libraryId, String libraryName,
                                      String address, String contactNumber,
                                      String openingHours) {
        if (instance == null) {
            instance = new Library(libraryId, libraryName, address,
                                   contactNumber, openingHours);
        } else {
            System.out.println("[Singleton] Returning existing Library instance.");
        }
        return instance;
    }

    /** Convenience overload — returns existing instance (must be created first). */
    public static Library getInstance() {
        if (instance == null) {
            throw new IllegalStateException(
                "Library not yet initialized. Call getInstance(...) with parameters first.");
        }
        return instance;
    }

    // ── Entity Methods ──────────────────────────────────────────────

    public List<Book> searchBook(String query) {
        List<Book> results = new ArrayList<>();
        String q = query.toLowerCase();
        for (Book b : inventory) {
            if (b.getTitle().toLowerCase().contains(q)  ||
                b.getAuthor().toLowerCase().contains(q) ||
                b.getCategory().toLowerCase().contains(q)) {
                results.add(b);
            }
        }
        System.out.println("[Library] '" + query + "' -> " + results.size() + " book(s) found.");
        return results;
    }

    public void manageInventory() {
        System.out.println("\n[Library] Inventory (" + inventory.size() + " books):");
        for (Book b : inventory) System.out.println("  " + b);
    }

    public void updateLibraryInformation(String field, String value) {
        switch (field) {
            case "libraryName":   this.libraryName   = value; break;
            case "address":       this.address       = value; break;
            case "contactNumber": this.contactNumber = value; break;
            case "openingHours":  this.openingHours  = value; break;
        }
        System.out.println("[Library] '" + field + "' updated to: " + value);
    }

    // ── Getters / Setters ──────────────────────────────────────────
    public String     getLibraryId()     { return libraryId; }
    public String     getLibraryName()   { return libraryName; }
    public String     getAddress()       { return address; }
    public String     getContactNumber() { return contactNumber; }
    public String     getOpeningHours()  { return openingHours; }
    public List<Book> getInventory()     { return inventory; }

    @Override
    public String toString() {
        return "Library(name=" + libraryName + ", books=" + inventory.size() + ")";
    }
}
