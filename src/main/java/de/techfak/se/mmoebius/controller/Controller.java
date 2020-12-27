package de.techfak.se.mmoebius.controller;

import de.techfak.se.mmoebius.model.Board;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.awt.*;
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

    @FXML
    private Button button;

    /**
     *
     */
    private int rowCount;
    private int colCount;
    public Group[][] field;
    private Board board;
    private List<Integer> playMoveRow;
    private List<Integer> playMoveCol;

    /**
     *
     * @param board
     */
    public void initialize(Board board) {
        this.board = board;
        colCount = board.getColCount();
        rowCount = board.getRowCount();
        playMoveRow = new ArrayList<>();
        playMoveCol = new ArrayList<>();
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
                    if(!testForEqual(currentRow,currentCol)) {
                        Rectangle crossTileOne = new Rectangle(-24, -2, 50, 5);
                        Rectangle crossTileTwo = new Rectangle(-2, -24, 5, 50);
                        crossTileOne.setRotate(45);
                        crossTileTwo.setRotate(45);
                        crossTileOne.setFill(Color.BLACK);
                        crossTileTwo.setFill(Color.BLACK);
                        group.getChildren().add(crossTileOne);
                        group.getChildren().add(crossTileTwo);
                        playMoveRow.add(currentRow);
                        playMoveCol.add(currentCol);
                    }
                });
                group.getChildren().add(rectangle);
                field[i][j] = group;
                containerH.getChildren().add(group);
            }
            containerV.getChildren().add(containerH);
        }
        board.addObserver((PropertyChangeEvent evt) -> updateField());
    }

    /**
     *
     * @param list
     * @return
     */
    private int[] toIntArray(List<Integer> list) {
        int[] array = new int[list.size()];
        for(int i = 0;i < array.length;i++)
            array[i] = list.get(i);
        return array;
    }

    //TODO Methode funktioniert noch nicht vollständig
    /**
     *
      * @param currentRow
     * @param currentCol
     * @return
     */
    private boolean testForEqual(int currentRow, int currentCol) {
        for(int i = 0; i < playMoveRow.size(); i++) {
            if(playMoveRow.get(i).equals(currentRow) && playMoveCol.get(i).equals(currentCol)) {
                System.out.print(playMoveRow.get(i) + ", " + playMoveCol.get(i));
                System.out.println(" ist schon angekreuzt");
                return true;
            }
        }
        return false;
    }

    /**
     *
     */
    private void updateField() {
    }

    /**
     *
     * @param actionEvent
     */
    public void buttonClicked(ActionEvent actionEvent) {
        if(board.validate(toIntArray(playMoveRow),toIntArray(playMoveCol))) {
            System.out.println("Valid");
            updateField();
            playMoveRow.clear();
            playMoveCol.clear();
        } else {
            removeCrosses();
        }
    }

    /**
     *
     */
    private void removeCrosses() {
    }
}

