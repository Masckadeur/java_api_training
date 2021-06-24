package fr.lernejo.navy_battle;

public class JsonFireHandlerProp {
    public final String cell;
    public final int row;
    public final int col;

    public JsonFireHandlerProp(String cell) {
        this.cell = cell;
        this.row = cell.charAt(1) - '0' - 1;
        this.col = cell.charAt(0) - 'A';
    }
}
