// ======================================================================
//  Observer Pattern — Subject Interface
// ======================================================================

/**
 * Observer Pattern — Subject Interface
 * Implemented by Book to manage a list of BookObservers.
 */
public interface BookSubject {
    void registerObserver(BookObserver observer);
    void removeObserver(BookObserver observer);
    void notifyObservers();
}
