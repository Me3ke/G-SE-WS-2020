package de.techfak.se.mmoebius;

import java.io.*;

/**
 * The main class, contains just the main method to start the application.
 */
public final class Encore {

    private static char[][] map = new char[7][15];

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
                checkMap();
                return;
            }
            System.out.println("<100> no valid file found with filename: " + file.getName());
        } else {
            System.out.println("<100> unknown program argument. Type -f <filename>");
        }
        System.exit(100);
    }

    private static void readFile(File file) {
        String row;
        int rowcount = 0;
        int colcount;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((row = reader.readLine()) != null) {
                char temp;
                for(colcount = 0; colcount < row.length(); colcount++){
                    map[rowcount][colcount] = row.charAt(colcount);
                }
                rowcount++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("given file is not valid");
            e.printStackTrace();
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                }
        }
    }

    private static void checkMap() {
        for(int i = 0; i< map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if(map[i][j] == 'b' ||map[i][j] == 'g' ||map[i][j] == 'o' || map[i][j] == 'r' || map[i][j] == 'y'){ }
                else {
                    System.out.println("sourcefile is not valid. Check Dokumentation for further information");
                }
            }
        }
    }
}
