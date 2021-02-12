package de.techfak.se.mmoebius.util;

import de.techfak.se.mmoebius.model.Dice;
import de.techfak.se.multiplayer.server.response_body.PlayerResponse;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import java.util.List;

/**
 *  The creater class is responsible for creating the multiplayer related objects
 *  in the GUI.
 */
public class Creater {

    private static final int DEFAULT_SPACING = 10;
    private static final int DICE_COUNT = 3;
    private static final Font BASIC_FONT = new Font(32);
    private static final int REC_X_CONST = -24;
    private static final int REC_Y_CONST = -24;
    private static final int REC_WIDTH_CONST = 48;
    private static final int REC_HEIGHT_CONST = 48;
    private static final int POINT_SPACING = 30;
    private static final int[] POINT_ARR = {5, 3, 3, 3, 2, 2, 2, 1, 2, 2, 2, 3, 3, 3, 5};
    private static final int MAX_PLAYERS = 5;
    private static final Color[] COLORS = {Color.BLUE, Color.YELLOW, Color.GREEN, Color.ORANGE, Color.RED};
    private static final int COLOR_COUNT = 5;
    private static final BorderStrokeStyle SOLID = BorderStrokeStyle.SOLID;
    private static final BorderWidths THIN = BorderStroke.THIN;
    private static final BorderStroke BORDER_STROKE_D = new BorderStroke(Color.BLACK, SOLID, CornerRadii.EMPTY, THIN);

    public Creater() {

    }

    /**
     * The createDices method is similar to the throw dices method, but instead of
     * generating new dices it get the dices as parameters.
     * @param diceArr the array of dices to be showed in the GUI.
     * @param numbers the number array.
     * @param colors the color array.
     * @param diceN the label where the dice numbers are shown.
     * @param diceC the rectangle where the dice colors are shown.
     * @param dices the Hbox where both above are shown.
     */
    public void createDices(Dice[] diceArr, int[] numbers, Color[] colors, Label diceN, Rectangle diceC, HBox dices) {
        HBox diceColors = new HBox();
        HBox diceNumbers = new HBox();
        diceColors.setSpacing(DEFAULT_SPACING);
        diceNumbers.setSpacing(DEFAULT_SPACING);
        for (int i = 0; i < DICE_COUNT; i++) {
            Dice dice = diceArr[i];
            numbers[i] = dice.getNumber();
            colors[i] = dice.getColor();
            diceN = new Label(String.valueOf(numbers[i]));
            diceN.setFont(BASIC_FONT);
            diceN.setBorder(new Border(BORDER_STROKE_D));
            diceC = new Rectangle(REC_X_CONST, REC_Y_CONST, REC_WIDTH_CONST, REC_HEIGHT_CONST);
            diceC.setFill(colors[i]);
            diceC.setStrokeType(StrokeType.INSIDE);
            diceC.setStroke(Color.BLACK);
            diceColors.getChildren().add(diceC);
            diceNumbers.getChildren().add(diceN);
        }
        dices.getChildren().clear();
        dices.getChildren().add(diceColors);
        dices.getChildren().add(diceNumbers);
    }

    /**
     * The createPointLabels method creates Labels below the playing field
     * showing the points you get for filling the corresponding columns.
     * @param colCount the amount of columns in the playing field.
     * @param containerV the root pane.
     */
    public void createPointLabels(int colCount, VBox containerV) {
        HBox pointsH = new HBox();
        pointsH.setSpacing(POINT_SPACING);
        pointsH.setPadding(new Insets(DEFAULT_SPACING));
        try {
            for (int i = 0; i < colCount; i++) {
                Label pointLabel = new Label(String.valueOf(POINT_ARR[i]));
                pointLabel.setFont(BASIC_FONT);
                pointsH.getChildren().add(pointLabel);
            }
            containerV.getChildren().add(pointsH);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("POINT_ARR in Controller/Score class needs to be adjusted to column length.");
            System.out.println("Terminating program...");
            Platform.exit();
        }
    }

