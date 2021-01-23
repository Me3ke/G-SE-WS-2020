package de.techfak.se.mmoebius.application;

import de.techfak.se.multiplayer.game.*;
import de.techfak.se.multiplayer.server.Server;

/**
 * The main class, contains just the main method to start the application.
 */

public class EncoreServer {

        private static final int DEFAULT_PORT = 8080;
        private static final int EXIT_FAILURE = -1;

        /**
         * The main method launches the Server.
         * @param args the program arguments which include the port of the Server
         */
        public static void main(final String... args) {
            int port;
            if (args.length == 0) {
                System.out.println("no parameters given");
                System.exit(EXIT_FAILURE);
            }
            else if (args[0].equals("-p")) {
                final BoardParser boardParser = new BoardParserImpl();
                final BaseGame baseGame = new BaseGameImpl(boardParser.parse()); //Spielfeld aus angegebener Datei parsen und damit das Spiel initialisieren
                final SynchronizedGame game = new SynchronizedGame(baseGame); //Synchronisiert das Spiel für eine Thread sichere Benutzung
                final Server server = new Server(game); //Neuen Server erstellen
                server.start(Integer.parseInt(args[1])); //Server starten unter angegebenem port
            } else {
                System.out.println("parameter has to be -p <port number>");
                System.exit(EXIT_FAILURE);
            }

        }

        //TODO wie wird -f behandelt bzw. das Spielfeld angegeben?

}
