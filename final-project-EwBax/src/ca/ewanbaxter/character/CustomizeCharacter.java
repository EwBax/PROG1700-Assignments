package ca.ewanbaxter.character;

import ca.ewanbaxter.util.Input;
import ca.ewanbaxter.util.Output;

public class CustomizeCharacter {

    //promp user to choose class
    public static int chooseClass() {
        int classChoice = 0;
        while(classChoice < 1 || classChoice > 2) {
            Output.listOptions("Which class will you play as?", new String[] {"Fighter", "Monk"});
            classChoice = Input.getInt(">");
            if(classChoice < 1 || classChoice > 2) {
                System.out.println("\nInvalid Input.\n");
            }
        }
        return classChoice;
    }

    //prompt user to choose weapon type
    public static boolean wepChoice() {
        int wepChoice = 0;
        while(wepChoice < 1 || wepChoice > 2) {
            Output.listOptions("Which armor will you choose?", new String[]
                    {"Heavy (1d10 damage, 1 attack per turn)", "Light (1d6 damage, 2 attacks per turn)"});
            wepChoice = Input.getInt(">");
            if(wepChoice < 1 || wepChoice > 2) {
                System.out.println("\nInvalid Input.\n");
            }
        }
        return wepChoice == 1;
    }

    //Prompt user to choose armor type
    public static boolean armorChoice() {
        int armorChoice = 0;
        while (armorChoice < 1 || armorChoice > 2) {
            Output.listOptions("Which weapon will you choose?", new String[]
                    {"Heavy (15 AC, 30 HP)", "Light (14 AC, 40 HP)"});
            armorChoice = Input.getInt(">");
            if (armorChoice < 1 || armorChoice > 2) {
                System.out.println("\nInvalid Input.\n");
            }
        }
        return armorChoice == 1;
    }

}
