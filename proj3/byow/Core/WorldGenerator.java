package byow.Core;

import byow.TileEngine.TERenderer;
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
    private static final int WIDTH = 100; //map width
    private static final int HEIGHT = 50; //map height
    private static final int minWidth = 4; // should be more than 3 considering the wall lengths
    private static final int maxWidth = 20;
    private static final int minHeight = 4;
    private static final int maxHeight = 10;
    private static final int maxRoomNum = 30;
    private static final int maxTry = 60; // maximum try to generate room, in case infinite loop to reach the room num
    private static final long SEED = 2873123;
    private static final Random RANDOM1 = new Random(SEED );
    private static final Random RANDOM2 = new Random(SEED );


    public static void generate() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        // initialize tiles
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        // generate the framework
        ArrayList<Room> rooms = new ArrayList<Room>();
        // use a strategy, every step just generate random rooms within certain area
        int span = maxWidth * 2;
        int stepWidth = span / 2;
        int step = WIDTH / stepWidth;
        int iterNum = step - 1;

        for (int i = 0; i < maxTry; i++) {
            int spanningWidthLeft = i * iterNum / maxTry * stepWidth;
            int spanningWidthRight = i * iterNum / maxTry * stepWidth + span - 1;
            placeOneRooms(tiles, RANDOM1, rooms, spanningWidthLeft, spanningWidthRight);
            if (rooms.size() == maxRoomNum) {
                break;
            }
        }
        cleanWalls(tiles);
        System.out.println(rooms.size());
        ter.renderFrame(tiles);
    }


    // place rooms
    private static void placeOneRooms(TETile[][] tiles, Random RAMDOM, ArrayList<Room> rooms, int spanningWidthLeft, int spanningWidthRight) {
        int w = RandomUtils.uniform(RAMDOM, minWidth, maxWidth + 1);
        int h = RandomUtils.uniform(RAMDOM, minHeight, maxHeight + 1);
        int x1 = RandomUtils.uniform(RAMDOM, spanningWidthLeft, spanningWidthRight - w);
        int y1 = RandomUtils.uniform(RAMDOM, h, HEIGHT);
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
            Point newCenter = newRoom.center();
            // draw hallways
            if (rooms.size() != 0) {
                Point preCenter = rooms.get(rooms.size() - 1).center();

                // carve out the corridors, randomly start with horizontal or vertical corridors
                if (RandomUtils.uniform(RANDOM2, 2) == 1) {
                    hCorridor(tiles, preCenter.x(), newCenter.x(), preCenter.y());
                    vCorridor(tiles, preCenter.y(), newCenter.y(), newCenter.x());
                } else {
                    vCorridor(tiles, preCenter.y(), newCenter.y(), preCenter.x());
                    hCorridor(tiles, preCenter.x(), newCenter.x(), newCenter.y());
                }
            }
            rooms.add(newRoom);
        }
    }

    private static void createRoom(TETile[][] tiles, Room room) {
        int x1 = room.upperLeftx();
        int y1 = room.upperLefty();
        int x2 = room.lowerRightx();
        int y2 = room.lowerRighty();
        // draw wall
        System.out.println("");
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
    private static void hCorridor (TETile[][] tiles, int x1, int x2, int y) {
        // make sure from left to right
        if (x1 == x2) {
            return;
        }
        if (x1 > x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
        }
        // draw floor
        for (int x = x1; x <= x2; x++) {
            tiles[x][y] = Tileset.FLOOR;
        }
        // draw wall
        for (int x = x1 - 1; x <= x2 + 1; x++) {
            if (!tiles[x][y + 1].equals(Tileset.FLOOR)) {
                tiles[x][y + 1] = Tileset.WALL;
            }
            if (!tiles[x][y - 1].equals(Tileset.FLOOR)) {
                tiles[x][y - 1] = Tileset.WALL;
            }
        }
        // draw the walls of two ends
        if (!tiles[x1 - 1][y].equals(Tileset.FLOOR)) {
            tiles[x1 - 1][y] = Tileset.WALL;
        }
        if (!tiles[x2 + 1][y].equals(Tileset.FLOOR)) {
            tiles[x2 + 1][y] = Tileset.WALL;
        }
    }

    private static void vCorridor (TETile[][] tiles, int y1, int y2, int x) {
        if (y1 == y2) {
            return;
        }
        if (y1 > y2) {
            int temp = y1;
            y1 = y2;
            y2 = temp;
        }
        // draw floor
        for (int y = y1; y <= y2; y++) {
            tiles[x][y] = Tileset.FLOOR;
        }
        // draw wall
        for (int y = y1 - 1; y <= y2 + 1; y++) {
            if (!tiles[x + 1][y].equals(Tileset.FLOOR)) {
                tiles[x + 1][y] = Tileset.WALL;
            }
            if (!tiles[x - 1][y].equals(Tileset.FLOOR)) {
                tiles[x - 1][y] = Tileset.WALL;
            }
        }
        // draw the walls of two ends
        if (!tiles[x][y1 -1].equals(Tileset.FLOOR)) {
            tiles[x][y1 - 1] = Tileset.WALL;
        }
        if (!tiles[x][y2 + 1].equals(Tileset.FLOOR)) {
            tiles[x][y2 + 1] = Tileset.WALL;
        }
    }

    // clean the adjacent walls
    private static void cleanWalls(TETile[][] tiles) {
        for (int i = 0; i < 2; i++) {
            for (int x = 1; x < WIDTH - 1; x += 1) {
                for (int y = 1; y < HEIGHT - 1; y += 1) {
                    if ((tiles[x - 1][y].equals(Tileset.FLOOR) & tiles[x + 1][y].equals(Tileset.FLOOR)) ||
                            (tiles[x][y - 1].equals(Tileset.FLOOR) & tiles[x][y + 1].equals(Tileset.FLOOR))) {
                        tiles[x][y] = Tileset.FLOOR;
                    }
                }
            }
        }
    }

    public static void main(String args[]){
        WorldGenerator.generate();
    }
}
