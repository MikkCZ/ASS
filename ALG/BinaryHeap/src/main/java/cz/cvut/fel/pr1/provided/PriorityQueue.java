package cz.cvut.fel.pr1.provided;

/**
 * Priority queue extends Queue, redefines order of tasks on the output and
 * prescribes methods to work with Comparator.
 *
 * The tasks on output of the priority queue are sorted according to the used
 * comparator. The comparator can be set by the method setComparator.
 *
 * @author ms
 */
public interface PriorityQueue extends Queue {

    /**
     * @return comparator that is used to compare objects is the priority queue.
     */
    public Comparator getComparator();

    /**
     * Allows to set the comparator to be used to compare objects in the
     * priority queue.
     *
     * @param comparator
     */
    public void setComparator(Comparator comparator);
        
}
