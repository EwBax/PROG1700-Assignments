public class GeniusPuzzle {
	public static void main(String []args) {
		
		System.out.println("\nThis program will solve the expression:\n" +
						   " ABCDE\n" +
						   "X    A\n" +
						   "______\n" +
						   "EEEEEE\n\n");
		//This represents ABCDE, starting at the lowest possible number it could be
		int abcde = 12345;
		
		boolean solved = false;
		
		//variables for each individual digit
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		int e = 0;
		//This one represents the solution
		int eeeeee = 0;
		
		while(!solved) {
			//Getting each individual number as a variable
			e = abcde % 10;
			abcde = abcde / 10;
			
			d = abcde % 10;
			abcde = abcde / 10; 
			
			c = abcde % 10;
			abcde = abcde / 10;
			
			b = abcde % 10;
			abcde = abcde / 10;
			
			a = abcde % 10;
			abcde = abcde / 10;
			
			abcde = (a * 10000) + (b * 1000) + (c * 100) + (d * 10) + e;
			
			eeeeee = (e * 100000) + (e * 10000) + (e * 1000) + (e * 100) + (e * 10) + e;
			
			//Checking the solution and if each digit is unique
			if (abcde * a == eeeeee) {
				if(a != b && a != c && a != d && a != e) {
					if(b != c && b != d && b != e) {
						if(c != d && c != e) {
							if(d != e) {
								solved = true;
							}
						}
					}
					
				}
			} else {
				abcde++;
			}
		}
		
		System.out.println("The solution is:\n\n" + 
						   " "+ abcde + "\n" +
						   "X    "+ a + "\n" +
						   "______" + "\n" +
						   +eeeeee);
	}
}