package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class CustomFireHandler implements HttpHandler {
    final Cell map;

    CustomFireHandler() { this.map = new Cell(); }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("GET"))
            NotFoundMethod(exchange);

        var JsonProp = Parser(exchange);
        if (JsonProp != null) {
            int statusBoat = CheckBoat(JsonProp);
            if (statusBoat == 0) { //miss case
                SendResponse(exchange, "{\n\t\"consequence\": \"miss\",\n\t\"shipLeft\": true\n}", 200);
            } else if (statusBoat == 2) { //Hit case
                SendResponse(exchange, "{\n\t\"consequence\": \"hit\",\n\t\"shipLeft\": true\n}", 200);
            } else { ShipLeaft(exchange); } //sunk case
            //try { new FireRespond().Fire(exchange); //envoie ça cible à l'autre
            //} catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private void ShipLeaft(HttpExchange exchange) throws IOException {
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                if (map.Sea[i][j] != 0) {
                    SendResponse(exchange, "{\n\t\"consequence\": \"sunk\",\n\t\"shipLeft\": true\n}", 200);
                    return;
                }
            }
        }
        SendResponse(exchange, "{\n\t\"consequence\": \"sunk\",\n\t\"shipLeft\": false\n}", 200);
    }

    private int CheckBoat(JsonFireHandlerProp JsonProp) {
        if (map.Sea[JsonProp.row][JsonProp.col] == 0) {
            return 0;
        }
        else {
            var sb = new SunkenBoat();
            int statusBoat = sb.sunkenBoat(map, JsonProp);
            map.Sea[JsonProp.row][JsonProp.col] = 0;
            if (statusBoat == 0)
                return 1;
            else
                return 2;
        }
    }

    private void SendResponse(HttpExchange exchange, String s, int rcode) throws IOException {
        exchange.sendResponseHeaders(rcode, s.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(s.getBytes());
        }
    }

    private JsonFireHandlerProp Parser(HttpExchange exchange) throws IOException {
        String param = exchange.getRequestURI().getQuery();
        final int length = param.substring(param.indexOf("=") + 1).length();
        if (length < 2) {
            SendResponse(exchange, "Wrong Param", 404);
            return null;
        }
        final int number = param.substring(param.indexOf("=") + 1).charAt(1) - '0';
        if (length > 3 || (length == 3 && !param.substring(param.indexOf("=") + 2).equals("10")) || param.substring(param.indexOf("=") + 1).charAt(0) > 'J' || number == 0 || number > 10) {
            SendResponse(exchange, "Wrong Param", 404);
            return null;
        }
        else
            return new JsonFireHandlerProp(param.substring(param.indexOf("=") + 1));
    }

    private void NotFoundMethod(HttpExchange exchange) throws IOException {
        String error = "TNull";
        exchange.sendResponseHeaders(404, error.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(error.getBytes());
        }
    }
}
