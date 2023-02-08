package ca.ewanbaxter.character;

import ca.ewanbaxter.main.Game;
import ca.ewanbaxter.util.Dice;
import ca.ewanbaxter.util.Input;
import ca.ewanbaxter.util.Output;
import ca.ewanbaxter.util.Save;

import java.io.Serializable;

//https://docs.oracle.com/javase/tutorial/jndi/objects/serial.html
public class Fighter implements GameCharacter, Serializable {

    //magic numbers
    final int STARTING_SECOND_WIND_COUNT = 2;
    final int STARTING_HIT_POINTS = 40;
    final int STARTING_ARMOR_CLASS = 14;

    //class variables/attributes
    private final String name;
    private final boolean playerCharacter;
    private final String[] actions = new String[] {"Attack", "Second Wind", "Get Stats", "Save Game"};
    private final Dice damageDie;
    final int attacksPerTurn;

    private int armorClass = STARTING_ARMOR_CLASS;
    private int hitPoints = STARTING_HIT_POINTS;
    private int initiative = 0;
    private boolean stunned = false;
    private int secondWindCounter = STARTING_SECOND_WIND_COUNT;

    //Constructor method
    public Fighter(String name, boolean heavyWep, boolean heavyArmor, boolean playerCharacter) {
        this.name = name;
        this.playerCharacter = playerCharacter;
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
        System.out.println("Remaining uses of Second Wind: " + this.secondWindCounter);
    }

    //setter methods
    public void setStunned(boolean stunned) {
        this.stunned = stunned;
    }

    //method for combat turn
    public void turn(Game game) {
        //if this is not a player character
        if(!this.playerCharacter) {
            //call enemy turn method to avoid player controlling enemy
            this.computerTurn(game.getPlayer());
        } else {
            //this is a player character, so player controls the turn
            if(!this.stunned) {
                this.chooseAction(game);
            } else {
                this.stunned = false;
            }
        }
    }

    //method prompting user to choose action
    private void chooseAction(Game game) {
        int choice = 0;
        while(choice < 1 || choice > (this.actions.length + 1)) {
            Output.listOptions("\nChoose " + this.name + "'s action: ", actions);
            choice = Input.getInt(">");
            if(choice < 1 || choice > (this.actions.length + 1)) {
                System.out.println("Invalid input.");
            }
        }
        this.doAction(game, actions[choice-1]);
    }

    //method for executing action choice
    public void doAction(Game game, String action) {
        GameCharacter enemy = game.getEnemy();
        if(action.equalsIgnoreCase("attack")) {
            this.attack(enemy);
        } else if(action.equalsIgnoreCase("second wind")) {
            if(this.secondWindCounter > 0) {
                this.secondWind();
            } else {
                System.out.printf("%s has no remaining uses of second wind.\n", this.name);
                this.chooseAction(game);
            }
        } else if(action.equalsIgnoreCase("save game")) {
            Save.saveGame(game);
            this.chooseAction(game);
        } else if(action.equalsIgnoreCase("get stats")) {
            this.getStats();
            this.chooseAction(game);
        }
    }

    //Non interface methods

    //method for attacking target
    void attack(GameCharacter target) {
        //uses a for loop for multiple attacks
        for(int i = 0; i < attacksPerTurn; i++) {
            //if the attack hits
            if(this.attackHit(target)) {
                //Fighter attack damage is D10
                int damage = this.damageDie.rollDice();
                //message saying the attack hit and how much damage
                System.out.printf("%s swings their sword at %s and hits them " +
                        "for %d damage!\n", this.getName(), target.getName(), damage);
                //applying the damage to the target
                target.takeDamage(damage);
            } else {
                //message saying the attack missed
                System.out.printf("%s swings their sword at %s, but the " +
                        "attack misses.\n", this.getName(), target.getName());
            }
        }
    }

    void computerTurn(GameCharacter player) {
        //checks if computer is stunned
        if(!this.stunned) {
            //if computer is below half heath, uses second wind
            if((this.hitPoints < (STARTING_HIT_POINTS / 2))
                    && (this.secondWindCounter > 0)) {
                this.secondWind();
            } else {
                //otherwise, attacks player
                this.attack(player);
            }
        } else {
            //if the computer is stunned, no longer stunned and turn ends
            this.stunned = false;
        }
    }


    //Method for using Second Wind ability
    void secondWind() {
        //secondWind heals for 1d12
        int healing = Dice.rollD12();
        this.hitPoints += healing;
        if(this.playerCharacter) {
            System.out.printf("Your second wind heals you for %d hit points.\n", healing);
        } else {
            System.out.printf("%s uses their Second Wind ablity!\n", this.name);
        }
        this.secondWindCounter--;
    }

}
