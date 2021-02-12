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

public final class EncoreServer {

        private static final int DEFAULT_PORT = 8080;
        private static final int SYS_EXIT_FAILURE = -1;
        private static final int SYS_EXIT_INVALID_SOURCE = 100;
        private static final int SYS_EXIT_INVALID_FILE = 101;
        private static final int ARGS_THREE = 3;

        private EncoreServer() {

        }

        /**
         * The main method launches the Server.
         * @param args the program arguments which include the port of the Server
         */
        public static void main(final String... args) {
            int port = 0;
            boolean serverStarted = false;
           if (args.length == 0 || args.length == 1) {
               System.out.println("no parameters given.");
               System.out.println("use -f <board path> -p <port number>");
           } else {
               if (args.length == ARGS_THREE || args.length == 2) {
                   port = DEFAULT_PORT;
               } else {
                   try {
                       port = Integer.parseInt(args[ARGS_THREE]);
                   } catch (NumberFormatException e) {
                       System.out.println("Port is not valid");
                       System.exit(SYS_EXIT_FAILURE);
                   }
               }
               if (args[0].equals("-f")) {
                   System.out.println("Multiplayer mode");
                   File file = new File(args[1]);
                   try {
                       if (file.isFile() && file.canRead()) {
                           final BoardParser boardParser = new BoardParserImpl();
                           final BaseGame baseGame = new BaseGameImpl(boardParser.parse(file));
                           final SynchronizedGame game = new SynchronizedGame(baseGame);
                           final Server server = new Server(game);
                           server.start(port);
                       }
                       Thread.currentThread().join();

                   } catch (InvalidBoardLayoutException | InvalidFieldException e) {
                       System.out.println(e.getMessage());
                       System.exit(SYS_EXIT_INVALID_FILE);
                   } catch (IOException e) {
                       System.out.print(e.getMessage());
                       System.exit(SYS_EXIT_FAILURE);
                   } catch (InterruptedException e) {
                       System.out.println("Server was interrupted.");
                   }
               } else {
                   System.out.println("invalid parameters. Use -f <board path> -p <port number>");
               }
           }
           System.exit(0);
        }
}
