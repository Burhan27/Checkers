import java.util.Scanner;


public class SimpleCheckers {

    static int MAX_DEPTH = 2;
    static int MAX = 1000;
    static int MIN = -1000;

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

        gameLogic(board);

    }



    static void gameLogic(String[][] board) {
        int turn = 0;
        Scanner scanner = new Scanner(System.in);
        int input, boardinputX, boardinputY, playerX = 0, playerY = 0, moveinputX, moveinputY, moveX, moveY;

        System.out.print("Tast 1 for at være 1 spiller, eller tast 2 for at være 2 spiller");

        input = scanner.nextInt();

        while (!(input > 0 && input < 3)) {
            System.out.print("Tast enten 1 og 2");
            input = scanner.nextInt();
        }

        if (input == 2) {
            turn++;
        }

        while (1 > 0) {
            turn++;

            if ((turn % 2) == 0) {
                System.out.println("bote");
            } else {
                System.out.print("Tast to tal  0-7 (Den brik du gerne vil rykke)");
                boardinputX = 900;
                boardinputY = 900;
                while ((!(boardinputY > -1 && boardinputY < 8 && boardinputX < 8 && boardinputX > -1))) {
                    boardinputX = scanner.nextInt();
                    boardinputY = scanner.nextInt();
                    System.out.print(boardinputX + " " + boardinputY);
                    if (boardinputY > -1 && boardinputY < 8 && boardinputX < 8 && boardinputX > -1) {
                        while (!board[boardinputX][boardinputY].equals("b")) {
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
                moveinputX = 900;
                moveinputY = 900;
                while ((!(moveinputX > -1 && moveinputX < 8 && moveinputY < 8 && moveinputY > -1))) {
                    moveinputX = scanner.nextInt();
                    moveinputY = scanner.nextInt();
                    if (moveinputX > -1 && moveinputX < 8 && moveinputY < 8 && moveinputY > -1) {
                        while (checkMove(playerX, playerY, moveinputX, moveinputY, board) == MoveType.illegal) {
                            System.out.print("Vælg et felt, som ikke er optaget!");
                            moveinputX = scanner.nextInt();
                            moveinputY = scanner.nextInt();
                        }
                        //tjek for andre playerType


                        //Her skal vi tjekke om det er et tilddat move
                        //Her skal vi rykke brækken der hen og så fjerne dem der er under
                        //Her skal vi udvikle brækken hvis det er ved enden
                        //break
                    }
                }
                //     printBoard(board);
            }
        }
    }



    static private MoveType checkMove(int x, int y, int x2, int y2, String[][] board) {
        if (board[y][x].equals("b")) {
            if (board[y2][x2].equals("b")) {
                return MoveType.illegal;
            } else if (board[y2][x2].equals("-")) {
                return MoveType.Standard;
            } else if ((board[y2][x2].equals("w") && (x2 + (x2 - x) < 8 || y2 + (y2 - y) < 8))) {
                if (board[x2 + (x2 - x)][y2 + (y2 - y)].equals("-")) {
                    return MoveType.Kill;
                }
            }
        }

            return MoveType.illegal;
    }


    //Alen Tjek, dont worry about it right now
    boolean checkLegalMove(int x1, int y1, int x2, int y2, String[][] board) {
        if (board[x1][y1].equals("w") || board[x1][y1].contains("k")) {
            if (board[x1 + 1][x2 + 1].equals(board[x2][y2])) {
                if (board[x2][y2].equals("-")) {
                    return true;
                } else if ((board[x2][y2].equals("b"))) {
                    if (board[x2 + 1][y2 + 1].equals("-")) {
                        return true;
                    } else {
                        return false;
                    }
                }

            }
        }
        return false;
    }



    private int[] getPossibleMoves(int x, int y, String[][] board) {
        int[] moves = new int[4];
        if (board[y][x].equals("b")) {
            if (board[y + 1][x + 1].equals("b")) {
                moves[0] = -1;
            } else if (board[y + 1][x + 1].equals("w")) {
                if (x + 2 < 8 || y + 2 < 8) {
                    if (board[y + 2][x + 2].equals("-")) {
                        moves[0] = 11;
                    }
                }

            }
        } else if (board[y][x].contains("k")) {

        }

        return moves;
    }


    void printBoard(String[][] board) {
        for(String[] x : board) {
            for(String y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }


}
