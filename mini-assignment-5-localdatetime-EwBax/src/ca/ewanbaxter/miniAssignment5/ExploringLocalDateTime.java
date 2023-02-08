package ca.ewanbaxter.miniAssignment5;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ExploringLocalDateTime {
    //Used for the user time zone, assuming they are in ADT zone
    public static final ZoneId ATLANTIC_DAYLIGHT_TIME = ZoneId.of("UTC-03:00");

    public static void main(String[] args) {
        /* Stores time zone options for user to select, with name of zone as one value and UTC offset
        as second value, UTC offset will be used for creating zoneID. New zones can be added without
        having to alter any other lines of code */
        String[][] timeZones = new String[][]{{"Eastern Daylight Time", "UTC-04:00"},
                {"Pacific Daylight Time", "UTC-07:00"},
                {"Australian Eastern Time", "UTC+10:00"}};

        //ZoneId has to be initialized to something, but will be reassigned by the user
        ZoneId timeZone = null;
        //Looping until user enters valid input
        while(timeZone == null) {
            try {
                timeZone = chooseTimeZone(timeZones);
            } catch (Exception e) {
                System.out.println(e.getMessage() + "\n");
            }
        }
        //for spacing & formatting
        System.out.println();

        /* List of formats for user to choose from. Additional formats can be added here without
        having to alter other lines of code */
        String[] dateTimeFormats = new String[]{"dd-MM-yyyy HH:mm:ss", "MM-dd-yyyy HH:mm:ss",
                "yyyy-MM-dd HH:mm:ss", "MMMM d, yyyy HH:mm:ss"};

        DateTimeFormatter formatChoice = null;
        while(formatChoice == null) {
            try{
                formatChoice = chooseDateTimeFormat(dateTimeFormats);
            } catch (Exception e) {
                System.out.println(e.getMessage() + "\n");
            }
        }
        System.out.println();

        /* gets user input for time (assuming ADT) then converts to selected zone,
        then formats and returns formatted string DateTime */
        String convertedTime = null;
        while(convertedTime == null) {
            try {
                convertedTime = getDateTime(timeZone, formatChoice);
            } catch (DateTimeParseException e) {
                System.out.println("\n" + e.getMessage() + "\n");
            }
        }
        System.out.println();

        System.out.println("Your date time converted to " + timeZone + " is: " + convertedTime);
    }

    //method for getting int input
    public static int getInt() throws InputMismatchException {
        Scanner terminal = new Scanner(System.in);
        int input;
        try {
            input = terminal.nextInt();
        } catch (InputMismatchException e) {
            //throws new exception with error message, and clears scanner token
            throw new InputMismatchException(
                    "\n\"" + terminal.nextLine() + "\" is not an integer.");
        }
        return input;
    }

    public static ZoneId chooseTimeZone(String[][] timeZones) throws Exception {
        /* Looping through each value in timeZones[][] with nested loop, prints each time zone plus
        UTC offset */
        System.out.println("Please choose a time zone by entering the corresponding number");
        for(int i = 0; i < timeZones.length; i++) {
            System.out.print("[" + (i+1) + "] ");
            for(int j = 0; j < timeZones[i].length; j++) {
                System.out.print(timeZones[i][j]);
                if(j == 0) {
                    System.out.print(" (");
                } else {
                    System.out.print(")");
                }
            }
            if(i < timeZones.length - 1) {
                System.out.println(",");
            }
        }
        System.out.print(": ");
        //getting user input
        int choice;
        try {
           choice = getInt();
        } catch (InputMismatchException e) {
            //throwing exception back to main method to be handled
            throw e;
        }
        //If user enters int that is not in expected range, throws exception with error message
        if(!(choice >= 1 && choice <= timeZones.length)) {
            //throws new exception with error message for user
            throw new Exception("\nInput not in expected range.");
        } else {
            //choice - 1 because index of array starts at 0 but user input starts at 1
            //second array always returns index 1 because that is where the UTC offset is stored
            return ZoneId.of(timeZones[choice - 1][1]);
        }
    }

    public static DateTimeFormatter chooseDateTimeFormat(String[] formats) throws Exception {
        //Looping through and printing each format option
        System.out.println("Please choose a date time format by entering the corresponding number");
        for(int i = 0; i < formats.length; i++) {
            System.out.print("[" + (i+1) + "] " + formats[i]);
            if(i < formats.length - 1) {
                System.out.println(",");
            }
        }
        System.out.print(": ");

        int choice;
        try{
            choice = getInt();
        } catch (InputMismatchException e) {
            //throws exception back to main method to be handled there
            throw e;
        }
        //If user enters int that is not in expected range, throws exception with error message
        if(!(choice >= 1 && choice <= formats.length)) {
            //throws new exception with error message for user
            throw new Exception("\nInput not in expected range.");
        } else {
            //choice - 1 because index of array starts at 0 but user input starts at 1
            return DateTimeFormatter.ofPattern(formats[choice - 1]);
        }
    }

    public static String getDateTime(ZoneId timeZone, DateTimeFormatter formatChoice)
            throws DateTimeParseException {
        //getting user input for date time
        System.out.print("Please enter a date and time using the format you " +
                "selected, or a blank line for the current time and date: ");
        Scanner terminal = new Scanner(System.in);
        String input = terminal.nextLine();
        ZonedDateTime parsedDateTime;
        //checking if user entered blank line, and if so, getting current time in ADT
        if(input.isBlank()) {
            parsedDateTime = LocalDateTime.now().atZone(ATLANTIC_DAYLIGHT_TIME);
        } else {
            //Parsing user input using user selected format, and setting to ADT
            try {
                parsedDateTime = LocalDateTime.parse(input, formatChoice)
                        .atZone(ATLANTIC_DAYLIGHT_TIME);
            } catch (DateTimeParseException e) {
                /* throws exception back to main method so this method can
                be called again */
                throw e;
            }
        }
        ZonedDateTime convertedDateTime = parsedDateTime.withZoneSameInstant(timeZone);
        return convertedDateTime.format(formatChoice);
    }

}
