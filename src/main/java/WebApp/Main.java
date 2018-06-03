package WebApp;

import WebApp.login.LoginController;
import WebApp.register.RegisterController;
import WebApp.user.UserDao;
import WebApp.util.Path;
import io.javalin.Javalin;
import io.javalin.embeddedserver.jetty.websocket.WsSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;
import WebApp.util.HerokuUtil;
import static j2html.TagCreator.article;
import static j2html.TagCreator.attrs;
import static j2html.TagCreator.b;
import static j2html.TagCreator.p;
import static j2html.TagCreator.span;

import WebApp.user.User;
//import sun.rmi.runtime.Log;


public class Main {


    public static Map<WsSession, User> onlineUsers = new ConcurrentHashMap<>();
    public static UserDao userDao;// user data access object (database in our example)

    public static void main(String[] args){

        userDao = new UserDao();
        // create an Javalin application
        // set stuff
        // start
        Javalin app = Javalin.create()
                .port(HerokuUtil.getHerokuAssignedPort())
                .enableStaticFiles("/public")
                .start();

        // ensures users hit login page first
        //app.before(LoginController.ensureLoginBeforeViewingApp);
        // handle post data from "/login"
        app.before(LoginController.ensureLoginBeforeViewingApp);
        app.get(Path.Web.LOGIN, LoginController.serveLoginPage);
        app.post(Path.Web.LOGIN, LoginController.handleLoginPost);
        app.post(Path.Web.LOGOUT, LoginController.handleLogoutPost);
        app.post(Path.Web.REGISTER, RegisterController.handleRegisterPost);


    }

}
