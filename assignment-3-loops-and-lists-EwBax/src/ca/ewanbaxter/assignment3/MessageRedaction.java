package ca.ewanbaxter.assignment3;

import ca.ewanbaxter.util.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MessageRedaction {

    static final String REDACTED_LETTER = "_";

    public static void main(String[] args) {

        String input = " ";
        while(!input.equalsIgnoreCase("quit")) {
            //Getting user input for phrase to be redacted, or quit
            input = Input.getString("Enter a phrase, or enter \"quit\" to exit the program: ");

            //If the user did not enter quit, does not run
            if(!input.equalsIgnoreCase("quit")) {
                OutputFormatting.horizontalRule();

                //Gets & parses input for list of letters than remove duplicates
                ArrayList<Character> redactedLetters = removeDuplicates(getListOfLetters());
                OutputFormatting.horizontalRule();

                //actually redacting the letters
                String redacted = redactLetters(redactedLetters, input);
                System.out.println("Redacted phrase: " + redacted);
                OutputFormatting.horizontalRule();
            }
        }
        //If they enter quit the loop is broken and the program exits
        System.out.println("Quitting...");
    }

    public static ArrayList<Character> getListOfLetters() {
        String input = Input.getString("Enter a list of letters to be redacted, separated " +
                "by commas: ");
        //list to hold all the letters
        ArrayList<Character> letters = new ArrayList<>();

        /* tracks if a comma or letter came previously (not counting spaces).
        Initializing to ',' allows spaces at the beginning before the first letter */
        char previousCharacter = ',';
        //looping through and checking each character in the string
        for(int i = 0; i < input.length(); i++) {
            char nextChar = input.charAt(i);
            if(previousCharacter == ',') {
                //if the last letter was a comma then we expect a letter next
                if(isLetter(nextChar)) {
                    /* converting to lowercase so case does not affect whether letters get
                    redacted */
                    letters.add(Character.toLowerCase(nextChar));
                    previousCharacter = nextChar;
                } else if(nextChar != ' ') {
                    //if not a letter the only other allowed character following a comma is a space
                    listOfLettersErrorMsg(i, input, "letter");
                }
            } else {    //if the previous character wasn't a comma it must be a letter,
                if(nextChar == ',') {
                    /* we expect a comma next, but we don't add the comma to the list of letters,
                    only set it as the previous character */
                    previousCharacter = nextChar;
                } else if (nextChar != ' ') {
                    //again the only other allowed character is a space
                    listOfLettersErrorMsg(i, input, "\",\"");
                }
            }
        }
        return letters;
    }

    //removes duplicate entries from the list
    public static ArrayList<Character> removeDuplicates(ArrayList<Character> list) {
        //https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/HashSet.html
        /* Hash sets do not allow duplicate values, so converting the list to a HashSet then back
        to a list removes any duplicate entries */
        Set<Character> noDuplicates = new HashSet<>(list);
        return new ArrayList<>(noDuplicates);
    }

    public static boolean isLetter(char c) {
        //Using ASCII values to check if the character is a letter
        return ((c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z'));
    }

    public static void listOfLettersErrorMsg(int index, String input, String expected) {
        /* error message for if the user breaks the format for the list of redacted letters.
        Prints out the index of the incorrect character and says what the expected character was */
        System.out.println("Error: \"" + input + "\" expected " + expected + " at index " + index);
        System.exit(0);
    }


    public static String redactLetters(ArrayList<Character> letters, String input) {
        /* https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/StringBuilder.html#replace(int,int,java.lang.String)
        converting String to StringBuilder so that I can use the StringBuilder method "replace"
        to replace the redacted letters with '_' */
        StringBuilder redacted = new StringBuilder(input);
        //keeps track of how many letters get redacted
        int counter = 0;
        //looping through every character in phrase to be redacted
        for(int i = 0; i < redacted.length(); i++) {
            //converting current letter to lowercase
            char currentLetter = Character.toLowerCase(redacted.charAt(i));
            if(letters.contains(currentLetter)) {
                redacted.replace(i, i + 1, REDACTED_LETTER);
                counter++;
            }
        }
        System.out.println("Number of letters redacted: " + counter);
        return  redacted.toString();
    }
}
