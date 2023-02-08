import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ManualCSV {
    public static void main(String[] args) {
        //Prompting user to select manual data entry or scan from file
        System.out.println("Welcome to the .csv maker.\n");
        int mode = getInt("Enter [1] for manual data entry or [2] to scan from text file: ");
        while(mode != 1 && mode != 2) {
            System.out.println("\nInvalid input.");
            mode = getInt("Enter [1] for manual data entry or [2] to scan from text file: ");
        }
        if(mode == 1) {
            //prompt user for number of columns
            int columns = getInt("Enter the number of columns: ");
            //prompt user for number of rows
            int rows = getInt("Enter the number of rows: ");
            System.out.println();

            //prompt for header for each column, one at a time
            String[][] data = getData(rows, columns);

            try {
                //creates the output file
                writeToCSV(data);
            } catch (IOException e) {
                System.out.println("ERROR: Input/Output Exception");
                System.exit(0);
            }

            System.out.println("output.csv should look like this:");
            printData(data);
        } else {
            CSVFromFile.csvFromFile();
        }
    }

    //method for getting int input
    public static int getInt(String prompt) {
        boolean goodInput = false;
        int input = 0;
        Scanner terminal = new Scanner(System.in);
        while(!goodInput) {
            System.out.print(prompt);
            try {
                input = terminal.nextInt();
                goodInput = true;   //if no exception from previous line, then an int was entered
            } catch (InputMismatchException e) {
                //error message if a non-int value is entered
                System.out.printf("\n\"%s\" is not an integer.\n\n", terminal.nextLine());
            }
        }
        return input;
    }


    //method for getting string input
    public static String getString(String prompt) {
        System.out.print(prompt);
        Scanner terminal = new Scanner(System.in);
        return terminal.nextLine();
    }


    public static String[][] getData(int rows, int columns) {
        //Getting the headers
        String[] headers = getColumnNames(columns);
        System.out.println();   //for spacing/formatting
        //2D Array to store each row and column. Extra row for the headers
        String[][] data = new String[rows + 1][columns];
        //top row is the headers
        data[0] = headers;
        //Looping through 2D array left to right, top to bottom (top left to bottom right)
        //Starting at second row, because top row is headers
        for(int i = 1; i < data.length ; i++) {
            for(int j = 0; j < data[i].length; j++) {
                data[i][j] = getString("Enter " + data[0][j] + " " + i + ": ");
                data[i][j] = handleEscapeCharacters(data[i][j]);
            }
            System.out.println();   //for spacing/formatting
        }
        return data;
    }

    //This method gets the user to input headings for the columns of the .csv
    public static String[] getColumnNames(int columns) {
        String[] headers = new String[columns];
        for(int i = 0; i < headers.length; i++) {
            int colNumber = i + 1;
            headers[i] = getString("Enter the header for column " + colNumber + ": ");
        }
        return headers;
    }


    //method for checking for escape characters and handling them
    public static String handleEscapeCharacters(String s) {
        //doubles quotes
        if(s.contains("\"")) {
            s = s.replaceAll("\"", "\"\"");
        }
        //if a comma is detected, wrap the string in quotes
        if(s.contains(",")) {
            s = "\"" + s + "\"";
        }
        return s;
    }


    public static void writeToCSV(String[][] data) throws IOException {
        //constructing file writer with default of overwriting file, not appending
        FileWriter fw = new FileWriter("output.csv");
        //looping through the 2D array left to right, top to bottom
        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[i].length; j++) {
                //appending the String to the file
                fw.append(data[i][j]);
                if(j < data[i].length - 1) {
                    //if not the last value in the row, add comma
                    fw.append(",");
                } else {
                    //else it IS the last value in the row, so append new line character
                    fw.append("\n");
                }
            }
        }

        fw.close();
    }


    public static void printData(String[][] data) {
        //similar to the function above to write to a file, but it outputs to terminal instead of file
        //loops through 2D array left to right, top to bottom
        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[i].length; j++) {
                //prints value
                System.out.print(data[i][j]);
                if(j < data[i].length - 1) {
                    //if not the last value, print comma
                    System.out.print(",");
                } else {
                    //else last value, print new line
                    System.out.println();
                }
            }
        }
    }

}
