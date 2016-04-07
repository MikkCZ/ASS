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
    static long D;
    static LinkedList<ArrayList<Long>> graph = new LinkedList<ArrayList<Long>>();
    private static boolean[][] COMPONENT_GRAPH_MATRIX;
    private static Queue QUEUE = new Queue();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        long N1 = input.nextLong();
        long N2 = input.nextLong();
        D = input.nextLong();
        int totalNodes = (int)(N2-N1+1);
        
        graph.add(new ArrayList<Long>(totalNodes));
        graph.get(0).add(N1);
        
        long newNode = N1;
        ArrayList<Long> temp;
        ArrayList<ArrayList<Long>> toRemove = new ArrayList<ArrayList<Long>>(totalNodes);
        
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
        
        int maxRadius = countMaxGraphRadius();
        
        System.out.println(graph.size()+" "+maxRadius);
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

    private static int countMaxGraphRadius() {
        sortGraphComponents();
        int maxRadius = 0;
        int componentSize, componentRadius;
        for(ArrayList<Long> component:graph) {
            componentSize = component.size();
            if(componentSize/2<maxRadius || componentSize==1) {
                break;
            }
            if(componentSize==2 || componentSize==3) {
                componentRadius=1;
                if(componentRadius>maxRadius) {
                    maxRadius = componentRadius;
                }
                continue;
            }
            componentRadius = countComponentRadius(component);
            if(componentRadius>maxRadius) {
                maxRadius=componentRadius;
            }
        }
        return maxRadius;
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
        int componentSize = component.size();
      //prepareMatrix
        COMPONENT_GRAPH_MATRIX = new boolean[componentSize][componentSize];
        for(int i=0; i<componentSize; i++) {
            for(int j=i+1; j<componentSize; j++) {
                if(!COMPONENT_GRAPH_MATRIX[i][j] && areConnected(component.get(i), component.get(j))) {
                    COMPONENT_GRAPH_MATRIX[i][j] = true;
                    COMPONENT_GRAPH_MATRIX[j][i] = true;
                }
            }
        }
      //count radius
        int radius, minRadius = Integer.MAX_VALUE;
        for(int i=0; i<componentSize; i++) {
            radius = countComponentRadiusForNodeIndex(componentSize, i); 
            if(radius<minRadius) {
                minRadius = radius;
            }
        }
        return minRadius;
    }

    private static int countComponentRadiusForNodeIndex(int componentSize, int nIndex) {
        boolean[] reachedIndexes = new boolean[componentSize];
        reachedIndexes[nIndex] = true;
        int radius = 0;
        QUEUE.clear();
        QUEUE.add(nIndex);
        QUEUE.add(-1);
        int next;
        while(true) {
            while(true) {
                next = QUEUE.popNext();
                if(next<0) {
                    break;
                }
                for(int i=0; i<componentSize; i++) {
                    if(reachedIndexes[i]) {
                        continue;
                    }
                    if(COMPONENT_GRAPH_MATRIX[next][i]) {
                        reachedIndexes[i] = true;
                        QUEUE.add(i);
                    }
                }
            }
            if(QUEUE.isEmpty()) {
                return radius;
            }
            QUEUE.add(-1);
            radius++;
        }
    }
    
    public static class Queue {
        Element first = null;
        Element last = null;
        int size = 0;
        public void add(int value) {
            if(first==null) {
                first = new Element(value);
                last = first;
            } else {
                last.setNext(value);
                last = last.getNext();
            }
            size++;
        }
        public int popNext() {
            int value = first.getValue();
            first = first.getNext();
            return value;
        }
        public void clear() {
            first = null;
            last = null;
        }
        public boolean isEmpty() {
            return (first==null);
        }
    }
    
    public static class Element {
        int value;
        Element next = null;
        public Element(int value) {
            this.value = value;
        }
        public void setNext(int value) {
            next = new Element(value);
        }
        public Element getNext() {
            return next;
        }
        public int getValue() {
            return value;
        }
    }

}
