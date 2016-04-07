package cz.cvut.fel.pr1.provided;

/**
 * Comparator is simplified java.util.Comparator, specified only for comparing tasks
 * (Task objects). This is used to avoid generics.
 * @author ms
 */
public interface Comparator {

    /**
     * Compares two tasks.
     * 
     * @param t1 task1
     * @param t2 task2
     * @return a negative integer, zero, or a positive integer as the first
     * argument is less than, equal to, or greater than the second.
     */
    public int compare(Task t1, Task t2);
}
