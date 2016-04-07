package ass.stankmic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 * @param <T>
 */
public class WorkerPoolImpl<T> implements WorkerPool<T> {

    private final int numOfThreads;
    private final Thread[] threads;
    private final ObjectPool<Task<T>> tasksPool = new ObjectPoolImpl();

    public WorkerPoolImpl(int numOfThreads) {
        this.numOfThreads = numOfThreads;
        threads = new Thread[this.numOfThreads];
        initAndStartThreads();
    }

    public WorkerPoolImpl() {
        this(Runtime.getRuntime().availableProcessors());
    }

    private void initAndStartThreads() {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new WorkerThread());
            threads[i].start();
        }
    }

    public List<Future<T>> call(Collection<Callable<T>> callables) {
        List<Future<T>> futures = new ArrayList(callables.size());
        for (Callable<T> c : callables) {
            FutureImpl<T> f = new FutureImpl<T>();
            Task<T> t = new Task(c, f);
            futures.add(f);
            tasksPool.offer(t);
        }
        return futures;
    }

    private class WorkerThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                Task<T> t;
                try {
                    t = tasksPool.poll();
                } catch (InterruptedException ex) {
                    continue;
                }
                t.run();
            }
        }
    }

    private class Task<T> {

        private final Callable<T> callable;
        private final FutureImpl<T> future;

        protected Task(Callable<T> callable, FutureImpl<T> future) {
            this.callable = callable;
            this.future = future;
        }

        protected void run() {
            try {
                setResult(callable.call());
            } catch (Exception ex) {
                setException(ex);
            }
        }

        private void setResult(T result) {
            future.setResult(result);
        }

        private void setException(Exception ex) {
            future.setException(ex);
        }
    }
}
