package ass.stankmic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class AddressbookSingletonTest {

    ByteArrayOutputStream testedOutput;
    File testingAddressBook;
    
    @Before
    public void setUp() {
        try {
            createTestingAddressBook();
        } catch (IOException ex) {
            Logger.getLogger(AddressbookSingletonTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        testedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testedOutput));
    }
    
    @After
    public void tearDown() {
        System.setOut(System.out);
        try {
            testedOutput.flush();
            testedOutput.close();
        } catch (IOException ex) {
            Logger.getLogger(AddressbookSingletonTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        testedOutput = null;
        testingAddressBook.delete();
        testingAddressBook = null;
    }
    
    private void createTestingAddressBook() throws IOException {
        final String testingContent = "smsman sms@email,567343243 sms\n"
                + "mailman mail@email,927890223 mail";
        testingAddressBook = Files.createTempFile("testing", "file").toFile();
        if(!testingAddressBook.exists()) {
            testingAddressBook.createNewFile();
        }
        FileWriter w = new FileWriter(testingAddressBook);
        w.write(testingContent);
        w.flush();
        w.close();
    }
    
    @Test
    public void testAddressBookSingleton() {
        //TODO
    }

}
