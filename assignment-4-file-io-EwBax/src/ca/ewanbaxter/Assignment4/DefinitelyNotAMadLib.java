package ca.ewanbaxter.Assignment4;

import ca.ewanbaxter.util.Input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DefinitelyNotAMadLib {
    public static void main(String[] args) {
        System.out.println("Welcome to \"For copyright reasons we cannot call it Mad Libs, " +
                "but it's basically Mad Libs\"!\n");
        System.out.println("You will be prompted to choose words that will be placed into a " +
                "story.");
        System.out.println("The story will be revealed once all words have been chosen.\n");
        //call method to read choices into arrayList
        ArrayList<String[]> choices = null;
        try {
            choices = splitList(getListFromFile("the_choices_file.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: The file containing the list of choices could not " +
                    "be found.");
            System.exit(0);
        }

        //call method to get user to choose, returns ArrayList of their choices
        ArrayList<String> madLibs = getMadLibs(choices);
        //call method to read story into ArrayList
        ArrayList<String> story = null;
        try {
            story = getListFromFile("the_story_file.txt");
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: The file containing the story could not be found.");
            System.exit(0);
        }
        //call method to replace mad libs
        replaceMadLibs(story, madLibs);
        //method to print new story
        printStory(story);
    }

    //Method to read choices into arrayList
    public static ArrayList<String> getListFromFile(String filePath) throws FileNotFoundException {
        File choicesSource = new File(filePath);
        Scanner fileReader = new Scanner(choicesSource);
        ArrayList<String> choices = new ArrayList<>();
        //reads each line of file, and adds it to ArrayList
        while(fileReader.hasNextLine()) {
            choices.add(fileReader.nextLine());
        }
        return choices;
    }

    public static ArrayList<String[]> splitList(ArrayList<String> originalList) {
        ArrayList<String[]> newList = new ArrayList<>();
        //splits each String in list on commas, then stores each array in list
        for(String s: originalList) {
            newList.add(s.split(","));
        }
        return newList;
    }

    //Method for user to choose words
    public static ArrayList<String> getMadLibs(ArrayList<String[]> choices) {
        //list to hold mad libs the user chooses
        ArrayList<String> madLibs = new ArrayList<>();
        //loops through each array in the list of choices... each array is a new mad lib to choose
        for(String[] array: choices) {
            //Printing the prompt/choice type first
            System.out.println("Please choose " + array[0] + ":");
            //loops through and prints each choice in the array
            for(int i = 1; i < array.length; i++) {
                    System.out.println("[" + i + "]" + array[i]);
            }
            int choice;
            //prompts user to enter choice. Method called handles non integer inputs
            choice = Input.getInt("Enter choice (1-" + (array.length - 1) + "): ");
            //if user enters invalid integer
            while(choice < 1 || choice >= array.length) {
                choice = Input.getInt("Invalid input. Enter choice " +
                        "(1-" + (array.length - 1) + "): ");
            }
            /* conveniently the choices start at index 1 instead of 0, so the numbers assigned
            for user choices match the index number in array */
            madLibs.add(array[choice].toUpperCase());
            System.out.println();
        }
        return madLibs;
    }

    //method to replace mad libs
        //use String.replace("_" + i + "_")
    public static void replaceMadLibs(ArrayList<String> story, ArrayList<String> madLibs) {
        //loops through each line of the story
        for(int i = 0; i < story.size(); i++) {
            //loops through each possible mad lib number for each line
            for(int j = 0; j < madLibs.size(); j++) {
                //variable for what is being replaced... eg: first iteration through will be "_1_",
                // then "_2_" and so on
                String replace = "_" + (j + 1) + "_";
                //checks if line contains each mad lib replacement number, and replaces with
                // corresponding mad lib
                String newVersion = story.get(i).replaceAll(replace, madLibs.get(j));
                //sets line of story to version with mad lib added
                story.set(i, newVersion);
            }
        }
    }

    //simple loop through the list and print each String
    public static void printStory(ArrayList<String> story) {
        System.out.println("Your completed story is:\n");
        for(String line: story) {
            System.out.println(line);
        }
    }
}
