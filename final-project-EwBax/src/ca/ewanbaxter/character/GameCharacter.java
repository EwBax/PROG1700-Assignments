package ca.ewanbaxter.character;

import ca.ewanbaxter.main.Game;

public interface GameCharacter {

    boolean attackHit(GameCharacter target);

    void takeDamage(int damage);

    void rollInitiative();

    String getName();

    int getHitPoints();

    int getArmorClass();

    int getInitiative();

    void getStats();

    void setStunned(boolean stunned);

    void turn(Game game);
}
