/*    */ package ass;
/*    */ 
/*    */ public class QuickSort<E extends Comparable<E>>
/*    */ {
/*    */   public void sort(E[] vetor, int inicio, int fim)
/*    */   {
                Integer[] vetor2 = new Integer[vetor.length];
                for(int i=0; i<vetor.length; i++) {
                    vetor2[i] = Integer.parseInt((String)vetor[i]);
                }
/*  6 */     if (inicio < fim) {
/*  7 */       int pivo = partition(vetor, inicio, fim);
/*  8 */       sort(vetor, inicio, pivo - 1);
/*  9 */       sort(vetor, pivo + 1, fim);
/*    */     }
/*    */   }
/*    */ 
/*    */   private void swap(E[] vetor, int i, int j) {
/* 14 */     Comparable temp = vetor[j];
/* 15 */     vetor[j] = vetor[i];
/* 16 */     vetor[i] = (E) temp;
/*    */   }
/*    */ 
/*    */   private int partition(E[] vetor, int inicio, int fim) {
/* 20 */     int i = inicio + 1;
/* 21 */     int j = fim;
/* 22 */     Comparable pivo = vetor[inicio];
/*    */ 
/* 24 */     while (i <= j) {
/* 25 */       if ( ((Comparable)vetor[i]).compareTo(pivo) <= 0) i++;
/* 26 */       else if ( ((Comparable)vetor[j]).compareTo(pivo) > 0) j--; else
/* 27 */         swap(vetor, i, j);
/*    */     }
/* 29 */     swap(vetor, inicio, j);
/* 30 */     return j;
/*    */   }
/*    */ }

/* Location:           /home/michal/Plocha/unknownapplication/unknownApplication.jar
 * Qualified Name:     ass.QuickSort
 * JD-Core Version:    0.6.2
 */