// ======================================================================
//  Control: UserController
//  Methods: registerUser, updateUser, deleteUser, searchUser, manageUsers
// ======================================================================

import java.util.ArrayList;
import java.util.List;

public class UserController {

    private List<User> users = new ArrayList<>();

    public void registerUser(User user) {
        users.add(user);
        System.out.println("[UserController] User '" + user.getName() + "' registered.");
    }

    public void deleteUser(User user) {
        users.remove(user);
        System.out.println("[UserController] User '" + user.getName() + "' deleted.");
    }

    public List<User> searchUser(String query) {
        List<User> results = new ArrayList<>();
        for (User u : users) {
            if (u.getName().toLowerCase().contains(query.toLowerCase()) ||
                u.getEmail().toLowerCase().contains(query.toLowerCase())) {
                results.add(u);
            }
        }
        System.out.println("[UserController] Search '" + query + "': " + results.size() + " user(s).");
        return results;
    }

    public void manageUsers() {
        System.out.println("\n[UserController] All Users (" + users.size() + "):");
        for (User u : users) System.out.println("  " + u);
    }

    public List<User> getUsers() { return users; }
}
