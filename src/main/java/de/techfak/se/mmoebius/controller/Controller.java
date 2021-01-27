package de.techfak.se.mmoebius.controller;

import de.techfak.se.mmoebius.client.Client;
import de.techfak.se.mmoebius.model.*;
import de.techfak.se.multiplayer.game.GameStatus;
import de.techfak.se.multiplayer.game.Round;
import de.techfak.se.multiplayer.server.response_body.PlayerResponse;
import javafx.application.Platform;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * The Controller class handles the interactions with the GUI
 * and does changes to the model.
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
    private static final int COLOR_COUNT = 5;
    private static final Color[] COLORS = {Color.BLUE, Color.YELLOW, Color.GREEN, Color.ORANGE, Color.RED};
    private static final BorderStrokeStyle SOLID = BorderStrokeStyle.SOLID;
    private static final BorderWidths THIN = BorderStroke.THIN;
    private static final BorderWidths THICK = BorderStroke.MEDIUM;
    private static final BorderStroke BORDER_STROKE_D = new BorderStroke(Color.BLACK, SOLID, CornerRadii.EMPTY, THIN);
    private static final BorderStroke BORDER_STROKE_P = new BorderStroke(Color.BLACK, SOLID, CornerRadii.EMPTY, THICK);
    private static final Font BASIC_FONT = new Font(32);
    private static final int[] POINT_ARR = {5, 3, 3, 3, 2, 2, 2, 1, 2, 2, 2, 3, 3, 3, 5};
    private static final int DEFAULT_SPACING = 10;
    private static final int POINT_SPACING = 30;
    private static final double STROKE_WIDTH_COMPLETE = 8;
    private static final int MAX_PLAYERS = 5;
    private static final int CONTAINER_COLOR_LOCATION = 11;
    private static final int CONTAINER_COLUMN_LOCATION = 10;

    /**
     * All the Objects here are initialized in the GUI.fxml.
     */
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

    @FXML
    private Button startGame;

    @FXML
    private Label gameStatusLabel;


    /**
     *  Controller Attributes:
     *  rowCount: the amount of rows on the current board.
     *  colCount: the amount of columns on the current board.
     *  field: a 2-dim group array. Every group is either a
     *  normal tile, or a crossed tile representing the board.
     *  board: the current board instance.
     *  score: the current score instance.
     *  player: the current player instance.
     *  playMoveRow: a list of integers containing the row numbers
     *  of the in the current play move crossed tiles.
     *  playMoveCol: a list of integers containing the column numbers
     *  of the in the current play move crossed tiles.
     *  numbers: an array of the numbers of the dices.
     *  colors: an array of the colors of the dices.
     *  ip:
     *  port:
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
    private String url;
    private Client client;
    private String name;
    private HBox multiplayerInfo;
    private GameStatus gameStatusInfo;
    private int round;
    private boolean isSinglePlayer;

    /**
     * the initialize method creates the playing field.
     * It initializes all the attributes of the class and
     * visualizes the board with rectangles in appropriate colors,
     * the point scale, the current points and the dices.
     * Additionally handles a click on one of the tiles by crossing them.
     * @param board The board is initialized in the GUI class where a game
     *              starts. It is the current board to be played on.
     * @param url
     * @param name
     */
    public void initialize(Board board, String url, String name) {
        multiplayerInfo = new HBox();
        gameStatusInfo = GameStatus.NOT_STARTED;
        gameStatusLabel.setText(gameStatusInfo.name());
        gameStatusLabel.setFont(BASIC_FONT);
        this.name = name;
        this.url = url;
        button.setDisable(true);
        isSinglePlayer = false;
        if (url.equals("")) {
            isSinglePlayer = true;
            startGame.setDisable(true);
            button.setDisable(false);
        }
        round = 0;
        client = new Client(url);
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
        points.setFont(BASIC_FONT);
        points.setBorder(new Border(BORDER_STROKE_P));
        for (int i = 0; i < rowCount; i++) {
            HBox containerH = new HBox();
            for (int j = 0; j < colCount; j++) {
                Rectangle rectangle = new Rectangle(REC_X_CONST, REC_Y_CONST, REC_WIDTH_CONST, REC_HEIGHT_CONST);
                rectangle.setFill(board.getFloor()[i][j].getColor());
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeType(StrokeType.INSIDE);
                Group group = new Group();
                final int currentRow = i;
                final int currentCol = j;
                rectangle.setOnMouseClicked(mouseEvent -> {
                    if (!testForEqual(currentRow, currentCol)) {
                        if (isSinglePlayer || gameStatusInfo.equals(GameStatus.RUNNING)) {
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
                    }
                });
                group.getChildren().add(rectangle);
                field[i][j] = group;
                containerH.getChildren().add(group);
            }
            containerV.getChildren().add(containerH);
        }
        createSurroundings();
        board.addObserver((PropertyChangeEvent evt) -> updateField());
    }

//---------------------------------------Create Methods-------------------------------------------

    /**
     *
     */
    private void createSurroundings() {
        if (isSinglePlayer) {
            throwDices();
        }
        createPointLabels();
        createColorLabels();
        List<PlayerResponse> playerList =  client.getPlayerList(name);
        createNameList(playerList);
        createPointList(playerList);
        createRunnables();
        containerV.getChildren().add(multiplayerInfo);
    }

    /**
     *
     */
    private void createRunnables() {
        Runnable pointsAndNames = new Runnable() {
            @Override public void run() {
                List<PlayerResponse> playerList =  client.getPlayerList(name);
                Platform.runLater(() -> {
                    multiplayerInfo.getChildren().clear();
                    createPointList(playerList);
                    createNameList(playerList);
                });
            }
        };
        Runnable statusQuery = new Runnable() {
            @Override
            public void run() {
                GameStatus currentGameStatus = client.getServerStatus(name);
                if (currentGameStatus != null) {
                    gameStatusInfo = currentGameStatus;
                    Platform.runLater(() -> gameStatusLabel.setText(gameStatusInfo.name()));
                    if (gameStatusInfo.equals(GameStatus.RUNNING)) {
                        Platform.runLater(() -> gameStatusLabel.setTextFill(Color.GREEN));
                    } else if (gameStatusInfo.equals(GameStatus.NOT_STARTED)) {
                        Platform.runLater(() -> gameStatusLabel.setTextFill(Color.RED));
                    } else {
                        //TODO farbe falls game finished
                    }
                } else {
                    System.out.println("Problem with current game status");
                }
            }
        };
        Runnable roundQuery = new Runnable() {
            @Override
            public void run() {
                int currentRound = client.getRound(name);
                if (currentRound != 0) {
                    round = currentRound;
                    Platform.runLater(() -> points.setText("Round: " + round));
                    Dice[] dices = client.getDices(name);
                    if (dices != null) {
                        Platform.runLater(() -> createDices(dices));
                    }
                }
            }
        };
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(pointsAndNames, 0, 1, TimeUnit.SECONDS);
        exec.scheduleAtFixedRate(statusQuery, 0, 1, TimeUnit.SECONDS);
        exec.scheduleAtFixedRate(roundQuery, 0, 1, TimeUnit.SECONDS);
        //TODO exec shutdown bei programmende?
    }

    private void createDices(Dice[] diceArr) {
        HBox diceColors = new HBox();
        HBox diceNumbers = new HBox();
        diceColors.setSpacing(DEFAULT_SPACING);
        diceNumbers.setSpacing(DEFAULT_SPACING);
        for (int i = 0; i < DICE_COUNT; i++) {
            Dice dice = diceArr[i];
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

    /**
     * The createPointLabels method creates Labels below the playing field
     * showing the points you get for filling the corresponding columns.
     */
    private void createPointLabels() {
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
     *
     * @param playerList
     */
    private void createNameList(List<PlayerResponse> playerList) {
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
     *
     * @param playerList
     */
    private void createPointList(List<PlayerResponse> playerList) {
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
     */
    private void createColorLabels() {
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

//---------------------------------------Button On Action Methods-------------------------------------------
    /**
     *
     * @param actionEvent
     */
    public void startGame(ActionEvent actionEvent) {
        Alert startAlert = new Alert(Alert.AlertType.ERROR);
        startAlert.setTitle("Server start");
        startAlert.setHeaderText(null);
        if (!client.isGameStarted(name)) {
            gameStatusInfo = client.startGame(name);
            if (gameStatusInfo != null) {
                System.out.println(gameStatusInfo.name());
                System.out.println("Game has been started");
                startGame.setDisable(true);
                button.setDisable(false);
                Platform.runLater(() -> points.setText("Round: " + round));
            } else {
                startAlert.setContentText("Game could not be started.");
                startAlert.showAndWait();
            }
        } else {
            startAlert.setContentText("Game already started.");
            startAlert.showAndWait();
        }
    }
//TODO throw dices nur bei Singleplayer sonst vom Server. Methode muss durchleuchtet werden
    /**
     * The buttonClicked method handles a click on the "check move" button.
     * It gets the fields clicked in this move (playMoveRow, playMoveCol) and
     * uses the board.validate method to validate the inputs if there were some.
     * If the move was invalid it shows a dialog. After a move it rolls the dices again.
     * Furthermore it tests if the game is over and shows a dialog then.
     * @param actionEvent The event of the clicked Button.
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
                    alert.setHeaderText("The game is over");
                    alert.setContentText("You achieved: " + updatePoints() + " points");
                    alert.showAndWait();
                    Platform.exit();
                }
                throwDices();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Turn");
                alert.setHeaderText("The chosen move is invalid");
                alert.setContentText("Crosses will be removed, try again.");
                alert.showAndWait();
                removeCrosses();
            }
            playMoveRow.clear();
            playMoveCol.clear();
        }
    }

//---------------------------------------Update Methods-------------------------------------------

    /**
     *  The throwDices method creates a dice instances and visualizes
     *  the color and the number in the application.
     */
    private void throwDices() {
        HBox diceColors = new HBox();
        HBox diceNumbers = new HBox();
        diceColors.setSpacing(DEFAULT_SPACING);
        diceNumbers.setSpacing(DEFAULT_SPACING);
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

    /**
     *  The update field method is called after a change on the model.board has been made.
     *  It adjusts the View by calling the methods.
     */
    private void updateField() {
        updateColors();
        updateColumns();
        updatePoints();
    }

    /**
     * The updateColors method uses the score.colorCountCrossed and the score.colorCount
     * methods to check for completely crossed colors. If there is one it changes the rectangles
     * below.
     */
    private void updateColors() {
        Color[] completeColors = new Color[colors.length];
        int containerSize = containerV.getChildren().size();
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
                    if (board.getFloor()[i][k].getColor().equals(completeColors[l])) {
                        Node nodeOut = containerV.getChildren().get(CONTAINER_COLOR_LOCATION);
                        if (nodeOut instanceof HBox) {
                            for (int j = 0; j < COLOR_COUNT; j++) {
                                Node nodeIn = ((HBox) nodeOut) .getChildren().get(j);
                                if (nodeIn instanceof Rectangle) {
                                    if (completeColors[l].equals(((Rectangle) nodeIn).getFill())) {
                                        ((Rectangle) nodeIn).setStroke(Color.DARKVIOLET);
                                        ((Rectangle) nodeIn).setStrokeWidth(STROKE_WIDTH_COMPLETE);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     *  The updateColumns method uses the score.getCompleteCols class to investigate
     *  if there are columns which are completely crossed. If this is the case, it changes
     *  the color of the corresponding label to gold.
     */
    private void updateColumns() {
        int[] completeCols = score.getCompleteCols(board);
        int containerSize = containerV.getChildren().size();
        if (completeCols[0] == 0) {
            return;
        } else {
            for (int i = 0; i < completeCols.length; i++) {
                if (completeCols[i] != 0) {
                    Node nodeOut = containerV.getChildren().get(CONTAINER_COLUMN_LOCATION);
                    if (nodeOut instanceof HBox) {
                        Node nodeIn = ((HBox) nodeOut).getChildren().get(completeCols[i] - 1);
                        if (nodeIn instanceof Label) {
                            Platform.runLater(() -> ((Label) nodeIn).setTextFill(Color.GOLD));
                        }
                    }
                }
            }
        }
    }

    /**
     * The updatePoints method uses the score.calculatePoints method to show the
     * current points of the player in the application.
     */
    private int updatePoints() {
        if (isSinglePlayer) {
            int currentPoints = score.calculatePoints(board);
            Platform.runLater(() -> points.setText("Points: " + currentPoints));
            return currentPoints;
        }
        return 0;
    }

//---------------------------------------Auxiliary Methods-------------------------------------------
    /**
     * The removeCrosses method removes the cross of a tile if a play move was
     * considered invalid.
     */
    private void removeCrosses() {
        for (int i = 0; i < playMoveRow.size(); i++) {
            field[playMoveRow.get(i)][playMoveCol.get(i)].getChildren().remove(2);
            field[playMoveRow.get(i)][playMoveCol.get(i)].getChildren().remove(1);
        }
    }

    /**
     * The testForEqual method checks if the current field the
     * player crossed, was already crossed beforehand.
     * @param currentRow The row of the tile to be crossed.
     * @param currentCol The column of the tile to be crossed.
     * @return  Returns true if the current field to be crossed was
     *          already crossed and false if not.
     */
    private boolean testForEqual(int currentRow, int currentCol) {
        if (field[currentRow][currentCol].getChildren().size() > 1) {
            System.out.println("Field " + currentRow + currentCol + " is already crossed.");
            return true;
        } else {
            return false;
        }
    }

    /**
     * The toIntArray is and auxiliary method to convert an list of integers
     * to an array of integers.
     * @param list The list to be converted.
     * @return The corresponding array.
     */
    private int[] toIntArray(List<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}

