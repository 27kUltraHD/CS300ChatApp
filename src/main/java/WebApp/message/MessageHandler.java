package WebApp.message;

import WebApp.history.HistoryUtil;
import org.json.JSONObject;
import io.javalin.embeddedserver.jetty.websocket.WsSession;
import org.eclipse.jetty.websocket.api.Session;
import static j2html.TagCreator.article;
import static j2html.TagCreator.attrs;
import static j2html.TagCreator.b;
import static j2html.TagCreator.p;
import static j2html.TagCreator.span;


import static WebApp.Main.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;

/*
    Handles messaging
 */
public class MessageHandler {

    public static void handleMessage(WsSession session, String sender, String recipient, String message){
        if(recipient == null) {
            HistoryUtil.saveMessage(onlineUsers.get(session), sender, message);
            broadcastMessage(sender, message);
        }
        else {
            sendMessage(sender, recipient, message);
        }

    }
    public static void sendMessage(String sender, String recipient, String message){

    }

    public static void loadMessageHistory(WsSession session){

        Queue<String> history = HistoryUtil.grabMessageHistory(onlineUsers.get(session));
                try{
                    for(String msg: history){
                        String[] fullString = msg.split(":");
                        String sender = fullString[0];
                        String message = fullString[1];
                        session.send(
                                new JSONObject()
                                        .put("userMessage", createHtmlMessageFromSender(sender, message))
                                        .put("userlist", onlineUsers.values()).toString()
                        );
                    }
                } catch (Exception e){
                        e.printStackTrace();
                    }


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
