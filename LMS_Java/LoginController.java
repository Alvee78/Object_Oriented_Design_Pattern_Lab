// ======================================================================
//  Control: LoginController
//  Methods: login, logout, authenticateUser, validateCredentials, manageSession
// ======================================================================

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginController {

    private Map<String, User> sessions = new HashMap<>();

    public User validateCredentials(String email, String password,
                                    List<User> userRegistry) {
        for (User u : userRegistry) {
            if (u.getEmail().equals(email)) return u;
        }
        return null;
    }

    public User authenticateUser(String email, String password,
                                  List<User> userRegistry) {
        User user = validateCredentials(email, password, userRegistry);
        if (user != null && user.login(password)) {
            manageSession(user);
            return user;
        }
        return null;
    }

    public User login(String email, String password, List<User> userRegistry) {
        return authenticateUser(email, password, userRegistry);
    }

    public void logout(User user) {
        user.logout();
        sessions.remove(user.getUserId());
    }

    public void manageSession(User user) {
        sessions.put(user.getUserId(), user);
        System.out.println("[LoginController] Session started for " + user.getName() + ".");
    }
}
