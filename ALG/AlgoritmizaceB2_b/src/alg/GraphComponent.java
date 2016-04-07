package alg;

import java.util.ArrayList;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class GraphComponent<LongT extends Long> extends ArrayList<Long> {
    boolean[][] graphMatrix;
    long D;
    int minGraphRadius;
    private int size;
    
    public GraphComponent(long node) {
        this.add(node);
    }
    
    public int getGraphRadius(long D) {
        if(this.size()==1) {return 0;}
        if(this.size()==2 || this.size()==3) {return 1;}
        this.D = D;
        prepareMatrix();
        minGraphRadius = Integer.MAX_VALUE;
        size = this.size();
        int temp;
        for(int nodeIndex=0; nodeIndex<size; nodeIndex++) {
            temp = getRadiusForNodeIndex(nodeIndex);
            if(temp<minGraphRadius) {
                minGraphRadius=temp;
            }
        }
        return minGraphRadius;
    }
    
    private void prepareMatrix() {
        graphMatrix = new boolean[this.size()][this.size()];
        for(int i=0; i<this.size(); i++) {
            for(int j=0; j<this.size(); j++) {
                if(!graphMatrix[i][j] && areConnected(this.get(i), this.get(j))) {
                    graphMatrix[i][j]=true;
                    graphMatrix[j][i]=true;
                }
            }
        }
    }
    
    private int getRadiusForNodeIndex(int nodeIndex) {
        boolean[] reached = new boolean[size];
        reached[nodeIndex] = true;
        int counter = 0;
        //Integer[] lastReachedIndexes = {nodeIndex};
        int[] lastReachedIndexes = {nodeIndex};
        int lastReachedCount = 1;
        
        //HashSet<Integer> temp = new HashSet<Integer>();
        int[] temp = new int[size];
        int tempCount = 0;
        do {
            if(counter >= minGraphRadius) {
                return Integer.MAX_VALUE;
            }
            for(int i : lastReachedIndexes) {
                for(int j=0; j<size; j++) {
                    if(reached[j]) {
                        continue;
                    }
                    if(graphMatrix[i][j]) {
                        reached[j]=true;
                        temp[tempCount++] = j;
                    }
                }
            }
            lastReachedIndexes = temp;
            lastReachedCount = tempCount;
            tempCount=0;
            counter++;
        } while(lastReachedCount!=0);
//        while(lastReachedIndexes.length!=0) {
//            if(counter >= minGraphRadius) {
//                return Integer.MAX_VALUE;
//            }
//            for(int i : lastReachedIndexes) {
//                for(int j=0; j<size; j++) {
//                    if(graphMatrix[i][j] && !reached[j]) {
//                        reached[j]=true;
//                        temp.add(j);
//                    }
//                }
//            }
//            lastReachedIndexes = temp.toArray(new Integer[0]);
//            temp.removeAll(temp);
//            if(lastReachedIndexes.length!=0) {
//                counter++;
//            }
//        }
        return counter;
        
        //do pole A dat sebe
        //do pole B dat propojeni primo s timhle
        //do pole A dej vse z B
        
        //while v poli A nejsou vsechny
        //do pole B dej vsechny propojene primo s vsemi z A
        //do pole A pridej z B, co tam jeste neni
        //return 0;
    }
    
    private boolean areConnected(long node, long otherNode) {
        long x = node+otherNode + Math.abs(node-otherNode) + 3;
        long y = node*otherNode + Math.abs(node-otherNode) + 2;
        if(findGCD(x,y)>=D) {
            return true;
        }
        return false;
    }
    
    private static long findGCD(long a, long b) {
        long temp;
        while (b!=0) {
            temp = b;
            b = a%b;
            a = temp;
        }
        return a;
    }
    
}
