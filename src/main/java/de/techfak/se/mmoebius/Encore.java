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
            System.out.println("No programarguments given");
            System.exit(100);
        }
        else if(args[0].equals("-f")){
            //hier überprüfen ob datei korrekt
            File file = new File(args[1]);
        }
        else{
            System.out.println("unknown programargument");
            System.exit(100);
        }
    }

}
