package de.techfak.se.mmoebius.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.techfak.se.multiplayer.game.*;
import de.techfak.se.multiplayer.game.Player;
import de.techfak.se.multiplayer.server.response_body.PlayerResponse;
import de.techfak.se.multiplayer.server.response_body.ResponseObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;

/**
 *
 */
public class Client {

    /**
     *
     */
    public String url;
    protected boolean isServerStarted = false;
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
            //TODO IP Adresse zu url?
        } else {
            System.out.println("Error in Server connection");
            return false;
        }
    }

    /**
     *
     * @param name
     */
    public boolean verifyName(String name) {
        HttpResponse<String> response;
        try {
            response = post("/api/game/players", name);
            System.out.println(response.body() + " " + response.statusCode() );
        }catch (IOException | InterruptedException e) {
            System.out.println("Adding player failed.");
            return false;
        }
        return true;
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

    /**
     *
     * @param name
     * @return
     */
    private Player createPlayer(String name) {
        Player player = new PlayerImpl(new PlayerName(name));
        return player;
    }

    public boolean isServerStarted() {
        return isServerStarted;
    }

    public void setServerStarted(boolean serverStarted) {
        isServerStarted = serverStarted;
    }

}
