package de.techfak.se.mmoebius.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.techfak.se.multiplayer.game.*;
import de.techfak.se.multiplayer.game.Player;
import de.techfak.se.multiplayer.server.response_body.StatusResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 */
public class Client {

    /**
     *
     */
    public String url;
    protected boolean isServerStarted;
    protected HttpClient httpClient;
    protected ObjectMapper objectMapper;
    private String ip;
    private int port;

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
            if(response.statusCode() == 200 && response.body().equals("Encore")) {
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

    /**
     *
     * @param name
     */
    public int verifyName(String name) {
        HttpResponse<String> response;
        try {
            response = post("/api/game/players", name);
            System.out.println(response.body() + " " + response.statusCode() );
            return response.statusCode();
        }catch (IOException | InterruptedException e) {
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
        response = get(url + "/api/game/status?name=" + name);
        if (response != null) {
            try {
                gameStatus = objectMapper.readValue(response.body(), StatusResponse.class);
                status = gameStatus.getStatus();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            if(response.statusCode() == 200 && status.equals(GameStatus.NOT_STARTED)) {
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

    public HttpResponse<String> post(String path, Object parameters) throws IOException, InterruptedException {
        String jsonBody = objectMapper.writeValueAsString(parameters);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url + path)).POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
}
