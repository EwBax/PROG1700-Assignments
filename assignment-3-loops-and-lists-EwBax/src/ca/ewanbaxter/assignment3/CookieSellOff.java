package ca.ewanbaxter.assignment3;

import ca.ewanbaxter.util.Input;
import ca.ewanbaxter.util.OutputFormatting;

import java.util.ArrayList;

public class CookieSellOff {
    public static void main(String[] args) {
        //getting the number of participants
        int numParticipants = Input.getInt("Enter the number of participants in the " +
                "cookie sale: ");
        //catching if they enter less than one participant
        while (numParticipants < 1) {
            numParticipants = Input.getInt("There must be at least one participant. Enter " +
                    "the number of participants in the cookie sale: ");
        }
        OutputFormatting.horizontalRule();

        //2d arraylist to store both participant names and number of boxes of cookies sold
        ArrayList<ArrayList<String>> participantInfo = getParticipantInfo(numParticipants);

        //extracting the two arraylists from the 2d arraylist to manipulate them more easily
        ArrayList<String> participantNames = participantInfo.get(0);
        ArrayList<String> boxesSold = participantInfo.get(1);

        //method to determine average sold
        double averageSold = averageBoxesSold(boxesSold);
        System.out.println("The average number of boxes sold was " + averageSold);
        OutputFormatting.horizontalRule();

        //Method to evaluate and print awards won by each participant
        printAwards(participantNames, boxesSold);
    }

    public static ArrayList<ArrayList<String>> getParticipantInfo (int numParticipants) {

        ArrayList<String> participantNames = new ArrayList<>();
        //using String for this so both lists are the same type and can be stored in a list together
        ArrayList<String> boxesSold = new ArrayList<>();

        for(int i = 0; i < numParticipants; i++) {
            String name = Input.getString("Enter the name of participant #" + (i+1) + ": ");
            participantNames.add(name);
            //getting number of cookies as an int then converting to String
            int numCookies = Input.getInt("Enter the number of boxes of cookies sold by " +
                    participantNames.get(i) + ": ");
            //catching if they enter a negative number of boxes sold
            while(numCookies < 0) {
                System.out.println("\nNumber of boxes of cookies sold must be 0 or greater.");
                numCookies = Input.getInt("Enter the number of boxes of cookies sold by " +
                        participantNames.get(i) + ": ");
            }
            boxesSold.add(String.valueOf(numCookies));

            OutputFormatting.horizontalRule();
        }

        //creating the 2d ArrayList... or arraylist of arraylists
        ArrayList<ArrayList<String>> participantInfo = new ArrayList<>();
        //adding participantNames and boxesSold to participantInfo so they can both be returned
        participantInfo.add(participantNames);
        participantInfo.add(boxesSold);

        return participantInfo;
    }


    public static double averageBoxesSold (ArrayList<String> boxesSold) {
        //finds total boxes sold then divides by number of participants
        double totalSold = 0;
        for(String num: boxesSold) {
            totalSold += Double.parseDouble(num);
        }
        return totalSold / boxesSold.size();
    }


    public static void printAwards(ArrayList<String> participantNames, ArrayList<String> boxesSold) {
        System.out.println("Participant\t\tPrize Won");
        OutputFormatting.horizontalRule();
        //finding average boxes sold for evaluating awards
        double averageSold = averageBoxesSold(boxesSold);
        //most boxes sold for evaluating awards
        int mostSold = getMostSold(boxesSold);
        //loops through and checks each participant and how many they sold
        for(int i = 0; i < participantNames.size(); i++) {
            String award = "";
            int numSold = Integer.parseInt(boxesSold.get(i));
            //checks awards in order of how many cookies sold from highest to lowest possible
            //if they were the top seller
            if(didEveryoneSellTheSame(boxesSold)) {
                award = "Everybody sold the same amount! SuperSeller + Leftover Cookies!";
            } else if(numSold == mostSold) {
                if(howManyTopSellers(boxesSold) > 1) {
                    award = "Tied for top seller! Entered into a draw for a trip to Yarmouth!";
                } else {
                    award = "Trip to Yarmouth!";
                }
            } else if(numSold > averageSold) { //You must sell above average for this award
                //if they sold above average
                award = "Super Seller!";
            } else if(numSold > 0) {
                //if they sold at least one
                award = "Leftover Cookies";
            }
            System.out.println(participantNames.get(i) + "\t\t\t- " + award);
        }
    }


    public static int getMostSold(ArrayList<String> boxesSold) {
        int mostSold = 0;
        //loops through list of boxes sold keeping track of highest value found
        for(String sold: boxesSold) {
            int numSold = Integer.parseInt(sold);
            if(numSold > mostSold) {
                mostSold = numSold;
            }
        }
        return mostSold;
    }


    public static int howManyTopSellers(ArrayList<String> boxesSold) {
        int counter = 0;
        int mostSold = getMostSold(boxesSold);
        for(String sold: boxesSold) {
            if(Integer.parseInt(sold) == mostSold) {
                counter++;
            }
        }
        return counter;
    }


    public static boolean didEveryoneSellTheSame(ArrayList<String> boxesSold) {
        int previousNumber = Integer.parseInt(boxesSold.get(0));
        for(int i = 1; i < boxesSold.size(); i++) {
            if(Integer.parseInt(boxesSold.get(i)) != previousNumber) {
                return false;
            }
        }
        return true;
    }
}
