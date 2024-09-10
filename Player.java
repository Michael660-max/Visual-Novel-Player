package AdventureModel;

import java.util.ArrayList;

/**
 * Player Class.
 * This class keeps track of the progress of the player in the game.
 * Code inspired by assignments created by Eric Roberts
 * and John Estell. Code tailored by the CSC207
 * instructional team at UTM, with special thanks to:
 *
 * @author anshag01
 * @author mustafassami
 * @author guninkakr03
 *  */
public class Player {
    private Room currentRoom; //The current room that the player is located in.
    public ArrayList<AdventureObject> inventory; //The list of items that the player is carrying at the moment.

    /**
     * Player Constructor
     * __________________________
     * Initializes attributes
     *
     * @param currentRoom the room in which the Player begins the game
     */
    public Player(Room currentRoom) {
        this.inventory = new ArrayList<AdventureObject>();
        this.currentRoom = currentRoom;
    }

    /**
     * takeObject
     * _________________________
     * This method adds an object to a player's inventory (and removes it from the room)
     * if the object is present in the room.  It then returns true.
     * If the object is not present in the room, the method
     * returns false.
     *
     * @param object name of the object to take
     * @return true if object is taken, false otherwise
     */
    public boolean takeObject(String object){
        for (AdventureObject o : this.getCurrentRoom().objectsInRoom) {
            if (o.getName().equalsIgnoreCase(object)) {
                this.inventory.add(o);
                return true;
            }
        }
        return false;
    }

    /**
     * dropObject
     * _________________________
     * This method removes an object from the inventory of the player, if it exists.
     * The object, once dropped, should be added to the current room.
     * If the object is not in the inventory, this method will do nothing.
     *
     * @param s String name of prop or object to be removed to the inventory.
     */
    public void dropObject(String s) {
        for (AdventureObject o : this.inventory) {
            if (o.getName().equalsIgnoreCase(s)) {
                this.inventory.remove(o);
                System.out.println(s + " HAS BEEN DROPPED");
                break;
            }
        }
        System.out.println(s + " IS NOT IN INVENTORY");
    }


    /**
     * setCurrentRoom
     * _________________________
     * Setter method for the current room attribute.
     *
     * @param currentRoom The location of the player in the game.
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * getCurrentRoom
     * _________________________
     * Getter method for the current room attribute.
     *
     * @return current room the player is in.
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

}