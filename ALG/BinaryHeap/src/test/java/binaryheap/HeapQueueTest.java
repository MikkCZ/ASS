package binaryheap;

import cz.cvut.fel.stankmic.HeapQueue;
import cz.cvut.fel.pr1.provided.Comparator;
import cz.cvut.fel.pr1.provided.Task;
import org.junit.After;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class HeapQueueTest {

    private HeapQueue TESTED;
    private Comparator c1, c2;
    private Task[] toOffer;
    private Task[] expected1; //c1
    private Task[] expected2; //c2

    @Before
    public void setUp() {
        TESTED = new HeapQueue();
        createComparators();
        fillTaskArrays();
        TESTED.setComparator(c1);
    }

    private void createComparators() {
        c1 = new Comparator() {
            @Override
            public int compare(Task t1, Task t2) {
                int p1 = t1.getPriority();
                int p2 = t2.getPriority();
                return p1 - p2;
            }
        };
        c2 = new Comparator() {
            @Override
            public int compare(Task t1, Task t2) {
                int p1 = t1.getPriority();
                int p2 = t2.getPriority();
                return p2 - p1;
            }
        };
    }

    private void fillTaskArrays() {
        Task t1 = new Task("Task 1", 1),
                t2 = new Task("Task 2", 2),
                t3 = new Task("Task 3", 3),
                t4 = new Task("Task 4", 4),
                t5 = new Task("Task 5", 5),
                t6 = new Task("Task 6", 6);
        toOffer
                = new Task[]{t2, t3, t5, t2, t1, t4, t6, t6, t3, t1, t4, t1};
        expected1
                = new Task[]{t1, t1, t1, t2, t2, t3, t3, t4, t4, t5, t6, t6}; //c1
        expected2
                = new Task[]{t6, t6, t5, t4, t4, t3, t3, t2, t2, t1, t1, t1}; //c2
    }

    @After
    public void tearDown() {
        TESTED = null;
        c1 = null;
        c2 = null;
    }

    @Test
    public void testOffer() {
        TESTED.offer(toOffer[0]);
        assertSame("The HeapQueue does not peek the offered Task.", toOffer[0], TESTED.peek());
    }

    @Test
    public void testPeek() {
        TESTED.offer(toOffer[0]);
        assertSame("The HeapQueue does not peek the offered Task.", toOffer[0], TESTED.peek());
        assertSame("The HeapQueue does not preserve the previously peeked Task.", toOffer[0], TESTED.peek());
    }

    @Test
    public void testPoll() {
        TESTED.offer(toOffer[0]);
        assertSame("The HeapQueue does not poll the offered Task.", toOffer[0], TESTED.poll());
        assertNotSame("The HeapQueue does not remove the previously polled Task.", toOffer[0], TESTED.poll());
    }

    @Test
    public void testOfferMoreAndPollThan() {
        Task[] expected;
        if (TESTED.getComparator() == c1) {
            expected = expected1;
        } else if (TESTED.getComparator() == c2) {
            expected = expected2;
        } else {
            expected = null;
            fail("Unexpected Comparator found.");
        }

        for (Task t : toOffer) {
            TESTED.offer(t);
        }
        for (Task t : expected) {
            assertSame("The HeapQueue does not peek the offered Task in the right order.", t, TESTED.poll());
        }
    }

    @Test
    public void testSimpleSetGetComparator() {
        Comparator[] comparators = {c1, c2};
        for (Comparator c : comparators) {
            TESTED.setComparator(c);
            assertSame("Setting/getting new Comparator does not work properly.", c, TESTED.getComparator());
        }
    }

    @Test
    public void testUpdateAfterSettingNewComnparator() {
        Comparator[] comparators = {c1, c2, c1, c2};
        for (Comparator c : comparators) {
            for (Task t : toOffer) {
                TESTED.offer(t);
            }
            TESTED.setComparator(c);
            Task[] expected;
            if (c == c1) {
                expected = expected1;
            } else if (c == c2) {
                expected = expected2;
            } else {
                expected = null;
                fail("Unexpected Comparator found.");
            }
            for (Task t : expected) {
                assertSame("The HeapQueue does not update properly after setting new Comparator.", t, TESTED.poll());
            }
        }
    }

    @Test
    public void testGetLeftChild() {
        TESTED.setComparator(c1);
        Task parent = new Task("Root", 1);
        Task child = new Task("Left", 5);
        TESTED.offer(parent);
        TESTED.offer(child);
        assertSame("The left child is not returned properly.", child, TESTED.getLeftChild(parent));
    }

    @Test
    public void testGetRightChild() {
        TESTED.setComparator(c1);
        Task parent = new Task("Root", 1), leftChild = new Task("Left", 5), rightChild = new Task("Right", 5);
        TESTED.offer(parent);
        TESTED.offer(leftChild);
        TESTED.offer(rightChild);
        assertSame("The right child is not returned properly.", rightChild, TESTED.getRightChild(parent));
    }

}
