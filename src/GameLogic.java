import java.util.ArrayList;
import java.util.Scanner;

public class GameLogic {

    ArrayList<Tile> board = new ArrayList<Tile>();
    final static int BOARD_SIZE = 64;
    Scanner keyboard = new Scanner(System.in);
    ArrayList<Tile> backupBoard = new ArrayList<Tile>();
    static int MAX_DEPTH = 2;
    static int MAX = 1000;
    static int MIN = -1000;

    public GameLogic() {

        board = generateBoard();
        printBoard();
        gameFlow();
    }

    private ArrayList<Tile> generateBoard() {
        int row_count;

        for (int i = 0; i < BOARD_SIZE; i++) {

            row_count = i / 8;
            if (row_count % 2 != 0) {
                if (i % 2 != 0) {
                    if (row_count < 3) {
                        if (row_count == 0) {
                            board.add(new Tile(true, true, new Piece(false, " B ", board.size()), " B "));
                        } else board.add(new Tile(true, false, new Piece(false, " B ", board.size()), " B "));
                    } else if (row_count > 4) {
                        if (row_count == 7) {
                            board.add(new Tile(true, true, new Piece(false, " W ", board.size()), " W "));
                        } else board.add(new Tile(true, false, new Piece(false, " W ", board.size()), " W "));
                    } else {
                        board.add(new Tile(true, false, null, " - "));
                    }
                } else board.add(new Tile(false, false, null, " X "));
            } else {
                if (i % 2 == 0) {
                    if (row_count < 3) {
                        if (row_count == 0) {
                            board.add(new Tile(true, true, new Piece(false, " B ", board.size()), " B "));
                        } else board.add(new Tile(true, false, new Piece(false, " B ", board.size()), " B "));
                    } else if (row_count > 4) {
                        if (row_count == 7) {
                            board.add(new Tile(true, true, new Piece(false, " W ", board.size()), " W "));
                        } else board.add(new Tile(true, false, new Piece(false, " W ", board.size()), " W "));
                    } else {
                        board.add(new Tile(true, false, null, " - "));
                    }
                } else board.add(new Tile(false, false, null, " X "));
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
                System.out.print("|\n");
            } else System.out.print(" | " + board.get(i).getGraphic() + " ");
        }
    }

    private void playerTurn() {
        int pieceW, moveW;
        System.out.println("Tast koordinatet for den brik du gerne vil rykke");
        pieceW = keyboard.nextInt();

        if (pieceW == 99) {
            return;
        } else {
            while (!(board.get(pieceW).getGraphic() == " W ")) {
                System.out.println("Tast rigtig koordinat");
                pieceW = keyboard.nextInt();
            }
            System.out.println("Tast koordinatet for destination");
            moveW = keyboard.nextInt();
            while (!(board.get(moveW).isDark)) {
                System.out.println("Tast rigtig koordinat");
                pieceW = keyboard.nextInt();
            }
            boolean ale = checkNormalMove(pieceW, moveW);
            if (ale == true) {
                placeMove(pieceW, moveW);
            } else {
                while (ale == false) {
                    System.out.println("Tast brik koordinat");
                    pieceW = keyboard.nextInt();
                    System.out.println("Tast destination");
                    moveW = keyboard.nextInt();
                }
                placeMove(pieceW, moveW);
            }
        }

    }

    private void gameFlow() {
        int turn = 0;
        int input, boardinputX, boardinputY, playerX, playerY, moveinputX, moveinputY;

        System.out.print("Tast 1 for at være 1. spiller, eller tast 2 for at være 2. spiller");

        input = this.keyboard.nextInt();

        while (!(input > 0 && input < 3)) {
            System.out.println("Tast enten 1 eller 2");
            input = this.keyboard.nextInt();
        }

        if (input == 2) {
            turn++;
        }

        while (!isGameOver()) {
            turn++;
            if ((turn % 2) == 0) {
                pcturn();
            } else {
                playerTurn();
            }
            printBoard();
        }

    }

    private void backupBoard() {
        backupBoard = board;
    }

