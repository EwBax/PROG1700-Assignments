package ca.ewanbaxter.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {

    public static double getDouble(String prompt) {
        Scanner terminal = new Scanner(System.in);

        //boolean to create infinite loop until user enters a valid double
        boolean goodInput = false;
        double d = 0;

        while(!goodInput) {
            try {
                System.out.print(prompt);
                d = terminal.nextDouble();
                /* if no exception is thrown from the previous line than the user must have
                entered a double */
                goodInput = true;
            } catch (InputMismatchException e) {
                /* If the user does not enter a double this catches the exception, spits out an
                error message, and loops back */
                String invalid = terminal.nextLine();
                System.out.println("Input Mismatch Exception: \"" + invalid + "\" is not a " +
                        "double.");
            }
        }
        return d;
    }

    public static int getInt(String prompt) {
        Scanner terminal = new Scanner(System.in);

        //boolean to create infinite loop until user enters a valid double
        boolean goodInput = false;
        int i = 0;

        while(!goodInput) {
            try {
                System.out.print(prompt);
                i = terminal.nextInt();
                /* if no exception is thrown from the previous line than the user must have
                entered an int */
                goodInput = true;
            } catch (InputMismatchException e) {
                /* If the user does not enter an int this catches the exception, spits out
                an error message, and loops back */
                String invalid = terminal.nextLine();
                System.out.println("Invalid input: \"" + invalid + "\" is not an " +
                        "integer.");
            }
        }
        return i;
    }

    public static String getString(String prompt){

        Scanner terminal = new Scanner(System.in);
        //initializing input as blank to enter the while loop
        String input = " ";
        //keeps looping if the user enters a blank string
        while(input.isBlank()) {
            System.out.print(prompt);
            input = terminal.nextLine();
        }
        return input;
    }

}
