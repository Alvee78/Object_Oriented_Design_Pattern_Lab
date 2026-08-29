// ======================================================================
//  Observer Pattern — Interfaces
//  BookObserver.java  &  BookSubject.java in same file (package-private)
//
//  Subject : Book (notifies when availability changes)
//  Observer: Student, Teacher (receive notifications)
// ======================================================================

/**
 * Observer Pattern — Observer Interface
 * Implemented by any User who wants to be notified when a Book is available.
 */
public interface BookObserver {
    /**
     * Called by the Subject (Book) when its availability changes.
     * @param book  the Book whose availability changed
     */
    void update(Book book);
}
