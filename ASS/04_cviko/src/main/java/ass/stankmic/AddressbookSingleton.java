package ass.stankmic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class AddressbookSingleton {
    
    private File AddressBook;
    
    public String getEmail(String forLogin) {
        return getUser(forLogin)[1];
    }
    
    public String getPhone(String forLogin) {
        return getUser(forLogin)[2];
    }
    
    public String getPrefered(String forLogin) {
        String[] user = getUser(forLogin);
        if("mail".equals(user[3])) {
            return user[1];
        } else if ("sms".equals(user[3])) {
            return user[2];
        } else {
            return null;
        }
    }
    
    public MessagingStrategy getPrefferedStrategy(String forLogin) {
        String[] user = getUser(forLogin);
        if("mail".equals(user[3])) {
            return new EmailMessaging();
        } else if ("sms".equals(user[3])) {
            return new SMSMessaging();
        } else {
            return null;
        }
    }
    
    private String[] getUser(String forLogin) {
        BufferedReader r;
        try {
            r = new BufferedReader(new FileReader(AddressBook));
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
        String line;
        String[] tmp;
        try {
            while((line = r.readLine()) != null) {
                tmp = line.split(" ");
                if(tmp.length == 3) {
                    if(tmp[0] == null ? forLogin == null : tmp[0].equals(forLogin)) {
                        String[] user = new String[4];
                        user[0] = tmp[0];
                        user[1] = tmp[1].split(",")[0];
                        user[2] = tmp[1].split(",")[1];
                        user[3] = tmp[2];
                        return user;
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
        System.err.println("User not found.");
        return null;
    }
    
    public void setAddressBook(File AddressBook) {
        this.AddressBook = AddressBook;
    }

    private AddressbookSingleton() {
        this.AddressBook = new File("files/addressbook.txt");
    }

    public static AddressbookSingleton getInstance() {
        return AddressbookSingletonHolder.INSTANCE;
    }

    private static class AddressbookSingletonHolder {

        private static final AddressbookSingleton INSTANCE = new AddressbookSingleton();
    }
}
