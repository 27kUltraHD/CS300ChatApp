package WebApp.util;

import io.javalin.Context;

public class RequestUtil {

    public static String getQueryUsername(Context ctx) {
        return ctx.formParam("username");
    }

    public static String getQueryPassword(Context ctx) {
        return ctx.formParam("password");
    }
}

