package cz.cvut.fel.pr1;

import cz.cvut.fel.pr1.provided.Task;
import cz.cvut.fel.pr1.provided.ArrayQueue;
import cz.cvut.fel.pr1.provided.Comparator;
import cz.cvut.fel.pr1.provided.Simulator;
import cz.cvut.fel.pr1.provided.LinkedList;
import cz.cvut.fel.pr1.provided.PriorityQueue;

/**
 * Lab12 class
 *
 * Lab 12 of A0B36PR1 course, FEE CTU in Prague
 *
 */
public class Lab12 {

    /**
     * @param args the command line arguments <br/>
     * Expected optional arguments are <code>initTaskCount</code> (int) and
     * <code>verbose</code> (boolean) <br/><br/>
     * Example: cz.cvut.fel.pr1.Lab12 5 true
     *
     * @author ms
     */
    public static void main(String[] args) {

        int initTaskCount = 3;
        boolean verbose = false;

        //read program arguments if available
        try {
            if (args.length > 0) {
                initTaskCount = Integer.parseInt(args[0]);
                if (args.length > 1) {
                    verbose = Boolean.parseBoolean(args[1]);
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid program arguments."
                    + " Expected optional arguments are initTaskCount (int) and verbose (boolean).\n"
                    + "Running program with defaults.");
        }

        System.out.println("Running Lab12 with arguments: "
                + "initTaskCount = " + initTaskCount + ", verbose = " + verbose + "\n");

        Simulator sim = new Simulator();

        ArrayQueue tasks = new ArrayQueue(3);
        System.out.println("Array Queue processing: ...");
        sim.run(tasks, initTaskCount, verbose);

        // vytvoreni fronty a pridani ukolu
        LinkedList linkedTasks = new LinkedList();
        System.out.println("Linkedlist Queue processing: ...");
        sim.run(linkedTasks, initTaskCount, verbose);

        /*
         * comparator nam umozni porovnat 2 ukoly, podivejte se, jak se da
         * rozhrani Comparator implementovat aniz bychom jej definovali jako nove
         * pojmenovany typ. Vytvarime tzv. anonymni tridu, kterou rovnou uvnitr
         * slozenych zavorek implementujeme.*
         */
        Comparator comparator = new Comparator() {

            @Override
            public int compare(Task t1, Task t2) {
                // zde implementujte 
                return 0;
            }
        };

        // vytvoreni prioritni fronty implementovanou polem
        PriorityQueue prioritizedTasks = new ArrayPriorityQueue(3);
        prioritizedTasks.setComparator(comparator);
        System.out.println("PriorityQueue processing: ...");
        sim.run(prioritizedTasks, initTaskCount, verbose);

        // vytvoreni prioritni fronty implementovanou spojovym seznamem
        PriorityQueue prioritizedLinkedTasks = new LListPriorityQueue();
        prioritizedLinkedTasks.setComparator(comparator);
        System.out.println("LListPriorityQueue processing: ...");
        sim.run(prioritizedLinkedTasks, initTaskCount, verbose);
    }
}
