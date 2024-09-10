import AdventureModel.AdventureGame;
import java.util.Scanner;

/**
 * Class Main, the class which drives the Adventure Game.
 * Inspired by assignments created by Eric Roberts
 * and John Estell. Course code tailored by the CSC207
 * instructional team at UTM, with special thanks to:
 *
 * @author anshag01
 * @author mustafassami
 * @author guninkakr03
 *  */
public class Main {
    /**
     * Main method that runs the game.
     * @param args command line arguments.
     **/
    public static void main(String[] args) {

        AdventureGame game = new AdventureGame(true);

        Scanner gameDirectory = new Scanner(System.in);
        String folder;
        Boolean flag = false;

        while (!flag) {
            System.out.println("Enter the name of the game you want to play: ");
            folder = gameDirectory.nextLine();
            try {
                game.setUpGame(folder);
                flag = true; //we made it!
            } catch (Exception e) {  //catch possible Formatting or File I/O errors
                System.out.println("Encountered an error: " + e.getMessage());
                System.out.println("Please try again"); //let the user fix the file or provide another name
            }
        }

        game.playGame(); //no error, play the game!

    }
}