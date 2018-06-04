package WebApp.user;

import static WebApp.Main.*;

/*
    User controller class that has access to current user data and database
 */
public class UserController {

    // keep track of current user
    public static User currentUser = null;

    // Authenticate user by comparing username and password
    public static boolean authenticateCredentials(String username, String password) {
        if(username == null || password == null)
            return false;

       currentUser = userDao.getUserByUsername(username);

        if(currentUser == null)
            return false;

        return password.equals(currentUser.password);
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void addNewUser(String username, String password){
        if(username == null || password == null)
            return;

        User user = new User(username, password);
        userDao.addUserToDAO(user);
    }
}
