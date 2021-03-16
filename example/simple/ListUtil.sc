import java.util.List;
import java.util.Arrays;

class ListUtil {
    private ListUtil() {}

    public static void reverseOrderOfList(int[] in) {
       int n = in.length;
       int hsz = n/2;
       boolean isOdd = n % 2 == 1;
       for (int i = 0; i < hsz; i++) {
          int t1 = in[i];
          int e = n - i - 1;
          int t2 = in[e];
          in[i] = t2;
          in[e] = t1;
       }
    }

    @sc.obj.MainSettings
    public static void main(String[] args) {
       int[] foo = {1, 2, 3};
       int[] bar = {1, 2, 3, 4};
       int[] baz = {1};

       reverseOrderOfList(foo);
       printList(foo);
       reverseOrderOfList(bar);
       printList(bar);
       reverseOrderOfList(baz);
       printList(baz);
    }

   private static void printList(int[] arr) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < arr.length; i++) {
         if (i != 0)
            sb.append(", ");
         sb.append(arr[i]);
      }
      System.out.println(sb);
   }

}