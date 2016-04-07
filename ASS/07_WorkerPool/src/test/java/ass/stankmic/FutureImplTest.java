package ass.stankmic;

import java.lang.Thread.State;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class FutureImplTest {

    private FutureImpl TESTED;

    @Before
    public void setUp() {
        TESTED = new FutureImpl();
    }

    @After
    public void tearDown() {
        TESTED = null;
    }

    @Test
    public void correctResultTest() throws Exception {
        Object result = new Object();
        TESTED.setResult(result);
        assertSame("Incorrect result returned", result, TESTED.get());
    }

    @Test
    public void correctExceptionTest() {
        Exception expected = new Exception();
        TESTED.setException(expected);
        try {
            TESTED.get();
        } catch (Exception ex) {
            assertSame("Incorrect exception thrown", expected, ex);
        }
    }

    @Test
    public synchronized void testBlocking() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    TESTED.get();
                } catch (Exception ex) {
                    Logger.getLogger(FutureImplTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
        wait(1000);
        State state = t.getState();
        assertEquals("The thread has not been blocked when no result or exception given.", Thread.State.WAITING, state);
    }

    @Test
    public synchronized void wakeOnSetResultTest() throws InterruptedException {
        final Object result = new Object();
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    assertSame("Incorrect result returned", result, TESTED.get());
                } catch (Exception ex) {
                    assertTrue("Unexpected exception thrown",false);
                }
            }
        });
        t.start();
        TESTED.setResult(result);
        wait(1000);
        State state = t.getState();
        assertFalse("The thread has not been waken up properly.", Thread.State.WAITING==state);
    }
    
    public synchronized void wakeOnSetExceptionTest() throws InterruptedException {
        final Exception expected = new Exception();
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    TESTED.get();
                    assertTrue("No result expected, exception should be thrown",false);
                } catch (Exception ex) {
                    assertSame("Incorrect exception thrown", expected, ex);
                }
            }
        });
        t.start();
        TESTED.setException(expected);
        wait(1000);
        State state = t.getState();
        assertFalse("The thread has not been waken up properly.", Thread.State.WAITING==state);
    }
}
