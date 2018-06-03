package WebApp.login;

import WebApp.user.UserController;
import WebApp.util.RequestUtil;
import io.javalin.Handler;

import WebApp.util.Path;

public class LoginController {


    // simple handler to redirect to login page
    public static Handler serveLoginPage = ctx -> {
        ctx.redirect(Path.Template.LOGIN);
    };

    // handles login form data & authenticating credentials
    public static Handler handleLoginPost = ctx -> {
        if(!UserController.authenticateCredentials(RequestUtil.getQueryUsername(ctx), RequestUtil.getQueryPassword(ctx))){
           // System.out.println(RequestUtil.getQueryUsername(ctx) + " " + RequestUtil.getQueryPassword(ctx));
           ctx.redirect(Path.Template.LOGIN);
            //ctx.request().setAttribute("errorMessage", "Invalid username/password, please try again");
        }else {
            ctx.request().getSession().setAttribute("currentUser", RequestUtil.getQueryUsername(ctx));
            ctx.request().setAttribute("errorMessage", null);
            ctx.redirect(Path.Template.INDEX);
        }
    };

    public static Handler handleLogoutPost = ctx -> {
        ctx.request().getSession().removeAttribute("currentUser");
        ctx.redirect(Path.Web.LOGIN);
    };

    // requires user to login before viewing app page
    public static Handler ensureLoginBeforeViewingApp = ctx -> {
        if (!ctx.path().equals("/index.html")) {
            return;
        }
        if (ctx.request().getSession().getAttribute("currentUser") == null) {
            ctx.request().setAttribute("errorMessage", "You need to log in before you use this feature");
            ctx.redirect(Path.Web.LOGIN);
        }
    };
}
