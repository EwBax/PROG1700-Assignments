package ca.ewanbaxter.Assignment4;

import ca.ewanbaxter.util.Input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WhatAWonderfulWorld {
    //class variables to avoid Magic Numbers
    static final int WONDERFUL_WORLD = 15;
    static final int MAX_LINE_LENGTH_FOR_CAPS = 40;

    public static void main(String[] args) {
        System.out.println("Welcome to WhatAWonderfulWorld.io!\n");
        //Prompt user for path of file
        String sourcePath = Input.getString("Enter the path of the file to be scanned: ");
        File source = new File(sourcePath);

        //declaring outside try block for scope
        ArrayList<String> listFromFile = null;
        try {
            listFromFile = readListFromFile(source);
        } catch (FileNotFoundException e) {
            System.out.println("That file could not be found.");
            System.exit(0);
        }

        System.out.println("-------------------------------------------------------------------");
        System.out.println("                        Original Version");
        System.out.println("-------------------------------------------------------------------\n");
        printList(listFromFile);

        capsShortLines(listFromFile);
        addLineNumbers(listFromFile);
        addExclamationPoints(listFromFile);
        blankRandomLine(listFromFile);

        System.out.println();
        System.out.println("-------------------------------------------------------------------");
        System.out.println("                           New Version");
        System.out.println("-------------------------------------------------------------------\n");
        printList(listFromFile);

        System.out.println();
        File outputFile = getOutputFile();
        try {
            writeListToFile(listFromFile, outputFile);
        } catch (IOException e) {
            System.out.println("Whoops! There was an error writing to the file.");
            System.exit(0);
        }
    }

    //Method for reading file into list
    public static ArrayList<String> readListFromFile(File source) throws FileNotFoundException {
        //create scanner
        Scanner fileReader = new Scanner(source);
        //loop through list while hasNextLine scanning each line and adding to ArrayList<String>
        ArrayList<String> listFromFile = new ArrayList<>();
        while(fileReader.hasNextLine()) {
            listFromFile.add(fileReader.nextLine());
        }
        return listFromFile;
    }



    //Method for printing list
    public static void printList(ArrayList<String> list) {
        for(String s: list) {
            System.out.println(s);
        }
    }


    //Method for adding line numbers
    public static void addLineNumbers(ArrayList<String> list) {
        //loop through list for each item
        int lineCount = 1;
        for(String s: list) {
            //add lineCount to the beginning of each item
            s = lineCount + ". " + s;
            //sets lineCount - 1 because line "1" is index 0 in the ArrayList
            list.set(lineCount - 1, s);
            lineCount++;
        }
    }

     //method to capitalize
      public static void capsShortLines(ArrayList<String> list) {
        //loop through list for each item
        int count = 0;
        for(String s: list) {
            //if item is < 40 characters long
            if(s.length() < MAX_LINE_LENGTH_FOR_CAPS) {
                //toUpperCase item
                s = s.toUpperCase();
                list.set(count, s);
            }
            count++;
        }
    }

    //method for adding '!'
    public static void addExclamationPoints(ArrayList<String> list) {
        //loop through list
        int count = 0;
        for(String s: list) {
            //if line contains "Wonderful world"
            //starts from index 0 checking 15 char long substring (length of wonderful world) ending
            //when i = length-15, because that is the last possible 15 char substring in the String
            for(int i = 0; i < s.length() - (WONDERFUL_WORLD + 1); i++) {
                //If the substring is "Wonderful World"
                if (s.substring(i, i + WONDERFUL_WORLD).equalsIgnoreCase("wonderful world")) {
                    //Uses string builder to insert "!" after wonderful world at the end of the
                    //substring that equals "wonderful world"
                    StringBuilder sB = new StringBuilder(s);
                    sB.insert(i + WONDERFUL_WORLD, "!");
                    s = sB.toString();
                    list.set(count, s);
                }
            }
            count++;
        }
    }


    //method to white-out random line
    public static void blankRandomLine(ArrayList<String> list) {
        //generate random number between 0 and last index in list
        Random rand = new Random();
        int randomLine = rand.nextInt(0, list.size());
        //change that index to blank like
        list.set(randomLine, "");
    }

    //method to write list to file
    public static void writeListToFile(ArrayList<String> list, File outputFile) throws IOException {
        FileWriter fw = new FileWriter(outputFile);
        //appends every string in the list to the file, then appends new line character
        for(String s: list) {
            fw.append(s);
            fw.append("\n");
        }
        fw.close();
        System.out.println("\nFile saved successfully.");
    }

    //gets user to enter name of output file
    public static File getOutputFile(){
        String outputFilePath = Input.getString("Enter a name and extension for your " +
                "new file: ");
        File outputFile = new File(outputFilePath);
        //If the user entered a file name that already exists
        if(outputFile.exists()) {
            //asks user if they want to overwrite
            String choice = Input.getString(outputFilePath + " already exists. " +
                    "Would you like to replace it? Y or N: ");
            //If user enters anything other than "Y" or "N", they get stuck in a loop until they
            //enter valid input
            while (!(choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("n"))) {
                choice = Input.getString("Replace " + outputFilePath + "? Y or N: ");
            }
            //if they want to overwrite, return the outputFile
            if(choice.equalsIgnoreCase("y")) {
                return outputFile;
            } else {
                //If they dont want to overwrite, recursive method call to ask for new file name
                return getOutputFile();
            }
        }
        //if file doesn't already exist then it just gets returned
        return outputFile;
    }
}
