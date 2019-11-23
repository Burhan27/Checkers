import java.util.ArrayList;
import java.util.Scanner;

public class GameLogic {

    ArrayList<Tile> board = new ArrayList<Tile>();
    final static int BOARD_SIZE = 64;
    Scanner keyboard = new Scanner(System.in);
    ArrayList<Tile> path = new ArrayList<Tile>();
    static int MAX_DEPTH = 5;
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

        if(pieceW == 99){
            return;
        }
        else {
            while (!(board.get(pieceW).getGraphic() == " W ")) {
                System.out.println("Tast rigtig koordinat");
                pieceW = keyboard.nextInt();
            }
            System.out.println("Tast koordinatet for destination");
            moveW = keyboard.nextInt();
            while (!(board.get(moveW).isDark)){
                System.out.println("Tast rigtig koordinat");
                pieceW = keyboard.nextInt();
            }
            boolean ale = checkNormalMove(pieceW, moveW);
            if(ale == true) {
                placeMove(pieceW, moveW);
            }
            else{
                System.out.println("fuck dig ale");
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
                System.out.println("bote");
            } else {
                playerTurn();
            }
            printBoard();
        }

    }

    private boolean checkSpecialMove(Piece piece, int destination) {
        //TODO: check for diagonals for both kings and non-kings for special moves (Kills etc.)
        /*
        tjek distancen mellem destinationen og brikkens nuværende position.
         */
        return false;
    }

    private boolean checkNormalMove(int start, int destination) {
        Piece piece = board.get(start).getPiece();
        System.out.println("PLACE: " + piece.placement);

        if (!board.get(destination).isDark) {
            return false;
        } else {
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

    private String getWinner() {
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

    private void placeMove(int lokation, int destination) {

        if (board.get(destination).getGraphic().equals(" - ")) {
            if (board.get(destination).isEndTile()) {
                board.get(lokation).getPiece().setKing(true);
                board.get(destination).setPiece(board.get(lokation).piece);
                board.get(lokation).setPiece(null);
                //SPØRG BURHAN
            }
            else {
                System.out.println("lol -");
                board.get(destination).setPiece(board.get(lokation).piece);
                board.get(lokation).setPiece(null);
            }
        }
//        if (destination == (lokation - 9) || destination == (lokation - 7) || destination == (lokation + 9) || destination == (lokation + 7)) {
        if (board.get(destination).getGraphic().equals(" B ")) {
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
                System.out.println("lol1");
                board.get(destination).setPiece(null);
                destination += 9;
                board.get(destination).setPiece(board.get(lokation).piece);
                board.get(lokation).setPiece(null);;
            } else if (destination == (lokation + 7)) {
                board.get(destination).setPiece(null);
                destination += 7;
                board.get(destination).setPiece(board.get(lokation).piece);
                board.get(lokation).setPiece(null);
            }
        }
    }

    private int stateEvaluation(int depth){
        if (getWinner().equals("White")) {
            int value = 0;
            value -= 500 - depth;
            return value;

        } else if (getWinner().equals("Black")) {
            int value = 0;
            value += 500 - depth;
            return value;

        } else{
            int value = 0;
            for (int i = 0; i < path.size(); i++) {
                if (path.get(i).getGraphic().equals("O")) {
                    value += path.get(i).getPiece().getValue();
                } else if (path.get(i).getGraphic().equals("X")) {
                    value -= path.get(i).getPiece().getValue();
                }
            }
            return value;
        }
    }

    private int AlphaBeta(int depth, ArrayList<Tile> moves, int alpha, int beta) {

        if (depth == MAX_DEPTH || isGameOver()) {
            return stateEvaluation(depth);
        }


        //kør det hele gennem med en liste over moves du kan lave, tag summen af de forskellige værdier hvor hver anden
        // er et minus tal.
        else if (depth == 0) {
            int current_value;
            int best_value = MIN;
            for (Tile tile : board) {
                //System.out.println("tile pos" + tile.position);
                if (tile.getGraphic().equals("")) {
                    tile.setGraphic("O");
                    path.add(tile);
                    //System.out.println("Tile pos: " + path.get(0).position);
                    current_value = AlphaBeta(depth + 1, moves, alpha, beta);
                    //System.out.println("C: "+ current_value);
                    tile.setGraphic("");
                    if (current_value > best_value) {
                        best_value = current_value;
                        moves.clear();
                        moves.add(tile);
                        //  System.out.println("moves: " + moves.get(0).position);
                    }
                    path.clear();
                    //System.out.println(best_value);

                }
            }

        } else {

            if (depth % 2 == 0) {
                int max = MIN;

                for (int i = 0; i < board.size(); i++) {
                    if (board.get(i).graphic.equals("")) {
                        board.get(i).setGraphic("O");
                        int child_value = AlphaBeta(depth + 1, moves, alpha, beta);
                        board.get(i).setGraphic("");
                        max = Math.max(max, child_value);
                        if (max > alpha) {
                            alpha = max;
                            if (path.size() < depth) {
                                path.add(board.get(i));
                            } else {
                                path.remove(depth - 1);
                                path.add(board.get(i));
                            }
                        }
                        if (alpha >= beta) {
                            break;
                        }
                    }
                }
                return alpha;
            } else {
                int min = MAX;
                for (int i = 0; i < board.size(); i++) {
                    if (board.get(i).graphic.equals("")) {
                        board.get(i).setGraphic("X");
                        int child_value = AlphaBeta(depth + 1, moves, alpha, beta);
                        board.get(i).setGraphic("");
                        min = Math.min(min, child_value);
                        if (min < beta) {
                            beta = min;
                            if (path.size() < depth) {
                                path.add(board.get(i));
                            } else {
                                path.remove(depth - 1);
                                path.add(board.get(i));
                            }
                        }
                        if (alpha >= beta) {
                            break;
                        }
                    }
                }

                return beta;
            }

        }
        return 0;
    }


}