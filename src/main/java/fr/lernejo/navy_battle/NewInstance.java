package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NewInstance {

    public final void New(int myPort, String adversaryUrl) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest requestPost = HttpRequest.newBuilder()
            .uri(URI.create(adversaryUrl + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + myPort + "\", \"message\":\"I will crush you!\"}"))
            .build();

        HttpResponse<String> response = client.send(requestPost, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        HttpRequest requestApiFire = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?ll=A1")).GET().build();
        response = client.send(requestApiFire, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }
}
