package alg;

import java.util.Scanner;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class Main {
    static int L, M, D, F, p1, p2, L2, hash1Collisions = 0, hash2Collisions = 0;
    static char[][] inputCharSets;
    static boolean[] hashTable1, hashTable2;
    static long[] powP1, powP2;

    public static void main(String[] args) {
      //parse input
        Scanner input = new Scanner(System.in);
        L = input.nextInt(); M = input.nextInt(); D = input.nextInt();
        inputCharSets = new char[M][D];
        hashTable1 = new boolean[L];
        hashTable2 = new boolean[L];
        powP1 = new long[M];
        powP2 = new long[M];
        input.nextLine();
        String temp;
        for(int i=0; i<M; i++) {
            inputCharSets[i] = input.nextLine().toCharArray();
        }
        F = input.nextInt(); p1 = input.nextInt(); p2 = input.nextInt(); L2 = input.nextInt();
        
      //prepare pow
        for(int i=0; i<M; i++) {
            powP1[i] = pow(p1,i,L);
            powP2[i] = pow(p2,i,L2);
        }
      //generate Strings
        for(int i=0; i<D; i++) {
            generateAndHashStrings(""+inputCharSets[0][i], 0);
        }
        
      //output
        double numOfGenStrings = D*( (Math.pow(D, M)-1) / (D-1) );
        double hash1avg = (double)hash1Collisions/numOfGenStrings;
        double hash2avg = (double)hash2Collisions/numOfGenStrings;
        System.out.printf("%.3f %.3f\n", hash1avg, hash2avg);
    }
    
    private static void generateAndHashStrings(String s, int fromLine) {
        hash(s);
        if(fromLine==M-1) {
            return;
        }
        for(int i=0; i<D; i++) {
            generateAndHashStrings(s+inputCharSets[fromLine+1][i], fromLine+1);
        }
    }
    
    private static void hash(String toHash) {
      //hash1
        long f1result = f1(toHash);
        int i1 = 0;
        int hash1 = h1(f1result, i1);
        while(hashTable1[hash1]) {
            hash1 = h1(f1result, ++i1);
        }
        hash1Collisions += i1;
        hashTable1[hash1] = true;
        
      //hash2
        long f2result = f2(toHash);
        int i2 = 0;
        int hash2 = h2(f1result, f2result, i2);
        while(hashTable2[hash2]) {
            hash2 = h2(f1result, f2result, ++i2);
        }
        hash2Collisions+=i2;
        hashTable2[hash2] = true;
    }
    
    private static int h1(long f1result, int i) {
        return (int) (f1result%L + (i*F)%L)%L;
    }
    
    private static long f1(String toHash) {
        int k = toHash.length();
        long result = 0;
        for(int j=1; j<=k; j++) {
            result += (long)anv(toHash.charAt(j-1)) * powP1[k-j];
            result = result%L;
        }
        return result;
    }
    
    private static int h2(long f1result, long f2result, int i) {
        return (int) (f1result%L + (i*f2result)%L)%L;
    }
    
    private static long f2(String toHash) {
        int k = toHash.length();
        long result = 0;
        for(int j=1; j<=k; j++) {
            result += (long)anv(toHash.charAt(j-1)) * powP2[k-j];
            result = result%L2;
        }
        return (result%L2)+1;
    }
    
    private static long pow(int p, int exp, int modulo) {
        long result=1;
        for(int i=0; i<exp; i++) {
            result*=p;
            result = result%modulo;
        }
        return result;
    }
    
    private static int anv(char toNum) {
        int num = 0;
        switch(toNum) {
            case 'a': num = 97; break;
            case 'b': num = 98; break;
            case 'c': num = 99; break;
            case 'd': num = 100; break;
            case 'e': num = 101; break;
            case 'f': num = 102; break;
            case 'g': num = 103; break;
            case 'h': num = 104; break;
            case 'i': num = 105; break;
            case 'j': num = 106; break;
            case 'k': num = 107; break;
            case 'l': num = 108; break;
            case 'm': num = 109; break;
            case 'n': num = 110; break;
            case 'o': num = 111; break;
            case 'p': num = 112; break;
            case 'q': num = 113; break;
            case 'r': num = 114; break;
            case 's': num = 115; break;
            case 't': num = 116; break;
            case 'u': num = 117; break;
            case 'v': num = 118; break;
            case 'w': num = 119; break;
            case 'x': num = 120; break;
            case 'y': num = 121; break;
            case 'z': num = 122; break;
        }
        return num;
    }
    
}
