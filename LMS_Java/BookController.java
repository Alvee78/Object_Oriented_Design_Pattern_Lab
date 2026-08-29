// ======================================================================
//  Control: BookController
//  Methods: addBook, updateBook, deleteBook, searchBook, checkAvailability
// ======================================================================

import java.util.List;

public class BookController {

    private Library library;

    public BookController(Library library) {
        this.library = library;
    }

    public void addBook(Book book) {
        library.getInventory().add(book);
        System.out.println("[BookController] '" + book.getTitle() + "' added.");
    }

    public void updateBook(Book book, String field, Object value) {
        book.updateBook(field, value);
        System.out.println("[BookController] '" + book.getTitle() + "' field '" + field + "' updated.");
    }

    public void deleteBook(Book book) {
        library.getInventory().remove(book);
        System.out.println("[BookController] '" + book.getTitle() + "' deleted.");
    }

    public List<Book> searchBook(String query) {
        return library.searchBook(query);
    }

    public boolean checkAvailability(Book book) {
        boolean a = book.checkAvailability();
        System.out.println("[BookController] '" + book.getTitle() + "': " +
                           (a ? "Available" : "Not Available"));
        return a;
    }
}
