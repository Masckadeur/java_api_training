package fr.lernejo.navy_battle;

import java.util.ArrayList;

/*
1 -> 1 porte-avion de 5 cases
2 -> 1 croiseur de 4 cases
3 -> 2 contre-torpilleurs de 3 cases
4 -> 1 torpilleur de 2 cases
 */

public class Cell {
    public final Integer[][] Sea = new Integer[10][10];

    Cell() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (j == 0 && i <= 4) { this.Sea[i][j] = 1; }
                else if (j == 3 && i >= 6) { this.Sea[i][j] = 2; }
                else if (j == 6 && i <= 2) { this.Sea[i][j] = 3;}
                else if (i == 5 && (j >= 5 && j <= 7)) { this.Sea[i][j] = 3;}
                else if (i == 2 && (j == 8 || j == 9)) { this.Sea[i][j] = 4; }
                else { this.Sea[i][j] = 0; }
            }
        }
    }
}
