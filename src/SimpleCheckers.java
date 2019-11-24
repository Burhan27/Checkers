public class SimpleCheckers {

    static int MAX_DEPTH = 2;
    static int MAX = 1000;
    static int MIN = -1000;

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

    private MoveType checkMove(int x, int y, int x2, int y2) {
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


    }

    private int[] getPossibleMoves(int x, int y) {
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

    }

}
