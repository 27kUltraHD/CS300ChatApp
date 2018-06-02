package WebApp.user;

import static WebApp.Main.*;

public class UserController {

    // Authenticate user by comparing username and password
    public static boolean authenticateCredentials(String username, String password) {
        if(username == null || password == null)
            return false;
        User user = userDao.getUserByUsername(username);
        if(user.username  == null)
            return false;

        return password.equals(user.password);
    }
}