    private boolean checkSpecialMove(int start, int destination) {
        int difference = start - destination;
        int dest_X = destination % 8;
        int dest_Y = destination / 8;
        int real_dest_X = destination + difference % 8;
        int real_dest_Y = destination + difference / 8;

        if (real_dest_Y - 1 == dest_Y || real_dest_Y + 1 == dest_Y) {
            if (board.get(destination + difference).getGraphic().equals(" - ")) {
                return true;
            } else return false;
        } else return false;
    }

    private boolean checkNormalMove(int start, int destination) {
        Piece piece = board.get(start).getPiece();

        if (!board.get(destination).isDark) {
            return false;
        }
        else if(destination > 63){
            return false;
        }
        else if(board.get(start).getGraphic().equals(board.get(destination).getGraphic())){
            return false;
        }
        else {
            int dest_X = destination % 8;
            int dest_Y = destination / 8;
            int piece_X = start % 8;
            int piece_Y = start / 8;

            if ((piece_X - 1 == dest_X || piece_X + 1 == dest_X)) {
                if (!piece.isKing && piece.graphic.equals(" B ")) {
                    if (dest_Y == piece_Y + 1) {
                        return true;
                    }

                } else if (!piece.isKing && piece.graphic.equals(" W ")) {
                    if (dest_Y == piece_Y - 1) {
                        return true;
                    }
                } else if (piece.isKing && (dest_Y == piece_Y + 1 || dest_Y == piece_Y - 1)) {
                    return true;
                }
            } else return false;
        }
        return false;
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

    private void pcturn() {
        System.out.println("board kopnik");
        backupBoard();
        int move[] = new int[2];
        AlphaBeta(0, move, board, MIN, MAX);
        placeMove(move[0], move[1]);
    }

    private String getWinner() {
        int countW = 0, countB = 0;

        //Tjek om nogle af dem har mulige moves, hvis ikke har så vinder den anden

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

    private void placeMove(int lokation, int destination) {

        if (board.get(destination).getGraphic().equals(" - ")) {
            if (board.get(destination).isEndTile()) {
                board.get(lokation).getPiece().setKing(true);
                board.get(destination).setPiece(board.get(lokation).piece);
                board.get(lokation).setPiece(null);
            } else {
                board.get(destination).setPiece(board.get(lokation).piece);
                board.get(lokation).setPiece(null);
            }
        }
        if (!board.get(destination).getGraphic().equals(board.get(lokation).getGraphic())) {
            if (destination == (lokation - 9)) {
                board.get(destination).setPiece(null);
                destination -= 9;
                board.get(destination).setPiece(board.get(lokation).piece);
                board.get(lokation).setPiece(null);
            } else if (destination == (lokation - 7)) {
                board.get(destination).setPiece(null);
                destination -= 7;
                board.get(destination).setPiece(board.get(lokation).piece);
                board.get(lokation).setPiece(null);
            } else if (destination == (lokation + 9)) {
                board.get(destination).setPiece(null);
                destination += 9;
                board.get(destination).setPiece(board.get(lokation).piece);
                board.get(lokation).setPiece(null);
                ;
            } else if (destination == (lokation + 7)) {
                board.get(destination).setPiece(null);
                destination += 7;
                board.get(destination).setPiece(board.get(lokation).piece);
                board.get(lokation).setPiece(null);
            }
        }
    }

    private int stateEvaluation(int depth) {
        if (getWinner().equals("White")) {
            int value = 0;
            value -= 500 - depth;
            return value;

        } else if (getWinner().equals("Black")) {
            int value = 0;
            value += 500 - depth;
            return value;

        } else {
            int value = 0;
            for (int i = 0; i < backupBoard.size(); i++) {
                if (backupBoard.get(i).getGraphic().equals(" B ")) {
                    value += 2;
                } else if (backupBoard.get(i).getGraphic().equals(" W ")) {
                    value -= 2;
                } else if (backupBoard.get(i).getGraphic().equals(" B K ")) {
                    value += 10;
                } else if (backupBoard.get(i).getGraphic().equals(" W K")) {
                    value -= 10;
                }
            }
            return value;
        }
    }

    private int AlphaBeta(int depth, int[] move, ArrayList<Tile> boardstate ,int alpha, int beta) {
        int nextmove;
        if (depth == MAX_DEPTH || isGameOver()) {
            System.out.println("we evalutating");
            return stateEvaluation(depth);
        }

        //kør det hele gennem med en liste over moves du kan lave, tag summen af de forskellige værdier hvor hver anden
        // er et minus tal.
        else if (depth == 0) {
            int current_value;
            int best_value = MIN;
            for (Tile tile : boardstate) {
                if(tile.getPiece() != null) {
                    if (tile.getPiece().getGraphic() == " B " || tile.getPiece().getGraphic() == " B K") {
                        if (checkNormalMove(boardstate.indexOf(tile), boardstate.indexOf(tile) + 7)) {
                            nextmove = boardstate.indexOf(tile) + 7;
                            System.out.println(boardstate.indexOf(tile)+"+7 =" + nextmove);
                        } else if (checkNormalMove(boardstate.indexOf(tile), boardstate.indexOf(tile) + 9)) {
                            nextmove = boardstate.indexOf(tile) + 9;
                            System.out.println(boardstate.indexOf(tile)+"+9 =" + nextmove);
                        } else {
                            continue;
                        }
                        System.out.println("making the move");
                        placeMove(boardstate.indexOf(tile), nextmove);
                        System.out.println("move has been made");
                        current_value = AlphaBeta(depth + 1, move, boardstate, alpha, beta);
                        if (current_value > best_value) {
                            System.out.println("bombastic");
                            best_value = current_value;
                            move[0] = boardstate.indexOf(tile);
                            move[1] = nextmove;
                        }
                        System.out.println("resetting board");
                        boardstate.clear();
                        System.out.println("board reset");
                        boardstate = backupBoard;
                    }
                    else continue;
                }
            }

        } else {

            if (depth % 2 == 0) {
                int max = MIN;
                ArrayList<Tile> currentState = boardstate;
                for (Tile tile : boardstate) {
                    if (tile.getPiece() != null) {
                        if (tile.getPiece().getGraphic() == " B " || tile.getPiece().getGraphic() == " B K") {
                            if (checkNormalMove(boardstate.indexOf(tile), boardstate.indexOf(tile) + 7)) {
                                nextmove = boardstate.indexOf(tile) + 7;
                                System.out.println(boardstate.indexOf(tile)+"+7 =" + nextmove);
                            } else if (checkNormalMove(boardstate.indexOf(tile), boardstate.indexOf(tile) + 9)) {
                                nextmove = boardstate.indexOf(tile) + 9;
                                System.out.println(boardstate.indexOf(tile)+"+9 =" + nextmove);
                            } else {
                                continue;
                            }
                            System.out.println("making the move");
                            placeMove(boardstate.indexOf(tile), nextmove);
                            System.out.println("move has been made");
                            int child_value = AlphaBeta(depth + 1, move, boardstate, alpha, beta);
                            max = Math.max(max, child_value);
                            boardstate = currentState;
                            if (max > alpha) {
                                alpha = max;
                            }
                            if (alpha >= beta) {
                                break;
                            }

                        }
                    } else continue;
                }
                return alpha;
            } else {
                int min = MAX;
                ArrayList<Tile> currentState = boardstate;
                for (Tile tile : boardstate) {
                    if (tile.getPiece() != null) {
                        if (tile.getPiece().getGraphic() == " W " || tile.getPiece().getGraphic() == " W K") {
                            if (checkNormalMove(boardstate.indexOf(tile), boardstate.indexOf(tile) - 7)) {
                                nextmove = boardstate.indexOf(tile) - 7;
                                System.out.println(boardstate.indexOf(tile)+"-7 =" + nextmove);
                            } else if (checkNormalMove(boardstate.indexOf(tile), boardstate.indexOf(tile) - 9)) {
                                nextmove = boardstate.indexOf(tile) - 9;
                                System.out.println(boardstate.indexOf(tile)+"-9 =" + nextmove);
                            } else {
                                continue;
                            }
                            System.out.println("making the move");
                            placeMove(boardstate.indexOf(tile), nextmove);
                            System.out.println("move has been made");
                            int child_value = AlphaBeta(depth + 1, move, boardstate, alpha, beta);
                            min = Math.min(min, child_value);
                            boardstate = currentState;
                            if (min < beta) {
                                beta = min;
                            }
                            if (alpha >= beta) {
                                break;
                            }
                        }
                    } else continue;
                }

                return beta;
            }

        }
        return 0;
    }


}