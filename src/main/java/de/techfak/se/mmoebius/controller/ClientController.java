package de.techfak.se.mmoebius.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
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
        button.setOnMouseClicked(mouseEvent -> {
            ip = ipField.getText();
            try {
                port = Integer.parseInt(portField.getText());
            } catch (NumberFormatException e) {
                System.out.println("invalid port");
            }
            System.out.println(ip);
            System.out.println(port);
            connectToServer(ip,port);
        });
    }

    /**
     *
     * @param ip
     * @param port
     */
    private void connectToServer(String ip, int port) {

    }
}
