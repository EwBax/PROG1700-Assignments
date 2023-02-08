package ca.ewanbaxter.assignment3;

import ca.ewanbaxter.util.*;

import java.util.ArrayList;

public class TimeSheet {

    //Class final variables
    static final int WORK_DAYS_IN_WEEK = 5;
    static final double MIN_HOURS = 7.0;

    public static void main(String[] args) {
        //calls method to get/parse user input for hours
        double[] hours = getHours();
        OutputFormatting.horizontalRule();   //for spacing

        //finds days with most hours worked and prints them nicely formatted
        printMostHours(hours);
        OutputFormatting.horizontalRule();

        System.out.println("The total number of hours worked was: " + getTotalHours(hours));
        System.out.println("The average number of hours worked was: " + getAverageHours(hours));
        OutputFormatting.horizontalRule();

        //Finds days slacked off and prints them
        daysSlackedOff(hours);
    }

    private static double[] getHours() {
        double[] hours = new double[WORK_DAYS_IN_WEEK];
        int dayNumber;
        for(int i = 0; i < WORK_DAYS_IN_WEEK; i++) {
            //i starts at index 0, but day starts at 1, so dayNumber = i+1;
            dayNumber = i + 1;
            //getting double input for hours worked on dayNumber and storing in index i
            hours[i] = Input.getDouble("Enter hours worked on Day #" + (dayNumber) + ": ");
            while(hours[i] < 0) {   //checking if they entered a negative double
                //If a negative was entered it sends an error and prompts them for another value
                hours[i] = Input.getDouble("Hours worked cannot be negative. Enter hours " +
                        "worked on Day #" + (dayNumber) + ": ");
            }
        }
        return hours;
    }

    /* Finds the highest value in the array by checking each index and storing the highest
    value found */
    private static double findMostHours(double[] hours) {
        double mostHours = Double.MIN_VALUE;
        for(double d: hours) {
            if(d > mostHours) {
                mostHours = d;
            }
        }
        return mostHours;
    }

    //Prints the days with the most hours worked
    public static void printMostHours(double[] hours) {
        //finds most hours
        double mostHours = findMostHours(hours);
        //calls method to find which days this many hours were worked
        ArrayList<Integer> days = findDays(mostHours, hours);
        //Prints out first day with the highest hours worked
        System.out.println("The most hours worked was on: ");
        System.out.print("Day #" + days.get(0));

        /* Checks if there was more than one day with most hours worked and prints the other
        days if so */
        if(days.size() > 1) {
            for (int i = 1; i < days.size(); i++){
                if(days.size() > 2){
                    //only prints comma if there are more than 2 days. Prints oxford comma as well
                    System.out.print(",");
                }
                System.out.print(" ");  //prints space between days
                if(i == (days.size() - 1)) {
                    //checks if this is the last day in the list and prints 'and' if it is
                    System.out.print("and ");
                }
                System.out.print("day #" + days.get(i));    //prints day#
            }
        }
        //prints most hours to finish the output
        System.out.println(" when you worked " + mostHours + " hours.");
    }

    //Checks each value in list to see if it equals most hours, stores day number as index + 1 if so
    public static ArrayList<Integer> findDays(double d, double[] a) {
        ArrayList<Integer> indexes = new ArrayList<>();

        for(int i = 0; i < a.length; i++) {
            if(a[i] == d) {
                int dayNumber = i + 1;
                indexes.add(dayNumber);
            }
        }

        return indexes;
    }

    //adds up all the values in hours array
    public static double getTotalHours(double[] hours) {
        double total = 0;
        for(double d: hours) {
            total += d;
        }

        return total;
    }

    /* calculates average using getTotalHours to find total, then dividing by
     WORK_DAYS_IN_WEEK (# of days) */
    public static double getAverageHours(double[] hours) {
        double totalHours = getTotalHours(hours);
        return totalHours / WORK_DAYS_IN_WEEK;
    }

    //finds and prints every day with less than 7 hours worked
    public static void daysSlackedOff(double[] hours) {
        //list to store days
        ArrayList<Integer> days = new ArrayList<>();

        //looping through and checking each value, if < 7 stores day number in list as index + 1
        for(int i = 0; i < hours.length; i++) {
            if(hours[i] < MIN_HOURS) {
                int dayNumber = i + 1;
                days.add(dayNumber);
            }
        }

        //If there are no days <7 hours
        if(days.size() == 0) {
            System.out.println("You worked at least 7 hours every day. Good job not slacking!");
        } else {
            //otherwise, prints off every day <7 hours from list
            System.out.println("Days you slacked off (i.e. worked less than 7 hours):");
            for(Integer day: days) {
                System.out.println("Day #" + day + " " + hours[day-1] + " hours");
            }
        }

    }

}
