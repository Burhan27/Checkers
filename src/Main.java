public class Main {

    public static void main(String[] args) {

        String [][] board = {
                {"-","b","-" ,"b" ,"-" ,"b" ,"-" ,"b"},
                {"b","-","b" ,"-" ,"b" ,"-" ,"b" ,"-"},
                {"-","b","-" ,"b" ,"-" ,"b" ,"-" ,"b"},
                {"-","-","-" ,"-" ,"-" ,"-" ,"-" ,"-"},
                {"-","-","-" ,"-" ,"-" ,"-" ,"-" ,"-"},
                {"w","-","w" ,"-" ,"w" ,"-" ,"w" ,"-"},
                {"-","w","-" ,"w" ,"-" ,"w" ,"-" ,"w"},
                {"w","-","w" ,"-" ,"w" ,"-" ,"w" ,"-"}
        };


        gameLogic(board);

    }

    public static void gameLogic(String[][] board) {
        printBoard(board);
    }




    public static void printBoard(String[][] board) {
        for(String[] x : board) {
            for(String y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }

}
