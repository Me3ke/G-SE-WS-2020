package de.techfak.se.mmoebius.controller;

import de.techfak.se.mmoebius.model.Board;
import de.techfak.se.mmoebius.model.Dice;
import de.techfak.se.mmoebius.model.Player;
import de.techfak.se.mmoebius.model.Score;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;

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
    private static final int DICE_COUNT = 3;
    private static final BorderStrokeStyle SOLID = BorderStrokeStyle.SOLID;
    private static final BorderWidths THIN = BorderStroke.THIN;
    private static final BorderWidths THICK = BorderStroke.MEDIUM;
    private static final BorderStroke BORDER_STROKE_D = new BorderStroke(Color.BLACK, SOLID, CornerRadii.EMPTY, THIN);
    private static final BorderStroke BORDER_STROKE_P = new BorderStroke(Color.BLACK, SOLID, CornerRadii.EMPTY, THICK);
    private static final Font BASIC_FONT = new Font(32);
    private static final int[] POINT_ARR = {5, 3, 3, 3, 2, 2, 2, 1, 2, 2, 2, 3, 3, 3, 5};
    private static final int DEFAULT_SPACING = 10;
    private static final int POINT_SPACING = 30;

    @FXML
    private VBox containerV;

    @FXML
    private Button button;

    @FXML
    private HBox dices;

    @FXML
    private Label points;

    @FXML
    private Label diceNumber;

    @FXML
    private Rectangle diceColor;


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
    private int[] numbers;
    private Color[] colors;

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
        numbers = new int[DICE_COUNT];
        colors = new Color[DICE_COUNT];
        throwDices();
        points.setFont(BASIC_FONT);
        points.setBorder(new Border(BORDER_STROKE_P));
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
        createPointLabels();
        board.addObserver((PropertyChangeEvent evt) -> updateField());
    }

    /**
     *
     */
    private void createPointLabels() {
        //TODO über FXML?
        HBox pointsH = new HBox();
        pointsH.setSpacing(POINT_SPACING);
        pointsH.setPadding(new Insets(DEFAULT_SPACING));
        for (int i = 0; i < colCount; i++) {
            Label pointLabel = new Label(String.valueOf(POINT_ARR[i]));
            pointLabel.setFont(BASIC_FONT);
            pointsH.getChildren().add(pointLabel);
        }
        containerV.getChildren().add(pointsH);
    }

    /**
     *
     */
    private void throwDices() {
        HBox diceColors = new HBox();
        HBox diceNumbers = new HBox();
        for (int i = 0; i < DICE_COUNT; i++) {
            Dice dice = new Dice();
            numbers[i] = dice.getNumber();
            colors[i] = dice.getColor();
            diceNumber = new Label(String.valueOf(numbers[i]));
            diceNumber.setFont(BASIC_FONT);
            diceNumber.setBorder(new Border(BORDER_STROKE_D));
            diceColor = new Rectangle(REC_X_CONST, REC_Y_CONST, REC_WIDTH_CONST, REC_HEIGHT_CONST);
            diceColor.setFill(colors[i]);
            diceColor.setStrokeType(StrokeType.INSIDE);
            diceColor.setStroke(Color.BLACK);
            diceColors.getChildren().add(diceColor);
            diceNumbers.getChildren().add(diceNumber);
        }
        dices.getChildren().clear();
        dices.getChildren().add(diceColors);
        dices.getChildren().add(diceNumbers);
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
     * @param actionEvent
     */
    public void buttonClicked(ActionEvent actionEvent) {
        if (playMoveRow.isEmpty() && playMoveCol.isEmpty()) {
            System.out.println("Passing play move");
            throwDices();
        } else {
            if (board.validate(toIntArray(playMoveRow), toIntArray(playMoveCol), numbers, colors)) {
                board.printBoard();
                if (score.testIfFinished(board)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Game Over");
                    alert.setHeaderText(null);
                    alert.setContentText("The game is over");
                    alert.showAndWait();
                    while (true) {
                        //TODO Anders implementieren (Spielende)
                    }
                }
                playMoveRow.clear();
                playMoveCol.clear();
                throwDices();
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
    }

    /**
     *
     */
    private void updateField() {
        updateColors();
        updateColumns();
        updatePoints();
    }

    /**
     *
     */
    private void updateColors() {
        Color[] completeColors = new Color[colors.length];
        int completeColorCount = 0;
        for (int i = 0; i < colors.length; i++) {
            if (score.colorCountCrossed(board, colors[i]) == score.colorCount(board, colors[i])) {
                completeColors[completeColorCount] = colors[i];
                completeColorCount++;
            }
        }
        if (completeColorCount == 0) {
            return;
        }
        for (int l = 0; l < completeColors.length; l++) {
            for (int i = 0; i < field.length; i++) {
                for (int k = 0; k < field[i].length; k++) {
                    if (board.floor[i][k].getColor().equals(completeColors[l])) {
                        Node node = field[i][k].getChildren().get(0);
                        if (node instanceof Rectangle) {
                            ((Rectangle) node) .setFill(Color.DARKVIOLET);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     */
    private void updateColumns() {
        int[] completeCols = score.getCompleteCols(board);
        int containerSize = containerV.getChildren().size();
        if (completeCols[0] == 0) {
            return;
        } else {
            for (int i = 0; i < completeCols.length; i++) {
                if (completeCols[i] != 0) {
                    Node nodeOut = containerV.getChildren().get(containerSize - 1);
                    if (nodeOut instanceof HBox) {
                        Node nodeIn = ((HBox) nodeOut).getChildren().get(completeCols[i] - 1);
                        if (nodeIn instanceof Label) {
                            ((Label) nodeIn).setTextFill(Color.GOLD);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     */
    private void updatePoints() {
        int currentPoints = score.calculatePoints(board);
        points.setText("Points: " + currentPoints);
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

    //TODO Javadoc + Checkstyle !!
    /*TODO Fragen im Tut:   -Nicht mehr weiterspielen (Spielende)
                            -Controller zu viel View?
                            -Sachen in .FXML unten einfügen, da initalize Rechtecke (Punkte unten)
                            -Updatefield + oberserver. Wie sinnvoll benutzen?
     */


}

