package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;

public class Interaction {
    private static final int TILE_SIZE = 16;
    private TETile[][] tiles;
    private Point playerLocation;
    private long seed;
    private Random rand;
    private int WIDTH;
    private int HEIGHT;
    private int xOffset = 0;
    private int yOffset = 0;

    public Interaction() {}

    // instruct given the seed, this is for new game;
    public Interaction(TETile[][] tiles, long seed) {
        this.tiles = tiles;
        this.seed = seed;
        rand = new Random(seed);
        WIDTH = tiles.length;
        HEIGHT = tiles[0].length;
        generatePlayerLocation(tiles, rand);
    }

    // this is used to draw the frame
    public void initialize() {
        StdDraw.setCanvasSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        Font font = new Font("Monaco", Font.BOLD, TILE_SIZE - 2);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    // create the player's location randomly, this is for the new game, if you load a game, then this should not
    // be called
    private void generatePlayerLocation(TETile[][] tiles, Random RANDOM) {
        boolean player = false;
        int x = 0;
        int y = 0;
        while (!player) {
            x = RandomUtils.uniform(RANDOM, 1, WIDTH - 1);
            y = RandomUtils.uniform(RANDOM, 1, HEIGHT - 1);
            if (tiles[x][y].equals(Tileset.FLOOR)) {
                tiles[x][y] = Tileset.PLAYER;
                player = true;
                playerLocation = new Point(x, y);
            }
        }
    }

    // move the player according to the input
    public void move(char c) {
        int newX = 0;
        int newY = 0;
        int oldX = playerLocation.x();
        int oldY = playerLocation.y();
        switch (c) {
            case 'W':
                newX = playerLocation.x();
                newY = playerLocation.y() + 1;
                break;
            case 'S':
                newX = playerLocation.x();
                newY = playerLocation.y() - 1;
                break;
            case 'A':
                newX = playerLocation.x() - 1;
                newY = playerLocation.y();
                break;
            case 'D':
                newX = playerLocation.x() + 1;
                newY = playerLocation.y();
                break;
        }
        // update the player location if the new location is not a wall, ow stay
        // this would change if new features are added to this game, such as the lockedDoor, trap, new world
        if (tiles[newX][newY].equals(Tileset.FLOOR)) {
            playerLocation.setX(newX);
            playerLocation.setY(newY);
            tiles[newX][newY] = Tileset.PLAYER;
            tiles[oldX][oldY] = Tileset.FLOOR;
        }
    }

    // display the info on the screen
    public void show(TETile[][] world) {
        int numXTiles = world.length;
        int numYTiles = world[0].length;
        StdDraw.clear(new Color(0, 0, 0));
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                world[x][y].draw(x + xOffset, y + yOffset);
            }
        }
        StdDraw.show();
    }

}
