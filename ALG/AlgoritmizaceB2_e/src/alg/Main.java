package alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    static long D;
    static LinkedList<ArrayList<Long>> graph = new LinkedList<ArrayList<Long>>();
    static ArrayList<LinkedList<Integer>> COMPONENT_MATRIX_2;
    static int COMPONENT_SIZE;
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        long N1 = input.nextLong();
        long N2 = input.nextLong();
        D = input.nextLong();
        int totalNodes = (int)(N2-N1+1);
        
        graph.add(new ArrayList<Long>(totalNodes));
        graph.get(0).add(N1);
        
        long newNode = N1;
        ArrayList<Long> newComponent;
        ArrayList<ArrayList<Long>> toRemove = new ArrayList<ArrayList<Long>>(totalNodes);
        
        do {
            newNode++;
            newComponent = new ArrayList<Long>(totalNodes);
            newComponent.add(newNode);
            toRemove.clear();
            for(ArrayList<Long> component : graph) {
                if(nodeConnectedWithComponent(newNode, component)) {
                    newComponent.addAll(component);
                    toRemove.add(component);
                }
            }
            graph.add(newComponent);
            graph.removeAll(toRemove);
        } while(newNode != N2);
        
        int maxRadius = countMaxGraphRadius();
        
        System.out.println(graph.size()+" "+maxRadius);
    }
    
    static boolean nodeConnectedWithComponent(long node, ArrayList<Long> component) {
        for(long internalNode : component) {
            if(areConnected(node, internalNode)) {
                return true;
            }
        }
        return false;
    }
    
    static boolean areConnected(long node, long otherNode) {
        long x, y;
        x = otherNode+node + Math.abs(otherNode-node) + 3;
        y = otherNode*node + Math.abs(otherNode-node) + 2;
        if(findGCD(x,y)>=D) {
            return true;
        }
        return false;
    }
    
    static long findGCD(long a, long b) {
        long temp;
        while (b!=0) {
            temp = b;
            b = a%b;
            a = temp;
        }
        return a;
    }
    
    static int countMaxGraphRadius() {
        sortGraph();
        int maxRadius = 0;
        int componentSize, componentRadius;
        for(ArrayList<Long> c : graph) {
            componentSize = c.size();
            if(componentSize/2<maxRadius || componentSize==1) {
                return maxRadius;
            }
            if(componentSize == 2 || componentSize==3) {
                componentRadius = 1;
            } else {
                componentRadius = countComponentRadius(c);
            }
            if(componentRadius>maxRadius) {
                maxRadius=componentRadius;
            }
        }
        return maxRadius;
    }
    
    static void sortGraph() {
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
    
    static int countComponentRadius(ArrayList<Long> c) {
        COMPONENT_SIZE = c.size();
        COMPONENT_MATRIX_2 = new ArrayList<LinkedList<Integer>>(COMPONENT_SIZE);
        for(int i=0; i<COMPONENT_SIZE; i++) {
            try{COMPONENT_MATRIX_2.get(i);} catch(Exception e) {
                COMPONENT_MATRIX_2.add(i, new LinkedList<Integer>());
            }
            for(int j=i+1; j<COMPONENT_SIZE; j++) {
                try{COMPONENT_MATRIX_2.get(j);} catch(Exception e) {
                    COMPONENT_MATRIX_2.add(j, new LinkedList<Integer>());
                }
                    if(areConnected(c.get(i), c.get(j))) {
                        try {
                            COMPONENT_MATRIX_2.get(i).add(j);
                        } catch (Exception e) {
                            LinkedList<Integer> temp = new LinkedList<Integer>();
                            temp.add(j);
                            COMPONENT_MATRIX_2.add(i, temp);
                        }
                        try {
                            COMPONENT_MATRIX_2.get(j).add(i);
                        } catch (Exception e) {
                            LinkedList<Integer> temp = new LinkedList<Integer>();
                            temp.add(i);
                            COMPONENT_MATRIX_2.add(j, temp);
                        }
                    }
            }
        }
        
        int radius, minRadius = Integer.MAX_VALUE;
        for(int i=0; i<COMPONENT_SIZE; i++) {
            radius = countComponentRadiusForIndex(i);
            if(radius<minRadius) {
                minRadius = radius;
            }
        }
        return minRadius;
    }
    
    static int countComponentRadiusForIndex(int index) {
        boolean[] reached = new boolean[COMPONENT_SIZE];
        reached[index] = true;
        int radius = 0;
        int[] lastReached = new int[COMPONENT_SIZE];
        lastReached[0] = index;
        int lIndex = 0;
        int rIndex = 1;
        int added = 0, nIndex;
        while (true) {
            for (;lIndex < rIndex; lIndex++) {
                nIndex = lastReached[lIndex];
                LinkedList<Integer> neighbours = COMPONENT_MATRIX_2.get(nIndex);
                for (int j : neighbours) {
                    if (reached[j]) {
                        continue;
                    }
                        reached[j] = true;
                        lastReached[rIndex + added] = j;
                        added++;
                }
            }
            if (added == 0) {
                return radius;
            }
            lIndex = rIndex;
            rIndex += added;
            added = 0;
            radius++;
        }
    }

}
