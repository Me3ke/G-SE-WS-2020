package de.techfak.se.mmoebius.controller;

import de.techfak.se.mmoebius.model.Board;
import de.techfak.se.mmoebius.model.Client;
import de.techfak.se.mmoebius.model.Game;
import de.techfak.se.mmoebius.view.GUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 *
 */
public class ClientController {

    private static final String DEFAULT_NAME = "Theben";
    private static final int STATUS_NONAME = 400;
    private static final int STATUS_ALREADY_REGISTERED = 409;
    /**
     *
     */
    private String ip;
    private int port;
    private String name;
    private Client client;

    @FXML
    private VBox containerV;

    @FXML
    private TextField ipField;

    @FXML
    private TextField portField;

    @FXML
    private Button buttonC;

    @FXML
    private Button buttonSP;

    /**
     *
     */
    public void initialize(String[] args) {
        name = null;
        buttonSP.setOnMouseClicked(mouseEvent -> {
            showGUI(args, "", name);
            Stage stage = (Stage) buttonSP.getScene().getWindow();
            stage.hide();
        });
        buttonC.setOnMouseClicked(mouseEvent -> {
            ip = ipField.getText();
            try {
                port = Integer.parseInt(portField.getText());
            } catch (NumberFormatException e) {
                System.out.println("invalid port");
            }
            System.out.println(ip);
            System.out.println(port);
            String url = "http://" + ip + ":" + port;
            client = new Client(url);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Server connection");
            alert.setHeaderText(null);
            if (client.connectToServer()) {
                alert.setContentText("Connection to server established");
                alert.showAndWait();
                Stage stage = (Stage) buttonC.getScene().getWindow();
                name = showNameField();
                System.out.println("Your name: " + name);
                if (verifyNameGUI(client)) {
                    showGUI(args, url, name);
                }
                stage.hide();
            } else {
                alert.setContentText("Server not available. Please try again");
                alert.showAndWait();
            }
        });
    }

    /**
     *
     * @param client
     * @return
     */
    private boolean verifyNameGUI(Client client) {
        Alert nameAlert = new Alert(Alert.AlertType.ERROR);
        nameAlert.setTitle("Server connection");
        nameAlert.setHeaderText("Connecting to Server with name: " + name);
        int statusCode = client.verifyName(name);
        if (statusCode == -1) {
            nameAlert.setContentText("Verification failed. Please try again.");
            nameAlert.showAndWait();
        } else if (statusCode == STATUS_NONAME) {
            nameAlert.setContentText("Name cannot be empty.");
            nameAlert.showAndWait();
        } else if (statusCode == STATUS_ALREADY_REGISTERED) {
            nameAlert.setContentText("Name is already registered");
            nameAlert.showAndWait();
        } else {
            if (client.isGameStarted(name)) {
                nameAlert.setContentText("Game already started.");
                nameAlert.showAndWait();
                client.deletePlayer(name);
            } else {
                Alert nameAlertSuccess = new Alert(Alert.AlertType.INFORMATION);
                nameAlertSuccess.setTitle("Server connection");
                nameAlertSuccess.setHeaderText("Connecting to Server with name: " + name);
                nameAlertSuccess.setContentText("Connection Successful");
                nameAlertSuccess.showAndWait();
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    private String showNameField() {
        Optional<String> result = null;
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Encore Multiplayer");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter your name:");
        result = dialog.showAndWait();
        if (result.isEmpty()) {
            return "";
        }
        return result.get();
    }

    /**
     *
     * @param args
     */
    private void showGUI(String[] args, String url, String name) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/GUI.fxml"));
        Pane root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String boardString = client.getBoard(name);
        stage.setOnCloseRequest(windowEvent -> {
            client.deletePlayer(name);
        });
        System.out.println(boardString);
        String lines[] = boardString.split("\\r?\\n");
        int rowCount, colCount;
        rowCount = lines.length;
        colCount = lines[0].toCharArray().length;
        char[][] map = new char[rowCount][colCount];
        for (int i = 0; i < lines.length; i++) {
            map[i] = lines[i].toCharArray();
        }
        Board board = new Board(map);
        board.printBoard();
        Controller controller = fxmlLoader.getController();
        controller.initialize(board, url, name);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
