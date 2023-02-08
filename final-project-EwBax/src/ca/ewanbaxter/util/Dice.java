package ca.ewanbaxter.util;

import java.io.Serializable;
import java.util.Random;

public class Dice implements Serializable {
    //magic numbers for max rolls for each die
    private static final int MIN_ROLL = 1;
    private static final int D4_MAX_ROLL = 4;
    private static final int D6_MAX_ROLL = 6;
    private static final int D8_MAX_ROLL = 8;
    private static final int D10_MAX_ROLL = 10;
    private static final int D12_MAX_ROLL = 12;
    private static final int D20_MAX_ROLL = 20;
    private static final Random die = new Random();

    private final int sides;

    public Dice(int sides) {
        this.sides = sides;
    }

    public int rollDice() {
        return die.nextInt((sides + 1) - MIN_ROLL) + MIN_ROLL;
    }

    //Methods for each size of dice
    public static int rollD4(){
        return die.nextInt((D4_MAX_ROLL + 1) - MIN_ROLL) + MIN_ROLL;
    }

    public static int rollD6(){
        return die.nextInt((D6_MAX_ROLL + 1) - MIN_ROLL) + MIN_ROLL;
    }

    public static int rollD8(){
        return die.nextInt((D8_MAX_ROLL + 1) - MIN_ROLL) + MIN_ROLL;
    }

    public static int rollD10(){
        return die.nextInt((D10_MAX_ROLL + 1) - MIN_ROLL) + MIN_ROLL;
    }

    public static int rollD12(){
        return die.nextInt((D12_MAX_ROLL + 1) - MIN_ROLL) + MIN_ROLL;
    }

    public static int rollD20(){
        return die.nextInt((D20_MAX_ROLL + 1) - MIN_ROLL) + MIN_ROLL;
    }
}
