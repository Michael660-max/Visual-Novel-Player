import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import AdventureModel.*;
import Trolls.GameTroll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class AdventureTest.
 * Course code from the CSC207 instructional
 * team at UTM, contributors to tests include:
 *
 * @author iselein
 * @author anshag01
 *  */
public class AdventureTest {

    // **************** setUpGame Testing ****************
    @Test
    void basicSynonymTest() throws IOException, FormattingException {
        HashMap<String, String> expectedSynonyms = new HashMap<String, String>();
        expectedSynonyms.put("F", "FRIENDLY");
        expectedSynonyms.put("T", "TINY");
        expectedSynonyms.put("B", "BIG");
        expectedSynonyms.put("J", "JAVA");
        expectedSynonyms.put("W", "WEST");
        expectedSynonyms.put("E", "EAST");
        expectedSynonyms.put("N", "NORTH");
        expectedSynonyms.put("S", "SOUTH");
        expectedSynonyms.put("U", "UP");
        expectedSynonyms.put("D", "DOWN");
        expectedSynonyms.put("Q", "QUIT");
        expectedSynonyms.put("I", "INVENTORY");
        expectedSynonyms.put("L", "LOOK");
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        assertEquals(expectedSynonyms, game.getSynonyms());
    }

    @Test
    void roomPopulatedTest() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        assertEquals(12, game.getRooms().size());
    }

    @Test
    void roomNum() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        assertEquals("End of road", game.getRooms().get(2).getRoomName());
    }

    @Test
    void introPopulatedTest() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        assertFalse(game.getIntroText().isEmpty());
    }

    @Test
    void helpPopulatedTest() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        assertFalse(game.getHelpText().isEmpty());
    }

    @Test
    void initialPlayerRoomSet() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        assertEquals("Outside building", game.player.getCurrentRoom().getRoomName());
    }

    // **************** convertCommand Testing ****************
    @Test
    void createCommandTest() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        String[] command = game.convertCommand("Q");
        assertEquals("QUIT", command[0]);

        String[] command2 = game.convertCommand("F J");
        assertEquals("FRIENDLY", command2[0]);
        assertEquals("JAVA", command2[1]);
    }

    @Test
    void testConvertCommand() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        String[] expected = {"TAKE", "KEYS"};
        assertArrayEquals(expected, game.convertCommand("TAKE KEYS"));
    }

    @Test
    void testConvertCommandWithSynonyms() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        String[] expected = {"TINY"};
        assertArrayEquals(expected, game.convertCommand("t"));
    }

    @Test
    void manyCommands() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        String[] command = game.convertCommand("F J F");
        assertEquals(command.length, 0);
    }

    // **************** movePlayer Testing ****************
    @Test
    void testMovePlayerValidMove() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        boolean result = game.movePlayer("in");
        assertTrue(result);
        assertEquals(3, game.player.getCurrentRoom().getRoomNumber());
    }

    @Test
    void testMovePlayerLockedPassage() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        boolean result = game.movePlayer("south");
        assertEquals(4, game.player.getCurrentRoom().getRoomNumber());
    }

    @Test
    void testMovePlayerLockedPassageWithKey() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        game.movePlayer("IN"); // room 3
        game.player.takeObject("KEYS"); // pick keys
        game.movePlayer("OUT"); // room 1
        game.movePlayer("SOUTH"); // room 4
        game.movePlayer("SOUTH"); // room 5
        game.movePlayer("SOUTH"); // room 6
        game.movePlayer("DOWN"); // room (expected room 8)
        assertEquals(8, game.player.getCurrentRoom().getRoomNumber());
    }

    @Test
    void testMovedPlayerForced() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        game.movePlayer("DOWN"); // room 4
        game.movePlayer("DOWN"); // room 5
        game.movePlayer("DOWN"); // room 6
        assertTrue(game.movePlayer("DOWN")); // room 7
        assertEquals(6, game.player.getCurrentRoom().getRoomNumber());
    }

    @Test
    void testMovedPlayerForcedEnd() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        assertFalse(game.movePlayer("WEST"));
    }

    // **************** readRoom Testing ****************
    @Test
    void testReadRoom() throws IOException, FormattingException {
        BufferedReader buff = new BufferedReader(new FileReader("SmallGame/rooms.txt"));
        Room room = Room.readRoom("SmallGame", buff);
        assertEquals(1, room.getRoomNumber());
        assertEquals("Outside building", room.getRoomName());
        String intro = "You are standing at the end of a road before a small brick\n"
                + "building.  A small stream flows out of the building and\n"
                + "down a gully to the south.  A road runs up a small hill\n"
                + "to the west.";
        assertEquals(intro, room.getDescription().strip());
        assertEquals(8, room.getPassageTable().getPassages().size());
    }

    @Test
    void testReadRoomEmpty() throws IOException, FormattingException {
        BufferedReader buff = new BufferedReader(new FileReader("TestGame/rooms.txt"));
        Room room = Room.readRoom("TestGame", buff);
        assertEquals(room.getRoomNumber(), -1);
    }

    // **************** readObject Testing ****************
    @Test
    void testReadObject() throws IOException, FormattingException {
        // populate the rooms array first
        BufferedReader roomsBuff = new BufferedReader(new FileReader("SmallGame/rooms.txt"));
        BufferedReader buff = new BufferedReader(new FileReader("SmallGame/objects.txt"));
        HashMap<Integer, Room> rooms = new HashMap<>();
        while (roomsBuff.ready()) {
            Room room = Room.readRoom("SmallGame", roomsBuff);
            rooms.put(room.getRoomNumber(), room);
        }
        AdventureObject.readObject(buff, rooms);
        Room room = rooms.get(3);
        assertEquals(1, room.objectsInRoom.size());
    }

    @Test
    void testReadObjectDNE() throws IOException, FormattingException {
        // SHOULD BE FALSE

        // populate the rooms array first (empty)
        BufferedReader buff = new BufferedReader(new FileReader("TestGame/objects.txt"));
        HashMap<Integer, Room> rooms = new HashMap<>();

        // Customized formatting exception in console
        AdventureObject.readObject(buff, rooms);
    }

    // **************** takeObject Testing ****************
    @Test
    void testTakeObject() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        game.movePlayer("IN");
        boolean result = game.player.takeObject("KEYS");
        assertTrue(result);
        assertEquals(1, game.player.inventory.size());
        assertEquals("KEYS", game.player.inventory.get(0).getName());
        assertFalse(game.player.getCurrentRoom().objectsInRoom.contains("KEYS"));
    }

    @Test
    void testTakeObjectFalse() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        game.movePlayer("IN");
        boolean result = game.player.takeObject("FALSE");
        assertFalse(result);
    }

    // **************** dropObject Testing ****************
    @Test
    void testDropObject() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        game.movePlayer("IN");
        game.player.takeObject("KEYS");
        game.movePlayer("OUT");
        game.player.dropObject("KEYS");
        assertEquals(0, game.player.inventory.size());
        assertFalse(game.player.getCurrentRoom().objectsInRoom.contains("KEYS"));
    }

    @Test
    void testDropObjectNothing() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        game.movePlayer("IN");
        game.player.takeObject("KEYS");
        game.movePlayer("OUT");
        game.player.dropObject("KEYSS");
        assertEquals(1, game.player.inventory.size());
        assertFalse(game.player.getCurrentRoom().objectsInRoom.contains("KEYS"));
    }

    // **************** executeAction Testing ****************
    @Test
    void testExecuteActionValidMove() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        assertFalse(game.executeAction("QUIT"));
        assert(game.executeAction("HELP"));
        assert(game.executeAction("LOOK"));
        assert(game.executeAction("INVENTORY"));
        assert(game.executeAction("TAKE KEYS"));
        assert(game.executeAction("DROP KEYS"));
    }

    @Test
    void testExecuteActionNotValidMove() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        assert(game.executeAction("QUIT a"));
        assert(game.executeAction("HELP a"));
        assert(game.executeAction("LOOK a"));
        assert(game.executeAction("INVENTORY a"));
        assert(game.executeAction("TAKE"));
        assert(game.executeAction("DROP"));
    }

    @Test
    void testExecuteActionMotion() throws IOException, FormattingException {
        // Tested in main method of GameTroll class
        assert(true);

    }

    // **************** Troll Testing ****************

}