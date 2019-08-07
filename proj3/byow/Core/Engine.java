package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    public static final int START_WIDTH = 50; // these two are for the start menu
    public static final int START_HEIGHT = 50;
    public static final int TILE_SIZE = 16;

    // load the saving WorldGenerator
    private static Interaction loadInteraction() {
        File f = new File("./save_data");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                return (Interaction) os.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        /* In the case no Editor has been saved yet, we return a new one. */
        System.exit(0);
        return new Interaction();
    }

    // save the status
    private static void saveInteraction(Interaction interact) {
        File f = new File("./save_data");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(interact);
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    // generating the page for user to enter the seed
    private void renderTypeString() {
        StdDraw.setCanvasSize(START_WIDTH * TILE_SIZE, START_HEIGHT * TILE_SIZE);
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, START_WIDTH );
        StdDraw.setYscale(0, START_HEIGHT );
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();
        StdDraw.text(START_WIDTH / 2, START_HEIGHT / 2, "Typed the seed and end with letter S");
        StdDraw.show();
    }

    // generating the start Menu
    private void renderMenu() {
        StdDraw.setCanvasSize(START_WIDTH * TILE_SIZE, START_HEIGHT * TILE_SIZE);
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, START_WIDTH );
        StdDraw.setYscale(0, START_HEIGHT );
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();
        StdDraw.text(START_WIDTH / 2, START_HEIGHT * 2 / 3, "CS61B The Game");
        StdDraw.text(START_WIDTH / 2, START_HEIGHT / 2 + 2, "New Game (N)");
        StdDraw.text(START_WIDTH / 2, START_HEIGHT / 2, "Load Game (L)");
        StdDraw.text(START_WIDTH / 2, START_HEIGHT / 2 - 2 , "Quit (Q)");
        StdDraw.show();
    }

    /**
     *
     * @return the seed after typed N
     */
    private long getSeed() {
        try {
            boolean seedTyped = false;
            String input = "";
            while (!seedTyped) {
                if (!StdDraw.hasNextKeyTyped()) {
                    continue;
                }
                char key = StdDraw.nextKeyTyped();
                input += key;
                if (key == 'S' | key == 's') {
                    seedTyped = true;
                }
            }
            int seed = Integer.parseInt(input.substring(0, input.length() - 1));
            return (long) seed;
        }
        catch (NumberFormatException e) {
            System.out.println("You typed a wrong format of seed");
            throw e;
        }
    }


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        renderMenu();
        Interaction interact = null;
        // open from the menu
        boolean wait = true;
        while (wait) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
              }
            String c = ("" + StdDraw.nextKeyTyped()).toUpperCase();
            System.out.println(c);
            switch (c) {
                case "N":
                    // get the seed
                    renderTypeString();
                    long seed = getSeed();
                    TETile[][] WorldFrame = null;
                    WorldFrame = WorldGenerator.generate(seed, WorldFrame, WIDTH, HEIGHT);
                    interact = new Interaction(WorldFrame, seed);
                    wait = false;
                    break;
                case "L":
                    interact = loadInteraction();
                    wait = false;
                    break;
                case "Q":
                    System.exit(0);
                    break;
                default:
                    System.exit(0);
                    break;
            }
        }
        // start to play
        interact.initialize();
        char preKey = ',';
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                // if a user enter ':' then 'Q', then save and exit the game
                if (("" + preKey + key).toUpperCase().equals(":Q")) {
                    saveInteraction(interact);
                    System.exit(0);
                }
                // in case the player enter something else
                if ("WSAD".contains(("" + key).toUpperCase())) {
                    interact.move(("" + key).toUpperCase());
                }
                preKey = key;
            }
            interact.show();
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        
        TETile[][] WorldFrame = null;
        Interaction interact = null;
        input = input.toUpperCase();
        char firstLetter = input.charAt(0);
        int startIndex = 1;
        // get the seed or load the game
        switch (firstLetter) {
            case 'N':
                // get the seed
                int indexS = 0;
                for (int i = startIndex; i < input.length(); i++) {
                    if (input.charAt(i) == 'S'){
                        indexS = i;
                        break;
                    }
                }
                startIndex = 1;
                long seed = Long.valueOf(input.substring(startIndex, indexS));
                WorldFrame = WorldGenerator.generate(seed, WorldFrame, WIDTH, HEIGHT);
                interact = new Interaction(WorldFrame, seed);
                startIndex = indexS + 1;
                break;
            case 'L':
                interact = loadInteraction();
                startIndex = 1;
                break;
            default:
                System.exit(0);
                break;
        }
        System.out.println("here");
        // start the interaction
        interact.initialize();
        char preKey = ',';
        for (int i = startIndex; i < input.length(); i++) {
            char key = input.charAt(i);
            if (("" + preKey + key).toUpperCase().equals(":Q")) {
                saveInteraction(interact);
                System.exit(0);
                return interact.getTiles();
            }
            if ("WSAD".contains(("" + key).toUpperCase())) {
                interact.move(("" + key).toUpperCase());
            }
            preKey = key;
            interact.show();
        }
        return interact.getTiles();
    }

    public static void main(String args[]){
        Engine tileEngine = new Engine();
        tileEngine.ter.initialize(WIDTH, HEIGHT);
        TETile[][] tiles = tileEngine.interactWithInputString("N12892S");
        tileEngine.ter.renderFrame(tiles);
    }
}
