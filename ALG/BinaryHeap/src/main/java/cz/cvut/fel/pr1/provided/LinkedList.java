package cz.cvut.fel.pr1.provided;

/**
 *
 * @author ms
 */
public class LinkedList implements Queue {

    protected Node head;
    protected Node tail;

    @Override
    public void offer(Task task) {
        Node newNode = new Node(task);
        if (tail == null) {
            tail = newNode;
            head = tail;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    @Override
    public Task peek() {
        if (head != null) {
            return head.task;
        } else {
            return null;
        }
    }

    @Override
    public Task poll() {
        Task task = null;
        if (head != null) {
            task = head.task;
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
        }
        return task;
    }

    protected class Node {

        private final Task task;
        private Node next;
        private Node prev;

        public Task getTask() {
            return task;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node(Task task) {
            this.task = task;
        }
    }
}
