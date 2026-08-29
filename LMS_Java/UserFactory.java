// ======================================================================
//  Factory Pattern — UserFactory
//
//  Creates User objects (Student, Teacher, Librarian, Admin) based on
//  a role string, without exposing the instantiation logic to the client.
//
//  Pattern: Factory (Simple Factory / Static Factory Method)
// ======================================================================

/**
 * Factory Pattern — UserFactory
 *
 * FACTORY PATTERN:
 * - Centralizes object creation for all User subtypes.
 * - The client (UserController / Admin) calls UserFactory.createUser(...)
 *   instead of writing `new Student(...)` or `new Teacher(...)`.
 * - Adding a new user type (e.g., "GUEST") only requires changing this class.
 *
 * Reason: The system creates different User types at runtime based on
 *         role input. Without a factory, every module would need its own
 *         if-else chain for instantiation, violating DRY and OCP.
 *
 * Supported types: "STUDENT", "TEACHER", "LIBRARIAN", "ADMIN"
 */
public class UserFactory {

    // ── Static Factory Method ─────────────────────────────────────────

    /**
     * Creates and returns a User object of the appropriate subtype.
     *
     * @param type       "STUDENT" | "TEACHER" | "LIBRARIAN" | "ADMIN"
     * @param id         User ID
     * @param name       Full name
     * @param email      Email address
     * @param department Department (used for Student/Teacher; pass "" for others)
     * @param phone      Phone number (pass "" for Admin)
     * @param password   Login password
     * @return           The created User object
     * @throws IllegalArgumentException for unknown type strings
     */
    public static User createUser(String type, String id, String name,
                                  String email, String department,
                                  String phone, String password) {
        System.out.println("[Factory] Creating user of type: " + type);
        switch (type.toUpperCase()) {
            case "STUDENT":
                return new Student(id, name, email, department, phone, password);
            case "TEACHER":
                return new Teacher(id, name, email, department, phone, password);
            case "LIBRARIAN":
                return new Librarian(id, name, email, phone, password);
            case "ADMIN":
                return new Admin(id, name, email, password);
            default:
                throw new IllegalArgumentException(
                    "[Factory] Unknown user type: " + type);
        }
    }
}
