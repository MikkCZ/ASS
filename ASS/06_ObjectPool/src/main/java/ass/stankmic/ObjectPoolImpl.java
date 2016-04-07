package ass.stankmic;

import java.util.LinkedList;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 * @param <T>
 */
public class ObjectPoolImpl<T> implements ObjectPool<T> {

    private final LinkedList<T> objectQueue = new LinkedList();

    public synchronized T poll() throws InterruptedException {
        while (objectQueue.isEmpty()) {
            wait();
        }
        return objectQueue.pop();
    }

    public synchronized void offer(T object) {
        if (objectQueue.contains(object)) {
            return;
        }
        objectQueue.add(object);
        notifyAll();
    }

}
