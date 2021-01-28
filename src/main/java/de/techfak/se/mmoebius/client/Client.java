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
 *
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
     *
     */
    protected boolean isServerStarted;

    /**
     *
     */
    protected HttpClient httpClient;

    /**
     *
     */
    protected ObjectMapper objectMapper;

    /**
     *
     */
    private String url;

    /**
     *
     * @param url
     */
    public Client(String url) {
        this.url = url;
        this.objectMapper = new ObjectMapper();
        httpClient = HttpClient.newHttpClient();
    }

    /**
     *
     * @return
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
     *
     * @param name
     * @return
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
     *
     * @param name
     * @return
     */
    public GameStatus startGame(String name) {
        HttpResponse<String> response;
        StatusResponse statusResponse;
        String encodedName = URLEncoder.encode(name, Charset.defaultCharset());
        try {
            StatusBody statusBody = new StatusBody(GameStatus.RUNNING, encodedName);
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

    /**
     *
     * @param name
     * @return
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

    public int changeRound(String name, int points) {
        HttpResponse<String> response;
        RoundResponse roundResponse;
        String encodedName = URLEncoder.encode(name, Charset.defaultCharset());
        try {
            EndRoundBody roundBody = new EndRoundBody(encodedName, points);
            response = post("/api/game/round", roundBody);
            roundResponse = objectMapper.readValue(response.body(), RoundResponse.class);
            if (response.statusCode() != STATUS_SUCCESS) {
                return 0;
            }
            return roundResponse.getRound();
        } catch (IOException | InterruptedException e) {
            System.out.println(DEFAULT_SERVER_COM_FAILED);
            return 0;
        }
    }

    /**
     *
     * @param name
     * @return
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

    /**
     *
     * @param name
     * @return
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
     *
     * @param name
     * @return
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
                System.out.println("Got board.");
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
     *
     * @param name
     * @return
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
     *
     * @param name
     * @return
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
     *
     * @param name
     * @return
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
     *
     * @param url
     * @return
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
     *
     * @param path
     * @param parameters
     * @return
     * @throws
     * @throws
     */
    public HttpResponse<String> post(String path, Object parameters) throws IOException, InterruptedException {
        String jsonBody = objectMapper.writeValueAsString(parameters);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url + path))
                                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    /**
     *
     * @param path
     * @param name
     * @return
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
     *
     * @param colors
     * @param numbers
     * @return
     */
    private Dice[] toDiceModel(List<Color> colors, List<Number> numbers) {
        javafx.scene.paint.Color[] colorArr = new javafx.scene.paint.Color[colors.size()];
        int[] numberArr = new int[numbers.size()];
        for (int i = 0; i < colors.size(); i++) {
            Color current = colors.get(i);
            switch(current) {
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
            switch(current) {
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

//TODO Playerdelete Synchronisieren
//TODO gucken was noch Synchronisiert werden muss
