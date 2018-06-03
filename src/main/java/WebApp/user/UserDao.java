package WebApp.user;

import WebApp.util.Path;
import org.eclipse.jetty.util.IO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.*;
import java.io.*;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class UserDao {

    public static Map<String, User> stringUserMap = null;
    private static BufferedReader br = null;
    private static FileWriter fw = null;
    private static BufferedWriter bw = null;
    private static PrintStream out = new PrintStream(System.out);

    public User getUserByUsername(String username) {
        if(stringUserMap == null)
            stringUserMap = new ConcurrentHashMap<>();

        stringUserMap = loadAccounts(stringUserMap);

        return stringUserMap.get(username);
    }

    public void addUserToDAO(User user){
        if(stringUserMap == null)
            stringUserMap = new ConcurrentHashMap<>();

        writeToAccounts(user);
        stringUserMap.put(user.username, user);
    }

    private static void writeToAccounts(User user) {
        try {
            fw = new FileWriter("../../accounts", true);
            bw = new BufferedWriter(fw);
            String toWrite = user.username + ":" + user.password;
            System.out.println(toWrite);
            bw.write("\n");
            bw.write(toWrite);
            bw.close();
        } catch (IOException i) {
            System.exit(0);
        }
    }

    private static Map<String, User>loadAccounts(Map<String, User> stringUserMap) {

        String line;

        try{
           br = new BufferedReader(new FileReader("C:\\Users\\HG0D-SSD\\Desktop\\CS300ChatApp\\accounts"));
        }catch(FileNotFoundException fnfex) {
            out.println(fnfex.getMessage() + "the file not found");
            System.exit(0);
        }

        try{
            while((line = br.readLine()) != null) {

                // reads in data separated by newline then split into an array of strings
                String[] holdData = line.split("\n");
                String[] splitUserData = holdData[0].split(":");

                // now, each index of holdData will be a separate user
                // then it will iterate through each index and split the category into user:pw
                // first index will be the name of the user
                User userToAdd = new User(splitUserData[0], splitUserData[1]);
                stringUserMap.put(userToAdd.username, userToAdd);

            }
        }catch(IOException ioex) {
            out.println(ioex.getMessage() + "Error reading file");
        }

        return stringUserMap;
    }
}
