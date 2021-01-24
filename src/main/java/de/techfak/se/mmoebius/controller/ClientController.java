package de.techfak.se.mmoebius.controller;

import de.techfak.se.mmoebius.model.Client;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 *
 */
public class ClientController {

    /**
     *
     */
    private String ip;
    private int port;

    @FXML
    private VBox containerV;

    @FXML
    private TextField ipField;

    @FXML
    private TextField portField;

    @FXML
    private Button button;

    /**
     *
     */
    public void initialize() {
        Client client = new Client();
        button.setOnMouseClicked(mouseEvent -> {
            ip = ipField.getText();
            try {
                port = Integer.parseInt(portField.getText());
            } catch (NumberFormatException e) {
                System.out.println("invalid port");
            }
            System.out.println(ip);
            System.out.println(port);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Serverconnection");
            if(client.connectToServer(ip,port)) {
                alert.setContentText("Connection to server established");
            } else {
                alert.setContentText("Server not available");
            }
            alert.showAndWait();
        });
    }

}
