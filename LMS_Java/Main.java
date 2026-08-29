// ======================================================================
//  Main.java — Library Management System (Interactive CLI)
//  OOD Lab Assignment | Design Patterns: Singleton, Factory, Observer, Strategy
//
//  HOW TO RUN:
//    javac *.java
//    java Main
// ======================================================================

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

        // ── ANSI Color Codes ──────────────────────────────────────────────
        static final String RESET = "\u001B[0m";
        static final String BOLD = "\u001B[1m";
        static final String RED = "\u001B[31m";
        static final String GREEN = "\u001B[32m";
        static final String YELLOW = "\u001B[33m";
        static final String BLUE = "\u001B[34m";
        static final String MAGENTA = "\u001B[35m";
        static final String CYAN = "\u001B[36m";
        static final String WHITE = "\u001B[97m";
        static final String BG_BLUE = "\u001B[44m";
        static final String BG_CYAN = "\u001B[46m";
        static final String DIM = "\u001B[2m";

        // ── Global ID Counters ────────────────────────────────────────────
        static int borrowCounter = 1;
        static int fineCounter = 1;

        // ── Shared Controllers ────────────────────────────────────────────
        static Library library;
        static LoginController loginCtrl;
        static UserController userCtrl;
        static LibrarianController libCtrl;
        static BookController bookCtrl;
        static BorrowController borrowCtrl;
        static FineController fineCtrl;
        static ReportController reportCtrl;
        static Scanner sc = new Scanner(System.in);

        // ═══════════════════════════════════════════════════════════════════
        // MAIN
        // ═══════════════════════════════════════════════════════════════════
        public static void main(String[] args) {
                initSystem();
                printBanner();

                boolean running = true;
                while (running) {
                        printBox("MAIN MENU", CYAN);
                        System.out.println("  " + CYAN + "[1]" + RESET + " Login");
                        System.out.println("  " + CYAN + "[2]" + RESET + " Show All Books");
                        System.out.println("  " + RED + "[0]" + RESET + " Exit");
                        System.out.print("\n" + BOLD + "  > " + RESET);
                        String choice = sc.nextLine().trim();

                        switch (choice) {
                                case "1":
                                        loginFlow();
                                        break;
                                case "2":
                                        showAllBooks();
                                        break;
                                case "0":
                                        printSuccess("Goodbye! Thank you for using Rajshahi University Library System.");
                                        running = false;
                                        break;
                                default:
                                        printError("Invalid option. Try again.");
                        }
                }
        }

        // ═══════════════════════════════════════════════════════════════════
        // INIT — Seed data using Singleton + Factory patterns
        // ═══════════════════════════════════════════════════════════════════
        static void initSystem() {
                // ── SINGLETON: only one Library instance ──────────────────────
                library = Library.getInstance("LIB001", "Rajshahi University Central Library",
                                "Rajshahi University, Bangladesh", "01700-000000",
                                "08:00 AM - 09:00 PM");

                loginCtrl = new LoginController();
                userCtrl = new UserController();
                libCtrl = new LibrarianController();
                bookCtrl = new BookController(library);
                borrowCtrl = new BorrowController();
                fineCtrl = new FineController();
                Report report = new Report("R001", "Admin");
                reportCtrl = new ReportController(report);

                // ── FACTORY: create all users ──────────────────────────────────
                Student alice = (Student) UserFactory.createUser("STUDENT", "S001",
                                "Alice Rahman", "alice@ru.edu", "CSE", "01711-111111", "pass123");
                Student bob = (Student) UserFactory.createUser("STUDENT", "S002",
                                "Bob Hasan", "bob@ru.edu", "EEE", "01722-222222", "bob123");
                Teacher karim = (Teacher) UserFactory.createUser("TEACHER", "T001",
                                "Dr. Karim", "karim@ru.edu", "CSE", "01811-222222", "teach456");
                Librarian rahim = (Librarian) UserFactory.createUser("LIBRARIAN", "L001",
                                "Rahim Uddin", "rahim@ru.edu", "", "01911-333333", "lib789");
                Admin adminUsr = (Admin) UserFactory.createUser("ADMIN", "A001",
                                "Admin User", "admin@ru.edu", "", "", "admin000");

                for (User u : new User[] { alice, bob, karim, rahim, adminUsr })
                        userCtrl.registerUser(u);
                libCtrl.addLibrarian(rahim);

                // ── Seed Books ─────────────────────────────────────────────────
                library.getInventory().add(new Book("B001", "Clean Code",
                                "Robert C. Martin", "978-0132350884", "Prentice Hall", "Programming", "A-12", 3));
                library.getInventory().add(new Book("B002", "Design Patterns",
                                "GoF", "978-0201633610", "Addison-Wesley", "Software Engineering", "B-05", 2));
                library.getInventory().add(new Book("B003", "Introduction to Algorithms",
                                "CLRS", "978-0262033848", "MIT Press", "Algorithms", "C-08", 5));
                library.getInventory().add(new Book("B004", "The Pragmatic Programmer",
                                "Hunt & Thomas", "978-0135957059", "Addison-Wesley", "Programming", "A-15", 2));
                library.getInventory().add(new Book("B005", "Operating System Concepts",
                                "Silberschatz", "978-1119800", "Wiley", "Systems", "D-10", 4));
                library.getInventory().add(new Book("B006", "Computer Networks",
                                "Andrew Tanenbaum", "978-0133594140", "Pearson", "Networks", "E-03", 3));
                library.getInventory().add(new Book("B007", "Database System Concepts",
                                "Silberschatz & Korth", "978-0078022159", "McGraw-Hill", "Database", "F-07", 4));
        }

        // ═══════════════════════════════════════════════════════════════════
        // LOGIN FLOW
        // ═══════════════════════════════════════════════════════════════════
        static void loginFlow() {
                printBox("LOGIN", CYAN);
                System.out.print("  Email    : ");
                String email = sc.nextLine().trim();
                System.out.print("  Password : ");
                String password = sc.nextLine().trim();

                User user = loginCtrl.login(email, password, userCtrl.getUsers());
                if (user == null) {
                        printError("Login failed. Invalid email or password.");
                        return;
                }

                printSuccess("Welcome, " + user.getName() + "! [" +
                                user.getClass().getSimpleName() + "]");

                // Route to role-based menu
                if (user instanceof Student)
                        studentMenu((Student) user);
                else if (user instanceof Teacher)
                        teacherMenu((Teacher) user);
                else if (user instanceof Librarian)
                        librarianMenu((Librarian) user);
                else if (user instanceof Admin)
                        adminMenu((Admin) user);
        }

        // ═══════════════════════════════════════════════════════════════════
        // STUDENT MENU
        // ═══════════════════════════════════════════════════════════════════
        static void studentMenu(Student student) {
                // STRATEGY: student fine rate
                fineCtrl.setStrategy(new StudentFineStrategy());

                boolean active = true;
                while (active) {
                        System.out.println();
                        printBox("STUDENT MENU  [ " + student.getName() + " ]", GREEN);
                        System.out.println("  " + CYAN + "[1]" + RESET + " Search Book");
                        System.out.println("  " + CYAN + "[2]" + RESET + " Borrow Book");
                        System.out.println("  " + CYAN + "[3]" + RESET + " Return Book");
                        System.out.println("  " + CYAN + "[4]" + RESET + " View Borrow History");
                        System.out.println("  " + CYAN + "[5]" + RESET + " View Fines");
                        System.out.println("  " + CYAN + "[6]" + RESET + " View My Profile");
                        System.out.println("  " + CYAN + "[7]" + RESET + " Show All Books");
                        System.out.println("  " + RED + "[0]" + RESET + " Logout");
                        System.out.print("\n" + BOLD + "  > " + RESET);
                        String choice = sc.nextLine().trim();

                        switch (choice) {
                                case "1":
                                        searchBook();
                                        break;
                                case "2":
                                        cliStudentBorrow(student);
                                        break;
                                case "3":
                                        cliReturnBook(student.getStudentId());
                                        break;
                                case "4":
                                        borrowCtrl.viewBorrowHistory(student.getStudentId());
                                        break;
                                case "5":
                                        cliViewFines(student.getStudentId());
                                        break;
                                case "6":
                                        printUserProfile(student);
                                        break;
                                case "7":
                                        showAllBooks();
                                        break;
                                case "0":
                                        loginCtrl.logout(student);
                                        active = false;
                                        break;
                                default:
                                        printError("Invalid option.");
                        }
                }
        }

        // ═══════════════════════════════════════════════════════════════════
        // TEACHER MENU
        // ═══════════════════════════════════════════════════════════════════
        static void teacherMenu(Teacher teacher) {
                // STRATEGY: teacher fine rate (reduced)
                fineCtrl.setStrategy(new TeacherFineStrategy());

                boolean active = true;
                while (active) {
                        System.out.println();
                        printBox("TEACHER MENU  [ " + teacher.getName() + " ]", MAGENTA);
                        System.out.println("  " + CYAN + "[1]" + RESET + " Search Book");
                        System.out.println("  " + CYAN + "[2]" + RESET + " Borrow Book");
                        System.out.println("  " + CYAN + "[3]" + RESET + " Return Book");
                        System.out.println("  " + CYAN + "[4]" + RESET + " View Borrow History");
                        System.out.println("  " + CYAN + "[5]" + RESET + " View Fines");
                        System.out.println("  " + CYAN + "[6]" + RESET + " View My Profile");
                        System.out.println("  " + CYAN + "[7]" + RESET + " Show All Books");
                        System.out.println("  " + RED + "[0]" + RESET + " Logout");
                        System.out.print("\n" + BOLD + "  > " + RESET);
                        String choice = sc.nextLine().trim();

                        switch (choice) {
                                case "1":
                                        searchBook();
                                        break;
                                case "2":
                                        cliTeacherBorrow(teacher);
                                        break;
                                case "3":
                                        cliReturnBook(teacher.getTeacherId());
                                        break;
                                case "4":
                                        borrowCtrl.viewBorrowHistory(teacher.getTeacherId());
                                        break;
                                case "5":
                                        cliViewFines(teacher.getTeacherId());
                                        break;
                                case "6":
                                        printUserProfile(teacher);
                                        break;
                                case "7":
                                        showAllBooks();
                                        break;
                                case "0":
                                        loginCtrl.logout(teacher);
                                        active = false;
                                        break;
                                default:
                                        printError("Invalid option.");
                        }
                }
        }

        // ═══════════════════════════════════════════════════════════════════
        // LIBRARIAN MENU
        // ═══════════════════════════════════════════════════════════════════
        static void librarianMenu(Librarian librarian) {
                boolean active = true;
                while (active) {
                        System.out.println();
                        printBox("LIBRARIAN MENU  [ " + librarian.getName() + " ]", YELLOW);
                        System.out.println("  " + CYAN + "[1]" + RESET + " Show All Books");
                        System.out.println("  " + CYAN + "[2]" + RESET + " Add Book");
                        System.out.println("  " + CYAN + "[3]" + RESET + " Search Book");
                        System.out.println("  " + CYAN + "[4]" + RESET + " Issue Book to User");
                        System.out.println("  " + CYAN + "[5]" + RESET + " Receive Returned Book");
                        System.out.println("  " + CYAN + "[6]" + RESET + " Show All Users");
                        System.out.println("  " + CYAN + "[7]" + RESET + " Delete Book");
                        System.out.println("  " + RED + "[0]" + RESET + " Logout");
                        System.out.print("\n" + BOLD + "  > " + RESET);
                        String choice = sc.nextLine().trim();

                        switch (choice) {
                                case "1":
                                        showAllBooks();
                                        break;
                                case "2":
                                        cliAddBook();
                                        break;
                                case "3":
                                        searchBook();
                                        break;
                                case "4":
                                        cliIssueBook(librarian);
                                        break;
                                case "5":
                                        cliReceiveReturn(librarian);
                                        break;
                                case "6":
                                        userCtrl.manageUsers();
                                        break;
                                case "7":
                                        cliDeleteBook();
                                        break;
                                case "0":
                                        loginCtrl.logout(librarian);
                                        active = false;
                                        break;
                                default:
                                        printError("Invalid option.");
                        }
                }
        }

        // ═══════════════════════════════════════════════════════════════════
        // ADMIN MENU
        // ═══════════════════════════════════════════════════════════════════
        static void adminMenu(Admin admin) {
                boolean active = true;
                while (active) {
                        System.out.println();
                        printBox("ADMIN MENU  [ " + admin.getName() + " ]", RED);
                        System.out.println("  " + CYAN + "[1]" + RESET + " View Dashboard");
                        System.out.println("  " + CYAN + "[2]" + RESET + " Manage Users");
                        System.out.println("  " + CYAN + "[3]" + RESET + " Add New User");
                        System.out.println("  " + CYAN + "[4]" + RESET + " Delete User");
                        System.out.println("  " + CYAN + "[5]" + RESET + " Manage Librarians");
                        System.out.println("  " + CYAN + "[6]" + RESET + " Add Book");
                        System.out.println("  " + CYAN + "[7]" + RESET + " Show All Books");
                        System.out.println("  " + CYAN + "[8]" + RESET + " Generate Borrow Report");
                        System.out.println("  " + CYAN + "[9]" + RESET + " Generate Fine Report");
                        System.out.println("  " + CYAN + "[10]" + RESET + " Generate Inventory Report");
                        System.out.println("  " + CYAN + "[11]" + RESET + " Generate User Report");
                        System.out.println("  " + RED + "[0]" + RESET + " Logout");
                        System.out.print("\n" + BOLD + "  > " + RESET);
                        String choice = sc.nextLine().trim();

                        switch (choice) {
                                case "1":
                                        admin.viewDashboard(library);
                                        break;
                                case "2":
                                        userCtrl.manageUsers();
                                        break;
                                case "3":
                                        cliAddUser();
                                        break;
                                case "4":
                                        cliDeleteUser();
                                        break;
                                case "5":
                                        libCtrl.manageLibrarians();
                                        break;
                                case "6":
                                        cliAddBook();
                                        break;
                                case "7":
                                        showAllBooks();
                                        break;
                                case "8":
                                        reportCtrl.generateBorrowReport(borrowCtrl.getBorrowRecords());
                                        break;
                                case "9":
                                        reportCtrl.generateFineReport(fineCtrl.getFines());
                                        break;
                                case "10":
                                        reportCtrl.generateInventoryReport(library.getInventory());
                                        break;
                                case "11":
                                        reportCtrl.generateUserReport(userCtrl.getUsers());
                                        break;
                                case "0":
                                        loginCtrl.logout(admin);
                                        active = false;
                                        break;
                                default:
                                        printError("Invalid option.");
                        }
                }
        }

        // ═══════════════════════════════════════════════════════════════════
        // SHARED OPERATIONS
        // ═══════════════════════════════════════════════════════════════════

        /** Show all books in a formatted table. */
        static void showAllBooks() {
                List<Book> books = library.getInventory();
                System.out.println();
                printBox("LIBRARY INVENTORY  (" + books.size() + " books)", CYAN);
                System.out.printf("  %-6s %-35s %-22s %-15s %-8s%n",
                                "ID", "Title", "Author", "Category", "Avail");
                System.out.println("  " + DIM + "─".repeat(90) + RESET);
                for (Book b : books) {
                        String avail = b.getAvailableCopies() > 0
                                        ? GREEN + b.getAvailableCopies() + "/" + b.getTotalCopies() + RESET
                                        : RED + "0/" + b.getTotalCopies() + RESET;
                        System.out.printf("  %-6s %-35s %-22s %-15s %s%n",
                                        b.getBookId(),
                                        truncate(b.getTitle(), 33),
                                        truncate(b.getAuthor(), 20),
                                        truncate(b.getCategory(), 13),
                                        avail);
                }
        }

        /** Search books by title / author / category. */
        static List<Book> searchBook() {
                System.out.print("\n  " + CYAN + "Search query: " + RESET);
                String query = sc.nextLine().trim();
                if (query.isEmpty()) {
                        printError("Query cannot be empty.");
                        return new ArrayList<>();
                }

                List<Book> results = library.searchBook(query);
                if (results.isEmpty()) {
                        printError("No books found for: \"" + query + "\"");
                } else {
                        System.out.println();
                        System.out.printf("  %-6s %-35s %-22s %-8s%n", "ID", "Title", "Author", "Avail");
                        System.out.println("  " + DIM + "─".repeat(75) + RESET);
                        for (Book b : results) {
                                String avail = b.getAvailableCopies() > 0
                                                ? GREEN + b.getAvailableCopies() + RESET
                                                : RED + "0" + RESET;
                                System.out.printf("  %-6s %-35s %-22s %s%n",
                                                b.getBookId(), truncate(b.getTitle(), 33),
                                                truncate(b.getAuthor(), 20), avail);
                        }
                }
                return results;
        }

        /** Student borrows a book. */
        static void cliStudentBorrow(Student student) {
                List<Book> results = searchBook();
                if (results.isEmpty())
                        return;

                System.out.print("  Enter Book ID to borrow: ");
                String bookId = sc.nextLine().trim().toUpperCase();
                Book book = findBookById(bookId);
                if (book == null) {
                        printError("Book ID not found: " + bookId);
                        return;
                }
                if (!book.checkAvailability()) {
                        printError("'" + book.getTitle() + "' has no available copies.");
                        return;
                }

                String borrowId = "BR" + String.format("%03d", borrowCounter++);
                BorrowRecord record = new BorrowRecord(borrowId, student.getStudentId(),
                                book.getBookId(), LocalDate.now());
                borrowCtrl.borrowBook(book, student, record);
                printSuccess("Borrowed '" + book.getTitle() + "'  |  Due: " + record.getDueDate() +
                                "  |  Borrow ID: " + borrowId);

                // OBSERVER: register student to the book (in case it runs out later)
                book.registerObserver(student);
        }

        /** Teacher borrows a book. */
        static void cliTeacherBorrow(Teacher teacher) {
                List<Book> results = searchBook();
                if (results.isEmpty())
                        return;

                System.out.print("  Enter Book ID to borrow: ");
                String bookId = sc.nextLine().trim().toUpperCase();
                Book book = findBookById(bookId);
                if (book == null) {
                        printError("Book ID not found: " + bookId);
                        return;
                }
                if (!book.checkAvailability()) {
                        printError("'" + book.getTitle() + "' has no available copies.");
                        return;
                }

                String borrowId = "BR" + String.format("%03d", borrowCounter++);
                BorrowRecord record = new BorrowRecord(borrowId, teacher.getTeacherId(),
                                book.getBookId(), LocalDate.now());
                borrowCtrl.borrowBook(book, teacher, record);
                printSuccess("Borrowed '" + book.getTitle() + "'  |  Due: " + record.getDueDate() +
                                "  |  Borrow ID: " + borrowId);
                book.registerObserver(teacher);
        }

        /** Return a book — look up active borrow records for this user. */
        static void cliReturnBook(String userId) {
                // Show active (not returned) borrows for this user
                List<BorrowRecord> active = borrowCtrl.getBorrowRecords().stream()
                                .filter(r -> r.getUserId().equals(userId) && r.getStatus().equals("Issued"))
                                .collect(Collectors.toList());

                if (active.isEmpty()) {
                        printError("You have no books currently borrowed.");
                        return;
                }

                System.out.println("\n  " + YELLOW + "Your borrowed books:" + RESET);
                System.out.printf("  %-10s %-35s %-12s %s%n", "BorrowID", "Book", "Due Date", "Overdue?");
                System.out.println("  " + DIM + "─".repeat(70) + RESET);
                for (BorrowRecord r : active) {
                        Book b = findBookById(r.getBookId());
                        String title = b != null ? b.getTitle() : r.getBookId();
                        int overdue = r.calculateOverdueDays();
                        String flag = overdue > 0 ? RED + "!" + overdue + " days" + RESET : GREEN + "On time" + RESET;
                        System.out.printf("  %-10s %-35s %-12s %s%n",
                                        r.getBorrowId(), truncate(title, 33), r.getDueDate(), flag);
                }

                System.out.print("\n  Enter Borrow ID to return (or 0 to cancel): ");
                String borrowId = sc.nextLine().trim().toUpperCase();
                if (borrowId.equals("0"))
                        return;

                BorrowRecord record = findBorrowById(borrowId);
                if (record == null || !record.getUserId().equals(userId)) {
                        printError("Borrow record not found for your account.");
                        return;
                }
                Book book = findBookById(record.getBookId());
                if (book == null) {
                        printError("Book data not found.");
                        return;
                }

                borrowCtrl.returnBook(book, record); // ← triggers Observer notification

                int overdueDays = record.calculateOverdueDays();
                if (overdueDays > 0) {
                        String fineId = "F" + String.format("%03d", fineCounter++);
                        Fine fine = fineCtrl.calculateFine(fineId, record);
                        printWarning("Book is " + overdueDays + " day(s) overdue.");
                        printWarning("Fine issued: " + fine.getAmount() + " BDT  (Fine ID: " + fine.getFineId() + ")");
                } else {
                        printSuccess("Book returned on time. No fine.");
                }
        }

        /** View fines for a user. */
        static void cliViewFines(String userId) {
                List<Fine> userFines = fineCtrl.viewFine(userId, borrowCtrl.getBorrowRecords());
                if (userFines.isEmpty())
                        return;

                System.out.println();
                System.out.printf("  %-8s %-12s %-10s %-10s %s%n",
                                "FineID", "BorrowID", "Overdue", "Amount", "Status");
                System.out.println("  " + DIM + "─".repeat(55) + RESET);
                for (Fine f : userFines) {
                        String status = f.getPaymentStatus().equals("Paid")
                                        ? GREEN + "Paid" + RESET
                                        : RED + "Unpaid" + RESET;
                        System.out.printf("  %-8s %-12s %-10s %-10s %s%n",
                                        f.getFineId(), f.getBorrowId(),
                                        f.getOverdueDays() + " days",
                                        f.getAmount() + " BDT", status);
                }

                // Offer to pay
                List<Fine> unpaid = userFines.stream()
                                .filter(f -> f.getPaymentStatus().equals("Unpaid"))
                                .collect(Collectors.toList());
                if (!unpaid.isEmpty()) {
                        System.out.print("\n  Pay a fine? Enter Fine ID (or 0 to skip): ");
                        String fineId = sc.nextLine().trim().toUpperCase();
                        if (!fineId.equals("0")) {
                                Fine target = unpaid.stream()
                                                .filter(f -> f.getFineId().equalsIgnoreCase(fineId))
                                                .findFirst().orElse(null);
                                if (target != null) {
                                        fineCtrl.markAsPaid(target);
                                        printSuccess("Fine " + fineId + " marked as paid!");
                                } else {
                                        printError("Fine ID not found.");
                                }
                        }
                }
        }

        /** Add a new book (Librarian / Admin). */
        static void cliAddBook() {
                printBox("ADD BOOK", CYAN);
                System.out.print("  Book ID     : ");
                String id = sc.nextLine().trim().toUpperCase();
                if (id.isEmpty()) {
                        printError("Book ID cannot be empty.");
                        return;
                }
                if (findBookById(id) != null) {
                        printError("Book ID already exists: " + id);
                        return;
                }
                System.out.print("  Title       : ");
                String title = sc.nextLine().trim();
                System.out.print("  Author      : ");
                String author = sc.nextLine().trim();
                System.out.print("  ISBN        : ");
                String isbn = sc.nextLine().trim();
                System.out.print("  Publisher   : ");
                String publisher = sc.nextLine().trim();
                System.out.print("  Category    : ");
                String category = sc.nextLine().trim();
                System.out.print("  Shelf No.   : ");
                String shelf = sc.nextLine().trim();
                System.out.print("  Copies      : ");
                int copies = 1;
                try {
                        copies = Integer.parseInt(sc.nextLine().trim());
                } catch (Exception e) {
                        copies = 1;
                }

                Book book = new Book(id, title, author, isbn, publisher, category, shelf, copies);
                bookCtrl.addBook(book);
                printSuccess("Book '" + title + "' added successfully!");
        }

        /** Delete a book (Librarian / Admin). */
        static void cliDeleteBook() {
                showAllBooks();
                System.out.print("\n  Enter Book ID to delete (or 0 to cancel): ");
                String bookId = sc.nextLine().trim().toUpperCase();
                if (bookId.equals("0"))
                        return;
                Book book = findBookById(bookId);
                if (book == null) {
                        printError("Book not found: " + bookId);
                        return;
                }
                System.out.print("  Confirm delete '" + book.getTitle() + "'? (yes/no): ");
                if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
                        bookCtrl.deleteBook(book);
                        printSuccess("Book deleted.");
                } else {
                        printWarning("Deletion cancelled.");
                }
        }

        /** Librarian issues a book to a user. */
        static void cliIssueBook(Librarian librarian) {
                printBox("ISSUE BOOK", CYAN);
                userCtrl.manageUsers();
                System.out.print("\n  Enter User ID to issue to: ");
                String userId = sc.nextLine().trim();
                User user = userCtrl.getUsers().stream()
                                .filter(u -> u.getUserId().equalsIgnoreCase(userId))
                                .findFirst().orElse(null);
                if (user == null) {
                        printError("User not found: " + userId);
                        return;
                }

                showAllBooks();
                System.out.print("\n  Enter Book ID to issue: ");
                String bookId = sc.nextLine().trim().toUpperCase();
                Book book = findBookById(bookId);
                if (book == null) {
                        printError("Book not found: " + bookId);
                        return;
                }
                if (!book.checkAvailability()) {
                        printError("No copies available.");
                        return;
                }

                String borrowId = "BR" + String.format("%03d", borrowCounter++);
                BorrowRecord record = new BorrowRecord(borrowId, user.getUserId(),
                                book.getBookId(), LocalDate.now());
                borrowCtrl.issueBook(book, user, record);
                printSuccess("Issued '" + book.getTitle() + "' to " + user.getName() +
                                " | Due: " + record.getDueDate() + " | ID: " + borrowId);
        }

        /** Librarian receives a returned book. */
        static void cliReceiveReturn(Librarian librarian) {
                printBox("RECEIVE RETURNED BOOK", CYAN);
                List<BorrowRecord> active = borrowCtrl.getBorrowRecords().stream()
                                .filter(r -> r.getStatus().equals("Issued"))
                                .collect(Collectors.toList());

                if (active.isEmpty()) {
                        printError("No books currently issued.");
                        return;
                }

                System.out.println();
                System.out.printf("  %-10s %-10s %-35s %-12s %s%n",
                                "BorrowID", "UserID", "Book", "Due Date", "Overdue");
                System.out.println("  " + DIM + "─".repeat(80) + RESET);
                for (BorrowRecord r : active) {
                        Book b = findBookById(r.getBookId());
                        String title = b != null ? b.getTitle() : r.getBookId();
                        int overdue = r.calculateOverdueDays();
                        String flag = overdue > 0 ? RED + overdue + " days" + RESET : GREEN + "-" + RESET;
                        System.out.printf("  %-10s %-10s %-35s %-12s %s%n",
                                        r.getBorrowId(), r.getUserId(), truncate(title, 33), r.getDueDate(), flag);
                }

                System.out.print("\n  Enter Borrow ID to mark as returned (or 0 to cancel): ");
                String borrowId = sc.nextLine().trim().toUpperCase();
                if (borrowId.equals("0"))
                        return;

                BorrowRecord record = findBorrowById(borrowId);
                if (record == null) {
                        printError("Borrow record not found.");
                        return;
                }
                Book book = findBookById(record.getBookId());
                if (book == null) {
                        printError("Book data not found.");
                        return;
                }

                librarian.receiveReturnedBook(book, record); // ← triggers Observer

                int overdueDays = record.calculateOverdueDays();
                if (overdueDays > 0) {
                        // Determine strategy based on who borrowed it
                        User borrower = userCtrl.getUsers().stream()
                                        .filter(u -> u.getUserId().equals(record.getUserId()))
                                        .findFirst().orElse(null);
                        if (borrower instanceof Teacher)
                                fineCtrl.setStrategy(new TeacherFineStrategy());
                        else
                                fineCtrl.setStrategy(new StudentFineStrategy());

                        String fineId = "F" + String.format("%03d", fineCounter++);
                        Fine fine = fineCtrl.calculateFine(fineId, record);
                        printWarning(overdueDays + " day(s) overdue. Fine: " +
                                        fine.getAmount() + " BDT issued to user " + record.getUserId());
                } else {
                        printSuccess("Book returned on time. No fine.");
                }
        }

        /** Admin: add a new user via factory. */
        static void cliAddUser() {
                printBox("ADD USER", CYAN);
                System.out.println("  Types: STUDENT | TEACHER | LIBRARIAN | ADMIN");
                System.out.print("  Type     : ");
                String type = sc.nextLine().trim().toUpperCase();
                System.out.print("  User ID  : ");
                String id = sc.nextLine().trim();
                System.out.print("  Name     : ");
                String name = sc.nextLine().trim();
                System.out.print("  Email    : ");
                String email = sc.nextLine().trim();
                System.out.print("  Dept     : ");
                String dept = sc.nextLine().trim();
                System.out.print("  Phone    : ");
                String phone = sc.nextLine().trim();
                System.out.print("  Password : ");
                String pass = sc.nextLine().trim();

                try {
                        User newUser = UserFactory.createUser(type, id, name, email, dept, phone, pass);
                        userCtrl.registerUser(newUser);
                        if (newUser instanceof Librarian)
                                libCtrl.addLibrarian((Librarian) newUser);
                        printSuccess("User '" + name + "' (" + type + ") added successfully!");
                } catch (IllegalArgumentException e) {
                        printError(e.getMessage());
                }
        }

        /** Admin: delete a user. */
        static void cliDeleteUser() {
                userCtrl.manageUsers();
                System.out.print("\n  Enter User ID to delete (or 0 to cancel): ");
                String uid = sc.nextLine().trim();
                if (uid.equals("0"))
                        return;
                User target = userCtrl.getUsers().stream()
                                .filter(u -> u.getUserId().equalsIgnoreCase(uid))
                                .findFirst().orElse(null);
                if (target == null) {
                        printError("User not found.");
                        return;
                }
                System.out.print("  Confirm delete '" + target.getName() + "'? (yes/no): ");
                if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
                        userCtrl.deleteUser(target);
                        printSuccess("User deleted.");
                } else {
                        printWarning("Deletion cancelled.");
                }
        }

        /** Show user profile details. */
        static void printUserProfile(User user) {
                printBox("MY PROFILE", CYAN);
                System.out.println("  " + CYAN + "Role   : " + RESET + user.getClass().getSimpleName());
                System.out.println("  " + CYAN + "ID     : " + RESET + user.getUserId());
                System.out.println("  " + CYAN + "Name   : " + RESET + user.getName());
                System.out.println("  " + CYAN + "Email  : " + RESET + user.getEmail());
                System.out.println("  " + CYAN + "Phone  : " + RESET + user.getPhone());
                if (user instanceof Student)
                        System.out.println("  " + CYAN + "Dept   : " + RESET + ((Student) user).getDepartment());
                if (user instanceof Teacher)
                        System.out.println("  " + CYAN + "Dept   : " + RESET + ((Teacher) user).getDepartment());
        }

        // ═══════════════════════════════════════════════════════════════════
        // LOOKUP HELPERS
        // ═══════════════════════════════════════════════════════════════════
        static Book findBookById(String id) {
                return library.getInventory().stream()
                                .filter(b -> b.getBookId().equalsIgnoreCase(id))
                                .findFirst().orElse(null);
        }

        static BorrowRecord findBorrowById(String id) {
                return borrowCtrl.getBorrowRecords().stream()
                                .filter(r -> r.getBorrowId().equalsIgnoreCase(id))
                                .findFirst().orElse(null);
        }

        // ═══════════════════════════════════════════════════════════════════
        // UI HELPERS
        // ═══════════════════════════════════════════════════════════════════
        static void printBanner() {
                System.out.println(CYAN + BOLD);
                System.out.println("  ╔══════════════════════════════════════════════════╗");
                System.out.println("  ║      Rajshahi University Library Management System  v1.0       ║");
                System.out.println("  ║   OOD Lab | Singleton·Factory·Observer·Strategy ║");
                System.out.println("  ╚══════════════════════════════════════════════════╝");
                System.out.println(RESET);
                System.out.println(DIM + "  Demo accounts:" + RESET);
                System.out.println(DIM + "    Student  : alice@ru.edu / pass123" + RESET);
                System.out.println(DIM + "    Teacher  : karim@ru.edu / teach456" + RESET);
                System.out.println(DIM + "    Librarian: rahim@ru.edu / lib789" + RESET);
                System.out.println(DIM + "    Admin    : admin@ru.edu / admin000" + RESET);
                System.out.println();
        }

        static void printBox(String title, String color) {
                String bar = "─".repeat(title.length() + 4);
                System.out.println("\n" + color + BOLD + "  ┌" + bar + "┐");
                System.out.println("  │  " + title + "  │");
                System.out.println("  └" + bar + "┘" + RESET);
        }

        static void printSuccess(String msg) {
                System.out.println("\n  " + GREEN + BOLD + "✓ " + msg + RESET);
        }

        static void printError(String msg) {
                System.out.println("\n  " + RED + BOLD + "✗ " + msg + RESET);
        }

        static void printWarning(String msg) {
                System.out.println("\n  " + YELLOW + BOLD + "⚠ " + msg + RESET);
        }

        static String truncate(String s, int max) {
                return s.length() <= max ? s : s.substring(0, max - 1) + "…";
        }
}
