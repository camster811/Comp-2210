
public class CountEvens {
   public static int foo(int[] a, int left, int right) {
      if (left > right) {
         return 0;
      } else if (a[left] < 0) {
         return foo(a, left + 1, right + 1);
      } else {
         return foo(a, left + 1, right);
      }
   }
   
   public static void main(String[] args) {
      int[] a = new int[]{0, 1, -2, 3, 4, -5};
      System.out.println(foo(a, 0, 5));
      
      
   }
}