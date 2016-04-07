package cz.cvut.fel.pr1.provided;

/**
 * BinaryHeap is a complete binary tree with so-called heap property. Heap property
 * says that all nodes are either greater than or equal to each of its children.
 *
 * In case the heap is not full, the elements are inserted from left to right.
 * "Heap property" is recursive - all subtrees of a heap are heaps too. That is
 * why the heap behaves like a priority queue - there is always the task with
 * the highest priority on the top of the heap / on the output (peek and poll).
 *
 *
 * Tip: Jelikoz se po odebrani nebo pridani vrcholu binarni halda muze dostat do
 * nekonzistentniho stavu, je pripadne potreba haldu opravit pri kazde takove operaci.
 * Platnost heap property bude prubezne testovana.
 *
 */
public interface BinaryHeap extends PriorityQueue {

    /**
     * @param task
     * @return left child of the task
     */
    public Task getLeftChild(Task task);

    /**
     * @param task
     * @return right child of the task
     */
    public Task getRightChild(Task task);

}
