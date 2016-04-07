package ass.stankmic;

import java.lang.Thread.State;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class ObjectPoolImplTest {

    private ObjectPool<CloneableSomething> TESTED;

    @Before
    public void setUp() {
        TESTED = ObjectPoolImpl.getInstance(new CloneableSomething());
    }

    @After
    public void tearDown() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//        GC is not reliable and for more objects seems to not place them back in the ReferenceQueue in time - for testing pool is reset needed
        Class poolClass = TESTED.getClass();
        Method resetPool = poolClass.getDeclaredMethod("reset");
        resetPool.setAccessible(true);
        resetPool.invoke(null);
        TESTED = null;
    }

    @Test
    public void testGetInstance() {
        ObjectPool<CloneableSomething> pool = ObjectPoolImpl.getInstance(new CloneableSomething());
        assertNotNull(pool);
    }

    @Test
    public void borrowSimpleTest() throws InterruptedException {
        borrowInstancesTest(1, State.TERMINATED);
    }

    @Test
    public void borrowMinInstancesTest() throws InterruptedException {
        borrowInstancesTest(ObjectPoolImpl.MIN_INSTANCES, State.TERMINATED);
    }

    @Test
    public void borrowMaxInstancesTest() throws InterruptedException {
        borrowInstancesTest(ObjectPoolImpl.MAX_INSTANCES, State.TERMINATED);
    }

    @Test
    public void borrowMoreThanMaxInstancesTest() throws InterruptedException {
        borrowInstancesTest2(ObjectPoolImpl.MAX_INSTANCES, State.TERMINATED, 1, State.WAITING);
    }

    private synchronized void borrowInstancesTest(int numOfInstances, State expectedState) throws InterruptedException {
        Thread[] borrowing = new Thread[numOfInstances];
        for (int i = 0; i < borrowing.length; i++) {
            borrowing[i] = new BorrowingThread();
            borrowing[i].start();
        }
        wait(borrowing.length * 10);
        for (int i = 0; i < borrowing.length; i++) {
            Thread t = borrowing[i];
            State state = t.getState();
            assertEquals("The borrowing thread " + i + " state is different than expected.", expectedState, state);
        }
    }

    private synchronized void borrowInstancesTest2(int numOfInstances, State expectedState, int numOfInstances2, State expectedState2) throws InterruptedException {
        Thread[] borrowing = new Thread[numOfInstances];
        for (int i = 0; i < borrowing.length; i++) {
            borrowing[i] = new BorrowingThread();
            borrowing[i].start();
        }
        wait(borrowing.length * 10);
        for (int i = 0; i < borrowing.length; i++) {
            Thread t = borrowing[i];
            State state = t.getState();
            assertEquals("The borrowing thread " + i + " state is different than expected.", expectedState, state);
        }
        borrowInstancesTest(numOfInstances2, expectedState2);
    }

    private class BorrowingThread extends Thread {

        private CloneableObject o;

        @Override
        public void run() {
            o = TESTED.borrowObject();
        }

    }

}
