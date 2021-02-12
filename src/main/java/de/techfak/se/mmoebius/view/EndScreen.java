package de.techfak.se.mmoebius.view;

import de.techfak.se.mmoebius.client.Client;
import de.techfak.se.mmoebius.util.Helper;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.concurrent.ScheduledExecutorService;

/**
 * The Endscreen class is responsible for the end screen.
 */
public class EndScreen {

    public EndScreen(Helper helper, Client client, String name, int points, ScheduledExecutorService exec) {
        showEndScreen(helper, client, name, points, exec);
    }

    /**
     * The showEndScreenMethod shows a dialog if the game is finished. The
     * dialog contains a ranking of all players.
     * @param helper the helper.
     * @param client the client.
     * @param exec the executor service of the threads.
     * @param name the name of the player.
     * @param points the current points of the player.
     */
    private void showEndScreen(Helper helper, Client client, String name, int points, ScheduledExecutorService exec) {
        exec.shutdown();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game over");
        alert.setHeaderText("You achieved " + points + " points.");
        alert.setContentText("To view ranking press details.");
        String playerRanking = helper.sortedPlayerListString(client, name);
        Label label = new Label("Results: ");
        TextArea textArea = new TextArea(playerRanking);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }
}
