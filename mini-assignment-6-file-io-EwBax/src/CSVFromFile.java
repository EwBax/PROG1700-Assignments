import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CSVFromFile {
    public static void csvFromFile() {
        //get user to enter relative path of file to read from
        String sourcePath = ManualCSV.getString("Enter the relative path of the file to " +
                "read from: ");
        System.out.println(); //for spacing/formatting
        File source = new File(sourcePath);
        //prints what the CSV should look like
        System.out.println("output.csv should look like this:");
        try {
            printDemoCSV(source);
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found.");
            System.exit(0);
        }
        //call method to create csv, pass file and number of columns
        try {
            createCSV(source);
        } catch (IOException e) {
            System.out.println("ERROR: Input/Output exception");
            System.exit(0);
        }
    }


    public static int getNumColumns(File source) throws FileNotFoundException {
        int columns = 1; //default one column if file doesn't specify
        Scanner fileReader = new Scanner(source);
        //calls method to check if first line is "cols:" + number
        if(checkForCols(source)){
            //if the first line DOES specify colums, gets the substring starting at 5 (after "cols:")
            //and parses integer for the number of columns specified
            int temp = 0;
            try {
                temp = Integer.parseInt(fileReader.nextLine().substring(5));
            } catch (NumberFormatException e) {
                System.out.println("Could not detect number of columns: defaulting to 1.");
            }
            if(temp > 1) {
                //if cols specified is NOT negative, or 0 (because that can't work)
                //if user does enter negative or 0 or 1, columns just stays default value of 1
                columns = temp;
            }
        }
        return columns;
    }


    public static boolean checkForCols(File source) throws FileNotFoundException{
        Scanner fileReader = new Scanner(source);
        //if file has next line
        if(fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            //returns whether the first line = "cols:" + number
            return line.substring(0, 5).equalsIgnoreCase("cols:");
        } else {
            return false;
        }
    }

    //create CSV method
    /* If I were to do this again or had the time to refactor I would make a separate method for
    reading the file and outputting to file. It would have to use an ArrayList<ArrayList<String>>
    because the number of rows is unknown, and I could use the output to file method from
    ManualCSV.java, but that uses 2D array, so I would have to refactor that to use
    ArrayList<ArrayList<String>> as well */
    public static void createCSV(File source) throws IOException {
        /* gets number of columns. Affects how many lines of input file are appended before going
        to new line on output file */
        int columns = getNumColumns(source);
        Scanner fileReader = new Scanner(source);
        FileWriter fw = new FileWriter("output.csv");
        //checks if the first line is "cols:" because we don't want to add that to output file
        if(checkForCols(source)) {
            //advances Scanner to next line to avoid "cols:" line being appended
            fileReader.nextLine();
        }
        //loops until reader is out of lines in file
        while(fileReader.hasNextLine()) {
            //loops until number of columns has been appended to row
            for (int i = 0; i < columns; i++) {
                /* avoids the scanner running out of lines in the source file mid row in output
                file, causing a runtime exception */
                if(fileReader.hasNextLine()) {
                    //store next line in string
                    String line = fileReader.nextLine();
                    //check for special characters and fix them
                    line = ManualCSV.handleEscapeCharacters(line);
                    fw.append(line);
                    //if not the last line in row i.e.: last iteration through
                    if (i < columns - 1) {
                        fw.append(',');
                    } else {
                        //if it is the last value in the column
                        fw.append('\n');
                    }
                }
            }
        }
        fw.close();
    }

    //almost the exact same as the method above, only instead of appending to new file it
    //prints to terminal
    public static void printDemoCSV(File source) throws FileNotFoundException{
        int columns = getNumColumns(source);
        Scanner fileReader = new Scanner(source);
        if(checkForCols(source)) {
            fileReader.nextLine();
        }
        while(fileReader.hasNextLine()) {
            for (int i = 0; i < columns; i++) {
                if(fileReader.hasNextLine()) {
                    //gets next line and handles escape characters in nested method call, then prints
                    System.out.print(ManualCSV.handleEscapeCharacters(fileReader.nextLine()));
                    if (i < columns - 1) {
                        System.out.print(',');
                    } else {
                        System.out.print('\n');
                    }
                }
            }
        }
    }

}
