package alg;

import java.util.Scanner;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class Main {
    static int L, H, modulo, D;
    static int nodes;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        long x = input.nextLong();
        L = input.nextInt();
        H = input.nextInt();
        modulo = input.nextInt();
        D = input.nextInt();
        countNodes(x, 0);
        System.out.println(nodes);
    }
    
    private static void countNodes(long x, int hloubka) {
        if (hloubka == D) {
            return;
        }
        
        long bbs = blumBlumShub(x);
        
        if (H <=x) {
            nodes++;
        } else if (L <= x && x < H) {
            if (bbs == x) {
                nodes += D-hloubka;
                return;
            }
            nodes++;
            countNodes(bbs, hloubka+1);
        } else if (x < L) {
            nodes++;
            countNodes(bbs+1, hloubka+1);
            countNodes(bbs+2, hloubka+1);
            countNodes(bbs+3, hloubka+1);
        }
    }
    
    private static long blumBlumShub(long x) {
        long result = (x*x)%modulo;
        return result;
    }

}
