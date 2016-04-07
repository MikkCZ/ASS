package ass.stankmic;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class EmailMessaging implements MessagingStrategy {

    public void sendMessage(String from, String to, String text) {
        System.out.println("Sending e-mail...");
        System.out.println("From: " + from);
        System.out.println("To: " + to);
        System.out.println("E-mail text:");
        System.out.println(text);
    }

}
