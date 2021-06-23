package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class StartServer {
    public final void Start(int port) throws IOException {
            InetSocketAddress soc = new InetSocketAddress(port);
            HttpServer server = HttpServer.create(soc, 0);

            server.setExecutor(Executors.newSingleThreadExecutor());

            server.createContext("/ping", new CustomPingHandler());
            server.createContext("/api/game/start", new CustomStartHandler(port));
            server.start();
    }
}
