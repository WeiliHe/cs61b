package byow.lab12;
import edu.princeton.cs.algs4.In;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashMap;
import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.lang.Math;
import java.util.Collections;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final long SEED = 287313;
    private static final Random RANDOM1 = new Random(SEED);
    private static final Random RANDOM2 = new Random(SEED);

    /**
     *
     * @param ulPoint
     * @param length the length of the hexagon
     * @param tiles
     * draw a single hexagon
     */
    public static void addHexagon(Point ulPoint, int length, TETile[][] tiles, TETile tileType) {
        int x = ulPoint.x();
        int y = ulPoint.y();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length + 2 * i; j++) {
                if (isInWorld(x - i + j, y + i)) {
//                    tiles[x - i + j][y + i] = tileType;
                    System.out.print("x:" + (x));
                    System.out.print(",y:" + (y));
                    System.out.print(",i:" + (i));
                    System.out.print(",j:" + (j));
                    System.out.print(",pos:" + "xa" + (x - i + j) + "ya" + (y + i));
                    System.out.println("");
                    tiles[x - i + j][y + i] = TETile.colorVariant(tileType, 32, 32, 32, RANDOM1);
                }
            }
        }
        for (int i = length - 1; i >= 0; i--) {
            for (int j = 0; j < length + 2 * i; j++) {
                if (isInWorld(x - i + j, y + 2 * length - i - 1)) {
//                    tiles[x - i + j][y + 2 * length - i - 1] = tileType;
                    tiles[x - i + j][y + 2 * length - i - 1] = TETile.colorVariant(tileType, 32, 32, 32, RANDOM1);
                }
            }
        }
    }

    /**
     *
     * @param x
     * @param y
     * @return boolean checking the x, y is within the world
     */
    private static boolean isInWorld(int x, int y) {
        return (x < WIDTH) && (y < HEIGHT);
    }

    /**
     *
     * @param col
     * @param row
     * @param length
     * @param ulPointStart
     * @return the upper left point of hexagon given the position in the tesselation and the overall ulPointStart in the leftmost place
     */
    private static Point getSingleUlPoint(int col, int row, int length, Integer[] columns, Point ulPointStart){
        int x;
        int y;
        x = ulPointStart.x() + (2 * length - 1) * col;

        if (col <= columns.length / 2) {
            y = ulPointStart.y() - col * length + row * (2 * length);
        } else {
//            because of symmetric
            y = ulPointStart.y() - (columns.length - 1 - col) * length + row * (2 * length);
        }
        return new Point(x, y);
    }

    /**
     *
     * @param columns
     * @param length
     * @return the position of the tesselated Hesagon
     * the position of the hexagon at the left most and up most position
     */
    public static Point getULPointofWhole(Integer[] columns, int length, int randomInt) {
        int totalColNum = columns.length;
        int longestHexagonNum = Collections.max(Arrays.asList(columns));
        int HexagonNumLeftMost = columns[0];

        int xlowerBound = length - 1;
        int xupperBound = Math.max(xlowerBound, WIDTH - ((2 * length - 1) * totalColNum + 1));
        int ylowerBound = (longestHexagonNum - HexagonNumLeftMost) * 2 * length;
        int yupperBound = Math.max(ylowerBound, HEIGHT - (longestHexagonNum * 2 * length - 1) + (longestHexagonNum - HexagonNumLeftMost) * length);

//       in case it's out of bound
        int x = (xupperBound > xlowerBound) ? xlowerBound + randomInt % (xupperBound - xlowerBound): xlowerBound;
        int y = (yupperBound > ylowerBound) ? ylowerBound + randomInt % (yupperBound - ylowerBound) : ylowerBound;
        return new Point(x, y);
    }

    private static TETile randomTile() {
        int tileNum = RANDOM2.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            default: return Tileset.NOTHING;
        }
    }

    /**
     * initialize the tiles, say nothing
     * @param tiles
     */
    private static void initTiles(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.WATER;
            }
        }
    }

    
    public static void tesselation(Integer[] columns, int length, TETile[][] tiles) {
        initTiles(tiles);
        Point ulPointStart = getULPointofWhole(columns, length, RANDOM1.nextInt(Integer.SIZE - 1));
        for (int i = 0; i < columns.length; i++) {
            for (int j = 0; j < columns[i]; j++) {
                Point ulPoint = getSingleUlPoint(i, j, length, columns, ulPointStart);
                TETile tileType = randomTile();
                addHexagon(ulPoint, length, tiles, tileType);
            }
        }
    }

    /**
     * Point class, with x and y
     */
    private static class Point {
        private int x;
        private int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int x(){
            return this.x;
        }
        public int y() {
            return this.y;
        }
    }

    public static void main(String[] args) {
        Integer[] columns = new Integer[]{2, 3, 4, 5, 4, 3, 2};
        int length = 4;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] randomTiles = new TETile[WIDTH][HEIGHT];
        tesselation(columns, length, randomTiles);
        ter.renderFrame(randomTiles);
    }


}
