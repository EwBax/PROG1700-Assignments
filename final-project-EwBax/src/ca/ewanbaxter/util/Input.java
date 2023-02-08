package ca.ewanbaxter.util;

import java.util.Scanner;

public class Input {

    public static String getString(String prompt) {
        Scanner terminal = new Scanner(System.in);
        System.out.print(prompt);
        return terminal.nextLine();
    }


    public static int getInt(String prompt) {
        int input = 0;
        Scanner terminal = new Scanner(System.in);
        boolean goodInput = false;
        while(!goodInput) {
            try {
                System.out.print(prompt);
                input = terminal.nextInt();
                goodInput = true;
            } catch (Exception e) {
                System.out.println("\nInvalid input.\n");
                terminal.nextLine();
            }
        }
        return input;
    }

}
