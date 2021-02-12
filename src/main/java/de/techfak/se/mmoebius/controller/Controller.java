package de.techfak.se.mmoebius.controller;

import de.techfak.se.mmoebius.client.Client;
import de.techfak.se.mmoebius.model.*;
import de.techfak.se.mmoebius.util.Creater;
import de.techfak.se.mmoebius.util.Helper;
import de.techfak.se.mmoebius.view.EndScreen;
import de.techfak.se.multiplayer.game.GameStatus;
import de.techfak.se.multiplayer.server.response_body.PlayerResponse;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.stage.Stage;
import java.beans.PropertyChangeEvent;
import java.util.*;
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
    private static final BorderStrokeStyle SOLID = BorderStrokeStyle.SOLID;
    private static final BorderWidths THIN = BorderStroke.THIN;
    private static final BorderWidths THICK = BorderStroke.MEDIUM;
    private static final BorderStroke BORDER_STROKE_D = new BorderStroke(Color.BLACK, SOLID, CornerRadii.EMPTY, THIN);
    private static final BorderStroke BORDER_STROKE_P = new BorderStroke(Color.BLACK, SOLID, CornerRadii.EMPTY, THICK);
    private static final Font BASIC_FONT = new Font(32);
    private static final int DEFAULT_SPACING = 10;
    private static final double STROKE_WIDTH_COMPLETE = 8;
    private static final int CONTAINER_COLOR_LOCATION = 11;
    private static final int CONTAINER_COLUMN_LOCATION = 10;
    private static final long ROUND_QUERY_DEFAULT_TIME = 500;
    private static final String ROUND = "Round: ";
    private static final String PASS = "Passing play move";

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
     *  url: the url of the server.
     *  client: the client of the game.
     *  name: the name of the player.
     *  multiplayerInfo: a hbox in which the multiplayer infos are.
     *  gameStatusInfo: the status of the current game.
     *  round: the current round.
     *  isSinglePlayer: a boolean which displays if the current mode is singleplayer.
     *  isBlocked: a boolean which displays if the playing field may be crossed.
     *  exec: the executor which executes the threads.
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
    private boolean isBlocked;
    private ScheduledExecutorService exec;
    private Helper helper;
    private Creater creater;

    /**
     * the initialize method creates the playing field.
     * It initializes all the attributes of the class and
     * visualizes the board with rectangles in appropriate colors,
     * the point scale, the current points and the dices.
     * Additionally handles a click on one of the tiles by crossing them.
     * @param board The board is initialized in the GUI class where a game
     *              starts. It is the current board to be played on.
     * @param url   The url from the server the client is connected with.
     * @param name  The name of the player of the current client.
     */
    public void initialize(Board board, String url, String name) {
        helper = new Helper();
        multiplayerInfo = new HBox();
        gameStatusInfo = GameStatus.NOT_STARTED;
        gameStatusLabel.setText(gameStatusInfo.name());
        gameStatusLabel.setFont(BASIC_FONT);
        this.name = name;
        this.url = url;
        button.setDisable(true);
        isSinglePlayer = false;
        isBlocked = true;
        if (url.equals("")) {
            isSinglePlayer = true;
            startGame.setDisable(true);
            button.setDisable(false);
            isBlocked = false;
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
                    if (!helper.testForEqual(field, currentRow, currentCol)) {
                        if (isSinglePlayer || !isBlocked) {
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
     * The createSurroundings method creates all the labels etc. which are
     * needed for a multiplayer game. If the current game is a single player game
     * only the dices, which are generated locally are shown.
     */
    private void createSurroundings() {
        creater = new Creater();
        if (isSinglePlayer) {
            creater.throwDices(numbers, colors, diceNumber, diceColor, dices);
        }
        creater.createPointLabels(colCount, containerV);
        creater.createColorLabels(containerV);
        List<PlayerResponse> playerList =  client.getPlayerList(name);
        creater.createNameList(playerList, multiplayerInfo);
        creater.createPointList(playerList, multiplayerInfo);
        createRunnable();
        containerV.getChildren().add(multiplayerInfo);
    }

    /**
     *  The createRunnable creates the threads which update the names, points, rounds
     *  and the game status. It also initialises the executor for them.
     */
    private void createRunnable() {
        Runnable pointsAndNames = new Runnable() {
            @Override public void run() {
                List<PlayerResponse> playerList =  client.getPlayerList(name);
                Platform.runLater(() -> {
                    multiplayerInfo.getChildren().clear();
                    creater.createPointList(playerList, multiplayerInfo);
                    creater.createNameList(playerList, multiplayerInfo);
                    Stage stage = (Stage) multiplayerInfo.getScene().getWindow();
                    stage.sizeToScene();
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
                        Platform.runLater(() -> {
                            startGame.setDisable(true);
                            button.setDisable(false);
                            gameStatusLabel.setTextFill(Color.GREEN);
                        });
                    } else if (gameStatusInfo.equals(GameStatus.NOT_STARTED)) {
                        Platform.runLater(() -> gameStatusLabel.setTextFill(Color.RED));
                    } else {
                        Platform.runLater(() -> {
                            gameStatusLabel.setTextFill(Color.BLUE);
                            int currentPoints = score.calculatePoints(board);
                            EndScreen endScreen = new EndScreen(helper, client, name, currentPoints, exec);
                            Platform.exit();
                        });
                    }
                } else {
                    System.out.println("Problem with current game status");
                    helper.end(client, name);
                }
            }
        };
        Runnable roundQuery = new Runnable() {
            @Override
            public void run() {
                int currentRound = client.getRound(name);
                if (currentRound != 0) {
                    if (currentRound != round) {
                        round = currentRound;
                        isBlocked = false;
                        Platform.runLater(() -> points.setText(ROUND + round));
                        Dice[] diceArr = client.getDices(name);
                        if (dices != null) {
                            Platform.runLater(() ->  {
                                creater.createDices(diceArr, numbers, colors, diceNumber, diceColor, dices);
                            });
                        }
                    }
                }
            }
        };
        exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(pointsAndNames, 0, 2, TimeUnit.SECONDS);
        exec.scheduleAtFixedRate(statusQuery, 0, 1, TimeUnit.SECONDS);
        exec.scheduleAtFixedRate(roundQuery, 0, ROUND_QUERY_DEFAULT_TIME, TimeUnit.MILLISECONDS);
    }

//---------------------------------------Button On Action Methods-------------------------------------------
    /**
     * The startGame method is called if the start game button is clicked.
     * It will send a request to the server to start the game. Enables the
     * check move button and disables the start game button if game started
     * normally. This initialises the first round unblocks the playing field.
     * It shows a dialog if something goes wrong.
     * @param actionEvent The event of the button clicked.
     */
    public void startGame(ActionEvent actionEvent) {
        boolean started = helper.startGame(gameStatusInfo, client, name);
        if (started) {
            startGame.setDisable(true);
            button.setDisable(false);
            isBlocked = false;
            Platform.runLater(() -> points.setText(ROUND + round));
        }
    }

    /**
     * The buttonClicked method handles a click on the "check move" button.
     * It gets the fields clicked in this move (playMoveRow, playMoveCol) and
     * uses the board.validate method to validate the inputs if there were some.
     * If the move was invalid it shows a dialog. After a move it rolls the dices again
     * if it is in single player mode. Otherwise it will send the results of the round
     * to the server.Furthermore it tests if the game is over and shows a dialog then.
     * @param actionEvent The event of the clicked Button.
     */
    public void buttonClicked(ActionEvent actionEvent) {
        if (playMoveRow.isEmpty() && playMoveCol.isEmpty()) {
            if (isBlocked) {
                System.out.println("doing nothing");
            } else if (isSinglePlayer) {
                System.out.println(PASS);
                creater.throwDices(numbers, colors, diceNumber, diceColor, dices);
            } else {
                System.out.println(PASS);
                int currentPoints = score.calculatePoints(board);
                int roundResponse = client.changeRound(name, currentPoints);
                if (roundResponse == -1) {
                    helper.end(client, name);
                }
                int currentRound = client.getRound(name);
                if (currentRound != 0) {
                    System.out.println(roundResponse);
                    isBlocked = true;
                } else {
                    helper.end(client, name);
                }
            }
        } else {
            if (board.validate(helper.toIntArray(playMoveRow), helper.toIntArray(playMoveCol), numbers, colors)) {
                board.printBoard();
                if (score.testIfFinished(board)) {
                    if (isSinglePlayer) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Game Over");
                        alert.setHeaderText("The game is over");
                        alert.setContentText("You achieved: " + updatePoints() + " points");
                        alert.showAndWait();
                        Platform.exit();
                    } else {
                        int currentPoints = score.calculatePoints(board);
                        int roundResponse = client.changeRound(name, currentPoints);
                        if (roundResponse == -1) {
                            helper.end(client, name);
                        }
                        isBlocked = true;
                        GameStatus endStatus = client.changeGameStatus(name, GameStatus.FINISHED);
                        if (!endStatus.equals(GameStatus.FINISHED)) {
                            System.out.println("Game could not be finished.");
                        }
                    }
                }
                if (isSinglePlayer) {
                    creater.throwDices(numbers, colors, diceNumber, diceColor, dices);
                } else {
                    int currentPoints = score.calculatePoints(board);
                    int roundResponse = client.changeRound(name, currentPoints);
                    if (roundResponse == -1) {
                        helper.end(client, name);
                    }
                    int currentRound = client.getRound(name);
                    if (currentRound != 0) {
                        isBlocked = true;
                    } else {
                        helper.end(client, name);
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Turn");
                alert.setHeaderText("The chosen move is invalid");
                alert.setContentText("Crosses will be removed, try again.");
                alert.showAndWait();
                helper.removeCrosses(field, playMoveRow, playMoveCol);
            }
            playMoveRow.clear();
            playMoveCol.clear();
        }
    }

//---------------------------------------Update Methods-------------------------------------------

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
        } else {
            return score.calculatePoints(board);
        }
    }
}

