# Library Management System — OOD Lab Assignment

**Course:** Object-Oriented Design (OOD) Lab  
**Language:** Java  
**Design Patterns Used:** Singleton · Factory · Observer · Strategy

---

## 📁 Project Structure

```
New folder (3)/
├── LMS_Java/                        ← Java source code (this project)
│   ├── Main.java                    ← Entry point — Interactive CLI
│   │
│   ├── ── Design Pattern Files ──
│   ├── BookObserver.java            ← Observer Pattern: Observer interface
│   ├── BookSubject.java             ← Observer Pattern: Subject interface
│   ├── FineStrategy.java            ← Strategy Pattern: Strategy interface
│   ├── StudentFineStrategy.java     ← Strategy Pattern: 5 BDT/day
│   ├── TeacherFineStrategy.java     ← Strategy Pattern: 3 BDT/day
│   ├── UserFactory.java             ← Factory Pattern: creates all User types
│   │
│   ├── ── Entity Objects ──
│   ├── User.java                    ← Abstract base user
│   ├── Student.java                 ← Student entity + BookObserver
│   ├── Teacher.java                 ← Teacher entity + BookObserver
│   ├── Librarian.java               ← Librarian entity
│   ├── Admin.java                   ← Admin entity
│   ├── Book.java                    ← Book entity + BookSubject (Observer)
│   ├── BorrowRecord.java            ← Borrow record entity
│   ├── Fine.java                    ← Fine entity (uses FineStrategy)
│   ├── Library.java                 ← Library entity (Singleton)
│   ├── Report.java                  ← Report entity
│   │
│   ├── ── Control Objects ──
│   ├── LoginController.java
│   ├── UserController.java
│   ├── LibrarianController.java
│   ├── BookController.java
│   ├── BorrowController.java
│   ├── FineController.java          ← Strategy Pattern: Context
│   └── ReportController.java
│   │
│   └── ── Boundary Objects ──
│       ├── LoginPage.java
│       ├── StudentDashboard.java
│       └── AdminDashboard.java
│
├── library_management_system.py     ← Python version (OOD concepts demo)
├── ood.docx                         ← Original OOD lab document
├── ood_updated.docx                 ← With Design Pattern section filled in
└── ood_final.docx                   ← Final: full doc + all Java source code
```

---

## 🚀 How to Run

### Prerequisites
- Java JDK 8 or higher installed
- Open a terminal / command prompt

### Steps

```bash
# 1. Navigate to the Java folder
cd "Object_Oriented_Design_Pattern_Lab\LMS_Java\"

# 2. Compile all Java files
javac *.java

# 3. Run the interactive CLI
java Main
```

---

## 👤 Demo Accounts

| Role      | Email              | Password  |
|-----------|--------------------|-----------|
| Student   | alice@ru.edu       | pass123   |
| Student   | bob@ru.edu         | bob123    |
| Teacher   | karim@ru.edu       | teach456  |
| Librarian | rahim@ru.edu       | lib789    |
| Admin     | admin@ru.edu       | admin000  |

---

## 🎛️ CLI Menu Overview

### Main Menu
```
[1] Login
[2] Show All Books
[0] Exit
```

### Student / Teacher Menu
```
[1] Search Book
[2] Borrow Book        ← picks book by ID after search
[3] Return Book        ← shows active borrows, auto-calculates fine if overdue
[4] View Borrow History
[5] View Fines         ← option to pay fine inline
[6] View My Profile
[7] Show All Books
[0] Logout
```

### Librarian Menu
```
[1] Show All Books
[2] Add Book           ← enter all book details interactively
[3] Search Book
[4] Issue Book to User ← select user + book
[5] Receive Returned Book ← triggers Observer notification + auto-fine
[6] Show All Users
[7] Delete Book
[0] Logout
```

### Admin Menu
```
[1]  View Dashboard
[2]  Manage Users
[3]  Add New User      ← uses Factory Pattern
[4]  Delete User
[5]  Manage Librarians
[6]  Add Book
[7]  Show All Books
[8]  Generate Borrow Report
[9]  Generate Fine Report
[10] Generate Inventory Report
[11] Generate User Report
[0]  Logout
```

---

## 🏗️ Design Patterns

### 1. 🔵 Singleton — `Library.java`
> Only one Library instance exists in the entire application.

```java
Library lib1 = Library.getInstance("LIB001", "RU Library", ...);
Library lib2 = Library.getInstance(...);   // returns SAME object
System.out.println(lib1 == lib2);          // true
```

### 2. 🟢 Factory — `UserFactory.java`
> Creates User objects (Student/Teacher/Librarian/Admin) from a type string.

```java
User student = UserFactory.createUser("STUDENT", "S001", "Alice", ...);
User teacher = UserFactory.createUser("TEACHER", "T001", "Dr. Karim", ...);
```

### 3. 🟡 Observer — `Book.java` + `Student/Teacher.java`
> When a returned book becomes available, all waiting users are notified automatically.

```java
book.registerObserver(student);  // student subscribes to this book
borrowCtrl.returnBook(book, record);
// → book.incrementCopy() → notifyObservers() → student.update(book)
// → "[Notification] 'Clean Code' is now available!"
```

### 4. 🔴 Strategy — `FineController.java`
> Fine rate switches at runtime based on user type. No if-else in the controller.

```java
// When student logs in:
fineCtrl.setStrategy(new StudentFineStrategy()); // 5 BDT/day

// When teacher logs in:
fineCtrl.setStrategy(new TeacherFineStrategy()); // 3 BDT/day

// Same call — different algorithm:
fineCtrl.calculateFine("F001", borrowRecord);
```

---

## 📋 Fine Policy

| User Type | Rate Per Overdue Day |
|-----------|----------------------|
| Student   | 5 BDT / day          |
| Teacher   | 3 BDT / day          |

Fines are automatically calculated when a book is returned after its due date.  
Users can view and pay their fines from their dashboard.

---

## 📚 Pre-Loaded Books

| ID   | Title                        | Category          | Copies |
|------|------------------------------|-------------------|--------|
| B001 | Clean Code                   | Programming       | 3      |
| B002 | Design Patterns              | Software Eng.     | 2      |
| B003 | Introduction to Algorithms   | Algorithms        | 5      |
| B004 | The Pragmatic Programmer     | Programming       | 2      |
| B005 | Operating System Concepts    | Systems           | 4      |
| B006 | Computer Networks            | Networks          | 3      |
| B007 | Database System Concepts     | Database          | 4      |

---

## 📄 Documents

| File               | Contents                                         |
|--------------------|--------------------------------------------------|
| `ood.docx`         | Original lab document                            |
| `ood_updated.docx` | Lab doc + Design Pattern section filled in       |
| `ood_final.docx`   | Complete submission: doc + all 27 Java source files |
