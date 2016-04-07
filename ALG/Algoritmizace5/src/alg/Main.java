package alg;

import java.util.Scanner;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class Main {
    static int T, N, D, B, M;
    static long A;
    
    static IntegerBinaryTree tree;
    static boolean[] inTree;
    static int treeSize;
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        T = input.nextInt();
        N = input.nextInt();
        D = input.nextInt();
        A = input.nextLong();
        B = input.nextInt();
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
        tree = new IntegerBinaryTree(keys);
        treeSize = numOfKeys;
        
        //double updateStart = System.currentTimeMillis();
        for(int t=1; t<T; t++) {
            updateTree(t);
        }
        //System.out.println(System.currentTimeMillis()-updateStart);
        
        int maxDepth = tree.maxDepth();
        int keysInMaxDepth = tree.keysInDepth(maxDepth);
        
        System.out.println("" + treeSize+ " " + maxDepth + " " + keysInMaxDepth);
    }
    
    private static void updateTree(int t) {
        int x = (int)( ((long)t*t)%tree.size() );
        
        int oldValue = tree.getValueLargerThanXNumbers(x);
        tree.remove(oldValue);
        inTree[oldValue] = false;
        treeSize--;
        
        int newValue = (int) ( (A*oldValue+B)%M );
        if(!inTree[newValue]) {
            tree.add(newValue);
            inTree[newValue] = true;
            treeSize++;
        }
    }

}
