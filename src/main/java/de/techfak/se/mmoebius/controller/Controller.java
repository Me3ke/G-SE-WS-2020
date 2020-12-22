package de.techfak.se.mmoebius.controller;

import de.techfak.se.mmoebius.model.Board;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;


/**
 *
 */
public class Controller {

    @FXML
    private VBox containerV ;

    /**
     *
     */
    private Board board;
    private int rowCount;
    private int colCount;

    /**
     *
     * @param board
     */
    public void initialize(Board board) {
        colCount = board.getColCount();
        rowCount = board.getRowCount();
        Rectangle [][] field = new Rectangle[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            HBox containerH = new HBox();
            for (int j = 0; j < colCount; j++) {
                Rectangle rectangle = new Rectangle(-24, -24, 48, 48);
                rectangle.setFill(board.floor[i][j].getColor());
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeType(StrokeType.INSIDE);
                field[i][j] = rectangle;
                containerH.getChildren().add(rectangle);
            }
            containerV.getChildren().add(containerH);
        }
    }


    /**
     *
     * @param mouseEvent
     */
    public void setOnMouseClicked(MouseEvent mouseEvent) {

    }
}
