package WebApp.login;

import io.javalin.Handler;

import WebApp.user.UserController;
import WebApp.util.RequestUtil;
import WebApp.util.Path;
/*
    This class handles login/logout POST method + login functionality
 */
public class LoginController {

    // simple handler to redirect to login page
    public static Handler serveLoginPage = ctx -> {
        ctx.redirect(Path.Template.LOGIN);
    };

    // handles login form post data & authenticating credentials
    public static Handler handleLoginPost = ctx -> {
        // if the user data is not found in the userDAO
        if(!UserController.authenticateCredentials(RequestUtil.getQueryUsername(ctx), RequestUtil.getQueryPassword(ctx))){
            // redirect back to login
            ctx.html("Bad login credentials");
            ctx.redirect(Path.Web.LOGIN, 500);

        }else { // successful login
            // sets "currentUser" to current user name
            // redirects to chatapp
            ctx.request().getSession().setAttribute("currentUser", RequestUtil.getQueryUsername(ctx));
            ctx.redirect(Path.Template.INDEX);
        }
    };

    // handlers logout post
    public static Handler handleLogoutPost = ctx -> {
        // removes current user from session
        // redirects to login
        ctx.request().getSession().removeAttribute("currentUser");
        ctx.redirect(Path.Web.LOGIN);
    };

    // requires user to login before viewing app page
    public static Handler ensureLoginBeforeViewingApp = ctx -> {
        // if the user is not trying to access index...
        if (!ctx.path().equals("/index.html")) {
            return;
        }
        // else ensure login
        // redirect to login
        if (ctx.request().getSession().getAttribute("currentUser") == null) {
            ctx.request().setAttribute("errorMessage", "You need to log in before you use this feature");
            ctx.redirect(Path.Web.LOGIN);
        }
    };
}
