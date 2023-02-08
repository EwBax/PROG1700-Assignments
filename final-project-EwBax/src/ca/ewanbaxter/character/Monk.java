package ca.ewanbaxter.character;

import ca.ewanbaxter.main.Game;
import ca.ewanbaxter.util.Dice;
import ca.ewanbaxter.util.Input;
import ca.ewanbaxter.util.Output;
import ca.ewanbaxter.util.Save;

//https://docs.oracle.com/javase/tutorial/jndi/objects/serial.html
public class Monk implements GameCharacter{

    //magic numbers
    final int STARTING_STUN_COUNT = 5;
    final int STARTING_HIT_POINTS = 40;
    final int STARTING_ARMOR_CLASS = 14;

    //class variables/attributes
    private final String name;
    private final String[] actions = new String[] {"Attack", "Get Stats", "Save Game"};
    private final Dice damageDie;
    final int attacksPerTurn;

    private int armorClass = STARTING_ARMOR_CLASS;
    private int hitPoints = STARTING_HIT_POINTS;
    private int initiative = 0;
    private boolean stunned = false;
    private int stunCounter = STARTING_STUN_COUNT;

    //constructor method
    public Monk(String name, boolean heavyWep, boolean heavyArmor) {
        this.name = name;
        if(heavyWep) {
            this.damageDie = new Dice(12);
            this.attacksPerTurn = 1;
        } else {
            damageDie = new Dice(6);
            this.attacksPerTurn = 2;
        }
        if(heavyArmor) {
            this.armorClass += 1;
            this.hitPoints -= 10;
        }
    }

    //method to check if attack hits or not
    public boolean attackHit(GameCharacter target) {
        int attackRoll = Dice.rollD20();
        System.out.printf("\n%s rolls a D20 to attack...%d\n", this.name, attackRoll);
        return attackRoll >= target.getArmorClass();
    }

    //method for modifying hitPoints when attacked
    public void takeDamage(int damage) {
        this.hitPoints -= damage;
    }

    //method for rolling initiative
    public void rollInitiative() {
        this.initiative = Dice.rollD20();
        System.out.printf("\n%s rolls a D20 for initiative...%d\n", this.name, this.initiative);
    }

    //getter methods
    public String getName() {
        return this.name;
    }

    public int getHitPoints() {
        return this.hitPoints;
    }

    public int getArmorClass() {
        return this.armorClass;
    }

    public int getInitiative() {
        return this.initiative;
    }

    public void getStats() {
        System.out.println("\nName: " + this.name);
        System.out.println("Hit Points: " + this.hitPoints);
        System.out.println("Armor Class: " + this.armorClass);
        System.out.println("Stunning strikes remaining: " + this.stunCounter);
    }

    //setter methods
    public void setStunned(boolean stunned) {
        this.stunned = stunned;
    }

    //method for combat turn
    public void turn(Game game) {
        if(!this.stunned) {
            this.chooseAction(game);
        } else {
            this.stunned = false;
        }
    }

    //method prompting user to choose action
    private void chooseAction(Game game) {
        int choice = 0;
        while(choice < 1 || choice > (this.actions.length + 1)) {
            Output.listOptions("Choose " + this.name + "'s action: ", actions);
            choice = Input.getInt(">");
            if(choice < 1 || choice > (this.actions.length + 1)) {
                System.out.println("Invalid input.");
            }
        }
        this.doAction(game, actions[choice-1]);
    }

    public void doAction(Game game, String action) {
        GameCharacter enemy = game.getEnemy();
        if(action.equalsIgnoreCase("attack")) {
            this.attack(enemy);
        } else if(action.equalsIgnoreCase("save game")) {
            Save.saveGame(game);
            this.chooseAction(game);
        } else if(action.equalsIgnoreCase("get stats")) {
            this.getStats();
            this.chooseAction(game);
        }
    }

    //method for attacking target
    void attack(GameCharacter target) {
        //uses a for loop for multiple attacks
        for(int i = 0; i < attacksPerTurn; i++) {
            if (this.attackHit(target)) {
                //monk attack damage is 1d12
                int damage = this.damageDie.rollDice();
                //message saying the attack hit and how much damage
                System.out.printf("%s strikes %s with their fists for %d damage!\n",
                        this.name, target.getName(), damage);
                target.takeDamage(damage);
                if (this.stunCounter > 0) {
                    this.stunningStrike(target);
                }
            } else {
                //message saying the attack missed
                System.out.printf("%s swings their fist at %s, but the attack misses.\n",
                        this.name, target.getName());
            }
        }
    }

    //method for stunning strike
    void stunningStrike(GameCharacter target) {
        int choice = 0;
        while(choice != 1 && choice != 2) {
            choice = Input.getInt("Would you like to use stunning strike?\n" +
                    "[1] Yes\n" +
                    "[2] No: ");
            if(choice != 1 && choice != 2) {
                System.out.println("\nInvalid input.\n");
            }
        }
        //if they want to stunning strike
        if(choice == 1) {
            if(this.attackHit(target)) {
                int damage = Dice.rollD12();
                target.takeDamage(damage);
                System.out.printf("%s strikes %s again, dealing %d damage and stunning them!\n",
                        this.name, target.getName(), damage);
                //sets target to stunned
                target.setStunned(true);
            } else {
                System.out.printf("%s attemps to stun %s, but misses the strike.\n",
                        this.name, target.getName());
            }
            this.stunCounter--;
        }
    }
}
