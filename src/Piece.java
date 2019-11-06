public class Piece {

    Boolean isKing;
    String graphic;

    public Piece(Boolean isKing, String graphic) {
        this.isKing = isKing;
        this.graphic = graphic;
    }

    public Boolean getKing() {
        return isKing;
    }

    public void setKing(Boolean king) {
        isKing = king;
    }

    public String getGraphic() {
        return graphic;
    }

    public void setGraphic(String graphic) {
        this.graphic = graphic;
    }
}
