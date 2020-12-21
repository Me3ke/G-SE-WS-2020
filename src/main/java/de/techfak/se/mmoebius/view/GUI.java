package de.techfak.se.mmoebius.view;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;

/**
 *
 */
public class GUI extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello World!");
        Scene scene = new Scene(label, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

}
