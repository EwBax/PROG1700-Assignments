
import java.util.Scanner;

public class Exercise2 {
	 public static void main(String []args) {
		
		//creating scanner to get input
		Scanner keyboard = new Scanner(System.in);
		
		//requesting user input
		System.out.println("Welcome. Please enter an integer and I will determine if it is even or odd:");
		int num1 = keyboard.nextInt();
		
		//determine if number is divisible by 2 and output result as even or odd
		if(num1%2 == 0){
			System.out.println(num1 + " is even!");
		}
		else{
			System.out.println(num1 + " is odd!");
		}
	}	
}
