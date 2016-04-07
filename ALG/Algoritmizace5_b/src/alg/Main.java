package alg;

import java.util.Scanner;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class Main {
    static int T, N, D, M;
    static long A, B;
    
    static IntBinTree tree;
    static boolean[] inTree;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        T = input.nextInt();
        N = input.nextInt();
        D = input.nextInt();
        A = input.nextLong();
        B = input.nextLong();
        M = input.nextInt();
        
        int numOfKeys = (int)Math.pow(2,D)-1;
        int maxKeyValue = numOfKeys + N;
        if(maxKeyValue>M) {
            inTree = new boolean[maxKeyValue];
        } else {
            inTree = new boolean[M];
        }
        int[] keys = new int[numOfKeys];
        for(int i=0; i<numOfKeys; i++) {
            keys[i] = i+N;
            inTree[i+N] = true;
        }
        tree = new IntBinTree(keys);
        
        for(long t=1; t<T; t++) {
            updateTree(t);
        }
        
        System.out.println("" + tree.size() + " " + tree.maxDepth() + " " + tree.valuesInMaxDepth());
    }
    
    private static void updateTree(long t) {
        if(t>8198) {
            System.out.print("");
        }
        int x = (int)( (t*t)%tree.size() );
        int oldValue = tree.getValueHigherThanXNumbers( x );
        tree.remove(oldValue);
        inTree[oldValue] = false;
        
        int newValue =(int)( (A*oldValue+B)%M );
        if(!inTree[newValue]) {
            tree.add(newValue);
            inTree[newValue] = true;
        }
    }
}
