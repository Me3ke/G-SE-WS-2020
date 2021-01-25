package de.techfak.se.mmoebius.view;

import de.techfak.se.mmoebius.controller.ClientController;
import de.techfak.se.mmoebius.controller.Controller;
import de.techfak.se.mmoebius.model.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.List;

/**
 * The GUI class initializes the GUI, the game, the view and the controller for the application.
 */
public class GUI extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader clientFxmlLoader = new FXMLLoader(GUI.class.getResource("/ClientGUI.fxml"));
        Pane clientRoot = clientFxmlLoader.load();
        Parameters parameters = getParameters();
        List<String> parameterList = parameters.getRaw();
        String[] args = parameterList.toArray(new String[0]);
        ClientController clientController = clientFxmlLoader.getController();
        clientController.initialize(args);
        Scene clientScene = new Scene(clientRoot);
        stage.setScene(clientScene);
        stage.show();
    }

}
