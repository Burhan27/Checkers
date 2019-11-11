import java.util.Scanner;

public class GameLogic {

    String[][] board;

    public GameLogic(String[][] board) {

        this.board = board;

        gameFlow(board);

    }

    public void gameFlow(String[][] board){
        int turn = 0;
        Scanner scanner = new Scanner(System.in);
        int input, boardinputX, boardinputY, playerX, playerY, moveinputX, moveinputY;

        System.out.print("Tast 1 for at være 1 spiller, eller tast 2 for at være 2 spiller");

        input = scanner.nextInt();

        while(!(input > 0 && input < 3)) {
            System.out.print("Tast enten 1 og 2");
            input = scanner.nextInt();
        }

        if (input == 2) {
            turn++;
        }

        while(1 > 0) {
            turn++;

            if((turn % 2) == 0) {
                System.out.println("bote");
            } else {
                System.out.print("Tast to tal  0-7 (Den brik du gerne vil rykke)");
                boardinputX = 900;
                boardinputY = 900;
                while((!(boardinputY > -1 && boardinputY < 8 && boardinputX < 8 && boardinputX > -1))) {
                    boardinputX = scanner.nextInt();
                    boardinputY = scanner.nextInt();
                    System.out.print(boardinputX +" " + boardinputY);
                    if(boardinputY > -1 && boardinputY < 8 && boardinputX < 8 && boardinputX > -1) {
                        while(!board[boardinputX][boardinputY].equals("b")) {
                            System.out.print("Vælg et felt, som ikke er optaget!");
                            boardinputX = scanner.nextInt();
                            boardinputY = scanner.nextInt();
                        }
                        playerX = boardinputX;
                        playerY = boardinputY;
                        break;
                    } else {
                        System.out.println("Vælg to tal mellem 0 og 7!");
                    }
                }

                System.out.print("Tast to tal  0-7 (Den feltet du gerne vil rykke hen tile)");



            }

            printBoard(board);
        }

    }


    boolean checkMove(int positionX, int positionY, int moveX, int moveY, String[][] State) {
        return false;
    }

    String getWinner(String [][] state) {
        int countW = 0, countB = 0;

        for(int i = 0; i < state.length; i ++) {
            for(int j = 0; j < state[i].length; i++) {
                if (state[i][j].equals("w")) {
                    countW++;
                } else if (state[i][j].equals("b")) {
                    countB++;
                }
            }
        }
        if(countW == 0) {
            return "Black";
        } else if (countB == 0) {
            return "White";
        }
        return "";
    }

    void printBoard(String[][] board) {
        for(String[] x : board) {
            for(String y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }





    String[][] placeMove(int positionX, int positonY, String[][] Board, String player) {
        Board[positionX][positonY] = player;
        return Board;
    }

}
