package alg;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author stankmic
 */
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        // load first array
        String lineA = scan.nextLine();
        double s = System.currentTimeMillis();
        int[] arrayA = stringToIntArrayWithoutFirstValue(lineA);
        // load second array
        String lineB = scan.nextLine();
        int[] arrayB = stringToIntArrayWithoutFirstValue(lineB);
        // count TROJ
        int counter = 0;
        Arrays.sort(arrayA);
        int valueCounter = Integer.MIN_VALUE;
        int prevValue = Integer.MIN_VALUE;
        int sizeA = arrayA.length;
        for (int i = 0; i < sizeA; i++) {
            int value = arrayA[i];
            if (value==prevValue) {
                counter+=valueCounter;
                continue;
            }
            valueCounter = countTROJ(value, arrayB);
            counter += valueCounter;
            prevValue = value;
        }
        System.out.println(counter);
        System.out.println(System.currentTimeMillis()-s);
    }
    
    private static int[] stringToIntArrayWithoutFirstValue (String s) {
        String[] temp = s.split("\\s+");
        int arraySize = temp.length-1;
        int[] array = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = Integer.parseInt(temp[i+1]);
        }
        return array;
    }
    
    private static int countTROJ(int target, int[] array) {
        int arrayLength = array.length;
        int counter = 0;
        
        int left = 0;
        int right = 0;
        int sum = array[0];
        while (true) {
            if (sum==target) {
                counter++;
                if (++right >= arrayLength) {break;}
                sum += array[right];
                sum -= array[left++];
            } else if (sum<target) {
                if (++right >= arrayLength) {break;}
                sum += array[right];
            } else {
                sum -= array[left++];
            }
        }
        return counter;
    }
}
