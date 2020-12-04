package de.techfak.se.mmoebius;

import java.io.*;

/**
 * The main class, contains just the main method to start the application.
 */

//TODO for Task #19799 create Classfile Board and other
    //TODO test if file throws exception if parameter is digit or symbol
public final class Encore {

    /* default */
    static final int SYS_EXIT_FAILED = 100;
    static final char[][] map = new char[7][15];

    private Encore() {
    }

    public static void main(final String... args) {
        Game game = new Game();
        game.play();
        if (args.length == 0) {
            System.out.println("<100> No program arguments given. Type -f <filename>");
        } else if (args[0].equals("-f")) {
            File file = new File(args[1]);
            if (file.isFile() && file.canRead()) {
                readFile(file);
                Board board = new Board(map);
                board.printBoard();
                return;
            }
            System.out.println("<100> no valid file found with filename: " + file.getName());
        } else {
            System.out.println("<100> unknown program argument. Type -f <filename>");
        }
        System.exit(SYS_EXIT_FAILED);
    }

    @SuppressWarnings("PMD.AvoidFileStream")
    private static void readFile(File file) {
        String row;
        int rowCount = 0;
        int colCount = 0;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((row = reader.readLine()) != null) {
                for (colCount = 0; colCount < row.length(); colCount++) {
                    map[rowCount][colCount] = row.charAt(colCount);
                }
                rowCount++;
            }
            System.out.println("Row Count: " + rowCount);
            System.out.println("Column Count: " + colCount);
            System.out.println("The size of the board is therefore valid");
        } catch (IOException | IndexOutOfBoundsException e) {
            System.out.println("Source file is not valid. Check documentation for further information");
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
