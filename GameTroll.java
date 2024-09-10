package Trolls;

import java.util.Random;
import java.util.Scanner;

/**
 * Class GameTroll.
 * Course code tailored by the CSC207 instructional
 * team at UTM, with special thanks to:
 *
 * @author anshag01
 * @author mustafassami
 * @author guninkakr03
 *  */
public class GameTroll implements Troll {

    Scanner scanner; //use to read input from the user

    /* Function: GameTroll Constructor
     * -------------------
     * Make a GameTroll
     */
    public GameTroll() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Print GameTroll instructions for the user
     */
    public void giveInstructions() {
        System.out.println("I am a RANDOM troll.");
        System.out.println("Pick a number, 0 or 1");
    }

    /**
     * Play the GameTroll game
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame() {
        try {
            giveInstructions();
            double randomDouble = Math.random();
            int randomInt = (randomDouble < 0.5) ? 0 : 1;
            String answer = scanner.next();
            if (Integer.parseInt(answer) == randomInt) {
                System.out.println("YOU GUESSED CORRECT");
                return true;
            } else {
                System.out.println("Better luck next time ... this time you MAY NOT PASS");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong format ... WRONG");
            return false;
        }
    }

    /**
     * Main method, use for debugging
     *
     * @param args: Input arguments
     */
    public static void main(String [] args) throws InterruptedException {
        GameTroll s = new GameTroll();
        boolean a = s.playGame();
    }
}
