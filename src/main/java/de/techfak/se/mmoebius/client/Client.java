package de.techfak.se.mmoebius.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.techfak.se.multiplayer.game.*;
import de.techfak.se.multiplayer.game.Board;
import de.techfak.se.multiplayer.game.Player;
import de.techfak.se.multiplayer.server.response_body.BoardResponse;
import de.techfak.se.multiplayer.server.response_body.PlayerListResponse;
import de.techfak.se.multiplayer.server.response_body.PlayerResponse;
import de.techfak.se.multiplayer.server.response_body.StatusResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Client {

    private static final int STATUS_SUCCESS = 200;

    /**
     *
     */
    protected boolean isServerStarted;
    protected HttpClient httpClient;
    protected ObjectMapper objectMapper;

    /**
     *
     */
    private String url;

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
            System.out.println("Error in Server connection");
            return false;
        }
    }

    public List<PlayerResponse> getPlayerList(String name) {
        if (name == null) {
            return null;
        }
        HttpResponse<String> response;
        PlayerListResponse playerListResponse;
        List<PlayerResponse> playerList;
        PlayerResponse playerResponse;
        List<String> playerNameList = new ArrayList<String>();
        String path = url + "/api/game/players?name=" + URLEncoder.encode(name, Charset.defaultCharset());
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
            System.out.println("Error in Server connection");
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
            System.out.println(response.body() + " " + response.statusCode());
            return response.statusCode();
        } catch (IOException | InterruptedException e) {
            System.out.println("Adding player failed.");
            return -1;
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
        String path = url + "/api/game/status?name=" + URLEncoder.encode(name, Charset.defaultCharset());
        System.out.println(path);
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
            System.out.println("Error in Server connection");
            return true;
        }
    }

    public GameStatus startGame(String name) {
        HttpResponse<String> response;
        try {
            response = post("/api/game/status", GameStatus.RUNNING);
            System.out.println(response.body() + " " + response.statusCode());
            return null;
        } catch (IOException | InterruptedException e) {
            System.out.println("Game cannot be started.");
            return null;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean deletePlayer(String name) {
        HttpResponse<String> response;
        String path = url + "/api/game/players?name=";
        response = delete(path, name);
        if (response != null) {
            if (response.statusCode() == STATUS_SUCCESS) {
                System.out.println("Player deleted.");
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println("Error in Server connection");
            return false;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public String getBoard(String name) {
        HttpResponse<String> response;
        String path = url + "/api/game/board?name=" + URLEncoder.encode(name, Charset.defaultCharset());
        response = get(path);
        System.out.println(response.body());
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
            System.out.println("Error in Server connection");
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
}
