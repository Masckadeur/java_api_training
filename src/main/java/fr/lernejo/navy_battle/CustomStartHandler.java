package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class CustomStartHandler implements HttpHandler {

    private final StringBuilder url = new StringBuilder();

    CustomStartHandler(int port) {
        this.url.append("http://localhost:").append(Integer.toString(port));
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equals("POST")) {
            System.out.println("Get method received");
            throw new IOException();
        }

        var s = exchange.getRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        APOD requestJson = null;
        String Body = "Bad request";

        StringBuilder sb = new StringBuilder();
        int c;
        while ((c = s.read()) > 0) {
            sb.append((char) c);
        }

        try {
            requestJson = mapper.readValue(sb.toString(), APOD.class);
        } catch (IllegalArgumentException e) {
            System.err.println("Wrong Json");
            exchange.sendResponseHeaders(400, Body.length());
            throw new IllegalArgumentException();
        }

        StringBuilder test = new StringBuilder();
        String msg = "May the best code win";
        test.append("{\n\t\"id\":\"000\",\n\t\"url\":\"")
            .append(this.url).append("\",\n\t\"message\":\"")
            .append(msg)
            .append("\"\n}");

        exchange.sendResponseHeaders(202, /*sb*/test.toString().length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(/*sb*/test.toString().getBytes());
        }

        /*JsonNode rb = mapper.readTree(sb.toString());
        APOD requestJson = new APOD(rb.get("id"), rb.get("url"), rb.get("message"));

        StringBuilder code = new StringBuilder();

        int rcode = 202;
        if (requestJson.id.isNull()) {
            System.err.println("no id in the request");
            code.append("Bad Request");
            rcode = 404;
        }
        else if (requestJson.url.isNull()) {
            System.err.println("no url in the request");
            code.append("Bad Request");
            rcode = 404;
        }
        else if (requestJson.message.isNull()) {
            System.err.println("no message in the request");
            code.append("Bad Request");
            rcode = 404;
        }
        else
            code.append("Accepted");

        exchange.sendResponseHeaders(rcode, code.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(code.toString().getBytes());
        }*/

    }
}
