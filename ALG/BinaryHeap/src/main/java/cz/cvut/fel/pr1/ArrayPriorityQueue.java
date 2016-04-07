package cz.cvut.fel.pr1;

import cz.cvut.fel.pr1.provided.ArrayQueue;
import cz.cvut.fel.pr1.provided.Comparator;
import cz.cvut.fel.pr1.provided.PriorityQueue;

/**
 * Trida ArrayPriorityQueue je rozsireni ArrayQueue a implementuje
 * PriorityQueue.
 *
 * Podivejme se na interface PriorityQueue, kde uvidime jake metody musime
 * implementovat. Vsimneme si, ze PriorityQueue rozsiruje Queue! Tedy tim, ze
 * deklarujeme, ze implementujeme PriorityQueue, implementujeme i Queue. Proč
 * nam IDE Netbeans nehlasi chybu, ze zde neimplementujeme napr. metodu offer z
 * Queue interface?
 *
 * @author ms
 */
public class ArrayPriorityQueue extends ArrayQueue implements PriorityQueue {

    private Comparator comparator;

    public ArrayPriorityQueue(int capacity) {
        super(capacity);
    }

    @Override
    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public Comparator getComparator() {
        return comparator;
    }

    /* Jake dalsi metody musime prepsat (Override),
     * aby se ArrayPriorityQueue chovala jako prioritni fronta?
     * Podivejte se do PriorityQueue.java, jake jsou pozadavky na prioritni frontu!
     * Jaky je vlastne rozdil mezi obecnou frontou a tou prioritni.
     */
}
