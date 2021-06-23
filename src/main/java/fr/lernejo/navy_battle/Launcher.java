package fr.lernejo.navy_battle;

import java.io.IOException;
import java.util.Arrays;

public class Launcher {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        /*if (args.length > 1)
            System.out.println("test recup arg : " + args[1]);*/
        new StartServer().Start(port);
    }
}
