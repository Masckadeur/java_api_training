package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class StartServer {
    final HttpServer server;

    StartServer(int port) throws IOException {
        InetSocketAddress soc = new InetSocketAddress(port);
        this.server = HttpServer.create(soc, 0);

        this.server.setExecutor(Executors.newSingleThreadExecutor());

        this.server.createContext("/ping", new CustomPingHandler());
        this.server.createContext("/api/game/start", new CustomStartHandler(port));
        this.server.createContext("/api/game/fire" , new CustomFireHandler());
        this.server.start();
    }

    public final void Stop() {
        this.server.stop(0);
    }
}