    /**
     * The createNameList method creates the list of names for every player
     * in multiplayer mode.
     * @param playerList the list of all players in the game.
     * @param multiplayerInfo the Hbox where the names and points are shown.
     */
    public void createNameList(List<PlayerResponse> playerList, HBox multiplayerInfo) {
        if (playerList != null) {
            Label header = new Label("Players:");
            header.setFont(BASIC_FONT);
            VBox names = new VBox(header);
            names.setPadding(new Insets(DEFAULT_SPACING));
            names.setSpacing(DEFAULT_SPACING);
            if (playerList.size() <= MAX_PLAYERS) {
                for (int i = 0; i < playerList.size(); i++) {
                    Label name = new Label(playerList.get(i).getName());
                    name.setFont(BASIC_FONT);
                    names.getChildren().add(name);
                }
                multiplayerInfo.getChildren().add(names);
            } else {
                System.out.println("Maximum of Players reached. Only 5 Players allowed");
                for (int i = 0; i < MAX_PLAYERS; i++) {
                    Label name = new Label(playerList.get(i).getName());
                    name.setFont(BASIC_FONT);
                    names.getChildren().add(name);
                }
                multiplayerInfo.getChildren().add(names);
            }
        }
    }

    /**
     * The createPointList method creates the list of points for every player
     * in multiplayer mode.
     * @param playerList the list of all players in the game.
     * @param multiplayerInfo the Hbox where the names and points are shown.
     */
    public void createPointList(List<PlayerResponse> playerList, HBox multiplayerInfo) {
        if (playerList != null) {
            Label header = new Label("Points:");
            header.setFont(BASIC_FONT);
            VBox pointsAll = new VBox(header);
            pointsAll.setPadding(new Insets(DEFAULT_SPACING));
            pointsAll.setSpacing(DEFAULT_SPACING);
            if (playerList.size() < MAX_PLAYERS) {
                for (int i = 0; i < playerList.size(); i++) {
                    Label thisPoints = new Label(String.valueOf(playerList.get(i).getPoints()));
                    thisPoints.setFont(BASIC_FONT);
                    pointsAll.getChildren().add(thisPoints);
                }
                multiplayerInfo.getChildren().add(pointsAll);
            } else {
                for (int i = 0; i < MAX_PLAYERS; i++) {
                    Label thisPoints = new Label(String.valueOf(playerList.get(i).getPoints()));
                    thisPoints.setFont(BASIC_FONT);
                    pointsAll.getChildren().add(thisPoints);
                }
                multiplayerInfo.getChildren().add(pointsAll);
            }
        }
    }

    /**
     * The createColorLabels method creates Rectangles below the playing field
     * showing all the Colors of the playing field. If all fields with the same
     * color are crossed, they will be highlighted.
     * @param containerV the root pane.
     */
    public void createColorLabels(VBox containerV) {
        HBox colorsH = new HBox();
        colorsH.setSpacing(POINT_SPACING);
        colorsH.setPadding(new Insets(DEFAULT_SPACING));
        try {
            for (int i = 0; i < COLOR_COUNT; i++) {
                Rectangle colorLabel = new Rectangle(REC_X_CONST, REC_Y_CONST, REC_WIDTH_CONST, REC_HEIGHT_CONST);
                colorLabel.setFill(COLORS[i]);
                colorLabel.setStroke(Color.BLACK);
                colorLabel.setStrokeType(StrokeType.INSIDE);
                colorsH.getChildren().add(colorLabel);
            }
            containerV.getChildren().add(colorsH);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ColorArray/Count in Controller/Score class needs to be adjusted to column length.");
            Platform.exit();
        }
    }

    /**
     *  The throwDices method creates a dice instances and visualizes
     *  the color and the number in the application.
     * @param numbers the number array.
     * @param colors the color array.
     * @param diceN the label where the dice numbers are shown.
     * @param diceC the rectangle where the dice colors are shown.
     * @param dices the Hbox where both above are shown.
     */
    public void throwDices(int[] numbers, Color[] colors, Label diceN, Rectangle diceC, HBox dices) {
        HBox diceColors = new HBox();
        HBox diceNumbers = new HBox();
        diceColors.setSpacing(DEFAULT_SPACING);
        diceNumbers.setSpacing(DEFAULT_SPACING);
        for (int i = 0; i < DICE_COUNT; i++) {
            Dice dice = new Dice();
            numbers[i] = dice.getNumber();
            colors[i] = dice.getColor();
            diceN = new Label(String.valueOf(numbers[i]));
            diceN.setFont(BASIC_FONT);
            diceN.setBorder(new Border(BORDER_STROKE_D));
            diceC = new Rectangle(REC_X_CONST, REC_Y_CONST, REC_WIDTH_CONST, REC_HEIGHT_CONST);
            diceC.setFill(colors[i]);
            diceC.setStrokeType(StrokeType.INSIDE);
            diceC.setStroke(Color.BLACK);
            diceColors.getChildren().add(diceC);
            diceNumbers.getChildren().add(diceN);
        }
        dices.getChildren().clear();
        dices.getChildren().add(diceColors);
        dices.getChildren().add(diceNumbers);
    }
}


