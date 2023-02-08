package ca.ewanbaxter.util;

import ca.ewanbaxter.main.Game;

import java.io.*;

//https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/FileOutputStream.html
//https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/ObjectOutputStream.html
public class Save {

    //method for saving the game
    public static void saveGame(Game game) {
        //gets user to enter save name
        File saveFile = getSaveFile();
        FileOutputStream fOut;
        ObjectOutputStream oOut;
        try {
            //writes to file
            fOut = new FileOutputStream(saveFile);
            oOut = new ObjectOutputStream(fOut);
            oOut.writeObject(game);
            oOut.close();
            fOut.close();
        } catch (FileNotFoundException e) {
            System.out.println("That save file could not be found.");
        } catch (IOException e) {
            System.out.println("There was an error saving your game.");
        }
        System.out.println("Save successful.\n");
    }

    //getting user input for file name, checking if the file exists, and prompting if they want to overwrite
    static File getSaveFile() {
        String saveFileName = "";
        while(saveFileName.isBlank()) {
            saveFileName = Input.getString("Enter a name for your save file: ");
        }
        File saveFile = new File(saveFileName + ".txt");
        if(saveFile.exists()) {
            //asks user if they want to overwrite
            String choice = Input.getString(saveFileName + " already exists. " +
                    "Would you like to replace it? Y or N: ");
            //If user enters anything other than "Y" or "N", they get stuck in a loop until they
            //enter valid input
            while (!(choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("n"))) {
                choice = Input.getString("Replace " + saveFileName + "? Y or N: ");
            }
            //if they want to overwrite, return the outputFile
            if(choice.equalsIgnoreCase("y")) {
                return saveFile;
            } else {
                //If they don't want to overwrite, recursive method call to ask for new file name
                return getSaveFile();
            }
        }
        //if file doesn't already exist then it just gets returned
        return saveFile;
    }

}
