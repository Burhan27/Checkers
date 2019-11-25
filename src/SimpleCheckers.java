import java.util.ArrayList;
import java.util.Scanner;


public class SimpleCheckers {

    static int MAX_DEPTH = 2;
    static int MAX = 1000;
    static int MIN = -1000;
    static int[] move = new int[3];

    public static void main(String[] args) {

        String[][] board = {
                {"-", "b", "-", "b", "-", "b", "-", "b"},
                {"b", "-", "b", "-", "b", "-", "b", "-"},
                {"-", "b", "-", "b", "-", "b", "-", "b"},
                {"-", "-", "-", "-", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "-", "-", "-", "-"},
                {"w", "-", "w", "-", "w", "-", "w", "-"},
                {"-", "w", "-", "w", "-", "w", "-", "w"},
                {"w", "-", "w", "-", "w", "-", "w", "-"}
        };

        String[][] board2 = {
                {"-", "-", "-", "-", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "b", "-", "-", "-"},
                {"-", "b", "-", "w", "-", "-", "-", "w"},
                {"-", "-", "-", "-", "-", "-", "-", "-"}
        };


      /*  System.out.println(board[5][0] + " and " + board[4][1]);
        System.out.println("TYPE IS " + checkMove(2, 3, 1, 4, board));
        int[] moves = getPossibleMoves(2, 1, board);
        System.out.println("possibluy moves for 'b'= " + moves[0] + " " + moves[3]);
        moves = getPossibleMoves(5, 0, board);
        System.out.println("possibluey moves for 'w' = " + moves[0] + " " + moves[3]);
        moves = getPossibleMoves(2, 3, board);
        System.out.println("plavesdeble movesv for 'K' =" + moves[0] + " " + moves[3]);

        System.out.println("java exksepirment  " + "wk".contains("b"));*/
        gameLogic(board);

    }

