package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class CustomStartHandler implements HttpHandler {

    private final StringBuilder url = new StringBuilder();

    CustomStartHandler(int port) { this.url.append("http://localhost:").append(port); }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) { NotFoundMethod(exchange); }
        else {
            JsonStartHandlerProp requestJson = ParseBody(exchange);
            if (requestJson == null || requestJson.message.equals("\"\"") || requestJson.id.equals("\"\"") || requestJson.url.equals("\"\"")) { SendResponse(exchange, "Bad Json", 400); }
            else { SendResponse(exchange, "{\n\t\"id\":\"" + UUID.randomUUID() + "\",\n\t\"url\":\"" + this.url + "\",\n\t\"message\":\"May the best code win\"\n}", 202); }
            var Game = new StartGame(requestJson, requestJson);
            try { Game.LaunchGame();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private void SendResponse(HttpExchange exchange, String s, int rcode) throws IOException {
        exchange.sendResponseHeaders(rcode, s.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(s.getBytes());
        }
    }

    private void NotFoundMethod(HttpExchange exchange) throws IOException {
        String error = "TNull";
        exchange.sendResponseHeaders(404, error.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(error.getBytes());
        }
    }

    private String ConvertInputStreamtoString(InputStream s) throws IOException {
        StringBuilder sb = new StringBuilder();
        int c;
        while ((c = s.read()) > 0) {
            sb.append((char) c);
        }
        return sb.toString();
    }

    private JsonStartHandlerProp ParseBody(HttpExchange exchange) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonStartHandlerProp requestJson = null;
        String sb = ConvertInputStreamtoString(exchange.getRequestBody());
        if (sb.isBlank()) { return null; }
        else {
            try {
                requestJson = mapper.readValue(sb, JsonStartHandlerProp.class);
            } catch (IllegalArgumentException e) {
                exchange.sendResponseHeaders(400, "Bad request".length());
                throw new IllegalArgumentException();
            }
        }
        return requestJson;
    }
}
