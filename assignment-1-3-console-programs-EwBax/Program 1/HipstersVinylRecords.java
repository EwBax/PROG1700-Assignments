import java.util.Scanner;
//adding a comment so I can recommit with "Ready for Marking" Message
public class HipstersVinylRecords {
	public static void main(String []args) {
		
		System.out.println("Hipster's Local Vinyl Records - Customer Order Details");
		
		System.out.print("\nEnter the customer's name: ");
		Scanner terminal = new Scanner(System.in);
		String customerName = terminal.nextLine();
		
		System.out.print("Enter the distance in kilometers for delivery: ");
		double distance = terminal.nextDouble();
		
		System.out.print("Enter the cost of the records purchased: ");
		double recordCost = terminal.nextDouble();
		
		double deliveryCost = distance * 15;
		//Rounding deliveryCost to two decimal places
		//Oracle documentation for Math.round() https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html#round-double-
		deliveryCost = (double)Math.round(deliveryCost*100) / 100;

		//calculating tax for purchase, then rounding to two decimals
		recordCost = recordCost * 1.14;
		recordCost = (double)Math.round(recordCost*100) / 100;
		
		//using System.out.printf() to make sure two decimals are printed even if the second is a 0
		//documentation for printf() https://docs.oracle.com/javase/tutorial/java/data/numberformat.html
		System.out.println("\nPurchase summary for " + customerName);
		System.out.printf("Delivery cost: $%.2f", deliveryCost);
		System.out.printf("\nPurchase cost: $%.2f", recordCost);
		System.out.printf("\nTotal cost: $%.2f", recordCost+deliveryCost);
	}
}