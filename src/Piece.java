public class Piece {

    Boolean isKing;
    String graphic;
    int value = 10;
    int placement;

    public Piece(Boolean isKing, String graphic,int placement) {
        this.isKing = isKing;
        this.graphic = graphic;
        this.placement = placement;
    }

    public Boolean getKing() {
        return isKing;
    }

    public void setKing(Boolean king) {
        isKing = king;
        if(king == true) {
            setGraphic(graphic + "K");
        }
    }

    public String getGraphic() {
        return graphic;
    }

    public void setGraphic(String graphic) {
        this.graphic = graphic;
    }

    public void setValue(int value) {
        value = value;
    }

    public int getValue(){
        return value;
    }
}