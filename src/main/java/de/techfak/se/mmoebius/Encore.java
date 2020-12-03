package de.techfak.se.mmoebius;

import java.io.*;

/**
 * The main class, contains just the main method to start the application.
 */
public final class Encore {

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
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((row = reader.readLine()) != null) {
                System.out.println(row);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                }
        }
    }
}
