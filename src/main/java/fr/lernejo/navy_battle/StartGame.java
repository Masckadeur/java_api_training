package fr.lernejo.navy_battle;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StartGame {
    final Cell map = new Cell();
    public final JsonStartHandlerProp AdversaryJsonProp;
    public final JsonStartHandlerProp MyJsonProp;

    StartGame(JsonStartHandlerProp adJsonProp, JsonStartHandlerProp myJsonProp) {
        this.AdversaryJsonProp = adJsonProp;
        this.MyJsonProp = myJsonProp;
    }

    public void LaunchGame() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(AdversaryJsonProp.url.substring(1, AdversaryJsonProp.url.length() - 1) + "/api/game/fire?cell=A1"))
            .setHeader("Accept", "application/json").setHeader("Content-Type", "application/json").GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
