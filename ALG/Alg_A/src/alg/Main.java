package alg;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class Main {
    static int L;
    static List<String> R = new LinkedList<String>();
    static int K;
    
    static Map<String, List<String>> followers = new HashMap<String, List<String>>();
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        L = input.nextInt();
        input.nextLine();
        String nextString = input.nextLine();
        K = nextString.length();
        while(!"*".equals(nextString)) {
            R.add(nextString);
            nextString = input.nextLine();
        }
        
        for(String inputStr1:R) {
            String stringEnd = inputStr1.substring(1,K);
            for(String inputStr2:R) {
                String str2Front = inputStr2.substring(0,K-1);
                if(stringEnd.equals(str2Front)) {
                    addFollowingString(inputStr1, inputStr2);
                }
            }
        }
        
        HashMap<String, Long> endingStringsFreq = new HashMap<String, Long>();
        for(String inputString:R) {
            endingStringsFreq.put(inputString, new Long(1));
        }
        
        for(int i=K; i<L; i++) {
            HashMap<String, Long> newEndingStrsFreq = new HashMap<String, Long>();
            for(String endStr:endingStringsFreq.keySet()) {
                List<String> endStrFollowers = followers.get(endStr);
                if(endStrFollowers==null) {
                    continue;
                }
                long frequency = endingStringsFreq.get(endStr);
                for(String follower:endStrFollowers) {
                    incrementCounter(newEndingStrsFreq, follower, frequency);
                }
            }
            endingStringsFreq = newEndingStrsFreq;
        }

        long result = 0;
        for(String endingString : endingStringsFreq.keySet()) {
            result += endingStringsFreq.get(endingString);
        }
        System.out.println(result);
    }
    
    private static void addFollowingString(String parent, String follower) {
        List<String> parentFollowers = followers.get(parent);
        if(parentFollowers==null) {
            parentFollowers = new LinkedList<String>();
            followers.put(parent, parentFollowers);
        }
        parentFollowers.add(follower);
    }
    
    private static void incrementCounter(Map<String, Long> counter, String key, long frequency) {
        Long value = counter.get(key);
        if(value==null) {
            value = new Long(0);
        }
        value+=frequency;
        counter.put(key, value);
    }

}
