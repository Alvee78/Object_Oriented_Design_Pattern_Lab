// ======================================================================
//  Library Management System — OOD Lab Assignment (Java)
//  Design Patterns: Singleton, Factory, Observer, Strategy
//  File: User.java  (Abstract Entity)
// ======================================================================

public abstract class User {
    protected String userId;
    protected String name;
    protected String email;
    protected String phone;
    private   String password;        // encapsulated
    protected boolean loggedIn = false;

    public User(String userId, String name, String email,
                String phone, String password) {
        this.userId   = userId;
        this.name     = name;
        this.email    = email;
        this.phone    = phone;
        this.password = password;
    }

    public boolean login(String password) {
        if (this.password.equals(password)) {
            this.loggedIn = true;
            System.out.println("[Auth] " + name + " logged in successfully.");
            return true;
        }
        System.out.println("[Auth] Invalid credentials.");
        return false;
    }

    public void logout() {
        this.loggedIn = false;
        System.out.println("[Auth] " + name + " logged out.");
    }

    public boolean isLoggedIn()  { return loggedIn; }
    public String  getUserId()   { return userId; }
    public String  getName()     { return name; }
    public String  getEmail()    { return email; }
    public String  getPhone()    { return phone; }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
               "(id=" + userId + ", name=" + name + ")";
    }
}
