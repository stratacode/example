public class SortTest {
   public static void doSort(int[] arr) {
      for (int i = 0; i < arr.length - 1; i++) {
         int index = i;
         for (int j = i + 1; j < arr.length; j++) {
            if (arr[j] < arr[index])
               index = j;
         }
         int toMove = arr[index]; 
         arr[index] = arr[i];
         arr[i] = toMove;
       }
   }
     
   @sc.obj.MainSettings
   public static void main(String args[]) {
       int[] arr = {5, 8, 0, 13, 21, 1, 2, 34, 3, 1}; 
       doSort(arr);

       for (int i : arr) {
           System.out.print(i + ", ");
       }
       System.out.println();
   }
}
