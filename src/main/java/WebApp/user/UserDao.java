package WebApp.user;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.*;
import java.io.*;

public class UserDao {

    public static Map<String, User> stringUserMap = new ConcurrentHashMap<>();
    private static BufferedReader br;
    private static PrintStream out = new PrintStream(System.out);

    public User getUserByUsername(String username) {
        if(username.equals("haison"))
            return new User("haison", "abc123");

        return stringUserMap.get(username);
    }


    private static void loadAccounts(Map<String, User> stringUserMap) {

        String line;

        try{
           br = new BufferedReader(new FileReader("../../../../../accounts.txt"));
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
    }
}
