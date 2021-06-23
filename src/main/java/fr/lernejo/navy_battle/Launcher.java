package fr.lernejo.navy_battle;

import java.io.IOException;
import java.util.Arrays;

public class Launcher {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = Integer.parseInt(args[0]);
        var server = new StartServer(port);
        if (args.length > 1)
            new NewInstance().New(port, args[1]);
    }
}
