package de.techfak.se.mmoebius.util;

import de.techfak.se.mmoebius.client.Client;
import de.techfak.se.multiplayer.game.GameStatus;
import de.techfak.se.multiplayer.server.response_body.PlayerResponse;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Alert;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *  The Helper class is used to do basic calculations and requests for the controller.
 */
public class Helper {

    public Helper() {

    }

    /**
     * The sortedPlayerListString method gets a list of all players on the server and
     * converts them into a string which is sorted after the maximum of the points of
     * the player.
     * @param client the client.
     * @param name the name of the player.
     * @return a string which contains the sorted player list.
     */
    public String sortedPlayerListString(Client client, String name) {
        List<PlayerResponse> playerResponseList = client.getPlayerList(name);
        Map<Integer, String> players = new TreeMap<>(Comparator.reverseOrder());
        for (int i = 0; i < playerResponseList.size(); i++) {
            players.put(playerResponseList.get(i).getPoints(), playerResponseList.get(i).getName());
        }
        String sortedPlayerListString = players.toString();
        sortedPlayerListString = sortedPlayerListString.replaceAll(",", "\n");
        sortedPlayerListString = sortedPlayerListString.replaceAll("\\{",  "");
        sortedPlayerListString = sortedPlayerListString.replaceAll("}",  "");
        return sortedPlayerListString;
    }

    /**
     * The end method is called if something wrong happens with synchronisation, because
     * there is no guideline to follow then. The end method informs the player about the
     * circumstances and deletes the player from the Server after. Then the application is closed.
     * @param client the client.
     * @param name the name of the player.
     */
    public void end(Client client, String name) {
        System.err.println("Unexpected Error with round synchronisation.");
        client.deletePlayer(name);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error in Server Connection");
        alert.setHeaderText(null);
        alert.setContentText("There was a problem with synchronising the rounds of"
            + " you and other players. Hence you are disconnecting");
        alert.showAndWait();
        Platform.exit();
    }

    /**
     * The removeCrosses method removes the cross of a tile if a play move was
     * considered invalid.
     * @param field the playing field as Group array.
     * @param playMoveRow the rows of this playing move.
     * @param playMoveCol the columns of this playing move.
     */
    public void removeCrosses(Group[][] field, List<Integer> playMoveRow, List<Integer> playMoveCol) {
        for (int i = 0; i < playMoveRow.size(); i++) {
            field[playMoveRow.get(i)][playMoveCol.get(i)].getChildren().remove(2);
            field[playMoveRow.get(i)][playMoveCol.get(i)].getChildren().remove(1);
        }
    }

    /**
     * The testForEqual method checks if the current field the
     * player crossed, was already crossed beforehand.
     * @param field the group array of the playing field.
     * @param currentRow The row of the tile to be crossed.
     * @param currentCol The column of the tile to be crossed.
     * @return  Returns true if the current field to be crossed was
     *          already crossed and false if not.
     */
    public boolean testForEqual(Group[][] field, int currentRow, int currentCol) {
        if (field[currentRow][currentCol].getChildren().size() > 1) {
            System.out.println("Field " + currentRow + currentCol + " is already crossed.");
            return true;
        } else {
            return false;
        }
    }

    /**
     * The toIntArray is and auxiliary method to convert an list of integers
     * to an array of integers.
     * @param list The list to be converted.
     * @return The corresponding array.
     */
    public int[] toIntArray(List<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * Starts the game and shows a dialog.
     * @param gameStatusInfo the current game status
     * @param client the client which starts the game
     * @param name the name of the player
     * @return returns true if the game could be started, false if not.
     */
    public boolean startGame(GameStatus gameStatusInfo, Client client, String name) {
        Alert startAlert = new Alert(Alert.AlertType.ERROR);
        startAlert.setTitle("Server start");
        startAlert.setHeaderText(null);
        if (!client.isGameStarted(name)) {
            gameStatusInfo = client.changeGameStatus(name, GameStatus.RUNNING);
            if (gameStatusInfo != null) {
                System.out.println("Game has been started");
                return true;
            } else {
                startAlert.setContentText("Game could not be started.");
                startAlert.showAndWait();
                return false;
            }
        } else {
            startAlert.setContentText("Game already started.");
            startAlert.showAndWait();
            return false;
        }
    }
}
