package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;

import java.net.http.HttpClient;

public class FireRespond {

    public void Fire(HttpExchange exchange) {
        HttpClient client = HttpClient.newHttpClient();

        String param = exchange.getRequestURI().toString().substring(exchange.getRequestURI().toString().indexOf("?") + 1);
    }
}
