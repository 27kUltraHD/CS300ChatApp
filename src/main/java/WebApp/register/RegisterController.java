package WebApp.register;

import WebApp.user.User;
import WebApp.user.UserController;
import WebApp.util.RequestUtil;
import io.javalin.Handler;

import WebApp.util.Path;
public class RegisterController {

    public static Handler handleRegisterPost = ctx -> {
        if(!UserController.authenticateCredentials(RequestUtil.getQueryUsername(ctx), RequestUtil.getQueryPassword(ctx))){
            UserController.addNewUser(RequestUtil.getQueryUsername(ctx), RequestUtil.getQueryPassword(ctx));
            ctx.redirect(Path.Web.LOGIN);
        }
    };
}
