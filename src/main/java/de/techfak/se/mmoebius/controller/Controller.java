package de.techfak.se.mmoebius.controller;

import de.techfak.se.mmoebius.model.Board;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Controller {

    @FXML
    private VBox containerV ;

    private List<Rectangle> rectangles;

    public void initialize() {
        rectangles = new ArrayList<>();
        for (int j = 0; j <= 6; j++) {
            HBox containerH = new HBox();
            for (int i = 0; i <= 14; i++) {
                Rectangle rectangle = new Rectangle(-24, -24, 48, 48);
                rectangle.setFill(Color.GRAY);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeType(StrokeType.INSIDE);
                rectangles.add(rectangle);
                containerH.getChildren().add(rectangle);
            }
            containerV.getChildren().add(containerH);
        }
    }

    public void setOnMouseClicked(MouseEvent mouseEvent) {

    }
}
