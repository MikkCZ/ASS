package alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class Main {
    static long N1, N2, D;
    static LinkedList<ArrayList<Long>> graph = new LinkedList<ArrayList<Long>>();
    static int totalNodes;
    
    private static int MAX_RADIUS;
    private static boolean[][] COMPONENT_GRAPH_MATRIX;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        N1 = input.nextLong();
        N2 = input.nextLong();
        D = input.nextLong();
        totalNodes = (int)(N2-N1+1);
        
        graph.add(new ArrayList<Long>(totalNodes));
        graph.get(0).add(N1);
        
        long newNode = N1;
        ArrayList<Long> temp;
        ArrayList<ArrayList> toRemove = new ArrayList<ArrayList>(totalNodes);
        
        do {
            newNode++;
            temp = new ArrayList<Long>(totalNodes);
            temp.add(newNode);
            toRemove.clear();
            for(ArrayList<Long> component : graph) {
                if(nodeConnectedWithComponent(newNode, component)) {
                    temp.addAll(component);
                    toRemove.add(component);
                }
            }
            graph.add(temp);
            graph.removeAll(toRemove);
        } while(newNode != N2);
        
        countMaxGraphRadius();
        
        System.out.println(graph.size()+" "+MAX_RADIUS);
    }
    
    private static boolean nodeConnectedWithComponent(long node, ArrayList<Long> component) {
        for(long internalNode : component) {
            if(areConnected(node, internalNode)) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean areConnected(long node, long otherNode) {
        long x, y;
        x = otherNode+node + Math.abs(otherNode-node) + 3;
        y = otherNode*node + Math.abs(otherNode-node) + 2;
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
    
    private static void countMaxGraphRadius() {
        sortGraphComponents();
        MAX_RADIUS = 0;
        int componentRadius;
        for(ArrayList<Long> component : graph) {
            if(component.size()==1) {
                continue;
            }
            if(component.size()==2 || component.size()==3) {
                componentRadius=1;
                if(componentRadius>MAX_RADIUS) {
                    MAX_RADIUS = componentRadius;
                }
                continue;
            }
            componentRadius = countComponentRadius(component);
            if(componentRadius>MAX_RADIUS) {
                MAX_RADIUS = componentRadius;
            }
        }
    }
    
    private static void sortGraphComponents() {
        Collections.sort(graph, new Comparator<ArrayList>() {
            @Override
            public int compare(ArrayList o1, ArrayList o2) {
                int size1 = o1.size();
                int size2 = o2.size();
                if(size1>size2) {
                    return -1;
                } else if(size2>size1) {
                    return 1;
                }
                return 0;
            }
        });
    }
    
    private static int countComponentRadius(ArrayList<Long> component) {
        int size = component.size();
      //prepare matrix
        COMPONENT_GRAPH_MATRIX = new boolean[size][size];
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                if(i==j || COMPONENT_GRAPH_MATRIX[i][j]) {
                    continue;
                }
                if(areConnected(component.get(i), component.get(j))) {
                    COMPONENT_GRAPH_MATRIX[i][j] = true;
                    COMPONENT_GRAPH_MATRIX[j][i] = true;
                }
            }
        }
      //count radius
        int radius = Integer.MAX_VALUE;
        
        boolean[] reached;
        int counter;
        int[] lastReachIndexes = new int[size];
        int lastReachCount;
        int[] temp = new int[size];
        int tempCount;
        
        for(int nIndex=0; nIndex<size; nIndex++) {
            reached = new boolean[size];
            reached[nIndex] = true;
            counter = 0;
            lastReachIndexes[0] = nIndex;
            lastReachCount = 1;
            tempCount = 0;
            
            do {
                for(int i=0; i<lastReachCount; i++) {
                    for(int j=0; j<size; j++) {
                        if(reached[j]) {
                            continue;
                        }
                        if(COMPONENT_GRAPH_MATRIX[i][j]) {
                            reached[j] = true;
                            temp[tempCount++]=j;
                        }
                    }
                }
                lastReachIndexes=temp;
                lastReachCount=tempCount;
                tempCount=0;
                counter++;
            } while(counter<=radius && lastReachCount!=0);
            if(counter<=radius) {
                radius=counter;
            }
        }
        return radius;
    }

}
