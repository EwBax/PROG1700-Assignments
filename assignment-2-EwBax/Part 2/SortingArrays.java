import java.util.Scanner;
import java.util.Arrays;

public class SortingArrays {
	
	
	public static void main (String[] args) {

		System.out.print("Please enter a list of integers separated by space: ");
		//getting the input as a String array first to avoid having to ask the size of the list
		String[] stringArray = getList();
		//then converting it to an int array
		int[] arrayToSort = convertToIntArray(stringArray);
		//sorting using the parallelSort() method https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#parallelSort(int%5B%5D)	
		int[] sortedArray = arrayToSort;
		Arrays.parallelSort(sortedArray);
		System.out.println("The list using parallelSort() is: " + Arrays.toString(sortedArray));
		
		//resetting the array then sorting it with my method
		sortedArray = new int[arrayToSort.length];
		sortedArray = sortIntArray(arrayToSort);
		
		System.out.println("The list sorted using my method is: " + Arrays.toString(sortedArray));
		
		System.out.print("Please enter a string of words: ");
		stringArray = getList();
		
		//using a method to get input for alphabetical or reverse alphabetical, then calling the appropriate method to sort
		stringArray = chooseSort(stringArray);
		
		System.out.println("The sorted array is: " + Arrays.toString(stringArray));
		
	}
	
	//method to get a line of input from the terminal and split it into a String array 
	public static String[] getList() {
		Scanner terminal = new Scanner(System.in);
		String[] stringArray = terminal.nextLine().split("\\s+");
		return stringArray;
	}
	
	//a method to request the uer inputs an int, with custom prompt and catches exception for if input is not an int
	public static int getInt(String prompt) {
		Scanner terminal = new Scanner(System.in);
		boolean goodInput = false;
		int input = 0;
		while (!goodInput) {
			try {
				System.out.print(prompt);
				input = terminal.nextInt();
				goodInput = true;
			} catch (Exception e) {
				terminal.nextLine();
				System.out.println("INPUT MISMATCH EXCEPTION: the value you entered is not an integer.");
			}
		}
		return input;
	}
	
	//Method to convert a string array to an int array
	public static int[] convertToIntArray(String[] stringArray) {
		int[] intArray = new int[stringArray.length];
		for (int i = 0; i < intArray.length; i++) {
			//custom method to check if value is an int first
			if(checkForInt(stringArray[i])) {
				intArray[i] = Integer.parseInt(stringArray[i]);
			} else {
				System.out.println("ERROR: \'" + stringArray[i] + "\" is not an integer.");
				System.exit(0);
			}
		}
		return intArray;
	}
	
	//returns true if String can be parsed to int, false if it can't
	public static boolean checkForInt(String s) {
		try {
			int i = Integer.parseInt(s);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	//method to sort the int array
	public static int[] sortIntArray(int[] arrayToSort) {
		int currentLow = 0;
		int previousLow = 0;
		int duplicates = 0;
		int counter = 0;
		int[] sortedArray = new int[arrayToSort.length];
		for (int i = 0; i < sortedArray.length; i++) { //loops through second array finding the lowest value to put in each spot
			currentLow = Integer.MAX_VALUE; //resets the current low each loop
			for (int j = 0; j < arrayToSort.length; j++) { //loops through the array to be sorted to find lowest value
				//if it isnt the first time through it compares the number against the previous low to make sure it isn't the same number
				if ((i > 0) && (arrayToSort[j] < currentLow) && (arrayToSort[j] > previousLow)) {
					currentLow = arrayToSort[j];
				//if it IS the same number (and isnt already the current low, in case there's duplicates)
				} else if (arrayToSort[j] == previousLow && arrayToSort[j] != currentLow) {
					//checks if there is already the same number of this value in the new array as in the old array to make sure
					//it's a duplicate and not a value that has already been added
					if (howManyOf(sortedArray, arrayToSort[j]) < howManyOf(arrayToSort, arrayToSort[j])) {
						currentLow = arrayToSort[j];
					}
				//First time through the loop, doesn't need to compare to the previous low
				} else if ((i == 0) && (arrayToSort[j] < currentLow)) {
					currentLow = arrayToSort[j];
				}
			}
			//sets the value in the new array to the lowest value found, then sets that to the previous low
			sortedArray[i] = currentLow;
			previousLow = currentLow;
		}
		return sortedArray;
	}
	
	//method to check how many of a value exist in an array i.e. to check for duplicates. returns how many there are
	public static int howManyOf(int[] anArray, int value) {
		int counter = 0;
		for (int i = 0; i < anArray.length; i++) {
			if (anArray[i] == value) counter++;
		}
		return counter;
	}
	
	//method to ask if alphabetical or reverse alphabetical sorting, then call appropriate method and return the result
	public static String[] chooseSort(String[] stringArray) {
		int choice = 0;
		while (choice != 1 && choice != 2) {
			choice = getInt("Enter [1] to sort alphabetically or [2] to sort reverse alphabetically: ");
		}
		
		if (choice == 1) {
			stringArray = sortStringArray(stringArray);
		} else {
			stringArray = sortStringArrayReverse(stringArray);
		}
		return stringArray;
	}
	
	
	//This uses selection sorting - it starts at the first item in the array and then compares it to each other item in the array
	//using the compareTo() method, which compares the ASCII values of the characters. If both a and b are Strings, a.compareTo(b);
	//would return an int less than zero if a comes first alphabetically, returns 0 if they are equal, and greater than 0 if a comes
	//after b alphabetically. Because it uses ASCII values words that start with a capital letter always come first.
	//The sorting works by starting at the first item in the array and comparing it to every other item, swapping them if
	//the one you're comparing comes after the other one.
	public static String[] sortStringArray(String[] stringArray) {
		String temp;
		for (int i = 0; i < (stringArray.length - 1); i++) {
			for (int j = i+1; j < stringArray.length; j++) {
				if (stringArray[i].compareTo(stringArray[j]) > 0) {
					temp = stringArray[i];
					stringArray[i] = stringArray[j];
					stringArray[j] = temp;
				}
			}
		}
		return stringArray;
	}
	
	
	//This method is the exact same as the one above but it is checking if compareTo is less than 0, to see if the item comes first
	//alphabeticall, then swapping if it does because it is sorting reverse alphabetcally
	public static String[] sortStringArrayReverse(String[] stringArray) {
		String temp;
		for (int i = 0; i < (stringArray.length - 1); i++) {
			for (int j = i+1; j < stringArray.length; j++) {
				if (stringArray[i].compareTo(stringArray[j]) < 0) {
					temp = stringArray[i];
					stringArray[i] = stringArray[j];
					stringArray[j] = temp;
				}
			}
		}
		return stringArray;
	}
}