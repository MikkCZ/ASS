package ass.stankmic;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 * @param <T>
 */
public class ObjectPoolImpl<T extends CloneableObject<T>> implements ObjectPool<T> {

    public static final int MIN_INSTANCES = 10, MAX_INSTANCES = 100;
    private static final Map<Class, ObjectPoolImpl> pools = new HashMap<Class, ObjectPoolImpl>();

    private final List<PhantomReference<T>> phReferences;
    private final ReferenceQueue<T> refQueue;
    private final T parentObject;
    private final Queue<T> freeObjects;

    public static synchronized <S extends CloneableObject<S>> ObjectPoolImpl<S> getInstance(S object) {
        Class key = object.getClass();
        ObjectPoolImpl<S> instance = pools.get(key);
        if (instance == null) {
            try {
                instance = new ObjectPoolImpl<S>(object);
            } catch (ObjectPoolFullException ex) {
                Logger.getLogger(ObjectPoolImpl.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            pools.put(key, instance);
        }
        return instance;
    }

    /**
     * For testing purposes only.
     */
    @Deprecated
    private static void reset() {
        System.err.println("reset");
        pools.clear();
    }

    private ObjectPoolImpl(T parentObject) throws ObjectPoolFullException {
        this.parentObject = parentObject.clone();
        this.refQueue = new ReferenceQueue<T>();
        this.phReferences = new ArrayList<PhantomReference<T>>(MAX_INSTANCES);
        this.freeObjects = new LinkedList<T>();
        // test ConcurrentLinkedQueue performance (some synchronization can be removed)
        cloneNewFreeObjects(MIN_INSTANCES);
        startRecyclingThread();
    }

    private void cloneNewFreeObjects(int numOfObjects) throws ObjectPoolFullException {
        for (int i = 0; i < numOfObjects; i++) {
            cloneNewFreeObject();
        }
    }

    private synchronized void cloneNewFreeObject() throws ObjectPoolFullException {
        if (isFull()) {
            throw new ObjectPoolFullException();
        }
        T t = parentObject.clone();
        phReferences.add(new PhantomReference<T>(t, refQueue));
        addFreeObject(t);
    }

    private synchronized boolean isFull() {
        return (phReferences.size() >= MAX_INSTANCES);
    }

    private synchronized void addFreeObject(T freeObject) {
        if (freeObjects.contains(freeObject)) {
            return;
        }
        freeObjects.add(freeObject);
        notifyAll();
    }

    private void startRecyclingThread() {
        Thread recycling = new Thread(new RecyclingThread());
        recycling.start();
    }

    public synchronized T borrowObject() {
        while (freeObjects.isEmpty()) {
            try {
                cloneNewFreeObject();
            } catch (ObjectPoolFullException ex) {
                try {
                    wait();
                } catch (InterruptedException ex1) {
                    Logger.getLogger(ObjectPoolImpl.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        return freeObjects.poll();
    }

    private class RecyclingThread extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    Reference<? extends T> pRef = refQueue.remove();
                    T t = getInstanceFromReference(pRef);
                    addFreeObject(t);
                } catch (Exception ex) {
                    Logger.getLogger(ObjectPoolImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private T getInstanceFromReference(Reference ref) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
            Class rClass = ref.getClass();
            Field referentField = rClass.getSuperclass().getDeclaredField("referent");
            referentField.setAccessible(true);
            Object o = referentField.get(ref);
            return (T) o;
        }

    }

    private static class ObjectPoolFullException extends Exception {
    }

}
