// ======================================================================
//  Boundary: LoginPage
//  Methods: enterEmail, enterPassword, clickLogin,
//           displayLoginError, displayLoginSuccess
// ======================================================================

import java.util.List;

public class LoginPage {

    private LoginController loginCtrl;
    private List<User>      userRegistry;
    private String          email    = "";
    private String          password = "";

    public LoginPage(LoginController loginCtrl, List<User> userRegistry) {
        this.loginCtrl    = loginCtrl;
        this.userRegistry = userRegistry;
    }

    public void enterEmail(String email)       { this.email    = email; }
    public void enterPassword(String password) { this.password = password; }

    public User clickLogin() {
        User user = loginCtrl.login(email, password, userRegistry);
        if (user != null) displayLoginSuccess(user);
        else              displayLoginError();
        return user;
    }

    public void displayLoginError()        { System.out.println("[LoginPage] Login failed. Invalid credentials."); }
    public void displayLoginSuccess(User u){ System.out.println("[LoginPage] Welcome, " + u.getName() +
                                                               "! (" + u.getClass().getSimpleName() + ")"); }
}
