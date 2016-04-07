package ass.stankmic;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ReversibleLinkedList<E extends Comparable<E>> implements ReversibleList<E> {

    private Node<E> first, last;
    private int size = 0, modifications = 0;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public boolean contains(E e) {
        return (!findNodes(e).isEmpty());
    }

    /**
     * Finds all occurances if the given Comparable instance and returns them as
     * a list of Nodes.
     *
     * @param e Comparable instance to find
     * @return list of Nodes containing given Comparable instance
     */
    private List<Node<E>> findNodes(E e) {
        List<Node<E>> nodes = new LinkedList<Node<E>>();
        // if e==null, we cannot reference any equals()
        if (e == null) {
            for (Node<E> node = first; node != null; node = node.next) {
                if (node.item == null) {
                    nodes.add(node);
                }
            }
        } else {
            for (Node<E> node = first; node != null; node = node.next) {
                if (e.equals(node.item)) {
                    nodes.add(node);
                }
            }
        }
        return nodes;
    }

    public Iterator<E> iterator() {
        return new AscIterator();
    }

    public void add(E e) {
        Node tmp = new Node(e, last, null);
        if (isEmpty()) {
            first = tmp;
        } else {
            last.next = tmp;
        }
        last = tmp;
        size++;
        modifications++;
    }

    public boolean remove(E e) {
        List<Node<E>> nodes = findNodes(e);
        if (nodes.isEmpty()) {
            return false;
        } else {
            for (Node<E> node : nodes) {
                removeNode(node);
            }
            return true;
        }
    }

    /**
     * Removes the given Node from the list - connects its previous and next
     * Nodes.
     *
     * @param node Node to remove from the list
     */
    private void removeNode(Node<E> node) {
        if (node == null) {
            return;
        }
        Node<E> prev = node.prev, next = node.next;
        if (prev != null) {
            prev.next = next;
        }
        if (next != null) {
            next.prev = prev;
        }
        if (node == first) {
            first = next;
        }
        if (node == last) {
            last = prev;
        }
        size--;
        modifications++;
    }

    public void addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
    }

    public void clear() {
        first = null;
        last = null;
        size = 0;
        modifications++;
    }

    public void sort() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (itemAt(j).compareTo(itemAt(j + 1)) > 0) {
                    swap(j, j + 1);
                }
            }
        }
    }

    /**
     * Returns Comparable instance on the required position.
     *
     * @param index position of the item
     * @return Comparable instance on the required position
     */
    private E itemAt(int index) {
        return nodeAt(index).item;
    }

    /**
     * Returns the list Node on the required position.
     *
     * @param index position of the Node
     * @return Node on the required position
     */
    private Node<E> nodeAt(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    /**
     * Swap items on the given indexes.
     *
     * @param indexA index of the first item
     * @param indexB index of the second item
     */
    private void swap(int indexA, int indexB) {
        if (indexA >= size || indexB >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> a = nodeAt(indexA), b = nodeAt(indexB);
        E aItem = a.item, bItem = b.item;
        a.item = bItem;
        b.item = aItem;
        modifications++;
    }

    public Iterator<E> descendingIterator() {
        return new DescIterator();
    }

    private abstract class Iter implements Iterator<E> {

        protected Node<E> lastListed, next;
        protected int listModifications;

        /**
         * Prepare next Node from the list.
         */
        protected abstract void updateNext();

        public boolean hasNext() {
            updateNext();
            return (next != null);
        }

        public E next() {
            lastListed = next;
            updateNext();
            if (lastListed == null) {
                return null;
            }
            return lastListed.item;
        }

        public abstract void remove();
    }

    private final class AscIterator extends Iter {

        public AscIterator() {
            lastListed = new Node(null, null, first);
            this.updateNext();
            listModifications = modifications;
        }

        @Override
        protected void updateNext() {
            if (lastListed == null) {
                next = null;
            } else {
                next = lastListed.next;
            }
        }

        @Override
        public void remove() {
            if (listModifications != modifications) {
                throw new ConcurrentModificationException();
            }
            removeNode(lastListed);
            if (this.next == null) {
                lastListed = null;
            } else {
                lastListed = lastListed.prev;
            }
            updateNext();
            listModifications++;
        }

    }

    private final class DescIterator extends Iter {

        public DescIterator() {
            lastListed = new Node(null, last, null);
            updateNext();
            listModifications = modifications;
        }

        protected void updateNext() {
            if (lastListed == null) {
                next = null;
            } else {
                next = lastListed.prev;
            }
        }

        public void remove() {
            if (listModifications != modifications) {
                throw new ConcurrentModificationException();
            }
            removeNode(lastListed);
            if (this.next == null) {
                lastListed = null;
            } else {
                lastListed = lastListed.next;
            }
            updateNext();
            listModifications++;
        }

    }

    private class Node<E> {

        E item;
        Node<E> prev, next;

        public Node(E item, Node<E> prev, Node<E> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
