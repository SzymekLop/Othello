package Player.Heuristics;

import Logic.Move;
import Logic.Othello;

public class LinearHeuristic implements IHeuristic{

    private static final int[][] DIRECTIONS = {{-1,0}, {1,0}, {0,-1}, {0,1}, {-1,-1}, {-1,1}, {1,-1}, {1,1}};

    public  LinearHeuristic(){
    }
    @Override
    public int heuristicValue(Othello gameState, int player) {
        int score = 0;
        for (Move move : gameState.getPossibleMoves(player)) {
            int count = 0;
            int dx = 0;
            int dy = 0;
            int nx = 0;
            int ny = 0;
            for (int[] direction : DIRECTIONS) {
                dx = direction[0];
                dy = direction[1];
                nx = move.getX() + dx;
                ny = move.getY() + dy;
                while (nx >= 0 && nx < gameState.getBoard().length && ny >= 0 && ny < gameState.getBoard().length
                        && gameState.getBoard()[nx][ny] != 0) {
                    count++;
                    nx += dx;
                    ny += dy;
                }
            }
            if(gameState.getBoard()[nx - dx][ny - dy] == player) {
                score += count;
            }
        }
        return score;
    }
}
