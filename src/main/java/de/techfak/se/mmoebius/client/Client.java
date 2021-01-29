package de.techfak.se.mmoebius.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.techfak.se.mmoebius.model.Dice;
import de.techfak.se.multiplayer.game.*;
import de.techfak.se.multiplayer.game.Number;
import de.techfak.se.multiplayer.server.request_body.EndRoundBody;
import de.techfak.se.multiplayer.server.request_body.StatusBody;
import de.techfak.se.multiplayer.server.response_body.*;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.List;

/**
 * The client class provides all the methods to communicate to the server.
 */
public class Client {

    private static final int STATUS_SUCCESS = 200;
    private static final String DEFAULT_SERVER_COM_FAILED = "Error in Server connection";
    private static final String PATH_TO_STATUS = "/api/game/status?name=";
    private static final String PATH_TO_PLAYER = "/api/game/players?name=";
    private static final String PATH_TO_BOARD = "/api/game/board?name=";
    private static final String PATH_TO_ROUND = "/api/game/round?name=";
    private static final String PATH_TO_DICE = "/api/game/dice?name=";
    private static final int NUMBER_THREE = 3;
    private static final int NUMBER_FOUR = 4;
    private static final int NUMBER_FIVE = 5;

    /**
     * the client which does the http requests.
     */
    protected HttpClient httpClient;

    /**
     * the objectmapper which serialises and deserialises the jsons.
     */
    protected ObjectMapper objectMapper;

    /**
     * the url of the server.
     */
    private String url;

    /**
     * The constructor for a client given an url.
     * @param url the url of the server.
     */
    public Client(String url) {
        this.url = url;
        this.objectMapper = new ObjectMapper();
        httpClient = HttpClient.newHttpClient();
    }

//---------------------------------------Methods using post/delete-------------------------------------------

    //---------------------------------------Game-------------------------------------------
    /**
     * The changeGameStatus method send a request to the server to change the
     * game status of the server.
     * @param name the name of the player who makes the request.
     * @param gameStatus the new game status to be changed.
     * @return the new game status of the server. Is null if the request failed.
     */
    public GameStatus changeGameStatus(String name, GameStatus gameStatus) {
        HttpResponse<String> response;
        StatusResponse statusResponse;
        try {
            StatusBody statusBody = new StatusBody(gameStatus, name);
            response = post("/api/game/status", statusBody);
            statusResponse = objectMapper.readValue(response.body(), StatusResponse.class);
            if (response.statusCode() != STATUS_SUCCESS) {
                return null;
            }
            return statusResponse.getStatus();
        } catch (IOException | InterruptedException e) {
            System.out.println(DEFAULT_SERVER_COM_FAILED);
            return null;
        }
    }

    //---------------------------------------Players-------------------------------------------
    /**
     * The verifyName method registers the name given in the server
     * using a post request.
     * @param name the name to be registered.
     * @return returns the status code of the request or -1 if an error occurs.
     */
    public int verifyName(String name) {
        HttpResponse<String> response;
        try {
            response = post("/api/game/players", name);
            return response.statusCode();
        } catch (IOException | InterruptedException e) {
            System.out.println("Adding player failed.");
            return -1;
        }
    }


