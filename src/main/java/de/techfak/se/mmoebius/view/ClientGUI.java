package de.techfak.se.mmoebius.view;

import de.techfak.se.mmoebius.controller.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 *
 */
public class ClientGUI extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientGUI.class.getResource("/ClientGUI.fxml"));
        Pane root = fxmlLoader.load();
        ClientController controller = fxmlLoader.getController();
        controller.initialize();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