    static void gameLogic(String[][] board) {
        int turn = 0;
        MoveType moveType;
        Scanner scanner = new Scanner(System.in);
        int input, boardinputX, boardinputY, moveDirection;

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
            turn++;
            if ((turn % 2) == 0) {
                pcTurn(board);
                printBoard(board);
            } else {
                System.out.print("Tast en y værdi og så en x værdi");
                boardinputY = scanner.nextInt();
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
                moveType = checkMove(boardinputY, boardinputX,moveDirection, board);
                placeMove(boardinputY, boardinputX, board, moveType, moveDirection);
                printBoard(board);
            }
        }
    }

    static int[] getDirectionCoordinate(int y, int x, int direction) {
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

    static private String getWinner(String[][] board) {
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
        if (bcount == 0) {
            return "w";
        } else if (wcount == 0) {
            return "b";
        } else return "NA";
    }

    static private MoveType checkMove(int y, int x, int direction, String[][] board) {
        MoveType moveType = MoveType.Illegal;
        int[] destination = getDirectionCoordinate(y, x, direction);
        int y2 = destination[0];
        int x2 = destination[1];

        if (y2 < 0 || y2 > 7 || x2 < 0 || x2 > 7) {
            return moveType;
        }

        if (board[y][x].contains("k")) {
            if (board[y][x].contains(board[y2][x2])) {
                moveType = MoveType.KingIllegal;
            } else if (board[y2][x2].equals("-")) {
                moveType = MoveType.KingStandard;
            } else if (!board[y][x].contains(board[y2][x2]) && !board[y2][x2].equals("-")) {
                int dx = x2 + (x2 - x);
                System.out.println("dx " + dx);
                int dy = y2 + (y2 - y);
                System.out.println("dy " + dy);
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
                int dx = x2 + (x2 - x);
                int dy = y2 + (y2 - y);
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


    static private int[] checkLegalMove(int y, int x, String[][] board) {
        int[] moves = getPossibleMoves(y, x, board);
        MoveType moveType;
        int illegal_count = 0;
        for (int i = 0; i < moves.length; i++) {

            if (moves[i] > 0) {
                moveType = checkMove(y, x, moves[i], board);
                if (moveType.equals(MoveType.Illegal) || moveType.equals(MoveType.KingIllegal)) {
                    moves[i] = -1;
                } else continue;
            }

            if (moves[i] == -1) {
                illegal_count++;
                if (illegal_count == 4) {
                    moves[0] = 0;
                }
            }
        }
        return moves;
    }


    static private int[] getPossibleMoves(int y, int x, String[][] board) {
        int[] moves = new int[4];
        if (board[y][x].equals("b")) {
            moves[0] = 3;
            moves[1] = 4;
            moves[2] = -1;
            moves[3] = -1;
        } else if (board[y][x].contains("k")) {
            moves[0] = 1;
            moves[1] = 2;
            moves[2] = 3;
            moves[3] = 4;
        } else if (board[y][x].equals("w")) {
            moves[0] = 1;
            moves[1] = 2;
            moves[2] = -1;
            moves[3] = -1;
        } else {
            moves[0] = -1;
            moves[1] = -1;
            moves[2] = -1;
            moves[3] = -1;
        }

        return moves;
    }


    static void printBoard(String[][] board) {
        for (String[] x : board) {
            for (String y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }

    static String[][] placeMove(int y, int x, String[][] board, MoveType movetype, int direction) {
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

    static String[][] undoPlaceMove(int y, int x, String[][] board, MoveType movetype, int direction) {
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

    static private int stateEvaluation(ArrayList<MoveType> moves, int depth, String[][] board) {
        if (getWinner(board).equals("w")) {
            int value = 0;
            value -= 500 - depth;
            return value;

        } else if (getWinner(board).equals("b")) {
            int value = 0;
            value += 500 - depth;
            return value;

        } else {
            int value = 0;
            for (int i = 0; i < moves.size(); i++) {
                System.out.println("MOVESIZE =" +moves.size());
                if (i % 2 == 0) {
                    if (moves.get(i).equals(MoveType.KingStandard) || moves.get(i).equals(MoveType.Standard)) {
                        value += 1;
                    } else if (moves.get(i).equals(MoveType.Kill)) {
                        value += 5;
                    } else if (moves.get(i).equals(MoveType.KingSlay)) {
                        value += 10;
                    } else if (moves.get(i).equals(MoveType.CrownKing)) {
                        value += 12;
                    } else if (moves.get(i).equals(MoveType.CrownKingSlay)) {
                        value += 22;
                    } else if (moves.get(i).equals(MoveType.CrownKingKill)) {
                        value += 17;
                    }
                } else {
                    if (i % 2 == 0) {
                        if (moves.get(i).equals(MoveType.KingStandard) || moves.get(i).equals(MoveType.Standard)) {
                            value -= 1;
                        } else if (moves.get(i).equals(MoveType.Kill)) {
                            value -= 5;
                        } else if (moves.get(i).equals(MoveType.KingSlay)) {
                            value -= 10;
                        } else if (moves.get(i).equals(MoveType.CrownKing)) {
                            value -= 12;
                        } else if (moves.get(i).equals(MoveType.CrownKingSlay)) {
                            value -= 22;
                        } else if (moves.get(i).equals(MoveType.CrownKingKill)) {
                            value -= 17;
                        }
                    }
                }
            }
            System.out.println("VALUE: " + value);
            return value;
        }
    }

    static private void pcTurn(String[][] board) {
        ArrayList<MoveType> moves = new ArrayList<MoveType>();
        alpharBeta(0, moves, MIN, MAX, board);
        MoveType moveType = checkMove(move[0], move[1], move[2], board);
        System.out.println(" black BOTE DO " + moveType);
        System.out.println(" BLACK BOTE TOOK y: " + move[0] + " x: " + move[1]);
        placeMove(move[0], move[1], board, moveType, move[2]);
    }

    static private boolean isGameOver(String[][] board){
       String winner = getWinner(board);
       if(!winner.equals("NA")){
           return true;
       }
       else return false;
    }
    static private int alpharBeta(int depth, ArrayList<MoveType> path, int alpha, int beta, String[][] board) {
        System.out.println("SIZE IN AB:" +path.size() + "in depth " + depth);
        if (depth == MAX_DEPTH || isGameOver(board)) {
            return stateEvaluation(path, depth, board);
        }
        //kør det hele gennem med en liste over moves du kan lave, tag summen af de forskellige værdier hvor hver anden
        // er et minus tal.
        else if (depth == 0) {
            int current_value;
            int best_value = MIN;
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
                                    path.add(moveType);
                                    placeMove(y, x, board, moveType, directions[i]);
                                    current_value = alpharBeta(depth + 1, path, alpha, beta, board);
                                    System.out.println("C: "+ current_value);
                                    undoPlaceMove(y, x, board, moveType, directions[i]);
                                    if (current_value > best_value) {
                                        best_value = current_value;
                                        move[0] = y;
                                        move[1] = x;
                                        move[2] = directions[i];
                                    }
                                    System.out.println("bout to clear");
                                    path.clear();
                                    System.out.println("done clear"+ path.size());
                                } else {
                                    System.out.println("imma clear out");
                                    path.clear();
                                    System.out.println("ale clear" + path.size());
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (depth % 2 == 0) {
                int max = MIN;

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
                                        path.add(moveType);
                                        placeMove(y, x, board, moveType, directions[i]);
                                        int child_value = alpharBeta(depth + 1, path, alpha, beta, board);
                                        System.out.println("MAX_CHILD = "+ child_value );
                                        undoPlaceMove(y, x, board, moveType, directions[i]);
                                        max = Math.max(max, child_value);
                                        if (max > alpha) {
                                            alpha = max;
                                            System.out.println("alpha up");
                                            if (path.size() < depth) {
                                                System.out.println("not deep enough");
                                                path.add(moveType);
                                            } else {
                                                System.out.println("remove 1");
                                                path.remove(depth-1);
                                                System.out.println(path.size());
                                                path.add(moveType);
                                            }
                                        }
                                        if (alpha > beta) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return alpha;
            } else {
                int min = MAX;

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
                                        path.add(moveType);
                                        placeMove(y, x, board, moveType, directions[i]);
                                        int child_value = alpharBeta(depth + 1, path, alpha, beta, board);
                                        System.out.println("MIN_CHILD = "+ child_value );
                                        undoPlaceMove(y, x, board, moveType, directions[i]);
                                        min = Math.min(min, child_value);
                                        if (min < beta) {
                                            beta = min;
                                            if (path.size() < depth) {
                                                System.out.println("min too deep");
                                                path.add(moveType);
                                            } else {
                                                System.out.println("min remove");
                                                path.remove(depth - 1);
                                                System.out.println("stinky "+ path.size());
                                                path.add(moveType);
                                            }
                                        }
                                        if (alpha > beta) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return beta;
            }

        }
        return 0;

    }
}