    /**
     * The deletePlayer method sends a delete request to the server
     * to delete a player from the register.
     * @param name the name of the player to be deleted.
     * @return true if it was successful and false if not.
     */
    public boolean deletePlayer(String name) {
        HttpResponse<String> response;
        String path = url + PATH_TO_PLAYER;
        response = delete(path, name);
        if (response != null) {
            if (response.statusCode() == STATUS_SUCCESS) {
                System.out.println("Player deleted.");
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println(DEFAULT_SERVER_COM_FAILED);
            return false;
        }
    }

    //---------------------------------------Round-------------------------------------------

    /**
     * The change round method ends the round for the given player
     * by doing a post request with the player name and points of the player.
     * @param name the name of the player.
     * @param points the total points of the player.
     * @return the round in which the game is in or -1 if an error occured.
     */
    public int changeRound(String name, int points) {
        HttpResponse<String> response;
        RoundResponse roundResponse;
        try {
            EndRoundBody roundBody = new EndRoundBody(name, points);
            response = post("/api/game/round", roundBody);
            roundResponse = objectMapper.readValue(response.body(), RoundResponse.class);
            if (response.statusCode() == STATUS_SUCCESS) {
                return roundResponse.getRound();
            }
            return roundResponse.getRound();
        } catch (IOException | InterruptedException e) {
            System.out.println(DEFAULT_SERVER_COM_FAILED);
            return -1;
        }
    }



//---------------------------------------Methods using get-------------------------------------------

    /**
     * The connectToServer method takes the given url and tries to connect to
     * the server at this address.
     * @return true if the server approachable false if not.
     */
    public boolean connectToServer() {
        HttpResponse<String> response;
        response = get(url);
        if (response != null) {
            if (response.statusCode() == STATUS_SUCCESS && response.body().equals("Encore")) {
                System.out.println("Connection to server established.");
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println(DEFAULT_SERVER_COM_FAILED);
            return false;
        }
    }

    /**
     * the isGameStarted method send a request to the server to get
     * the game status. If the status shows that the game has started it returns true.
     * @param name the name of the player.
     * @return true if the game is started, false if not.
     */
    public boolean isGameStarted(String name) {
        HttpResponse<String> response;
        StatusResponse gameStatus;
        GameStatus status = null;
        String path = url + PATH_TO_STATUS + URLEncoder.encode(name, Charset.defaultCharset());
        response = get(path);
        if (response != null) {
            try {
                gameStatus = objectMapper.readValue(response.body(), StatusResponse.class);
                status = gameStatus.getStatus();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return true;
            }
            if (response.statusCode() == STATUS_SUCCESS && status.equals(GameStatus.NOT_STARTED)) {
                System.out.println("Game has not started yet.");
                return false;
            } else {
                return true;
            }
        } else {
            System.out.println(DEFAULT_SERVER_COM_FAILED);
            return true;
        }
    }

    /**
     * The getServerStatus requests the status from the server and return it.
     * @param name the player doing the request.
     * @return the current game status or null if an error occurred.
     */
    public GameStatus getServerStatus(String name) {
        HttpResponse<String> response;
        StatusResponse gameStatus;
        GameStatus status;
        String path = url + PATH_TO_STATUS + URLEncoder.encode(name, Charset.defaultCharset());
        response = get(path);
        if (response != null) {
            try {
                gameStatus = objectMapper.readValue(response.body(), StatusResponse.class);
                status = gameStatus.getStatus();
                if (response.statusCode() == STATUS_SUCCESS) {
                    return status;
                } else {
                    return null;
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println(DEFAULT_SERVER_COM_FAILED);
            return null;
        }
    }

    /**
     * the getBoard method requests the board from server.
     * @param name the name of the player doing the request.
     * @return the board as a string or null if an error occurred.
     */
    public String getBoard(String name) {
        HttpResponse<String> response;
        String path = url + PATH_TO_BOARD + URLEncoder.encode(name, Charset.defaultCharset());
        response = get(path);
        BoardResponse boardResponse = null;
        String board = null;
        if (response != null) {
            try {
                boardResponse = objectMapper.readValue(response.body(), BoardResponse.class);
                board = boardResponse.getBoard();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
            if (response.statusCode() == STATUS_SUCCESS) {
                return board;
            } else {
                return null;
            }
        } else {
            System.out.println(DEFAULT_SERVER_COM_FAILED);
            return null;
        }
    }

    /**
     * The getRound method requests the current round from the server.
     * @param name the name of the player doing the request.
     * @return the current round or 0 if an error occured.
     */
    public int getRound(String name) {
        HttpResponse<String> response;
        String path = url + PATH_TO_ROUND + URLEncoder.encode(name, Charset.defaultCharset());
        response = get(path);
        RoundResponse roundResponse;
        int round;
        if (response != null) {
            try {
                roundResponse = objectMapper.readValue(response.body(), RoundResponse.class);
                round = roundResponse.getRound();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return 0;
            }
            if (response.statusCode() == STATUS_SUCCESS) {
                return round;
            } else {
                return 0;
            }
        } else {
            System.out.println(DEFAULT_SERVER_COM_FAILED);
            return 0;
        }
    }

    /**
     * The getDices method requests the dices from the server.
     * @param name the name of the player sending the request.
     * @return an array with the dices or null if an error occurred.
     */
    public Dice[] getDices(String name) {
        HttpResponse<String> response;
        String path = url + PATH_TO_DICE + URLEncoder.encode(name, Charset.defaultCharset());
        response = get(path);
        DiceResponse diceResponse;
        List<Color> colors;
        List<Number> numbers;
        Dice[] dices;
        if (response != null) {
            try {
                diceResponse = objectMapper.readValue(response.body(), DiceResponse.class);
                colors = diceResponse.getColors();
                numbers = diceResponse.getNumbers();
                dices = toDiceModel(colors, numbers);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
            if (response.statusCode() == STATUS_SUCCESS) {
                return dices;
            } else {
                return null;
            }
        } else {
            System.out.println(DEFAULT_SERVER_COM_FAILED);
            return null;
        }
    }

    /**
     * The getPlayerList method request a list of all current players
     * from the server.
     * @param name the name of the player sending the request.
     * @return the list of players or null if an error occured.
     */
    public List<PlayerResponse> getPlayerList(String name) {
        if (name == null) {
            return null;
        }
        HttpResponse<String> response;
        PlayerListResponse playerListResponse;
        List<PlayerResponse> playerList;
        String path = url + PATH_TO_PLAYER + URLEncoder.encode(name, Charset.defaultCharset());
        response = get(path);
        if (response != null) {
            if (response.statusCode() == STATUS_SUCCESS) {
                try {
                    playerListResponse = objectMapper.readValue(response.body(), PlayerListResponse.class);
                    playerList = playerListResponse.getPlayers();
                    return playerList;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
        } else {
            System.out.println(DEFAULT_SERVER_COM_FAILED);
            return null;
        }
    }

//---------------------------------------Get Post Delete Methods-------------------------------------------
    /**
     * The get methods does a get request to the server.
     * @param url the url of the server.
     * @return the response to the request.
     */
    public HttpResponse<String> get(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * The post methods sends a post request to the server.
     * @param path the path to the aimed directory on the server.
     * @param parameters the json object containing the things to be changed.
     * @return the response to the request.
     * @throws IOException if an error occurs.
     * @throws InterruptedException if the request was interrupted.
     */
    public HttpResponse<String> post(String path, Object parameters) throws IOException, InterruptedException {
        String jsonBody = objectMapper.writeValueAsString(parameters);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url + path))
                                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    /**
     * The delete method sends a delete request to the server.
     * @param path the path to the aimed directory on the server.
     * @param name the name of the player doing the delete request.
     * @return the response to the request.
     */
    public HttpResponse<String> delete(String path, String name) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(path + URLEncoder.encode(name, Charset.defaultCharset())))
                .DELETE()
                .build();
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IllegalArgumentException | IOException e) {
            return null;
        }
    }

//---------------------------------------Auxiliary Methods-------------------------------------------

    /**
     * The toDiceModel method takes the Dices from the Server and converts
     * them to dices of the model, so they can be used in the GUI.
     * @param colors the colors of the dices from the server.
     * @param numbers the numbers of the dices from the server.
     * @return an array of the new dices from the model.
     */
    private Dice[] toDiceModel(List<Color> colors, List<Number> numbers) {
        javafx.scene.paint.Color[] colorArr = new javafx.scene.paint.Color[colors.size()];
        int[] numberArr = new int[numbers.size()];
        for (int i = 0; i < colors.size(); i++) {
            Color current = colors.get(i);
            switch (current) {
                case GREEN:
                    colorArr[i] = javafx.scene.paint.Color.GREEN;
                    break;
                case RED:
                    colorArr[i] = javafx.scene.paint.Color.RED;
                    break;
                case BLUE:
                    colorArr[i] = javafx.scene.paint.Color.BLUE;
                    break;
                case YELLOW:
                    colorArr[i] = javafx.scene.paint.Color.YELLOW;
                    break;
                default:
                    colorArr[i] = javafx.scene.paint.Color.ORANGE;
                    break;
            }
        }
        for (int j = 0; j < numbers.size(); j++) {
            Number current = numbers.get(j);
            switch (current) {
                case ONE:
                    numberArr[j] = 1;
                    break;
                case TWO:
                    numberArr[j] = 2;
                    break;
                case THREE:
                    numberArr[j] = NUMBER_THREE;
                    break;
                case FOUR:
                    numberArr[j] = NUMBER_FOUR;
                    break;
                default:
                    numberArr[j] = NUMBER_FIVE;
                    break;
            }
        }
        Dice[] dices = new Dice[colors.size()];
        for (int k = 0; k < dices.length; k++) {
            dices[k] = new Dice(colorArr[k], numberArr[k]);
        }
        return dices;
    }

    public String getUrl() {
        return url;
    }

}

