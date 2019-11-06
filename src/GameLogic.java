public class GameLogic {

    String[][] board;

    public GameLogic(String[][] board) {

        this.board = board;

    }

    public void gameFlow(){

    }

    public void turn(){
        
    }

    boolean isGameOver(){

        if(pcWin() || playerWin()){
            return true;
        }

        else return false;
    }

    boolean pcWin(){
        return false;
    }

    boolean playerWin(){
        return false;
    }

}
