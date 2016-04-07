package cz.cvut.fel.pr1.provided;

import java.util.Random;

/**
 * Simulator models operations on a task queue. Randomly adds and removes tasks.
 *
 * @author ms
 *
 */
public class Simulator {

    private final long SEED = 0;
    private boolean verbose = false;
    private final Random rand = new Random();

    /**
     *
     * @param tasks
     */
    public void run(Queue tasks) {
        run(tasks, 3, false);
    }

    /**
     *
     * @param tasks
     * @param initialNumOfTasks
     */
    public void run(Queue tasks, int initialNumOfTasks) {
        run(tasks, initialNumOfTasks, false);
    }

    /**
     *
     * @param tasks
     * @param initialNumOfTasks
     * @param verbose
     */
    public void run(Queue tasks, int initialNumOfTasks, boolean verbose) {
        this.verbose = verbose;
        long startTime = System.currentTimeMillis();
        rand.setSeed(SEED);

        // modelove ukoly
        Task task1 = new Task("Dojit s kosem", 4);
        Task task2 = new Task("Umyt nadobi", 3);
        Task task3 = new Task("Ucit se PR1", 10);

        tasks.offer(task1);
        tasks.offer(task2);
        tasks.offer(task3);

        //vygeruje dalsich n-3 tasku
        generateTasks(tasks, initialNumOfTasks - 3);

        while (tasks.peek() != null) {
            if (rand.nextDouble() < 0.5) {
                generateTasks(tasks, rand.nextInt(3));
            }
            Task task = tasks.poll();
            if (verbose) {
                System.out.println("- Odebran: \t" + task);
            }
        }
        System.out.println("Duration of processing: \t" + (System.currentTimeMillis() - startTime) + " ms\n");
    }

    /**
     *
     * @param tasks
     * @param n
     */
    public void generateTasks(Queue tasks, int n) {

        for (int i = 0; i < n; i++) {
            Task task = new Task("Ukol c. " + rand.nextInt(1000), (int) (rand.nextInt(10)));
            tasks.offer(task);
            if (verbose) {
                System.out.println("+ Pridan: \t" + task);
            }
        }
    }

    /**
     *
     * @param seed
     */
    public void setSeed(long seed) {
        rand.setSeed(seed);
    }
}
