package cz.cvut.fel.pr1;

import cz.cvut.fel.pr1.provided.Comparator;
import cz.cvut.fel.pr1.provided.LinkedList;
import cz.cvut.fel.pr1.provided.PriorityQueue;
import cz.cvut.fel.pr1.provided.Task;

/**
 *
 * @author ms
 */
public class LListPriorityQueue extends LinkedList implements PriorityQueue {

    Comparator comparator;

    @Override
    public Comparator getComparator() {
        return comparator;
    }

    @Override
    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public Task peek() {
        Node maxNode = getMax();
        if (maxNode == null) {
            return null;
        } else {
            return maxNode.getTask();
        }
    }

    @Override
    public Task poll() {
        Node max = getMax();
        remove(max);
        return max.getTask();
    }

    private Node getMax() {
        Node max = head;
        Node node = head;
        while (node != null && node.getNext() != null) {
            if (comparator.compare(max.getTask(), node.getNext().getTask()) < 0) {
                max = node.getNext();
            }
            node = node.getNext();
        }
        return max;
    }

    private void remove(Node nodeToRemove) {
        if (head.equals(nodeToRemove)) { // if removing head
            head = head.getNext();
        }

        if (tail.equals(nodeToRemove)) { //if removing tail
            tail = tail.getPrev();
        }

        Node prev = nodeToRemove.getPrev();
        Node next = nodeToRemove.getNext();

        if (prev != null) { //connect previous to next
            prev.setNext(next);
        }
        if (next != null) { //connect next to previous
            next.setPrev(prev);
        }
    }
}
