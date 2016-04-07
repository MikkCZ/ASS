package alg;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    static long A;
    static int C, M, Z, D;
    //static int pocet = 0;
    static long minPot = Long.MAX_VALUE, maxPot = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        A = input.nextInt();
        C = input.nextInt();
        M = input.nextInt();
        Z = input.nextInt();
        D = input.nextInt();
        Uzel strom = new Uzel(null, Z, D);
        strom.spocitejPotencial();
        //System.out.println(pocet);
        System.out.println("" + minPot + " " + maxPot);
    }
    
    private static class Uzel {
        final Uzel RODIC;
        public final int KLIC;
        Uzel levy, pravy;
        final int thisD;
        
        public Uzel(Uzel rodic, int klic, int thisD) {
            this.RODIC = rodic;
            this.KLIC = klic;
            this.thisD = thisD;
            spocitejPotomky();
        }
        
        private void spocitejPotomky() {
            if(thisD==0) {
                return;
            }
            int m = KLIC%4;
            if(m==1 || m==3) {
                //pocet++;
                levyPotomek();
            }
            if (m==2 || m==3) {
                //pocet++;
                pravyPotomek();
            }
        }
        
        private void levyPotomek() {
            long klicLeveho = (A*(this.KLIC+1) + C)%M;
            levy = new Uzel(this, (int)klicLeveho, thisD-1);
        }
        
        private void pravyPotomek() {
            long klicPraveho = (A*(this.KLIC+2) + C)%M;
            pravy = new Uzel(this, (int)klicPraveho, thisD-1);
        }
        
        private Set<Integer> spocitejPotencial() {
            Set<Integer> soubor = new HashSet<Integer>();
            if(levy==null && pravy==null) {
                soubor.add(this.KLIC);
                return soubor;
            }
            if(levy!=null) {
                Set<Integer> LSoubor = levy.spocitejPotencial();
                soubor.addAll(LSoubor);
            }
            if(pravy!=null) {
                Set<Integer> PSoubor = pravy.spocitejPotencial();
                soubor.addAll(PSoubor);
            }
            
            Integer[] mnozina = new Integer[soubor.size()];
            soubor.toArray(mnozina);
            Arrays.sort(mnozina);
//            System.out.print("klic" + this.KLIC + " mnozina");
//            for(Integer i : mnozina) {
//                System.out.print(" " + i);
//            }
//            System.out.println("");
            
            int RLMIndex = RLMIndex(mnozina.length);
            long potencial = ((long)this.KLIC)*((long)mnozina[RLMIndex]);
//            System.out.println("" + RLMIndex + " " + potencial);
            
            if(potencial<minPot) {
                minPot=potencial;
            }
            if(potencial>maxPot) {
                maxPot=potencial;
            }
            
            return soubor;
        }
        
        private int RLMIndex(int velikostMnoziny) {
            int vetsichPrvku;
            if(velikostMnoziny%2 == 1) {
                vetsichPrvku = (velikostMnoziny-1)/2;
            } else {
                vetsichPrvku = (velikostMnoziny)/2;
            }
            return (velikostMnoziny-vetsichPrvku)-1;
        }
    }
}
