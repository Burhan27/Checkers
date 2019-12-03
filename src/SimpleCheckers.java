import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;


public class SimpleCheckers {

    static int total = 0;
    static int MAX_DEPTH = 10;
    static int MAX = 100000;
    static int MIN = -100000;
    static int[] move = new int[5];
    String[][] board;
    boolean multikill;

    public SimpleCheckers(String[][] board) {
        this.board = board;
    }


    public void gameLogic() {
        int turn = 0;
        boolean playerDone;
        Scanner scanner = new Scanner(System.in);
        int input, boardinputX, boardinputY, moveDirection;

        printBoard(board);
        System.out.print("Tast 1 for at være 1 spiller, eller tast 2 for at være 2 spiller");

        input = scanner.nextInt();

        while (!(input > 0 && input < 3)) {
            System.out.print("Tast enten 1 og 2");
            input = scanner.nextInt();
        }

        if (input == 2) {
            turn++;
        }

        while (!isGameOver(board)) {
            playerDone = false;
            turn++;
            if ((turn % 2) == 0) {
                pcTurn(board);
                printBoard(board);
            } else {
                while (!playerDone) {
                    playerDone = playerTurn(scanner);
                }
            }
        }
    }


    private boolean playerTurn(Scanner scanner) {
        int boardinputX, boardinputY, moveDirection;
        MoveType moveType;
        System.out.print("Tast en y værdi og så en x værdi");
        boardinputY = scanner.nextInt();
        if (boardinputY == 99) {
            return true;
        }
        boardinputX = scanner.nextInt();
        while ((!(boardinputY > -1 && boardinputY < 8 && boardinputX < 8 && boardinputX > -1))) {
            System.out.println("Valgt brik " + boardinputY + " " + boardinputX);
            boardinputY = scanner.nextInt();
            boardinputX = scanner.nextInt();
            if (boardinputY > -1 && boardinputY < 8 && boardinputX < 8 && boardinputX > -1) {
                while ((board[boardinputY][boardinputX].contains("w"))) {
                    System.out.print("Vælg et felt, som ikke er optaget!");
                    boardinputY = scanner.nextInt();
                    boardinputX = scanner.nextInt();
                }
                break;
            } else {
                System.out.println("Vælg to tal mellem 0 og 7!");
            }
        }

        System.out.print("Tast et tal mellem 1 og 4 for retning");
        moveDirection = 900;
        while ((!(moveDirection > 0 && moveDirection < 5))) {
            moveDirection = scanner.nextInt();
            if (moveDirection > 0 && moveDirection < 5) {
                while (checkMove(boardinputY, boardinputX, moveDirection, board) == MoveType.Illegal) {
                    System.out.print("Vælg et lovligt træk!");
                    moveDirection = scanner.nextInt();
                }
                //tjek for andre playerType

                //Her skal vi tjekke om det er et tilddat move
                //Her skal vi rykke brækken der hen og så fjerne dem der er under
                //Her skal vi udvikle brækken hvis det er ved enden
                //break
                break;
            } else {
                System.out.println("Tast et tal mellem 1-4");
            }
        }
        moveType = checkMove(boardinputY, boardinputX, moveDirection, board);
        placeMove(boardinputY, boardinputX, board, moveType, moveDirection);
        printBoard(board);
        if (moveType == MoveType.Kill || moveType == MoveType.KingSlay) {
            return false;
        } else {
            return true;
        }
    }


    private int[] getDirectionCoordinate(int y, int x, int direction) {
        int[] coordinates = new int[2];
        switch (direction) {
            case 1:
                coordinates[0] = y - 1;
                coordinates[1] = x - 1;
                break;
            case 2:
                coordinates[0] = y - 1;
                coordinates[1] = x + 1;
                break;
            case 3:
                coordinates[0] = y + 1;
                coordinates[1] = x - 1;
                break;
            case 4:
                coordinates[0] = y + 1;
                coordinates[1] = x + 1;
                break;
        }
        return coordinates;
    }

