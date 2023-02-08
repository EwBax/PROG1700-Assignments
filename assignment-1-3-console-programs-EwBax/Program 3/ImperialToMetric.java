import java.util.Scanner;
//adding a comment so I can recommit with "Ready for Marking" Message
public class ImperialToMetric {
	public static void main(String []args) {
		System.out.println("Imperial To Metric Conversion\n");
		
		System.out.print("Enter the number of tons: ");
		Scanner terminal = new Scanner(System.in);
		double tons = terminal.nextDouble();
		
		System.out.print("Enter the number of stone: ");
		double stone = terminal.nextDouble();
		
		System.out.print("Enter the number of pounds: ");
		double pounds = terminal.nextDouble();
		
		System.out.print("Enter the number of ounces: ");
		double ounces = terminal.nextDouble();
		
		double totalOunces = (35840 * tons) + (224 * stone) + (16 * pounds) + ounces;
		double totalKilos = totalOunces / 35.274;
		//casting values to int as a way to floor the decimals
		int metricTons = (int)(totalKilos / 1000);
		int kilos = (int)(totalKilos - (metricTons * 1000));
		double grams = (totalKilos - (metricTons * 1000) - kilos) * 1000;
		
		//Rounding grams to one decimal place
		//Oracle documentation for Math.round() https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html#round-double-
		grams = (double)Math.round(grams*10) / 10;
		
		System.out.println("\nThe metric weight is " + metricTons + " metric tons, " + kilos + " kilos, and " + grams + " grams.");
	}
}