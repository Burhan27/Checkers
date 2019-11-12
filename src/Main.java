import java.util.Scanner;

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


     static void gameLogic(String[][] board) {
         int turn = 0;
         Scanner scanner = new Scanner(System.in);
         int input, boardinputX, boardinputY, playerX, playerY, moveinputX, moveinputY, moveX, moveY;

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
                         while (!board[boardinputX][boardinputY].equals("-")) {
                             System.out.print("Vælg et felt, som ikke er optaget!");
                             moveinputX = scanner.nextInt();
                             moveinputY = scanner.nextInt();
                         }
                         //Her skal vi tjekke om det er et tilddat move
                         playerX = boardinputX;
                         playerY = boardinputY;
                         //Her skal vi rykke brækken der hen og så fjerne dem der er under
                         //Her skal vi udvikle brækken hvis det er ved enden
                         //break
                     }
                 }
                 //     printBoard(board);
             }
         }
     }
/*
    static boolean checkMove(int positionX, int positionY, int moveX, int moveY, String[][] State) {
        return false;
    }

 */
/*
    static String getWinner(String [][] state) {
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
*/
/*
void printBoard(String[][] board){
             for (String[] x : board) {
                 for (String y : x) {
                     System.out.print(y + " ");
                 }
                 System.out.println();
             }
         }
  */


    static String[][] placeMove(int x, int y, String[][] Board, String player) {
       //Tjek om brikken er DamBLACK.

        //Tjek først om feltet er skråt for

        //Fjern bræk og sæt nyt op

    Board[x][y] = player;
        return Board;
    }



}
