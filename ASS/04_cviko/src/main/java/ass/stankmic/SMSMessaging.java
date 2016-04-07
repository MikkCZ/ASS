package ass.stankmic;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class SMSMessaging implements MessagingStrategy {

    public void sendMessage(String from, String to, String text) {
        System.out.println("Sending SMS...");
        System.out.println("From: " + from);
        System.out.println("To: " + to);
        System.out.println("SMS text:");
        System.out.println(text);
    }

}
