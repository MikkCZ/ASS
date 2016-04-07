package ass.stankmic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class MergeSorter implements Sorter {
    
    private final int splitLength;
    private final int threads;
    
    public MergeSorter() {
        splitLength = 1000;
        threads = Runtime.getRuntime().availableProcessors();
    }
    
    public MergeSorter(int splitLength, int threads) {
        this.splitLength = splitLength;
        this.threads = threads;
    }

    public void sort(double[] data) {
        int dataSize = data.length;
        if (dataSize <= splitLength) {
            Arrays.sort(data);
            return;
        }
        // if data arrays is too long, split it and sort in paralell
        List<double[]> smallerArrays = new ArrayList((dataSize / 100) + 1);
        List<Callable<MergeSorterCallable>> sortTasks = new ArrayList((dataSize / 100) + 1);

        for (int i = 0; i < dataSize; i = i + splitLength) {
            int maxIndex = i + splitLength;
            if (maxIndex > dataSize) {
                maxIndex = dataSize;
            }
            double[] tmp = Arrays.copyOfRange(data, i, maxIndex);
            smallerArrays.add(tmp);
            sortTasks.add(new MergeSorterCallable(tmp));
        }

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        try {
            executor.invokeAll(sortTasks);
        } catch (InterruptedException ex) {
            // something wrong happend - sort data using inbuild sort
            Arrays.sort(data);
            return;
        }
        mergeSortedArrays(smallerArrays, data);
    }

    private void mergeSortedArrays(List<double[]> arrays, double[] target) {
        // merged = final sorted array
        int mergedSize = target.length;

        // index = "pointers" to the first unmerged values in arrays
        int[] indexes = new int[arrays.size()];
        double[] firstUnmergedVals = new double[arrays.size()];
        for(int i=0;i<arrays.size();i++) {
            firstUnmergedVals[i] = arrays.get(i)[0];
        }

        // merge all values
        for (int totalMerged = 0; totalMerged < mergedSize; totalMerged++) {
            double smallest = Double.MAX_VALUE;
            int smallestArrayIndex = 0;
            // iterate all arrays      
            for (int i = 0; i < firstUnmergedVals.length; i++) {
                double firstUnmergedVal = firstUnmergedVals[i];
                if(firstUnmergedVal == Double.NaN) {
                    continue;
                }
                // if first unmerged value is the smallest now
                if (firstUnmergedVal < smallest) {
                    smallest = firstUnmergedVal;
                    smallestArrayIndex = i;
                }
            }
            // marge the smallest value
            target[totalMerged] = smallest;
            // move the appropriate array "pointer"
            indexes[smallestArrayIndex]++;
            try {
                firstUnmergedVals[smallestArrayIndex] = arrays.get(smallestArrayIndex)[indexes[smallestArrayIndex]];
            } catch (IndexOutOfBoundsException e) {
                firstUnmergedVals[smallestArrayIndex] = Double.NaN;
            }
        }
    }

    private class MergeSorterCallable implements Callable {

        private final double[] data;

        private MergeSorterCallable(double[] data) {
            this.data = data;
        }

        public Void call() throws Exception {
            Arrays.sort(data);
            return null;
        }

    }

}
