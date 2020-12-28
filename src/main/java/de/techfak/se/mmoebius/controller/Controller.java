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
    private static final BorderWidths WIDTH = BorderStroke.DEFAULT_WIDTHS;
    private static final BorderStroke BORDER_STROKE = new BorderStroke(Color.BLACK, SOLID , CornerRadii.EMPTY, WIDTH);
    private static final Font BASIC_FONT = new Font(32);
    private static final int POINTS_FOR_COLOR = 5;
    private static final int POINTS_FOR_AO = 5;
    private static final int POINTS_FOR_BCD_LMN = 3;
    private static final int POINTS_FOR_EFG_IJK = 2;
    private static final int POINTS_FOR_H = 1;

    @FXML
    private VBox containerV;

    @FXML
    private Button button;

    @FXML
    private HBox dices;

    @FXML
    private Rectangle diceOneColor;

    @FXML
    private Rectangle diceTwoColor;

    @FXML
    private Rectangle diceThreeColor;

    @FXML
    private Label diceOneNumber;

    @FXML
    private Label diceTwoNumber;

    @FXML
    private Label diceThreeNumber;
    private Label pointsForA;
    private Label pointsForB;
    private Label pointsForC;
    private Label pointsForD;
    private Label pointsForE;
    private Label pointsForF;
    private Label pointsForG;
    private Label pointsForH;
    private Label pointsForI;
    private Label pointsForJ;
    private Label pointsForK;
    private Label pointsForL;
    private Label pointsForM;
    private Label pointsForN;
    private Label pointsForO;

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
        pointsForA = new Label(String.valueOf(POINTS_FOR_AO));
        pointsForB = new Label(String.valueOf(POINTS_FOR_BCD_LMN));
        pointsForC = new Label(String.valueOf(POINTS_FOR_BCD_LMN));
        pointsForD= new Label(String.valueOf(POINTS_FOR_BCD_LMN));
        pointsForE = new Label(String.valueOf(POINTS_FOR_EFG_IJK));
        pointsForF = new Label(String.valueOf(POINTS_FOR_EFG_IJK));
        pointsForG = new Label(String.valueOf(POINTS_FOR_EFG_IJK));
        pointsForH = new Label(String.valueOf(POINTS_FOR_H));
        pointsForI = new Label(String.valueOf(POINTS_FOR_EFG_IJK));
        pointsForJ = new Label(String.valueOf(POINTS_FOR_EFG_IJK));
        pointsForK = new Label(String.valueOf(POINTS_FOR_EFG_IJK));
        pointsForL = new Label(String.valueOf(POINTS_FOR_BCD_LMN));
        pointsForM = new Label(String.valueOf(POINTS_FOR_BCD_LMN));
        pointsForN = new Label(String.valueOf(POINTS_FOR_BCD_LMN));
        pointsForO = new Label(String.valueOf(POINTS_FOR_AO));
        pointsForA.setFont(BASIC_FONT);
        pointsForB.setFont(BASIC_FONT);
        pointsForC.setFont(BASIC_FONT);
        pointsForD.setFont(BASIC_FONT);
        pointsForE.setFont(BASIC_FONT);
        pointsForF.setFont(BASIC_FONT);
        pointsForG.setFont(BASIC_FONT);
        pointsForH.setFont(BASIC_FONT);
        pointsForI.setFont(BASIC_FONT);
        pointsForJ.setFont(BASIC_FONT);
        pointsForK.setFont(BASIC_FONT);
        pointsForL.setFont(BASIC_FONT);
        pointsForM.setFont(BASIC_FONT);
        pointsForN.setFont(BASIC_FONT);
        pointsForO.setFont(BASIC_FONT);
        HBox points = new HBox(pointsForA,pointsForB,pointsForC,pointsForD,pointsForE,pointsForF,pointsForG,pointsForH);
        points.setSpacing(30);
        points.setPadding(new Insets(10));
        points.getChildren().add(pointsForI);
        points.getChildren().add(pointsForJ);
        points.getChildren().add(pointsForK);
        points.getChildren().add(pointsForL);
        points.getChildren().add(pointsForM);
        points.getChildren().add(pointsForN);
        points.getChildren().add(pointsForO);
        containerV.getChildren().add(points);
    }

    /**
     *
     */
    private void throwDices() {
        for (int i = 0; i < DICE_COUNT; i++) {
            Dice dice = new Dice();
            numbers[i] = dice.getNumber();
            System.out.print(dice.getNumber());
            colors[i] = dice.getColor();
            System.out.println(" " + dice.getColor());
        }
        diceOneNumber.setText(String.valueOf(numbers[0]));
        diceTwoNumber.setText(String.valueOf(numbers[1]));
        diceThreeNumber.setText(String.valueOf(numbers[2]));
        diceOneNumber.setFont(BASIC_FONT);
        diceTwoNumber.setFont(BASIC_FONT);
        diceThreeNumber.setFont(BASIC_FONT);
        diceOneNumber.setBorder(new Border(BORDER_STROKE));
        diceTwoNumber.setBorder(new Border(BORDER_STROKE));
        diceThreeNumber.setBorder(new Border(BORDER_STROKE));
        diceOneColor.setFill(colors[0]);
        diceTwoColor.setFill(colors[1]);
        diceThreeColor.setFill(colors[2]);
        diceOneColor.setStroke(Color.BLACK);
        diceOneColor.setStrokeType(StrokeType.INSIDE);
        diceTwoColor.setStroke(Color.BLACK);
        diceTwoColor.setStrokeType(StrokeType.INSIDE);
        diceThreeColor.setStroke(Color.BLACK);
        diceThreeColor.setStrokeType(StrokeType.INSIDE);
        //TODO verschönern
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
        if (playMoveRow.isEmpty() && playMoveCol.isEmpty()) {
            System.out.println("Passing play move");
            throwDices();
        } else {
            if (board.validate(toIntArray(playMoveRow), toIntArray(playMoveCol), numbers, colors)) {
                board.printBoard();
                updateColors();
                updateColumns();
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
    private void updateColumns() {
        int[] completeCols = score.getCompleteCols(board);
        if(completeCols[0] == 0) {
            return;
        } else {
            for (int i = 0; i < completeCols.length; i++) {
                if (completeCols[i] == 1) {
                    pointsForA.setTextFill(Color.GOLD);
                } else if (completeCols[i] == 2) {
                    pointsForB.setTextFill(Color.GOLD);
                } else if (completeCols[i] == 3) {
                    pointsForC.setTextFill(Color.GOLD);
                } else if (completeCols[i] == 4) {
                    pointsForD.setTextFill(Color.GOLD);
                } else if (completeCols[i] == 5) {
                    pointsForE.setTextFill(Color.GOLD);
                } else if (completeCols[i] == 6) {
                    pointsForF.setTextFill(Color.GOLD);
                } else if (completeCols[i] == 7) {
                    pointsForG.setTextFill(Color.GOLD);
                } else if (completeCols[i] == 8) {
                    pointsForH.setTextFill(Color.GOLD);
                } else if (completeCols[i] == 9) {
                    pointsForI.setTextFill(Color.GOLD);
                } else if (completeCols[i] == 10) {
                    pointsForJ.setTextFill(Color.GOLD);
                } else if (completeCols[i] == 11) {
                    pointsForK.setTextFill(Color.GOLD);
                } else if (completeCols[i] == 12) {
                    pointsForL.setTextFill(Color.GOLD);
                } else if (completeCols[i] == 13) {
                    pointsForM.setTextFill(Color.GOLD);
                } else if (completeCols[i] == 14) {
                    pointsForN.setTextFill(Color.GOLD);
                } else if (completeCols[i] == 15) {
                    pointsForO.setTextFill(Color.GOLD);
                }
            }
        }
    }

    /**
     *
     */
    private void updateColors() {
        Color[] completeColors = new Color[colors.length];
        int completeColorCount = 0;
        for (int i = 0; i < colors.length; i++) {
            if (score.colorCountCrossed(board,colors[i]) == score.colorCount(board,colors[i])) {
                completeColors[completeColorCount] = colors[i];
                completeColorCount++;
            }
        }
        if (completeColorCount == 0) {
            return;
        }
        for (int l = 0; l < completeColors.length; l++)
            for (int i = 0; i < field.length; i++) {
                for (int k = 0; k < field[i].length; k++) {
                    if (board.floor[i][k].getColor().equals(completeColors[l])) {
                        Node node = field[i][k].getChildren().get(0);
                        if (node instanceof Rectangle) {
                            ((Rectangle)node).setFill(Color.DARKVIOLET);
                        }
                    }
                }
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

    //TODO Javadoc + Checkstyle !!
    /*TODO Fragen im Tut:   -Nicht mehr weiterspielen (Spielende)
                            -Controller zu viel View?
                            -Sachen in .FXML unten einfügen, da initalize Rechtecke (Punkte unten)
                            -Updatefield + oberserver. Wie sinnvoll benutzen?
     */


}

