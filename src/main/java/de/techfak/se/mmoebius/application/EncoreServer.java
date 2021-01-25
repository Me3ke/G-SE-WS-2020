package de.techfak.se.mmoebius.application;

import de.techfak.se.multiplayer.game.*;
import de.techfak.se.multiplayer.game.exceptions.InvalidBoardLayoutException;
import de.techfak.se.multiplayer.game.exceptions.InvalidFieldException;
import de.techfak.se.multiplayer.server.Server;

import java.io.File;
import java.io.IOException;

/**
 * The main class, contains just the main method to start the application.
 */

public class EncoreServer {

        private static final int DEFAULT_PORT = 8080;
        private static final int SYS_EXIT_FAILURE = -1;
        private static final int SYS_EXIT_INVALID_SOURCE = 100;
        private static final int SYS_EXIT_INVALID_FILE = 101;


        /**
         * The main method launches the Server.
         * @param args the program arguments which include the port of the Server
         */
        public static void main(final String... args) {
            int port;
            boolean serverStarted = false;
           if (args.length == 0) {
               System.out.println("no parameters given.");
               System.out.println("use -f <board path> -p <port number>");
           }
           else if (args.length == 2) {
               System.out.println("To start a Server type ~ -p <port number> as parameter");
           } else {
               if(args[0].equals("-f") && args[2].equals("-p")) {
                   System.out.println("Multiplayer mode");
                   File file = new File(args[1]);
                   if (args.length == 3) {
                       port = DEFAULT_PORT;
                   } else {
                       port = Integer.parseInt(args[3]);
                   }
                   try {
                       if (file.isFile() && file.canRead()) {
                           final BoardParser boardParser = new BoardParserImpl();
                           final BaseGame baseGame = new BaseGameImpl(boardParser.parse(file)); //Spielfeld aus angegebener Datei parsen und damit das Spiel initialisieren
                           final SynchronizedGame game = new SynchronizedGame(baseGame); //Synchronisiert das Spiel für eine Thread sichere Benutzung
                           final Server server = new Server(game); //Neuen Server erstellen
                           server.start(port); //Server starten unter angegebenem port
                           serverStarted = true;
                       }
                       Thread.currentThread().join();

                   } catch (InvalidBoardLayoutException | InvalidFieldException e) {
                       System.out.println(e.getMessage());
                       System.exit(SYS_EXIT_INVALID_FILE);
                   } catch (IOException e) {
                       System.out.print(e.getMessage());
                       System.exit(SYS_EXIT_FAILURE);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               } else {
                   System.out.println("invalid parameters. Use -f <board path> -p <port number>");
               }
           }
           System.exit(0);
        }

        //TODO Evtl System prints überarbeiten.

}
