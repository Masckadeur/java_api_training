package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CustomStartHandler implements HttpHandler {

    private final StringBuilder url = new StringBuilder();

    CustomStartHandler(int port) { this.url.append("http://localhost:").append(port); }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equals("POST")) {
            NotFoundMethod(exchange);
        }
        else {
            APOD requestJson = ParseBody(exchange);

            if (requestJson.message.equals("\"\"") || requestJson.id.equals("\"\"") || requestJson.url.equals("\"\"")) {
                exchange.sendResponseHeaders(400, "Bad Json".length());
                try (OutputStream os = exchange.getResponseBody()) { // (1)
                    os.write("Bad Json".getBytes());
                }
            }
            else {
                StringBuilder test = new StringBuilder();
                String msg = "May the best code win";
                test.append("{\n\t\"id\":\"000\",\n\t\"url\":\"").append(this.url).append("\",\n\t\"message\":\"").append(msg).append("\"\n}");

                exchange.sendResponseHeaders(202, test.toString().length());
                try (OutputStream os = exchange.getResponseBody()) { // (1)
                    os.write(test.toString().getBytes());
                }
            }
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

    private APOD ParseBody(HttpExchange exchange) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        APOD requestJson = null;
        String Body = "Bad request";

        String sb = ConvertInputStreamtoString(exchange.getRequestBody());

        try {
            requestJson = mapper.readValue(sb, APOD.class);
        } catch (IllegalArgumentException e) {
            exchange.sendResponseHeaders(400, Body.length());
            throw new IllegalArgumentException();
        }

        return requestJson;
    }
}
