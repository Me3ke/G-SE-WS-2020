package de.techfak.se.mmoebius.controller;

import de.techfak.se.mmoebius.model.Board;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class Controller {

    private static final int REC_X_CONST = -24;
    private static final int REC_Y_CONST = -24;
    private static final int REC_WIDTH_CONST = 48;
    private static final int REC_HEIGHT_CONST = 48;

    @FXML
    private VBox containerV;

    /**
     *
     */
    private int rowCount;
    private int colCount;
    public Group[][] field;
    private Board board;

    /**
     *
     * @param board
     */
    public void initialize(Board board) {
        this.board = board;
        colCount = board.getColCount();
        rowCount = board.getRowCount();
        List<Integer> playMoveRow = new ArrayList<>();
        List<Integer> playMoveCol = new ArrayList<>();
        field = new Group[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            HBox containerH = new HBox();
            for (int j = 0; j < colCount; j++) {
                Rectangle rectangle = new Rectangle(REC_X_CONST, REC_Y_CONST, REC_WIDTH_CONST, REC_HEIGHT_CONST);
                rectangle.setFill(board.floor[i][j].getColor());
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeType(StrokeType.INSIDE);
                Group group = new Group();
                final int currentRow = i;
                final int currentCol = j;
                rectangle.setOnMouseClicked(mouseEvent -> {
                        Rectangle crossTileOne = new Rectangle(-24,-2,50,5);
                        Rectangle crossTileTwo = new Rectangle(-2,-24,5,50);
                        crossTileOne.setRotate(45);
                        crossTileTwo.setRotate(45);
                        crossTileOne.setFill(Color.BLACK);
                        crossTileTwo.setFill(Color.BLACK);
                        group.getChildren().add(crossTileOne);
                        group.getChildren().add(crossTileTwo);
                        playMoveRow.add(currentRow);
                        playMoveCol.add(currentCol);
                    });
                group.getChildren().add(rectangle);
                field[i][j] = group;
                containerH.getChildren().add(group);
            }
            containerV.getChildren().add(containerH);
        }
        board.addObserver((PropertyChangeEvent evt) -> updateField());
    }

    private void updateField() {
    }

    /**
     *
     *
     */
    public void setOnMouseClicked(MouseEvent event) {

    }
}

