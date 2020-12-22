package de.techfak.se.mmoebius.view;

import de.techfak.se.mmoebius.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;

/**
 *
 */
public class GUI extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/GUI.fxml"));
        Pane root = fxmlLoader.load();
        int initialRow = 7;
        int initialColumn = 15;
        Parameters parameters = getParameters();
        List<String> parameterList = parameters.getRaw();
        initialRow = Integer.parseInt(parameterList.get(0));
        initialColumn = Integer.parseInt(parameterList.get(1));

        Controller controller = fxmlLoader.getController();
        controller.initialize(initialRow,initialColumn);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
