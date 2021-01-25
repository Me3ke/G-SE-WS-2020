package de.techfak.se.mmoebius.controller;

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
    /**
     *
     */
    private String ip;
    private int port;
    private String name;

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
        Client client = new Client();
        buttonSP.setOnMouseClicked(mouseEvent -> {
            showGUI(args);
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Server connection");
            alert.setHeaderText(null);
            if(client.connectToServer(ip,port)) {
                alert.setContentText("Connection to server established");
                alert.showAndWait();
                Stage stage = (Stage) buttonC.getScene().getWindow();
                name = showNameField();
                System.out.println("Your name: " + name);
                stage.hide();
            } else {
                alert.setContentText("Server not available. Please try again");
                alert.showAndWait();
            }
        });
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
        if (result.get().equals("")) {
            return DEFAULT_NAME;
        }
        return result.get();
    }

    /**
     *
     * @param args
     */
    private void showGUI(String[] args) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/GUI.fxml"));
        Pane root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Game game = new Game(args);
        game.createBoard();
        Controller controller = fxmlLoader.getController();
        controller.initialize(game.getBoard());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
