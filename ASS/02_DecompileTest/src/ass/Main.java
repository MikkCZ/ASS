/*    */ package ass;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class Main
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/*  8 */     if ((args == null) || (args.length == 0)) {
/*  9 */       System.out.println("Some arguments have to be passed");
/* 10 */       System.exit(-1);
/*    */     }
/* 12 */     QuickSort quickSort = new QuickSort();
/* 13 */     quickSort.sort(args, 0, args.length - 1);
/* 14 */     System.out.println(Arrays.toString(args));
/*    */   }
/*    */ }

/* Location:           /home/michal/Plocha/unknownapplication/unknownApplication.jar
 * Qualified Name:     ass.Main
 * JD-Core Version:    0.6.2
 */