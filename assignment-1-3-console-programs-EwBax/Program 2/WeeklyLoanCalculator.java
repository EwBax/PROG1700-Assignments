import java.util.Scanner;
//adding a comment so I can recommit with "Ready for Marking" Message
public class WeeklyLoanCalculator {
	public static void main(String []args) {
		
		System.out.println("Weekly Loan Calculator\n");
		
		System.out.print("Enter the amount of loan: ");
		Scanner terminal = new Scanner(System.in);
		double loan = terminal.nextDouble();
		
		System.out.print("Enter the interest rate (%): ");
		double rate = terminal.nextDouble();
		
		System.out.print("Enter the number of years: ");
		int years = terminal.nextInt();
	
		//calculating weekly payment 
		double i = rate / 5200;
		
		double weeklyPayment = (i / (1 - Math.pow(1 + i, -52 * years))) * loan;
		
		//Rounding weeklyPayment to two decimal places
		//Oracle documentation for Math.round() https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html#round-double-
		weeklyPayment = (double)Math.round(weeklyPayment * 100) / 100;
		
		//using System.out.printf() to make sure two decimals are printed even if the second is a 0
		//documentation for printf() https://docs.oracle.com/javase/tutorial/java/data/numberformat.html
		System.out.printf("Your weekly payment will be: $%.2f", weeklyPayment);
	}
}