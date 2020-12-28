package de.techfak.se.mmoebius.controller;

import de.techfak.se.mmoebius.model.Board;
import de.techfak.se.mmoebius.model.Player;
import de.techfak.se.mmoebius.model.Score;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    private static final int CROSS_X_CONST = -24;
    private static final int CROSS_Y_CONST = -2;
    private static final int CROSS_W = 50;
    private static final int CROSS_H = 5;
    private static final int ROTATE_CONST = 45;

    @FXML
    private VBox containerV;

    @FXML
    private Button button;

    /**
     *
     */
    private int rowCount;
    private int colCount;
    private Group[][] field;
    private Board board;
    private Score score;
    private Player player;
    private List<Integer> playMoveRow;
    private List<Integer> playMoveCol;

    /**
     *
     * @param board
     */
    public void initialize(Board board) {
        this.board = board;
        player = new Player(1, board);
        score = new Score(player);
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
                    if (!testForEqual(currentRow, currentCol)) {
                        Rectangle crossTileOne = new Rectangle(CROSS_X_CONST, CROSS_Y_CONST, CROSS_W, CROSS_H);
                        Rectangle crossTileTwo = new Rectangle(CROSS_Y_CONST, CROSS_X_CONST, CROSS_H, CROSS_W);
                        crossTileOne.setRotate(ROTATE_CONST);
                        crossTileTwo.setRotate(ROTATE_CONST);
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
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
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
        for (int i = 0; i < playMoveRow.size(); i++) {
            if (playMoveRow.get(i).equals(currentRow) && playMoveCol.get(i).equals(currentCol)) {
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
        //TODO evtl implementieren. Vielleicht unsinnig
    }

    /**
     *
     * @param actionEvent
     */
    public void buttonClicked(ActionEvent actionEvent) {
        if (board.validate(toIntArray(playMoveRow), toIntArray(playMoveCol))) {
            board.printBoard();
            if (score.testIfFinished(board)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(null);
                alert.setContentText("The game is over");
                alert.showAndWait();
                while(true) {
                    //TODO Anders implementieren
                }
            }
            playMoveRow.clear();
            playMoveCol.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Turn");
            alert.setHeaderText("The chosen move is invalid");
            alert.setContentText("Crosses will be removed, try again.");
            alert.showAndWait();
            removeCrosses();
            playMoveRow.clear();
            playMoveCol.clear();
        }
    }

    /**
     *
     */
    private void removeCrosses() {
        for (int i = 0; i < playMoveRow.size(); i++) {
            field[playMoveRow.get(i)][playMoveCol.get(i)].getChildren().remove(2);
            field[playMoveRow.get(i)][playMoveCol.get(i)].getChildren().remove(1);
        }
    }

    //TODO Score handling und Finish handling
    //TODO Javadoc + Checkstyle !!

}

