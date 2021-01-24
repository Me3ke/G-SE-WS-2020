package de.techfak.se.mmoebius.model;

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
    protected boolean isServerStarted = false;

    /**
     *
     * @param ip
     * @param port
     * @return
     */
    public boolean connectToServer(String ip, int port) {
        HttpResponse<String> response;
        response = get(ip + port);
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
     * @param url
     * @return
     */
    public HttpResponse<String> get(String url) {
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            return null;
        }
    }

    public boolean isServerStarted() {
        return isServerStarted;
    }

    public void setServerStarted(boolean serverStarted) {
        isServerStarted = serverStarted;
    }
}
