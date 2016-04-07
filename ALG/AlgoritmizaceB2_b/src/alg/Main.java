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
    static LinkedList<GraphComponent<Long>> graph = new LinkedList<GraphComponent<Long>>();
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        N1 = input.nextLong();
        N2 = input.nextLong();
        D = input.nextLong();
        
        graph.add(new GraphComponent<Long>(N1));
        
        long newNode=N1;
        boolean added;
        do {
            newNode++;
            added=false;
            ArrayList<GraphComponent<Long>> temp = new ArrayList<GraphComponent<Long>>();
            for(GraphComponent<Long> component : graph) {
                if(nodeConnectedWithComponent(newNode, component)) {
                    temp.add(component);
                    added=true;
                }
            }
            if(added) {
                inGraphMergeComponentsAndAddNewNode(temp, newNode);
            } else {
                graph.add(new GraphComponent<Long>(newNode));
            }
        } while(newNode!=N2);
        
        sortGraphComponents();
        int MaxRadius = graph.get(0).getGraphRadius(D);
        
//        int MaxRadius = Integer.MIN_VALUE;
//        for(GraphComponent<Long> component : graph) {
//            if(MaxRadius>component.size()) {
//                break;
//            }
//            int radius = component.getGraphRadius(D);
//            if(radius>MaxRadius) {
//                MaxRadius = radius;
//            }
//        }
        
        System.out.println(""+graph.size()+" "+MaxRadius);
    }
    
    private static boolean nodeConnectedWithComponent(long node, GraphComponent<Long> component) {
        long x, y;
        for(long componentNode : component) {
            x = componentNode+node + Math.abs(componentNode-node) + 3;
            y = componentNode*node + Math.abs(componentNode-node) + 2;
            if(findGCD(x,y)>=D) {
                return true;
            }
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
    
    private static void inGraphMergeComponentsAndAddNewNode(ArrayList<GraphComponent<Long>> components, long newNode) {
        GraphComponent<Long> temp = components.get(0);
        for(GraphComponent<Long> component : components) {
            if(component!=temp) {
                temp.addAll(component);
                graph.remove(component);
            }
        }
        temp.add(newNode);
    }
    
    private static void sortGraphComponents() {
        Collections.sort(graph, new graphComponentSizeComparator());
    }
    
    public static class graphComponentSizeComparator implements Comparator<GraphComponent> {
        @Override
        public int compare(GraphComponent o1, GraphComponent o2) {
            if(o1.size()>o2.size()) {
                return -1;
            } else if(o1.size()<o2.size()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    
}
