import java.util.ArrayList;

public class AI_Player {

    ArrayList<Tile> path = new ArrayList<Tile>();

    private int AlphaBeta(int depth, ArrayList<Tile> moves, int alpha, int beta){

        if(isLost()){
            int value = 0;
            value-= 500-depth;
            //System.out.println(value);
            return value;
        }
        else if(isWon()){
            int value = 0;
            value += 500-depth;
            //System.out.println(value);
            return  value;
        }


        if(isDraw()){
            return 0;
        }

        else if(depth == MAX_DEPTH){

            //else {
            int value = 0;
            for (int i = 0; i < path.size(); i++) {
                if (path.get(i).getGraphic().equals("O")) {
                    value += path.get(i).Value;
                } else if (path.get(i).getGraphic().equals("X")) {
                    value -= path.get(i).Value;
                }
            }
            return value;
        }
        //}



        //kør det hele gennem med en liste over moves du kan lave, tag summen af de forskellige værdier hvor hver anden
        // er et minus tal.
        else if(depth == 0){
            int current_value;
            int best_value = MIN;
            for(Tile tile: tiles){
                //System.out.println("tile pos" + tile.position);
                if(tile.getGraphic().equals("")) {
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

        }

        else {

            if (depth % 2 == 0) {
                int max = MIN;

                for(int i = 0; i < tiles.size(); i++) {
                    if (tiles.get(i).graphic.equals("")) {
                        tiles.get(i).setGraphic("O");
                        int child_value = AlphaBeta(depth + 1, moves,alpha, beta);
                        tiles.get(i).setGraphic("");
                        max = Math.max(max, child_value);
                        if(max > alpha){
                            alpha = max;
                            if(path.size() < depth){
                                path.add(tiles.get(i));
                            }
                            else{
                                path.remove(depth-1);
                                path.add(tiles.get(i));
                            }
                        }
                        if (alpha >= beta) {
                            break;
                        }
                    }
                }
                return alpha;
            }

            else {
                int min = MAX;
                for(int i = 0; i < tiles.size(); i++) {
                    if (tiles.get(i).graphic.equals("")) {
                        tiles.get(i).setGraphic("X");
                        int child_value = AlphaBeta(depth + 1, moves, alpha, beta);
                        tiles.get(i).setGraphic("");
                        min = Math.min(min, child_value);
                        if(min < beta){
                            beta = min;
                            if(path.size() < depth){
                                path.add(tiles.get(i));
                            }
                            else{
                                path.remove(depth-1);
                                path.add(tiles.get(i));
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
