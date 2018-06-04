package WebApp;

import io.javalin.Javalin;
import io.javalin.embeddedserver.jetty.websocket.WsSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import WebApp.login.LoginController;
import WebApp.register.RegisterController;
import WebApp.user.User;
import WebApp.user.UserController;
import WebApp.user.UserDao;
import WebApp.util.HerokuUtil;
import WebApp.util.Path;
import WebApp.message.MessageHandler;

import static io.javalin.ApiBuilder.*;
/*
    This class handles Model-View-Controller interaction
    // Model UserDao -> User data access object
    // View onlineUsers
    // Controllers login/message/register/user
 */
public class Main {
    // a map of online users with their websocket session as a key and their username as a value
    public static Map<WsSession, String> onlineUsers = new ConcurrentHashMap<>();
    // instantiate dependecy
    public static UserDao userDao;// user data access object (database in our example)

    public static void main(String[] args){
        // instantiate dependency
        userDao = new UserDao();
        // create an Javalin application
        // set stuff
        // start
        Javalin app = Javalin.create()
                .port(HerokuUtil.getHerokuAssignedPort())
                .enableStaticFiles("/public")
                .enableRouteOverview("/routes")
                // Web socket maps to "/index" and takes in a ws object that has handlers for connection open/close and messaging events
                .ws("/index", ws -> {
                    ws.onConnect(session -> {
                        /*
                            Upon opening a websocket connection
                            1. grabs the current user (after login)
                            2. adds the user to onlineUsers
                            3. broadcast into the channel that a user has entered
                         */
                        User user = UserController.getCurrentUser();
                        onlineUsers.put(session, user.username);
                        MessageHandler.handleMessage("server", null, (user.username + " has joined the chat"));
                    });
                    /*
                        Upon receiving a message
                        pass that to message handler lol
                     */
                    ws.onMessage((session, message) -> {
                        MessageHandler.handleMessage(onlineUsers.get(session),null, message);
                    });
                    /*
                        Upon websocket connection closing
                        1. grabs the current user from online via key
                        2. remove from view
                     */
                    ws.onClose((session, statusCode, reason) -> {
                        String user = onlineUsers.get(session);
                        onlineUsers.remove(user);
                    });
                })
                .start();


        app.routes(() -> {
            before(LoginController.ensureLoginBeforeViewingApp);
            get(Path.Web.LOGIN, LoginController.serveLoginPage);
            post(Path.Web.LOGIN, LoginController.handleLoginPost);
            post(Path.Web.LOGOUT, LoginController.handleLogoutPost);
            post(Path.Web.REGISTER, RegisterController.handleRegisterPost);
        });



    }

}
