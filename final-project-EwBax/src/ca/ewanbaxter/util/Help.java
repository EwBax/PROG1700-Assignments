package ca.ewanbaxter.util;

public class Help {

    //explanation of how game works
    public static void gameExplanation() {
        System.out.println("\nWelcome to Arena Combat Simulator 2022!\n");
        System.out.println("This game is based on a (very) simplified version of Dungeons " +
                "and Dragons 5th edition.\n");
        System.out.println("The game uses dice rolls to determine things like who attacks first," +
                " if an attack hits, and how much damage.");
        System.out.println("Dice will be referred to using the notation \"nDi\" where i " +
                "represents how many sides the dice has, and n represents the number of dice " +
                "rolled");
        System.out.println("The player will choose a class, either Fighter or Monk, with each class" +
                " having a special ability that can be used in battle.\n");
        System.out.println("Combat is turn based and fought against an AI controlled Fighter " +
                "character. At the beginning of combat, each combatant will roll initiative, and " +
                "the combatant with the higher roll will go first each round.\n");
        System.out.println("You can either attack or use a special ability each turn - you " +
                "can also save the game on your turn and continue from that point when you load " +
                "the game again.\n");
        System.out.println("Characters have the following stats:");
        System.out.println("Hit Points (HP): the character's health pool. When hit points drops to 0, " +
                "the character is defeated.");
        System.out.println("Armor Class (AC): when a character attacks a target, the attack roll must " +
                "be greater than or equal to the target's armor class for the attack to succeed.\n");
        System.out.println("Combat rounds will continue until either the player or the AI reaches " +
                "0 hit points, at which point they are defeated.");
    }

    //explanation of Fighter
    public static void fighterHelp() {
        System.out.println("The Fighter is a beefy melee class with the ability \"Second Wind\", " +
                "which heals the user for 1d12 hit points.");
    }

    //explanation of Monk
    public static void monkHelp() {
        System.out.println("The Monk is a more agile melee class with the ability " +
                "\"Stunning Strike\", which can be used after a successful attack to attempt to " +
                "attack again and stun the target. If a target is stunned, they cannot take an " +
                "action on their next turn.");
    }
}
