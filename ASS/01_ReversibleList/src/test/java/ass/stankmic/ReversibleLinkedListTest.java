package ass.stankmic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReversibleLinkedListTest {

    private ReversibleList<Filling> TESTED;

    public ReversibleLinkedListTest() {
    }

    @Before
    public void setUp() {
        TESTED = new ReversibleLinkedList<Filling>();
    }

    @After
    public void tearDown() {
        TESTED = null;
    }

    @Test
    public void testSize() {
        int expected = 6;
        fillTestedWith(expected);
        assertEquals("The returned size of the list is incorrect.", expected, TESTED.size());
    }

    @Test
    public void testIsEmpty() {
        assertEquals("New list seems as not empty.", true, TESTED.isEmpty());
        Filling tmp = new Filling(0);
        TESTED.add(tmp);
        assertEquals("List with an object seems empty.", false, TESTED.isEmpty());
        TESTED.remove(tmp);
        assertEquals("List with no objects does not seem empty.", true, TESTED.isEmpty());
    }

    @Test
    public void testContains() {
        Filling tmp = new Filling(0);
        assertEquals("New list contains an object.", false, TESTED.contains(tmp));
        TESTED.add(tmp);
        assertEquals("List does not contain added object.", true, TESTED.contains(tmp));
        TESTED.remove(tmp);
        assertEquals("List still contains removed object.", false, TESTED.contains(tmp));
    }

    @Test
    public void testIterator() {
        ArrayList<Filling> expected = new ArrayList<Filling>();
        fillCollectionWith(expected, 8);
        for (int i = 0; i < expected.size(); i++) {
            TESTED.add(expected.get(i));
        }
        int i = 0;
        for (Filling f : TESTED) {
            assertEquals("Ascending iterator is not implemented correctly.", expected.get(i++), f);
        }
    }

    @Test
    public void testAdd() {
        Filling tmp = new Filling(0);
        TESTED.add(tmp);
        assertEquals("Object is not added into the list.", true, TESTED.contains(tmp));
    }

    @Test
    public void testRemove() {
        Filling tmp = new Filling(0);
        TESTED.add(tmp);
        TESTED.remove(tmp);
        assertEquals("Object is not removed from the list.", false, TESTED.contains(tmp));
    }
    
    @Test
    public void testRemoveReturnValue1() {
        Filling tmp = new Filling(0);
        TESTED.add(tmp);
        boolean removed = TESTED.remove(tmp);
        assertEquals("Remove function should return proper value when removing object.", true, removed);
    }
    
    @Test
    public void testRemoveReturnValue2() {
        boolean removed = TESTED.remove(new Filling(0));
        assertEquals("Remove function should return proper value when removing object which is not in the list.", false, removed);
    }

    @Test
    public void testAddAll() {
        Collection<Filling> tmp = new ArrayList<Filling>();
        fillCollectionWith(tmp, 8);
        TESTED.addAll(tmp);
        for (Filling o : tmp) {
            assertEquals("Object is not added into the list.", true, TESTED.contains(o));
        }
    }

    @Test
    public void testClear() {
        fillTestedWith(8);
        TESTED.clear();
        assertEquals("List does not seem empty after clear.", true, TESTED.isEmpty());
    }
    
    @Test
    public void testSizeAfterClear() {
        int expected = 6;
        fillTestedWith(expected);
        TESTED.clear();
        assertEquals("The list size should be zero after clear.", 0, TESTED.size());
    }

    @Test
    public void testSort() {
        fillTestedWith(8);
        TESTED.sort();
//        for (Filling f : TESTED) {
//            System.out.println(f);
//        }
//        System.out.println();
        Filling previous = null;
        for (Filling f : TESTED) {
            if (previous != null) {
                assertEquals("List is not sorted correctly.", true, previous.compareTo(f) <= 0);
            }
            previous = f;
        }
    }

    @Test
    public void testDescIterator() {
        ArrayList<Filling> expected = new ArrayList<Filling>();
        fillCollectionWith(expected, 8);
        for (int i = 0; i < expected.size(); i++) {
            TESTED.add(expected.get(i));
        }
        Iterator<Filling> iter = TESTED.descendingIterator();
        int i = expected.size();
        while (iter.hasNext()) {
            Filling f = iter.next();
            assertEquals("Descending iterator is not implemented correctly.", expected.get(--i), f);
        }
    }

    private void fillTestedWith(int number) {
        // descending to avoid pre-sorted List
        for (int i = number; i > 0; i--) {
            TESTED.add(new Filling(i));
        }
    }

    private void fillCollectionWith(Collection<Filling> collection, int number) {
        // descending to avoid pre-sorted List
        for (int i = number; i > 0; i--) {
            collection.add(new Filling(i));
        }
    }

    private class Filling implements Comparable<Filling> {

        private final int VALUE;

        public Filling(int VALUE) {
            this.VALUE = VALUE;
        }

        public int compareTo(Filling o) {
            final int BEFORE = -1, EQUAL = 0, AFTER = 1;
            final Filling other = (Filling) o;
            if (this.equals(other)) {
                return EQUAL;
            } else if (this.VALUE < other.VALUE) {
                return BEFORE;
            } else {
                return AFTER;
            }
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + this.VALUE;
            return hash;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (this.getClass() != o.getClass()) {
                return false;
            }
            if (this.hashCode() != o.hashCode()) {
                return false;
            }
            final Filling other = (Filling) o;
            return (this.VALUE == other.VALUE);
        }

        @Override
        public String toString() {
            return "Filling{" + "VALUE=" + VALUE + '}';
        }
    }
}
