package de.techfak.se.mmoebius;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The main class, contains just the main method to start the application.
 */
public final class Encore {

    private Encore() {
    }

    public static void main(final String... args) {
        Game game = new Game();
        game.play();
        if(args.length == 0) {
            System.out.println("<100> No programarguments given. Type -f <filename>");
        }
        else if(args[0].equals("-f")){
                File file = new File(args[1]);
                if(file.isFile())
                {
                    //Datei auslesen
                    return;
                }
                System.out.println("<100> no valid file found with filename: "+file.getName());
        }
        else{
            System.out.println("<100> unknown programargument. Type -f <filename>");
        }
        System.exit(100);
    }

}
