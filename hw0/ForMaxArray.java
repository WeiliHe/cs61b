public class ForMaxArray {
    /** Returns the maximum value from m using a for loop. */
    public static int forMax(int[] m) {
    	int maximum = 0;
    	for (int i = 0; i < m.length; i = i + 1){
    		if (m[i] > maximum){
    			maximum = m[i];
    		}
    		i = i + 1;
    	}
    	return maximum;
    }
    public static void main(String[] args) {
       int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};      
       System.out.println(forMax(numbers));
    }
}