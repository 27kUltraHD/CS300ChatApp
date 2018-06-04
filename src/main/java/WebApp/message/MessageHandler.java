package WebApp.message;

import org.json.JSONObject;
import org.eclipse.jetty.websocket.api.Session;
import static j2html.TagCreator.article;
import static j2html.TagCreator.attrs;
import static j2html.TagCreator.b;
import static j2html.TagCreator.p;
import static j2html.TagCreator.span;


import static WebApp.Main.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
    Handles messaging
 */
public class MessageHandler {

    public static void handleMessage(String sender, String recipient, String message){
        if(recipient == null) {
            broadcastMessage(sender, message);
        }
        else{
            sendMessage(sender, recipient, message);
        }

    }
    public static void sendMessage(String sender, String recipient, String message){

    }

    // broadcast to "all users"
    public static void broadcastMessage(String sender, String message) {
        onlineUsers.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try{
                session.send(
                        new JSONObject()
                        .put("userMessage", createHtmlMessageFromSender(sender, message))
                        .put("userlist", onlineUsers.values()).toString()
                );
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    // Builds a HTML element with a sender-name, a message, and a timestamp
    private static String createHtmlMessageFromSender(String sender, String message) {
        return article(
                b(sender + " says:"),
                span(attrs(".timestamp"), new SimpleDateFormat("HH:mm:ss").format(new Date())),
                p(message)
        ).render();
    }

}
