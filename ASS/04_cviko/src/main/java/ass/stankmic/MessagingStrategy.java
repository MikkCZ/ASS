package ass.stankmic;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public interface MessagingStrategy {

    public void sendMessage(String from, String to, String text);

}
