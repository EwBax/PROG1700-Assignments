package ca.ewanbaxter.main;

import ca.ewanbaxter.character.GameCharacter;
import ca.ewanbaxter.character.Fighter;

import java.io.Serializable;

//https://docs.oracle.com/javase/tutorial/jndi/objects/serial.html
public class Game implements Serializable {

    //class variables

    private final GameCharacter player;
    private final GameCharacter enemy =
            new Fighter("George the Orc", false, true, false);
    private int round = 1;
    private boolean victorious;

    //constructor for game object
    public Game(GameCharacter player) {
        this.player = player;
    }

    //loop for main game combat
    void combatLoop() {
        //rolls initiative for both player and enemy to see who goes first
        this.player.rollInitiative();
        this.enemy.rollInitiative();
        boolean combatOver = false;
        while(!combatOver) {
            System.out.printf("\nROUND: %d\n", this.round);
            //if the player goes first
            if(this.player.getInitiative() >= this.enemy.getInitiative()) {
                //calls method for combat round, which plays out combat round and returns whether
                //combat is over or not
                combatOver = this.combatRound(this.player, this.enemy);
            } else {
                combatOver = this.combatRound(this.enemy, this.player);
            }
            this.round++;
        }
    }

    //this method represents a single round of combat
    boolean combatRound(GameCharacter first, GameCharacter second) {
        first.turn(this);
        boolean combatOver = this.isCombatOver();
        if(!combatOver) {
            second.turn(this);
            combatOver = this.isCombatOver();
        }
        return combatOver;
    }

    //this method checks if either player or enemy is at or below 0 hit points
    boolean isCombatOver() {
        //if enemy is dead
        if(this.enemy.getHitPoints() <= 0) {
            System.out.printf("And with that final blow, %s falls to the ground, and " +
                    "is defeated. You are victorious!\n", this.enemy.getName());
            this.victorious = true;
            return true;    //returns that combat is over
        } else if(this.player.getHitPoints() <= 0) {
            //if player is dead
            System.out.printf("And with that final blow, %s falls to the ground. You have been " +
                    "defeated!\n", this.player.getName());
            this.victorious = false;
            return true;
        } else {
            return false;   //if neither character is dead, returns that combat is not over
        }
    }

    public void loadedGame() {
        System.out.println("\nLoaded game from save file.\n");
        //since game is saved on player turn, if player goes second in round we want to make sure they dont
        //lose a turn when the game is loaded back
        if(player.getInitiative() < enemy.getInitiative()) {
            System.out.printf("ROUND %d.5\n", this.round);
            this.player.getStats();
            this.player.turn(this);
        }
    }

    //ending message
    public void ending() {
        if(victorious) {
            System.out.printf("After a tough battle, %s comes out victorious, having defeated %s " +
                    "in one on one combat! Hail the new Arena Champion, %s!",
                    this.player.getName(), this.enemy.getName(), this.player.getName());
        } else {
            System.out.printf("After a tough battle, %s is defeated at the hands of %s " +
                            "in one on one combat! Hail the reigning Arena Champion, %s!",
                    this.player.getName(), this.enemy.getName(), this.player.getName());
        }
    }

    //getter methods
    public GameCharacter getPlayer() {
        return this.player;
    }

    public GameCharacter getEnemy() {
        return this.enemy;
    }
}
