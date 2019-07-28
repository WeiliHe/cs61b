package byow.lab13;

import byow.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.event.KeyEvent;

import java.awt.Color;
import java.awt.Font;
import java.util.LinkedList;
import java.util.Random;
import java.lang.*;


public class MemoryGame {
    private int width;
    private int height;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();
        this.rand = new Random(seed);
        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        int charLength = CHARACTERS.length;
        String randString = "";
        for (int i = 0; i < n; i++) {
            int index = RandomUtils.uniform(this.rand, charLength);
            randString += CHARACTERS[index];
        }
        return randString;
    }

    public void drawFrame(int round) {
        StdDraw.clear(Color.BLACK);
        StdDraw.line(0, this.height - 3, this.width, this.height - 3);
        StdDraw.text(3, this.height - 1, String.format("Round: %d", round));
        StdDraw.text(this.width - 5, this.height - 1, ENCOURAGEMENT[1]);
        if (!playerTurn) {
            StdDraw.text(this.width / 2, this.height - 1, "Watch!");
        } else {
            StdDraw.text(this.width / 2, this.height - 1, "Enter!");
        }
        StdDraw.show();
    }

    private void sleep (int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void drawStart(int round) {
        drawFrame(round);
        StdDraw.text(this.width / 2, this.height / 2, "GameStart");
        StdDraw.show();
        sleep(1000);
    }

    public void drawString(String s, int round) {
//        Font font = new Font("Monaco", Font.BOLD, 20);
//        StdDraw.setFont(font);
        drawFrame(round);
        if (!playerTurn) {
            sleep(300);
        }
//        Font letterFont = new Font("Monaco", Font.BOLD, 40);
//            StdDraw.setFont(letterFont);
        StdDraw.text(this.width / 2, this.height / 2, s);
        StdDraw.show();
        if (!playerTurn) {
            sleep(300);
            StdDraw.clear(Color.BLACK);
        }
        StdDraw.show();
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, diisplay relevant game information at the top of the screen
    }


    public void flashSequence(String letters, int round) {
        for (int i = 0; i < letters.length(); i++){
            String s = letters.substring(i, i + 1);
            drawString(s, round);
        }
        playerTurn = true;
        //TODO: Display each character in letters, making sure to blank the screen between letters
    }

    public String solicitNCharsInput(int round) {
        //TODO: Read n letters of player input
        InputAPI inputSource= new KeyboardInput();
        String typeString = "";
        int totalCharacters = 0;
        drawFrame(round);
        while (inputSource.possibleNextInput()) {
        	if (totalCharacters >= round) {
        		break;
        	}
        	char c = inputSource.getNextKey();
        	typeString += c;
            drawString(typeString, round);
        	totalCharacters += 1;
        }
        playerTurn = false;
        return typeString;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        gameOver = false;
        int round = 1;
        drawStart(0);
        while (!gameOver) {
            String letters = generateRandomString(round).toUpperCase();
            flashSequence(letters, round);
            String typeString = solicitNCharsInput(round);
            if (typeString.equals(letters)) {
                round += 1;
            } else {
                gameOver = true;
                StdDraw.clear(Color.black);
                StdDraw.text(this.width / 2, this.height / 2, String.format("Game Over! You made" +
                        "it to round %d", round));
                StdDraw.show();
            }
        }
        //TODO: Establish Engine loop
    }

}
