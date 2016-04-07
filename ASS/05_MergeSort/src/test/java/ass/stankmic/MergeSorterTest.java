package ass.stankmic;

import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class MergeSorterTest {
    
    private double[] reference, test;
    private Sorter TESTED;
    private final double doubleEqualsEpsilon = 0;
    
    @Before
    public void setUp() {
        TESTED = new MergeSorter();
    }
    
    @After
    public void tearDown() {
        reference = null;
        test = null;
        TESTED = null;
    }
    
    @Test
    public void test8() {
        testOnArraySize(8);
    }
    
    @Test
    public void test80() {
        testOnArraySize(80);
    }
    
    @Test
    public void test7999() {
        testOnArraySize(7999);
    }
    
    @Test
    public void test8000() {
        testOnArraySize(8000);
    }
    
    @Test
    public void test8001() {
        testOnArraySize(8001);
    }
    
    @Test
    public void testMany() {
        testOnArraySize(800001);
    }
    
    private void testOnArraySize(int size) {
        fillArrays(size);
        Arrays.sort(reference);
        TESTED.sort(test);
        for(int i=0; i<reference.length; i++) {
            assertEquals("The array is not sorted correctly.", reference[i], test[i], doubleEqualsEpsilon);
        }
    }
    
    private void fillArrays(int size) {
        reference = new double[size];
        test = new double[size];
        for(int i=0; i<size; i++) {
            double tmp = Math.random()*100;
            reference[i] = tmp;
            test[i] = tmp;
        }
    }
    
}
