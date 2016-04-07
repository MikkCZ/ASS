package cz.cvut.fel.pr1.provided;

/**
 *
 * @author ms
 */
public class Task {

    private final String name;
    private final int priority;

    //constructor
    public Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "\tpriority = " + priority;
    }

}
