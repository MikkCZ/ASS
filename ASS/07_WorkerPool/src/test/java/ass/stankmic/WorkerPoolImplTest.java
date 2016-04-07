package ass.stankmic;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class WorkerPoolImplTest {

    private WorkerPool TESTED;

    @Before
    public void setUp() {
        TESTED = new WorkerPoolImpl(Runtime.getRuntime().availableProcessors());
    }

    @After
    public void tearDown() {
        TESTED = null;
    }

    @Test
    public void testCorrectResult() throws Exception {
        final Object result = new Object();
        Callable c = new Callable() {

            public Object call() throws Exception {
                return result;
            }

        };
        Collection<Callable> callables = new LinkedList();
        callables.add(c);
        List<Future> futures = TESTED.call(callables);
        Future f = futures.get(0);
        assertSame("Incorrect result returned", result, f.get());
    }

    @Test
    public void testCorrectException() {
        final Exception expected = new Exception();
        Callable exceptionCallable = new Callable() {

            public Object call() throws Exception {
                throw expected;
            }

        };
        Collection<Callable> callables = new LinkedList();
        callables.add(exceptionCallable);
        List<Future> futures = TESTED.call(callables);
        Future f = futures.get(0);
        try {
            f.get();
            assertTrue("No result expected, exception should be thrown", false);
        } catch (Exception ex) {
            assertSame("Incorrect exception thrown", expected, ex);
        }
    }

    @Test
    public void testMoreCollables() {
        LinkedList<Callable> callables = new LinkedList();
        LinkedList<Object> results = new LinkedList();
        LinkedList<Exception> expected = new LinkedList();
        fillCallables(callables, results, expected);
        List<Future> futures = TESTED.call(callables);
        for(Future f : futures) {
            try {
                Object result = f.get();
                assertSame("Incorrect result returned", results.pop(), result);
            } catch (Exception ex) {
                assertSame("Incorrect exception thrown", expected.pop(), ex);
            }
        }
    }

    private void fillCallables(LinkedList<Callable> callables, LinkedList<Object> results, LinkedList<Exception> expected) {
        for (int i = 0; i < 100; i += 2) {
            // Callable returning result
            final Object result = new Object();
            results.add(result);
            callables.add(new Callable() {

                public Object call() throws Exception {
                    return result;
                }

            });

            // Callable throwing and Exception
            final Exception exception = new Exception();
            expected.add(exception);
            callables.add(new Callable() {

                public Object call() throws Exception {
                    throw exception;
                }

            });
        }
    }

}
