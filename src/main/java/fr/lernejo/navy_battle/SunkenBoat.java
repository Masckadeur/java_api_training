package fr.lernejo.navy_battle;

public class SunkenBoat {

    public int sunkenBoat(Cell map, JsonFireHandlerProp JsonProp) {
        if (map.Sea[JsonProp.row][JsonProp.col] == 1 && SunkenBoatAircraftCarrier(map) == 0) {
            return 0;
        }
        else if (map.Sea[JsonProp.row][JsonProp.col] == 2 && SunkenBoatCruiser(map) == 0)
            return 0;
        else if (map.Sea[JsonProp.row][JsonProp.col] == 3 && SunkenBoatDestroyers(map) == 0)
            return 0;
        else if (map.Sea[JsonProp.row][JsonProp.col] == 4 && SunkenBoatTorpedo(map) == 0)
            return 0;

        return 1;
    }

    private int SunkenBoatTorpedo(Cell map) {
        int PartOfBoat = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map.Sea[i][j] == 4)
                    PartOfBoat += 1;
            }
        }
        if (PartOfBoat == 1)
            return 0;
        return 1;
    }

    private int SunkenBoatDestroyers(Cell map) {
        int PartOfBoat = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map.Sea[i][j] == 3) {
                    PartOfBoat += 1;
                }
            }
        }
        if (PartOfBoat == 1) {
            return 0;
        }
        else if (PartOfBoat == 4) {
            return SunkenBoatDestroyersCheck(map);
        }
        return 1;
    }

    private int SunkenBoatDestroyersCheck(Cell map) {
        for (int i = 0; i < 10; i++) { for (int j = 0; j < 10; j++) {
            if (map.Sea[i][j] == 3 && (j > 0 && j < 9 && (map.Sea[i][j - 1] != 3 || map.Sea[i][j + 1] != 3))) {
                if (i > 0 && i < 9 && (map.Sea[i - 1][j] != 3 || map.Sea[i + 1][j] != 3)) { return 0; }
                else if ((i == 0 && map.Sea[i + 1][j] != 3) || (i == 9 && map.Sea[i - 1][j] != 3)) { return 0; }
            }
            else if (map.Sea[i][j] == 3 && (j == 0 && map.Sea[i][j + 1] != 3) || (j == 9 && map.Sea[i][j - 1] != 3)) {
                if (i > 0 && i < 9 && (map.Sea[i - 1][j] != 3 || map.Sea[i + 1][j] != 3)) { return 0; }
                else if ((i == 0 && map.Sea[i + 1][j] != 3) || (i == 9 && map.Sea[i - 1][j] != 3)) { return 0; }
            }
        } }
        return 1;
    }

    private int SunkenBoatAircraftCarrier(Cell map) {
        int PartOfBoat = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map.Sea[i][j] == 1)
                    PartOfBoat += 1;
            }
        }
        if (PartOfBoat == 1)
            return 0;
        return 1;
    }

    private int SunkenBoatCruiser(Cell map) {
        int PartOfBoat = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map.Sea[i][j] == 2)
                    PartOfBoat += 1;
            }
        }
        if (PartOfBoat == 1)
            return 0;
        return 1;
    }
}
