package WebApp.history;
import WebApp.user.User;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class HistoryUtil {

    public static Queue<String> messages = new LinkedList<>();
    private static BufferedReader br = null;
    private static FileWriter fw = null;
    private static BufferedWriter bw = null;
    private static PrintStream out = new PrintStream(System.out);

    public static Queue<String> grabMessageHistory(String username) {
        String line;
        String path = "./messages/" + username + ".txt";
        try{
            br = new BufferedReader(new FileReader(path));
        }catch(FileNotFoundException fnfex) {
            out.println(fnfex.getMessage() + "the file not found");

            System.exit(0);
        }
        try{
            while((line = br.readLine()) != null) {
                // reads in data separated by newline then split into an array of strings
                String[] holdData = line.split("\n");
                String[] splitMessageData = holdData[0].split(":");

                // now, each index of holdData will be a separate user
                // then it will iterate through each index and split the category into user:pw
                // first index will be the name of the user
                String to_add = (splitMessageData[0] + ":" + splitMessageData[1]);
                messages.add(to_add);
            }
            br.close();

            for(String msg: messages){
                System.out.println(msg);
            }

        }catch(IOException ioex) {
            out.println(ioex.getMessage() + "Error reading file");
        }

        return messages;
    }

    public static void saveMessage(String username, String sender, String message) {
        String path = "./messages/" + username + ".txt";
        try {
            fw = new FileWriter(path, true);
            bw = new BufferedWriter(fw);
            String toWrite = sender + ":" + message;
            bw.write(toWrite);
            bw.write("\n");
            bw.close();
        } catch (IOException i) {
            System.exit(0);
        }
    }

}
