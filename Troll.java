package Trolls;


/**
 * Troll interface for Trolls used in the adventure game.
 * Course code tailored by the CSC207 instructional
 * team at UTM, with special thanks to:
 *
 * @author anshag01
 * @author mustafassami
 * @author guninkakr03
 *  */
public interface Troll {

    /**
     * giveInstructions
     * _________________________
     * All Trolls should explain how their game is played
     */
    public void giveInstructions();

    /**
     * playGame
     * _________________________
     * Play the Trolls game
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame();
}
