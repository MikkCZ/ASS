package alg;

import java.util.Scanner;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class Main_old {
    private static int x1;
    private static int A1, B1, A2, B2, A3, B3, M;
    private static short[] H_values;
    private static int num_of_H_values = 0;
    
    public static void main(String[] args) {
      /*parse input*/
        Scanner input = new Scanner(System.in);
        x1 = input.nextInt();
        A1 = input.nextInt();
        B1 = input.nextInt();
        A2 = input.nextInt();
        B2 = input.nextInt();
        A3 = input.nextInt();
        B3 = input.nextInt();
        M = input.nextInt();
      /*prepare to store gained values*/
        H_values = new short[M];
      /*save input value*/
        H_values[x1] = 1;
        num_of_H_values++;
        
      /*prepare input data*/
        int[] new_values = {x1};
      /*run*/
        for(int i=0; i<M; i++) {
            if(new_values.length == 0) {
                break;
            }
            new_values = updateArray(new_values);
        }
      /*output*/
        System.out.println(num_of_H_values);
    }
    
    private static int[] updateArray(int[] values) {
        int i, num_of_values = values.length;
      /*exclude g1(new_values)*/
        int notToAdd;
        for(i = 0; i<num_of_values; i++) {
            notToAdd = g1(values[i]);
            if(H_values[notToAdd] == 0) {
                H_values[notToAdd] = -1;
            }
        }
      /*save and add gained values*/
        int[] temp_added = new int[num_of_values*2];
        int temp, num_of_added = 0;
        for(i = 0; i<num_of_values; i++) {
            temp = f1(values[i]);
            if(H_values[temp]==0) {
                H_values[temp]=1;
                temp_added[num_of_added] = temp;
                num_of_added++;
            }
            temp = f2(values[i]);
            if(H_values[temp]==0) {
                H_values[temp]=1;
                temp_added[num_of_added] = temp;
                num_of_added++;
            }
        }
      /*count new values and save them to a new array*/
        num_of_H_values+=num_of_added;
        int[] added = new int[num_of_added];
        System.arraycopy(temp_added, 0, added, 0, added.length);
      /*return new values*/
        return added;
    }
    
    private static int f1(int x) {
        long f1 = ((long)A1*x + B1)%M;
        return (int)f1;
    }
    
    private static int f2(int x) {
        long f2 = ((long)A2*x + B2)%M;
        return (int)f2;
    }
    
    private static int g1(int x) {
        long g1 = ((long)A3*x + B3)%M;
        return (int)g1;
    }
}
