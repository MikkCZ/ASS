package cz.cvut.fel.stankmic;

import cz.cvut.fel.pr1.provided.BinaryHeap;
import cz.cvut.fel.pr1.provided.Comparator;
import cz.cvut.fel.pr1.provided.PriorityQueue;
import cz.cvut.fel.pr1.provided.Task;
import java.util.ArrayList;
import java.util.List;

public class HeapQueue implements PriorityQueue, BinaryHeap {

    private final int INIT_SIZE = 1000, ROOT = 1, LEFT_CHILD = 0, RIGHT_CHILD = 1;
    private final ArrayList<Task> tasks;
    private Comparator c;

    public HeapQueue() {
        tasks = new ArrayList<>(this.INIT_SIZE);
        initTasksArray();
        c = null;
    }

    private List<Task> initTasksArray() {
        List<Task> tmp = new ArrayList(tasks.size());
        for (Task t : tasks) {
            if (t instanceof Task) {
                tmp.add(t);
            }
        }
        tasks.clear();
        for (int i = 0; i < ROOT; i++) {
            tasks.add(null);
        }
        return tmp;
    }

    @Override
    public void offer(final Task task) {
        tasks.add(task);
        int offeredIndex = tasks.size() - 1;
        int parentIndex = getParentIndex(offeredIndex);
        final Task offered = task;
        Task parent = tasks.get(parentIndex);
        while (parent != null && c.compare(offered, parent) < 0) {
            swap(parentIndex, offeredIndex);
            offeredIndex = parentIndex;
            parentIndex = getParentIndex(offeredIndex);
            parent = tasks.get(parentIndex);
        }
    }

    private int getParentIndex(final int childIndex) {
        if (childIndex % 2 == 0) {
            return childIndex / 2;
        } else {
            return (childIndex - 1) / 2;
        }
    }

    private void swap(int index1, int index2) {
        Task t1 = tasks.get(index1);
        Task t2 = tasks.get(index2);
        tasks.set(index1, t2);
        tasks.set(index2, t1);
    }

    @Override
    public Task peek() {
        try {
            return tasks.get(ROOT);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public Task poll() {
        final Task root = peek();
        fixPolledRoot();
        return root;
    }

    private void fixPolledRoot() {
        if (tasks.size() - 1 == ROOT - 1) {
            return;
        } else if (tasks.size() - 1 == ROOT) {
            tasks.remove(ROOT);
            return;
        }
        Task lastTask = tasks.remove(tasks.size() - 1);
        tasks.set(ROOT, lastTask);

        int n = ROOT;
        Task left = getChildByIndex(n, LEFT_CHILD);
        Task right = getChildByIndex(n, RIGHT_CHILD);
        while ((left != null && c.compare(lastTask, left) > 0)
                || (right != null && c.compare(lastTask, right) > 0)) {
            int smallerChildIndex = 2 * n + LEFT_CHILD;
            if (right != null && c.compare(left, right) > 0) {
                smallerChildIndex = 2 * n + RIGHT_CHILD;
            }
            swap(n, smallerChildIndex);
            n = smallerChildIndex;
            left = getChildByIndex(smallerChildIndex, LEFT_CHILD);
            right = getChildByIndex(smallerChildIndex, RIGHT_CHILD);
        }
    }

    @Override
    public Comparator getComparator() {
        return this.c;
    }

    @Override
    public void setComparator(final Comparator comparator) {
        this.c = comparator;
        List<Task> tmp = initTasksArray();
        for (Task t : tmp) {
            this.offer(t);
        }
    }

    @Override
    public Task getLeftChild(final Task task) {
        return getChild(task, LEFT_CHILD);
    }

    @Override
    public Task getRightChild(final Task task) {
        return getChild(task, RIGHT_CHILD);
    }

    private Task getChild(final Task task, final int hand) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i) == task) {
                return getChildByIndex(i, hand);
            }
        }
        return null;
    }

    private Task getChildByIndex(final int index, final int hand) {
        try {
            return tasks.get(2 * index + hand);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

}
