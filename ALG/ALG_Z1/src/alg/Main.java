package alg;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int M, N, K, targetX, targetY;
    static String[] array;
    static HashMap<String,Integer> strings = new HashMap<String,Integer>();
    static List<String> result = new LinkedList<String>();
    
    public static void main(String[] args) {
      //input
        Scanner input = new Scanner(System.in);
        M = input.nextInt();
        targetX = M-1;
        N = input.nextInt();
        targetY = N-1;
        K = input.nextInt();
        input.nextLine();
        array = new String[M];
        for(int m=0; m<M; m++) {
            array[m] = input.nextLine();
        }
      //input END
        int startX=0, startY=0, zbyvaZlomu=K;
        boolean priselZhora=true, priselZleva=true;
        boolean muzeDolu = muzeDolu(startX, startY, zbyvaZlomu, priselZhora);
        boolean muzeDoprava = muzeDoprava(startX, startY, zbyvaZlomu, priselZleva);
        if(muzeDolu) {
            if(priselZhora) {
                jdiDal(startX+1, startY, zbyvaZlomu, true, false, "");
            } else {
                jdiDal(startX+1, startY, zbyvaZlomu-1, true, false, "");
            }
        }
        if(muzeDoprava) {
            if(priselZleva) {
                jdiDal(startX, startY+1, zbyvaZlomu, false, true, "");
            } else {
                jdiDal(startX, startY+1, zbyvaZlomu-1, false, true, "");
            }
        }
        int MAX = 0;
        for(String key : strings.keySet()) {
            Integer value = strings.get(key);
            if(value>MAX) {
                result.clear();
                MAX = value;
                result.add(key);
            } else if(value==MAX) {
                result.add(key);
            }
        }
        Collections.sort(result);
        for(String s : result) {
            System.out.println(s);
        }
    }
    
    private static void jdiDal(int startX, int startY, int zbyvaZlomu, boolean priselZhora, boolean priselZleva, String current) {
        if(startX==targetX && startY==targetY && zbyvaZlomu==0) {
            //System.out.println("target " + current);
            Integer count = strings.get(current);
            if(count==null) {
                count = 1;
            } else {
                count++;
            }
            strings.put(current, count);
        }
        boolean muzeDolu = muzeDolu(startX, startY, zbyvaZlomu, priselZhora);
        boolean muzeDoprava = muzeDoprava(startX, startY, zbyvaZlomu, priselZleva);
        char thisField = array[startX].charAt(startY);
        if(muzeDolu) {
            if(priselZhora) {
                jdiDal(startX+1, startY, zbyvaZlomu, true, false, current+thisField);
            } else {
                jdiDal(startX+1, startY, zbyvaZlomu-1, true, false, current+thisField+thisField);
            }
        }
        if(muzeDoprava) {
            if(priselZleva) {
                jdiDal(startX, startY+1, zbyvaZlomu, false, true, current+thisField);
            } else {
                jdiDal(startX, startY+1, zbyvaZlomu-1, false, true, current+thisField+thisField);
            }
        }
    }

    private static boolean muzeDolu(int x, int y, int zbyvaZlomu, boolean priselZhora) {
        if(zbyvaZlomu==0 && !priselZhora) {
            return false;
        }
        if(x==targetX) {
            return false;
        }
        int vyska=M-x, sirka=N-y, minZlomu=0, maxZlomu;
        if(y!=targetY) {
            minZlomu++;
        }
        if(!priselZhora) {
            minZlomu++;
        }
        if(minZlomu>zbyvaZlomu) {
            return false;
        }
        if(vyska>sirka) {
            maxZlomu = (sirka-1)*2;
        } else /*if(sirka>=vyska)*/ {
            maxZlomu = ( (vyska-1)*2 )-1;
        }
        if(!priselZhora) {
            maxZlomu++;
        }
        return (maxZlomu>=zbyvaZlomu);
    }
    
    private static boolean muzeDoprava(int x, int y, int zbyvaZlomu, boolean priselZleva) {
        if(zbyvaZlomu==0 && !priselZleva) {
            return false;
        }
        if(y==targetY) {
            return false;
        }
        int vyska=M-x, sirka=N-y, minZlomu=0, maxZlomu;
        if(x!=targetX) {
            minZlomu++;
        }
        if(!priselZleva) {
            minZlomu++;
        }
        if(minZlomu>zbyvaZlomu) {
            return false;
        }
        if(sirka>vyska) {
            maxZlomu = (vyska-1)*2;
        } else /*if (vyska>=sirka)*/ {
            maxZlomu = ( (sirka-1)*2 )-1;
        }
        if(!priselZleva) {
            maxZlomu++;
        }
        return (maxZlomu>=zbyvaZlomu);
    }
}
