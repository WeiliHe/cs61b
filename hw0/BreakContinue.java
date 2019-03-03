public class BreakContinue {
  public static void windowPosSum(int[] a, int n) {
    /** your code here */ 
    for (int i = 0; i < a.length; i = i + 1){
    	if (a[i] <= 0){
    		continue;
    	}else{
        for (int j = 0; j <= n; j = j + 1){
          if (i + j == a.length){
            break;
          }else{
            if (j == 0){
              a[i] = a[i];
            }else{
              a[i] = a[i] + a[i + j];
            }
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    int[] a = {1, 2, -3, 4, 5, 4};
    int n = 3;
    windowPosSum(a, n);

    // Should print 4, 8, -3, 13, 9, 4
    System.out.println(java.util.Arrays.toString(a));
  }
}