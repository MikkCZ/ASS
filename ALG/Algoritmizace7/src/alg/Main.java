package alg;

import java.util.HashSet;
import java.util.Scanner;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class Main {
    static int M, K;
    static String[] L0;
    static String[] Lk1;
    static int Lk1Size;
    static HashSet<String> words = new HashSet<String>();
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        M = input.nextInt();
        K = input.nextInt();
        L0 = new String[M];
        Lk1 = new String[M];
        Lk1Size = M;
        input.nextLine();
        for(int i=0; i<M; i++) {
            String word = input.nextLine();
            L0[i] = word;
            Lk1[i] = word;
            words.add(word);
        }
        
        boolean singleAlphabet = true;
        char test = L0[0].charAt(0);
        for(String s : L0) {
            for(int i=0; i<s.length(); i++) {
                if(s.charAt(i)!=test) {
                    singleAlphabet = false;
                    break;
                }
            }
        }
        if(singleAlphabet) {
            singleAlphabetFunction();
            return;
        }
        
        String[] temp;
        int index;
        String word;
        for(int k=1; k<K; k++) {
            temp = new String[Lk1Size*L0.length];
            index = 0;
            for(int i=0; i<Lk1Size; i++) {
                for(String s0 : L0) {
                    word = Lk1[i]+s0;
                    if(words.add(word)) {
                        temp[index++] = word;
                    }
                }
            }
            Lk1 = temp;
            Lk1Size = index;
        }
        
        System.out.println(words.size());
    }
    
    private static void singleAlphabetFunction() {
        boolean[] length = new boolean[20000000];
        int counter = 0;
        
        int[] LK1 = new int[Lk1Size];
        for(int i=0; i<Lk1Size; i++) {
            int word = Lk1[i].length();
            length[word] = true;
            counter++;
            LK1[i] = word;
        }
        
        int[] temp;
        int index;
        int word;
        for(int k=1; k<K; k++) {
            temp = new int[Lk1Size*L0.length];
            index = 0;
            for(int i=0; i<Lk1Size; i++) {
                for(String s0 : L0) {
                    word = LK1[i]+s0.length();
                    if(!length[word]) {
                        length[word] = true;
                        counter++;
                        temp[index++] = word;
                    }
                }
            }
            LK1 = temp;
            Lk1Size = index;
        }
        
        System.out.println(counter);
    }
}
