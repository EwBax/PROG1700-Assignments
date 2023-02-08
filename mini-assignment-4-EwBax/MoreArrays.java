import java.util.*;

public class MoreArrays {
	public static void main(String[] args) {
		//prompting to choose which part of the program to run
		int whichPart = 0;
		while(whichPart != 1 && whichPart != 2 && whichPart != 3) {
			whichPart = getInt("Enter [1] for part 1 - missing values. Enter [2] for part 2 - removing " +
								"duplicates. Enter [3] for part 3 - creating a multiplication table: ");
		}
		switch (whichPart) {
			case 1 -> part1();
			case 2 -> part2();
			case 3 -> part3();
			default -> System.out.println("Unexpected value: " + whichPart);
		}

	}
	
	
	public static void part1(){
		System.out.print("Enter a list of integers. All missing numbers between zero and the highest in the list " +
						"will be printed: ");
		//uses custom methods to get list as String[] then convert to int[]
		int[] anArray = convertToIntArray(getList());
		//custom method to find the highest value
		int highestValue = findHighestValue(anArray);
		System.out.println("The highest value is: " + highestValue);
		//custom method to find missing values
		ArrayList<Integer> missingValues = findMissingValues(anArray, highestValue);
		System.out.println("This array is missing " + missingValues.size() + " numbers, they are: "
							+ missingValues);
	}


	public static void part2() {
		System.out.print("Enter a list of integers, any duplicates will be removed: ");
		int[] anArray = convertToIntArray(getList());
		//sorting array first to make checking duplicates easier
		Arrays.parallelSort(anArray);

		ArrayList<Integer> uniqueValues = new ArrayList<>();
		//adds first value to list, then every other value only adds if it is not equal to the previous value
		for(int i = 0; i < anArray.length; i++) {
			if(i > 0 && anArray[i] != anArray[i-1]) {
				uniqueValues.add(anArray[i]);
			} else if(i==0) {
				uniqueValues.add(anArray[i]);
			}
		}
		System.out.println("The list with all the duplicates removed is: " + uniqueValues);
	}
	
	
	public static void part3() {
		int size = getInt("Please enter an integer for the size of the multiplication table: ");
		size++; //so that the table goes all the way to the int they entered since it starts at 0 instead of 1
		int[][] multiplicationTable = new int[size][size];
		//iterates through each row from left to right. Top left to bottom right
		for(int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				//if neither number is 0, then it must be in the table, so it is i*j
				if(i != 0 && j != 0) {
					multiplicationTable[i][j] = j * i;
				} else if(j == 0) {
					//if either number is 0 (the else statements works for i == 0 in this case)
					//then the value is the opposite number
					//this works for [0][0] too
					multiplicationTable[i][j] = i;
				} else {
					multiplicationTable[i][j] = j;
				}
			}
		}
		
		System.out.println("The multiplication table is : ");
		//custom method to print the multiplication table with nice formatting
		printMultiplicationTable(multiplicationTable);
	}

	//loops through the array to find the highest value, and returns it
	public static int findHighestValue(int[] a) {
		int currentHigh = Integer.MIN_VALUE;
		for(int i: a) {
			if(i > currentHigh) currentHigh = i;
		}
		return currentHigh;
	}
	

	public static ArrayList<Integer> findMissingValues(int[] a, int n) {
		ArrayList<Integer> missingValues = new ArrayList<>();
		boolean exists = false;
		//loops through the array to check if each number from 0 - n exists in the array
		for(int i = 1; i < n; i++) {
			for(int j = 0; j < a.length; j++) {
				if(a[j] == i) {
					exists = true;
					break; //to break out of the loop
				}
			}

			//if the number is not found in the array, add it to a list of missing numbers
			if(!exists) {
				missingValues.add(i);
			} else {
				exists = false;
			}
		}
		return missingValues;	
	}


	public static void printMultiplicationTable(int[][] a) {
		//finds the largest number in the table and how many digits long it is
		int largestNum = a.length * a.length;
		int largestNumLength = String.valueOf(largestNum).length();
		//for each loop to get each row of the 2d array as a single array
		for (int[] row : a) {
			for (int i = 0; i < a.length; i++) {
				//checks how many digits long the current number is
				int numLength = String.valueOf(row[i]).length();
				//add spaces before the number is printed to make up for the difference in length between the smaller
				//numbers and the largest number and have them line up nicely
				while (numLength < largestNumLength) {
					System.out.print("\s");
					numLength++;
				}
				//prints a tab after the number for spacing/formatting
				System.out.print(row[i] + "\t");
			}
			//new line at the end of each row
			System.out.println();
		}
	}


	//splits next line in terminal into array of words
	public static String[] getList() {
		Scanner terminal = new Scanner(System.in);
		return terminal.nextLine().split("\\s+");
	}

	//requests an int from terminal with custom prompt
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
				System.out.println("INPUT MISMATCH EXCEPTION: \"" + terminal.nextLine() + "\" is not an integer.");
			}
		}
		return input;
	}

	//converts String array to int array
	public static int[] convertToIntArray(String[] stringArray) {
		int[] intArray = new int[stringArray.length];
		for (int i = 0; i < intArray.length; i++) {
			//custom method to check if value is an int first
			if(checkForInt(stringArray[i])) {
				intArray[i] = Integer.parseInt(stringArray[i]);
			} else {
				System.out.println("ERROR: \"" + stringArray[i] + "\" is not an integer.");
				System.exit(0);
			}
		}
		return intArray;
	}

	//returns true if String can be parsed to int, false if it can't
	public static boolean checkForInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch(Exception e) {
			return false;
		}
	}

}