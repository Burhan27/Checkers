import java.util.ArrayList;
import java.util.Scanner;

public class GameLogic {

    ArrayList<Tile> board = new ArrayList<Tile>();
    final static int MAX_PIECES = 12;
    final static int BOARD_SIZE = 64;
    Scanner keyboard = new Scanner(System.in);

    public GameLogic() {

        board = generateBoard();
        printBoard();
    }

    private ArrayList<Tile> generateBoard() {
        int row_count;

        for (int i = 0; i < BOARD_SIZE; i++) {

            row_count = i / 8;
            if (row_count % 2 != 0) {
                if (i % 2 != 0) {
                    if (row_count < 3) {
                        if (row_count == 0) {
                            board.add(new Tile(true, true, new Piece(false, " B "), " B "));
                        } else board.add(new Tile(true, false, new Piece(false, " B "), " B "));
                    } else if (row_count > 4) {
                        if (row_count == 7) {
                            board.add(new Tile(true, true, new Piece(false, " W "), " W "));
                        } else board.add(new Tile(true, false, new Piece(false, " W "), " W "));
                    } else {
                        board.add(new Tile(true, false, null, " - "));
                    }
                } else board.add(new Tile(false, false, null, " - "));
            } else {
                if (i % 2 == 0) {
                    if (row_count < 3) {
                        if (row_count == 0) {
                            board.add(new Tile(true, true, new Piece(false, " B "), " B "));
                        } else board.add(new Tile(true, false, new Piece(false, " B "), " B "));
                    } else if (row_count > 4) {
                        if (row_count == 7) {
                            board.add(new Tile(true, true, new Piece(false, " W "), " W "));
                        } else board.add(new Tile(true, false, new Piece(false, " W "), " W "));
                    } else {
                        board.add(new Tile(true, false, null, " - "));
                    }
                } else board.add(new Tile(false, false, null, " - "));
            }
        }
        return board;
    }

    private void printBoard() {


        for (int i = 0; i <= BOARD_SIZE; i++) {
            if (i == 7 || i == 15 || i == 23 || i == 31 || i == 39 || i == 47 || i == 55 || i == 63) {

                System.out.print(" | " + board.get(i).getGraphic() + " | \n");
                System.out.println("----------------------------------------------------------");
            } else if (i == 0 || i == 8 || i == 16 || i == 24 || i == 32 || i == 40 || i == 48 || i == 56) {
                System.out.print((i / 8) + 1 + " | " + board.get(i).getGraphic() + " ");
            } else if (i == BOARD_SIZE) {
                System.out.print(" ");
                for (int j = 1; j < 9; j++) {
                    System.out.print(" |  " + j + "  ");
                }
                System.out.print("|");
            } else System.out.print(" | " + board.get(i).getGraphic() + " ");
        }
    }

    private void playerTurn() {
        int x, y, position;
        System.out.println("Tast x koordinatet for den brik du gerne vil rykke");
        x = keyboard.nextInt();
        System.out.println("Tast y koordinatet for den brik du gerne vil rykke");
        y = keyboard.nextInt();

        if ((!(x > 0 && y < 9 && x < 9 && y > 0))) {
            System.out.println(x + " " + y);
            if (y == 1) {
                position = y * x - 1;
            } else position = ((y * 8) + x) - 1;
            while (!checkMove(position, board)) {
                System.out.print("Vælg et felt, som ikke er optaget!");
                playerTurn();
            }
        } else {
            System.out.println("Vælg to tal mellem 1 og 8!");
        }
    }

    public void gameFlow() {
        int turn = 0;
        int input, boardinputX, boardinputY, playerX, playerY, moveinputX, moveinputY;

        System.out.print("Tast 1 for at være 1 spiller, eller tast 2 for at være 2 spiller");

        input = this.keyboard.nextInt();

        while (!(input > 0 && input < 3)) {
            System.out.print("Tast enten 1 eller 2");
            input = this.keyboard.nextInt();
        }

        if (input == 2) {
            turn++;
        }

        while (!isGameOver()) {
            turn++;
            if ((turn % 2) == 0) {
                System.out.println("bote");
            } else {
                System.out.print("Tast to tal  0-7 (Den brik du gerne vil rykke)");
                boardinputX = 900;
                boardinputY = 900;

                System.out.print("Tast to tal  0-7 (Den feltet du gerne vil rykke hen tile)");


            }

            printBoard(board);
        }

    }


    boolean checkMove(int position, ArrayList<Tile> board) {

        if (board.get(position).getGraphic().equals(" - ")) {
            return true;
        } else if (position > -1 || position < BOARD_SIZE) {
            return true;
        } else return false;
    }


    private boolean isGameOver() {
        if (!getWinner().equals("")) {
            return true;
        } else return false;
    }

    private boolean noPossibleMoves() {

        //TODO: logic for checking if a player still can make moves.

        return false;
    }

    String getWinner() {
        int countW = 0, countB = 0;

        for (Tile t :
                board) {
            if (t.getGraphic().equals(" W ")) {
                countW++;
            } else if (t.getGraphic().equals(" B ")) {
                countB++;
            } else continue;

        }

        if (countW == 0) {
            return "Black";
        } else if (countB == 0) {
            return "White";
        } else return "";
    }

    String[][] placeMove(int positionX, int positonY, String[][] Board, String player) {
        Board[positionX][positonY] = player;
        return Board;
    }

}
