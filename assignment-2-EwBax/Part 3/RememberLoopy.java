public class RememberLoopy {
	public static void main (String[] args) {
		/**
		* In this program swap for loops for while loops 
		* if constructs for case statements and the reverse.
		* 
		* Aside: I was looking for a repetitive song for this 
		* assignment, and choose this 90's elctronica hit.
		* In looking up the lyrics it saw the sample was by 
		* Marlena Shaw who is excellent.
		*
		**/
		System.out.println("Blue Boy - Remember Me\n\n");
		
		
		for (int i = 0; i < 7; i++) {
			int chorusOrVerse = i % 2;
			if (chorusOrVerse == 0) {
				verse();
			} else {
				chorus();
			}
			
			if (i < 6) {
				System.out.println("");
			}
		}
		
	}	
	
	public static void verse() {
		boolean done = false;
		int counter = 0;
		while (counter < 8) {
			int oddOrEven = counter % 2;
			switch (oddOrEven) {
				case 0:
					System.out.println("  Remember me?");
					break;
				case 1:
					System.out.println("\tI'm the one who had your babies, I."); 
					break;
			}
			counter++;
		}
	}
	
	public static void chorus() {
		// This is not accurate... but works for our purposes:
		boolean done = false;
		int i = 0;
		while(i < 4) {
			int j = 0;
			while (j < 3) {
				switch (j) {
					case 0:
						System.out.print("  Dingdiggidiggiding,");
						break;
					case 1:
						System.out.print(" dingdingding,");
						break;
					case 2:
						System.out.println(" diggiding");
						break;
				}
				j++;
			}
			i++;
		}
	}
}