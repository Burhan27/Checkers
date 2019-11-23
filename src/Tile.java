public class Tile {

    boolean isDark;
    boolean isEndTile;
    Piece piece;
    String graphic;

    public Tile(boolean isDark, boolean isEndTile, Piece piece, String graphic) {
        this.isDark = isDark;
        this.isEndTile = isEndTile;
        this.piece = piece;
        this.graphic = graphic;
    }

    public boolean isDark() {
        return isDark;
    }

    public boolean isEndTile() {
        return isEndTile;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {

        if(piece == null){
            graphic = " - ";
        }
        else {
            this.piece = piece;
            graphic = piece.graphic;
        }
    }

    public String getGraphic() {
        return graphic;
    }

    public void setGraphic(String graphic) {
        this.graphic = graphic;
    }
}
