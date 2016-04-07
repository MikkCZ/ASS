/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.pr1.provided;

/**
 *
 * @author ms
 */
public class ArrayQueue implements Queue {

    protected Task[] tasks;
    protected int size = 0;
    protected int head = 0;
    protected int tail = -1;

    public ArrayQueue(int capacity) {
        tasks = new Task[capacity];
    }

    @Override
    public void offer(Task task) {
        if (size == tasks.length) {
            Task[] newTasks = new Task[tasks.length * 2];
            System.arraycopy(tasks, head, newTasks, 0, tasks.length - head);
            System.arraycopy(tasks, 0, newTasks, tasks.length - head, head);
            head = 0;
            tasks = newTasks;
            tail = size - 1;
        }
        tail = (tail + 1) % tasks.length;
        tasks[tail] = task;
        size++;

    }

    @Override
    public Task peek() {
        return tasks[head];
    }

    @Override
    public Task poll() {
        if (size == 0) {
            return null;
        } else {
            Task taskToPoll = tasks[head];
            tasks[head] = null;
            head = (head + 1) % tasks.length;
            size--;
            return taskToPoll;
        }
    }

    /**
     * naive remove method, note that ArrayQueue is not suitable structure for
     * this operation
     *
     * @param indexToRemove index (in tasks array) of task to be removed
     */
    protected void remove(int indexToRemove) {

        size--;
        Task[] newTasks = new Task[size];
        int taskBefore = indexToRemove - head;
        System.arraycopy(tasks, head, newTasks, 0, taskBefore);
        System.arraycopy(tasks, indexToRemove + 1, newTasks, taskBefore, size - taskBefore);
        tasks = newTasks;
        head = 0;
        tail = size - 1;
    }
}
