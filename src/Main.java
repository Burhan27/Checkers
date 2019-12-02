import java.util.Scanner;

public class Main {

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
                {"-", "-", "-", "b", "-", "b", "-", "b"},
                {"-", "-", "b", "-", "w", "-", "b", "-"},
                {"-", "w", "-", "w", "-", "b", "-", "b"},
                {"-", "-", "w", "-", "w", "-", "-", "-"},
                {"-", "w", "-", "w", "-", "w", "-", "w"},
                {"b", "-", "-", "-", "-", "-", "-", "-"},
                {"-", "-", "-", "w", "-", "w", "-", "-"},
                {"w", "-", "w", "-", "-", "-", "w", "-"}
        };

        String[][] board3 = {
                {"-", "-", "-", "-", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "b", "-", "-", "-"},
                {"-", "-", "-", "w", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "-", "-", "-", "-"},
                {"b", "-", "-", "w", "-", "-", "-", "-"},
                {"-", "w", "-", "-", "-", "-", "-", "-"},
                {"-", "-", "-", "w", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "-", "-", "-", "-"}
        };

        SimpleCheckers game = new SimpleCheckers(board3);
        game.gameLogic();

    }




}
