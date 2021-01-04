package de.techfak.se.mmoebius.view;

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
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/GUI.fxml"));
        Pane root = fxmlLoader.load();
        Parameters parameters = getParameters();
        List<String> parameterList = parameters.getRaw();
        String[] args = parameterList.toArray(new String[0]);
        Game game = new Game(args);
        int indicator = game.createBoard();
        if (indicator != 1) {
            System.exit(indicator);
        }
        Controller controller = fxmlLoader.getController();
        controller.initialize(game.getBoard());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
