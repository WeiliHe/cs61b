package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

/**
 * the index increases from left to right, from down to up.
 * the basic process is, create one room, create the next, connect two, after finishing creating
 * clean the wall that are adjacent to two hallways or two room. (up and down are both floors)
 */
public class WorldGenerator {
    private static final int WIDTH = 70; //map width
    private static final int HEIGHT = 50; //map height
    private static final int minWidth = 4; // should be more than 3 considering the wall lengths
    private static final int maxWidth = 15;
    private static final int minHeight = 4;
    private static final int maxHeight = 10;
    private ArrayList<Room> rooms;

    // place rooms
    private void placeRooms(TETile[][] tiles, Random RAMDOM) {
        int w = RandomUtils.uniform(RAMDOM, minWidth, maxWidth + 1);
        int h = RandomUtils.uniform(RAMDOM, minHeight, maxHeight + 1);
        int x1 = RandomUtils.uniform(RAMDOM, 0, WIDTH - w + 1);
        int y1 = RandomUtils.uniform(RAMDOM, h, WIDTH - 1);
        Room newRoom = new Room(new Point (x1, y1), w, h);
        boolean overlapped = false;
        for (Room room: rooms) {
            if (newRoom.overlap(room)) {
                overlapped = true;
                break;
            }
        }

        if (!overlapped) {
            createRoom(tiles, newRoom);
            rooms.add(newRoom);
        }
    }

    private void createRoom(TETile[][] tiles, Room room) {
        int x1 = room.upperLeftx();
        int y1 = room.upperLefty();
        int x2 = room.lowerRightx();
        int y2 = room.lowerRighty();
        // draw wall
        for (int x = x1; x <= x2; x++) {
            tiles[x][y1] = Tileset.WALL;
            tiles[x][y2] = Tileset.WALL;
        }
        for (int y = y1; y >= y2; y--) {
            tiles[x1][y] = Tileset.WALL;
            tiles[x2][y] = Tileset.WALL;
        }
        // draw room
        for (int y = y1 - 1; y >= y2 + 1; y--) {
            for (int x = x1 + 1; x <= x2 - 1; x++) {
                tiles[x][y] = Tileset.FLOOR;
            }
        }
    }

    /**
     * draw horizontal corridor
     */
    private void hCorridor (int )
}
