package ca.ewanbaxter.main;

import ca.ewanbaxter.character.*;
import ca.ewanbaxter.character.GameCharacter;
import ca.ewanbaxter.util.Help;
import ca.ewanbaxter.util.Input;
import ca.ewanbaxter.util.Output;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Main {

    //Program starts here
    public static void main(String[] args) {
        System.out.println("ARENA COMBAT SIMULATOR 2022");
        int choice = 0;
        //ask user to start new game or load save file
        while(choice != 1 && choice != 2) {
            Output.listOptions("Choose an option: ", new String[]{"New Game", "Load Game"});
            choice = Input.getInt(">");
            if(choice != 1 && choice != 2) {
                System.out.println("\nInvalid Input.\n");
            }
        }

        //loading or new game based on user input
        if(choice == 1) {
            newGame();
        } else {
            try {
                loadGame();
            } catch (Exception e) {
                System.out.println("Error loading game.");
            }
        }
    }

    //starting a new game
    static void newGame() {
        //prints out game rules
        Help.gameExplanation();

        //gets player name
        String name = Input.getString("\nEnter a name for your character: ");
        System.out.println();
        //info about each class
        Help.fighterHelp();
        System.out.println();
        Help.monkHelp();
        //gets user to choose class
        int classChoice = CustomizeCharacter.chooseClass();

        //user chooses weapon and armor types
        boolean heavyWep = CustomizeCharacter.wepChoice();
        boolean heavyArmor = CustomizeCharacter.armorChoice();

        //player has to be initialized, but default player is overwritten by user input player
        GameCharacter player =
                new Fighter("default", true, true, false);

        switch (classChoice) {
            case 1 -> player = new Fighter(name, heavyWep, heavyArmor, true);
            case 2 -> player = new Monk(name, heavyWep, heavyArmor);
        }

        //creates game object with player character
        Game game = new Game(player);
        //intro
        System.out.printf("\nWelcome one and all to the Arena! Today we have an exciting match " +
                "for you. Our reigning champion, %s, has a new mysterious challenger, %s! " +
                "Let's get right into the action.\n\n",
                game.getEnemy().getName(), game.getPlayer().getName());
        //enters game combat loop
        game.combatLoop();
        //after combat loop is over, game ending is called
        game.ending();
    }

    //if loading game
    static void loadGame() throws IOException, ClassNotFoundException {
        //https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/FileInputStream.html
        //https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/ObjectInputStream.html
        //gets the file name
        String savePath = Input.getString("\nEnter the name of your save: ");
        File saveFile = new File(savePath + ".txt");
        FileInputStream fi = new FileInputStream(saveFile);
        ObjectInputStream oi = new ObjectInputStream(fi);
        //reads the game object from file and recreates it
        Game game = (Game) oi.readObject();

        oi.close();
        fi.close();

        //calls loadedGame first to check if game was saved in the middle of a round
        game.loadedGame();
        game.combatLoop();
    }
}
