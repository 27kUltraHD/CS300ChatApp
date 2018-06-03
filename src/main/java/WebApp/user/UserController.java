package WebApp.user;

import static WebApp.Main.*;

public class UserController {

    // Authenticate user by comparing username and password
    public static boolean authenticateCredentials(String username, String password) {
        if(username == null || password == null)
            return false;

        User user = userDao.getUserByUsername(username);

        if(user == null)
            return false;

        return password.equals(user.password);
    }

    public static void addNewUser(String username, String password){
        if(username == null || password == null)
            return;

        User user = new User(username, password);
        userDao.addUserToDAO(user);
    }
}