    private String getWinner(String[][] board) {
        int wcount = 0;
        int bcount = 0;
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x].contains("w")) {
                    wcount++;
                } else if (board[y][x].contains("b")) {
                    bcount++;
                } else continue;
            }
        }

        ArrayList<int[]> whitepos = getMovablePieces(board, "w");
        ArrayList<int[]> blackpos = getMovablePieces(board, "b");


        if (bcount == 0 || blackpos.size() == 0) {
            return "w";
        } else if (wcount == 0 || whitepos.size() == 0) {
            return "b";
        } else return "NA";
    }

    private MoveType checkMove(int y, int x, int direction, String[][] board) {
        MoveType moveType = MoveType.Illegal;
        int[] destination = getDirectionCoordinate(y, x, direction);
        int y2 = destination[0];
        int x2 = destination[1];

        if (y2 < 0 || y2 > 7 || x2 < 0 || x2 > 7) {
            return moveType;
        }

        if (y2 == 0 || y2 == 7 || x2 == 0 || x2 == 7) {
            if (!board[y2][x2].equals("-")) {
                return moveType;
            }
        }

        if (board[y][x].contains("k")) {
            if (board[y][x].contains(board[y2][x2])) {
                moveType = MoveType.KingIllegal;
            } else if (board[y2][x2].equals("-")) {
                moveType = MoveType.KingStandard;
            } else if (!board[y][x].contains(board[y2][x2]) && !board[y2][x2].equals("-")) {
                int[] truedest = getDirectionCoordinate(y2, x2, direction);
                int dx = truedest[1];
                int dy = truedest[0];
                if (dx < 8 && dy < 8 && dx > -1 && dy > -1) {
                    if (board[dy][dx].equals("-")) {
                        if (board[y2][x2].contains("k")) {
                            moveType = MoveType.KingSlay;
                        } else moveType = MoveType.Kill;
                    }
                }
            } else moveType = MoveType.KingIllegal;

        } else {
            if (board[y2][x2].contains(board[y][x]) || board[y][x].contains(board[y2][x2])) {
                moveType = MoveType.Illegal;
            } else if (board[y2][x2].equals("-")) {
                if ((y2 == 0 && board[y][x].equals("w")) || (y2 == 7 && board[y][x].equals("b"))) {
                    moveType = MoveType.CrownKing;
                } else moveType = MoveType.Standard;
            } else if (!board[y2][x2].equals("-")) {
                int[] truedest = getDirectionCoordinate(y2, x2, direction);
                int dx = truedest[1];
                int dy = truedest[0];
                if (dx < 8 && dy < 8 && dx > -1 && dy > -1) {
                    if (board[dy][dx].equals("-")) {
                        if ((dy == 0 && board[y][x].equals("w")) || (dy == 7 && board[y][x].equals("b"))) {
                            if (board[y2][x2].contains("k")) {
                                moveType = MoveType.CrownKingSlay;
                            } else moveType = MoveType.CrownKingKill;
                        } else if (board[y2][x2].contains("k")) {
                            moveType = MoveType.KingSlay;
                        } else moveType = MoveType.Kill;
                    }
                }
            } else moveType = MoveType.Illegal;
        }

        return moveType;
    }


    private ArrayList<Integer> checkLegalMove(int y, int x, String[][] board) {
        ArrayList<Integer> moves = getPossibleMoves(y, x, board);
        ArrayList<Integer> legalmoves = new ArrayList<>();
        MoveType moveType;
        if (moves.size() <= 0) {
            return legalmoves;
        } else {
            for (int i = 0; i < moves.size(); i++) {
                moveType = checkMove(y, x, moves.get(i), board);
                if (moveType == MoveType.KingIllegal || moveType == MoveType.Illegal) {
                    continue;
                } else legalmoves.add(moves.get(i));
            }
        }
        return legalmoves;
    }


    private ArrayList<Integer> getPossibleMoves(int y, int x, String[][] board) {
        ArrayList<Integer> possiblemoves = new ArrayList<Integer>();
        if (board[y][x].equals("b")) {
            possiblemoves.add(3);
            possiblemoves.add(4);
        } else if (board[y][x].contains("k")) {
            possiblemoves.add(1);
            possiblemoves.add(2);
            possiblemoves.add(3);
            possiblemoves.add(4);
        } else if (board[y][x].equals("w")) {
            possiblemoves.add(1);
            possiblemoves.add(2);
        }
        return possiblemoves;
    }


    private void printBoard(String[][] board) {
        System.out.println();
        for (String[] x : board) {
            for (String y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }

    private String[][] placeMove(int y, int x, String[][] board, MoveType movetype, int direction) {
        int[] destination = getDirectionCoordinate(y, x, direction);

        if (movetype == MoveType.Illegal || movetype == MoveType.KingIllegal) {
            return board;
        } else if (movetype == MoveType.Kill || movetype == MoveType.KingSlay) {
            board[destination[0]][destination[1]] = "-";
            int[] trueDestination = getDirectionCoordinate(destination[0], destination[1], direction);
            board[trueDestination[0]][trueDestination[1]] = board[y][x];
            board[y][x] = "-";
        } else if (movetype == MoveType.Standard || movetype == MoveType.KingStandard) {
            board[destination[0]][destination[1]] = board[y][x];
            board[y][x] = "-";

        } else if (movetype == MoveType.CrownKing) {
            board[destination[0]][destination[1]] = board[y][x] + "k";
            board[y][x] = "-";
        } else if (movetype == MoveType.CrownKingKill || movetype == MoveType.CrownKingSlay) {
            board[destination[0]][destination[1]] = "-";
            int[] trueDestination = getDirectionCoordinate(destination[0], destination[1], direction);
            board[trueDestination[0]][trueDestination[1]] = board[y][x] + "k";
            board[y][x] = "-";
        }


        return board;
    }

    private String[][] undoPlaceMove(int y, int x, String[][] board, MoveType movetype, int direction) {
        int[] destination = getDirectionCoordinate(y, x, direction);
        String tile = board[destination[0]][destination[1]];
        if (movetype == MoveType.Illegal || movetype == MoveType.KingIllegal) {
            return board;
        } else if (movetype == MoveType.Standard || movetype == movetype.KingStandard) {
            board[y][x] = board[destination[0]][destination[1]];
            board[destination[0]][destination[1]] = "-";
        } else if (movetype == MoveType.CrownKing) {
            board[y][x] = tile.substring(0, tile.length() - 1);
            board[destination[0]][destination[1]] = "-";
        } else if (movetype == MoveType.Kill) {
            int[] trueDestination = getDirectionCoordinate(destination[0], destination[1], direction);
            tile = board[trueDestination[0]][trueDestination[1]];
            board[y][x] = tile;
            if (tile.contains("w")) {
                board[destination[0]][destination[1]] = "b";
            } else {
                board[destination[0]][destination[1]] = "w";
            }
            board[trueDestination[0]][trueDestination[1]] = "-";

        } else if (movetype == MoveType.CrownKingKill) {
            int[] trueDestination = getDirectionCoordinate(destination[0], destination[1], direction);
            tile = board[trueDestination[0]][trueDestination[1]];
            board[y][x] = tile.substring(0, tile.length() - 1);
            if (tile.contains("w")) {
                board[destination[0]][destination[1]] = "b";
            } else {
                board[destination[0]][destination[1]] = "w";
            }
            board[trueDestination[0]][trueDestination[1]] = "-";
        } else if (movetype == MoveType.CrownKingSlay) {
            int[] trueDestination = getDirectionCoordinate(destination[0], destination[1], direction);
            tile = board[trueDestination[0]][trueDestination[1]];
            board[y][x] = tile.substring(0, tile.length() - 1);
            if (tile.contains("w")) {
                board[destination[0]][destination[1]] = "bk";
            } else {
                board[destination[0]][destination[1]] = "wk";
            }
            board[trueDestination[0]][trueDestination[1]] = "-";
        } else if (movetype == MoveType.KingSlay) {
            int[] trueDestination = getDirectionCoordinate(destination[0], destination[1], direction);
            tile = board[trueDestination[0]][trueDestination[1]];
            board[y][x] = tile;
            if (tile.contains("w")) {
                board[destination[0]][destination[1]] = "bk";
            } else {
                board[destination[0]][destination[1]] = "wk";
            }
            board[trueDestination[0]][trueDestination[1]] = "-";
        }
        return board;
    }

    private int[] upcomingKingsAndKillers(String[][] board, String type) {
        ArrayList<int[]> movables = getMovablePieces(board, type);
        int[] kingAndKillers = {0, 0};
        for (int[] piece : movables) {
            if (type.contains("w")) {
                if (piece[0] == 1 &&
                        (board[piece[0] - 1][piece[1] + 1].equals("-") || board[piece[0] - 1][piece[1] - 1].equals("-"))) {
                    kingAndKillers[0]++;
                }
            } else if (type.contains("b")) {
                if (piece[0] == 7 &&
                        (board[piece[0] + 1][piece[1] + 1].equals("-") || board[piece[0] + 1][piece[1] - 1].equals("-"))) {
                    kingAndKillers[0]++;
                }
            }
            MoveType mv = checkMove(piece[0], piece[1], piece[2], board);
            if (mv.equals(MoveType.Kill) || mv.equals(MoveType.CrownKingSlay)
                    || mv.equals(MoveType.KingSlay) || mv.equals(MoveType.CrownKingKill)) {
                kingAndKillers[1]++;
            }
        }
        return kingAndKillers;
    }

    private int stateEvaluation(int depth, String[][] board) {
        int value = 0;
        total++;

        String winner = getWinner(board);
        if(winner.equals("b")){
            value+=10000;
        }
        else if(winner.equals("w")){
            value -= 10000;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals("w")) {
                    if ((i == 3 || i == 4) && j > 1 && j < 6) {
                        value -= 3;
                    } else {
                        value -= 2;
                    }
                } else if (board[i][j].equals("b")) {
                    if ((i == 3 || i == 4) && j > 1 && j < 6) {
                        value += 3;
                    } else {
                        value += 2;
                    }
                } else if ((board[i][j].equals("wk"))) {
                    if ((i == 3 || i == 4) && j > 1 && j < 6) {
                        value -= 6;
                    } else {
                        value -= 5;
                    }
                } else if ((board[i][j].equals("bk"))) {
                    if ((i == 3 || i == 4) && j > 1 && j < 6) {
                        value += 6;
                    } else {
                        value += 5;
                    }
                }
            }
        }
        return value;
    }

    private String[][] simulateMultikill(int y, int x, int direction, int depth, String[][] board) {
        String[][] newBoard = new String[8][8];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, board[i].length);
        }
        MoveType checked = checkMove(y, x, direction, newBoard);
        placeMove(y, x, newBoard, checked, direction);
        int[] dest = getDirectionCoordinate(y, x, direction);
        int[] trueDest = getDirectionCoordinate(dest[0], dest[1], direction);
        ArrayList<Integer> legalMove = checkLegalMove(trueDest[0], trueDest[1], newBoard);
        if (checked == MoveType.CrownKingKill || checked == MoveType.CrownKingSlay || legalMove.size() == 0) {
            return newBoard;
        } else {
            for (int i = 0; i < legalMove.size(); i++) {
                MoveType simMove = checkMove(trueDest[0], trueDest[1], legalMove.get(i), newBoard);
                if (simMove == MoveType.CrownKingKill || simMove == MoveType.CrownKingSlay) {
                    multikill = true;
                    placeMove(trueDest[0], trueDest[1], newBoard, simMove, legalMove.get(i));
                    break;
                } else if (simMove == MoveType.Kill || simMove == MoveType.KingSlay) {
                    multikill = true;
                    newBoard = simulateMultikill(trueDest[0], trueDest[1], legalMove.get(i), depth, newBoard);
                    break;

                }
            }
        }
        return newBoard;
    }

    private ArrayList<int[]> getMovablePieces(String[][] board, String type) {
        ArrayList<int[]> movables = new ArrayList<>();
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x].contains(type)) {
                    ArrayList<Integer> legalMoves = checkLegalMove(y, x, board);
                    if (legalMoves.size() == 0) {
                        continue;
                    } else {
                        for (Integer direction : legalMoves) {
                            MoveType mv = checkMove(y, x, direction, board);
                            int[] coordinates = new int[3];
                            coordinates[0] = y;
                            coordinates[1] = x;
                            coordinates[2] = direction;
                            if (mv == MoveType.Kill || mv == MoveType.KingSlay || mv == MoveType.CrownKingKill || mv == MoveType.CrownKingSlay) {
                                movables.add(0, coordinates);
                            } else {
                                movables.add(coordinates);
                            }
                        }
                    }
                } else continue;
            }
        }
        return movables;
    }

    private void pcTurn(String[][] board) {
        move[4] = 0;
        alphaBeta(0, MIN, MAX, board);
        MoveType moveType = checkMove(move[0], move[1], move[2], board);
        if (move[3] > 0) {
            this.board = simulateMultikill(move[0], move[1], move[2], 0, board);
        } else {
            placeMove(move[0], move[1], board, moveType, move[2]);
        }
        System.out.println(total);
    }


    private boolean isGameOver(String[][] board) {
        String winner = getWinner(board);
        if (!winner.equals("NA")) {
            return true;
        } else return false;
    }

    private int alphaBeta(int depth, int alpha, int beta, String[][] board) {
        if (depth == MAX_DEPTH || isGameOver(board)) {
            return stateEvaluation(depth, board);
        }
        //kør det hele gennem med en liste over moves du kan lave, tag summen af de forskellige værdier hvor hver anden
        // er et minus tal.
        if (depth == 0) {
            boolean kill = false;
            int current_value;
            int best_value = MIN;
            multikill = false;
            ArrayList<int[]> movable = getMovablePieces(board, "b");
            for (int[] piece : movable) {
                MoveType moveType = checkMove(piece[0], piece[1], piece[2], board);
                if (kill == true) {
                    if (!(moveType.equals(MoveType.Kill) || moveType.equals(MoveType.KingSlay) || moveType.equals(MoveType.CrownKingKill) || moveType.equals(MoveType.CrownKingSlay))) {
                        continue;
                    }
                }
                if ((moveType.equals(MoveType.Kill) || moveType.equals(MoveType.KingSlay)
                        || moveType.equals(MoveType.CrownKingKill) || moveType.equals(MoveType.CrownKingSlay))) {
                    String[][] newBoard;
                    newBoard = simulateMultikill(piece[0], piece[1], piece[2], 0, board);
                    current_value = alphaBeta(depth + 1, alpha, beta, newBoard);
                } else {
                    placeMove(piece[0], piece[1], board, moveType, piece[2]);
                    current_value = alphaBeta(depth + 1, alpha, beta, board);
                    undoPlaceMove(piece[0], piece[1], board, moveType, piece[2]);
                    multikill = false;
                }

                if (current_value > best_value || (moveType.equals(MoveType.Kill) || moveType.equals(MoveType.KingSlay) || moveType.equals(MoveType.CrownKingKill) || moveType.equals(MoveType.CrownKingSlay))) {

                    if (move[4] == 0) {
                        best_value = current_value;
                        move[0] = piece[0];
                        move[1] = piece[1];
                        move[2] = piece[2];
                        move[3] = 0;


                    if (multikill == true) {
                        move[0] = piece[0];
                        move[1] = piece[1];
                        move[2] = piece[2];
                        move[3] = 1;
                        move[4] = 1;
                        kill = true;


                    } else if ((moveType.equals(MoveType.Kill) || moveType.equals(MoveType.KingSlay) || moveType.equals(MoveType.CrownKingKill) || moveType.equals(MoveType.CrownKingSlay))) {
                        move[0] = piece[0];
                        move[1] = piece[1];
                        move[2] = piece[2];
                        move[3] = 0;
                        move[4] = 1;
                        kill = true;
                    }

                } else if (move[4] > 0) {
                    if (current_value > best_value) {
                        best_value = current_value;
                        move[0] = piece[0];
                        move[1] = piece[1];
                        move[2] = piece[2];
                        if(multikill){
                            move[3] = 1;
                            kill = true;
                        }
                        else  move[3] = 0;
                    }
                }
            }
        }
    } else

    {
        if (depth % 2 == 0) {
            int max = MIN;
            boolean kill = false;
            int child_value;
            ArrayList<int[]> movable = getMovablePieces(board, "b");
            for (int[] piece :
                    movable) {
                MoveType moveType = checkMove(piece[0], piece[1], piece[2], board);
                if (kill == true) {
                    if (!(moveType.equals(MoveType.Kill) || moveType.equals(MoveType.KingSlay) || moveType.equals(MoveType.CrownKingKill) || moveType.equals(MoveType.CrownKingSlay))) {
                        continue;
                    }
                }
                if ((moveType.equals(MoveType.Kill) || moveType.equals(MoveType.KingSlay)
                        || moveType.equals(MoveType.CrownKingKill) || moveType.equals(MoveType.CrownKingSlay))) {
                    String[][] newBoard;
                    newBoard = simulateMultikill(piece[0], piece[1], piece[2], 0, board);
                    child_value = alphaBeta(depth + 1, alpha, beta, newBoard);
                    kill = true;
                } else {
                    placeMove(piece[0], piece[1], board, moveType, piece[2]);
                    child_value = alphaBeta(depth + 1, alpha, beta, board);
                    undoPlaceMove(piece[0], piece[1], board, moveType, piece[2]);
                }
                max = Math.max(max, child_value);
                if (max > alpha) {
                    alpha = max;
                }
                if (alpha >= beta) {
                    break;
                }
            }
            return alpha;
        } else {
            int min = MAX;
            boolean kill = false;
            int child_value;
            ArrayList<int[]> movable = getMovablePieces(board, "w");
            for (int[] piece : movable) {
                MoveType moveType = checkMove(piece[0], piece[1], piece[2], board);
                if (kill == true) {
                    if (!(moveType.equals(MoveType.Kill) || moveType.equals(MoveType.KingSlay) || moveType.equals(MoveType.CrownKingKill) || moveType.equals(MoveType.CrownKingSlay))) {
                        continue;
                    }
                }
                if ((moveType.equals(MoveType.Kill) || moveType.equals(MoveType.KingSlay)
                        || moveType.equals(MoveType.CrownKingKill) || moveType.equals(MoveType.CrownKingSlay))) {
                    String[][] newBoard;
                    newBoard = simulateMultikill(piece[0], piece[1], piece[2], 0, board);
                    child_value = alphaBeta(depth + 1, alpha, beta, newBoard);
                    kill = true;
                } else {
                    placeMove(piece[0], piece[1], board, moveType, piece[2]);
                    child_value = alphaBeta(depth + 1, alpha, beta, board);
                    undoPlaceMove(piece[0], piece[1], board, moveType, piece[2]);
                }
                min = Math.min(min, child_value);
                if (min < beta) {
                    beta = min;
                }
                if (alpha >= beta) {
                    break;
                }
            }
            return beta;
        }
    }
        return 0;
}

    /*
    private int minMax(int depth, String[][] board) {
        if (depth == MAX_DEPTH || isGameOver(board)) {
            return stateEvaluation(depth, board);
        }

        if (depth == 0) {
            boolean kill = false;
            int current_value;
            int best_value = MIN;
            multikill = false;
            for (int y = 0; y < board.length; y++) {
                for (int x = 0; x < board[y].length; x++) {
                    if (board[y][x].contains("b")) {
                        int[] directions = checkLegalMove(y, x, board);
                        if (directions[0] == 0) {
                            continue;
                        } else {
                            for (int i = 0; i < directions.length; i++) {
                                if (directions[i] > 0) {
                                    MoveType moveType = checkMove(y, x, directions[i], board);
                                    if (kill == true) {
                                        if (!(moveType.equals(MoveType.Kill) || moveType.equals(MoveType.KingSlay) || moveType.equals(MoveType.CrownKingKill) || moveType.equals(MoveType.CrownKingSlay))) {
                                            continue;
                                        }
                                    }
                                    if ((moveType.equals(MoveType.Kill) || moveType.equals(MoveType.KingSlay)
                                            || moveType.equals(MoveType.CrownKingKill) || moveType.equals(MoveType.CrownKingSlay))) {
                                        String[][] newBoard;
                                        newBoard = simulateMultikill(y, x, directions[i], 0, board);
                                        current_value = minMax(depth + 1, newBoard);
                                    } else {
                                        placeMove(y, x, board, moveType, directions[i]);
                                        current_value = minMax(depth + 1, board);
                                        undoPlaceMove(y, x, board, moveType, directions[i]);
                                        multikill = false;
                                    }
                                    if (current_value > best_value || (moveType.equals(MoveType.Kill) || moveType.equals(MoveType.KingSlay) || moveType.equals(MoveType.CrownKingKill) || moveType.equals(MoveType.CrownKingSlay))) {

                                        if (move[4] == 0) {
                                            best_value = current_value;
                                            move[0] = y;
                                            move[1] = x;
                                            move[2] = directions[i];
                                            move[3] = 0;

                                            if (multikill == true) {
                                                move[0] = y;
                                                move[1] = x;
                                                move[2] = directions[i];
                                                move[3] = 1;
                                                move[4] = 1;
                                                kill = true;


                                            } else if ((moveType.equals(MoveType.Kill) || moveType.equals(MoveType.KingSlay) || moveType.equals(MoveType.CrownKingKill) || moveType.equals(MoveType.CrownKingSlay))) {
                                                move[0] = y;
                                                move[1] = x;
                                                move[2] = directions[i];
                                                move[3] = 0;
                                                move[4] = 1;
                                                kill = true;
                                            }
                                        } else if (move[4] > 0) {
                                            if (current_value > best_value) {
                                                best_value = current_value;
                                                move[0] = y;
                                                move[1] = x;
                                                move[2] = directions[i];
                                                move[3] = 0;
                                            }
                                        }

                                    }

                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (depth % 2 == 0) {
                int max = MIN;
                boolean kill = false;
                int child_value;
                for (int y = 0; y < board.length; y++) {
                    for (int x = 0; x < board[y].length; x++) {
                        if (board[y][x].contains("b")) {
                            int[] directions = checkLegalMove(y, x, board);
                            if (directions[0] == 0) {
                                continue;
                            } else {
                                for (int i = 0; i < directions.length; i++) {
                                    if (directions[i] > 0) {
                                        MoveType moveType = checkMove(y, x, directions[i], board);
                                        if (kill == true) {
                                            if (!(moveType.equals(MoveType.Kill) || moveType.equals(MoveType.KingSlay) || moveType.equals(MoveType.CrownKingKill) || moveType.equals(MoveType.CrownKingSlay))) {
                                                continue;
                                            }
                                        }
                                        if ((moveType.equals(MoveType.Kill) || moveType.equals(MoveType.KingSlay)
                                                || moveType.equals(MoveType.CrownKingKill) || moveType.equals(MoveType.CrownKingSlay))) {
                                            String[][] newBoard = simulateMultikill(y, x, directions[i], 0, board);
                                            kill = true;
                                            child_value = minMax(depth + 1, newBoard);
                                        } else {
                                            placeMove(y, x, board, moveType, directions[i]);
                                            child_value = minMax(depth + 1, board);
                                            undoPlaceMove(y, x, board, moveType, directions[i]);
                                        }
                                        max = Math.max(max, child_value);
                                    }
                                }
                            }
                        }
                    }
                }
                return max;
            } else {
                int min = MAX;
                boolean kill = false;
                int child_value = MAX;
                for (int y = 0; y < board.length; y++) {
                    for (int x = 0; x < board[y].length; x++) {
                        if (board[y][x].contains("w")) {
                            int[] directions = checkLegalMove(y, x, board);
                            if (directions[0] == 0) {
                                continue;
                            } else {
                                for (int i = 0; i < directions.length; i++) {
                                    if (directions[i] > 0) {
                                        MoveType moveType = checkMove(y, x, directions[i], board);
                                        if (kill == true) {
                                            if (!(moveType.equals(MoveType.Kill) || moveType.equals(MoveType.KingSlay) || moveType.equals(MoveType.CrownKingKill) || moveType.equals(MoveType.CrownKingSlay))) {
                                                continue;
                                            }
                                        }
                                        if ((moveType.equals(MoveType.Kill) || moveType.equals(MoveType.KingSlay)
                                                || moveType.equals(MoveType.CrownKingKill) || moveType.equals(MoveType.CrownKingSlay))) {
                                            String[][] newBoard = simulateMultikill(y, x, directions[i], 0, board);
                                            kill = true;
                                            child_value = minMax(depth + 1, newBoard);
                                        } else {
                                            placeMove(y, x, board, moveType, directions[i]);
                                            child_value = minMax(depth + 1, board);
                                            undoPlaceMove(y, x, board, moveType, directions[i]);
                                        }
                                        min = Math.min(min, child_value);
                                    }
                                }
                            }
                        }
                    }
                }
                return min;
            }
        }
        return 0;
    }

     */
}
