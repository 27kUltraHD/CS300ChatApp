package WebApp.register;

import io.javalin.Handler;

import WebApp.user.UserController;
import WebApp.util.Path;

import static WebApp.util.RequestUtil.*;


/*
    Handles register post form data
 */
public class RegisterController {

    public static Handler handleRegisterPost = ctx -> {
        // if user is not in database.. add to database
        if(UserController.authenticateCredentials(getQueryUsername(ctx),getQueryPassword(ctx)) || getQueryUsername(ctx).startsWith(" ") || getQueryPassword(ctx).startsWith(" ")){
                ctx.redirect(Path.Template.REGISTER);
        }
        else{
            UserController.addNewUser(getQueryUsername(ctx), getQueryPassword(ctx));
            ctx.redirect(Path.Web.LOGIN);
        }

    };
}
