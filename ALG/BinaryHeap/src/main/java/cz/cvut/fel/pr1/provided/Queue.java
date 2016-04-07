package cz.cvut.fel.pr1.provided;

/**
 * Simplified Queue interface for queue of tasks (Task). The collection is FIFO
 * (First In, First Out), it means that tasks added first into the queue by
 * method offer are first on the output (peek or poll methods)
 *
 * @author ms
 */
public interface Queue {

    /**
     * Offer method serves to add task into the queue. Task object is added at
     * the end
     *
     * @param task
     */
    void offer(Task task);

    /**
     * Returns the first task in the queue. The task remains in the queue.
     *
     * @return task that is the first in the queue
     */
    Task peek();

    /**
     * Returns and removes the first task in the queue.
     *
     * @return task that is the first in the queue
     */
    Task poll();
}
