public class print_star{
	public static void main(String[] args){
		int loop = 1;
		while (loop <= 5){
			int i = 1;
			while (i <= loop){
				System.out.print('*');
				i = i + 1;
			}
			loop += 1;
			System.out.println();
		}
	}
}