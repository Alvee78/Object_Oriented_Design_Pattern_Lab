// ======================================================================
//  Control: LibrarianController
//  Methods: addLibrarian, updateLibrarian, deleteLibrarian, manageLibrarians
// ======================================================================

import java.util.ArrayList;
import java.util.List;

public class LibrarianController {

    private List<Librarian> librarians = new ArrayList<>();

    public void addLibrarian(Librarian librarian) {
        librarians.add(librarian);
        System.out.println("[LibrarianController] Librarian '" + librarian.getName() + "' added.");
    }

    public void deleteLibrarian(Librarian librarian) {
        librarians.remove(librarian);
        System.out.println("[LibrarianController] Librarian '" + librarian.getName() + "' removed.");
    }

    public void manageLibrarians() {
        System.out.println("\n[LibrarianController] All Librarians (" + librarians.size() + "):");
        for (Librarian l : librarians) System.out.println("  " + l);
    }

    public List<Librarian> getLibrarians() { return librarians; }
}
