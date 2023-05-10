package Player.Heuristics;

import Logic.Othello;

public class NOPointsHeuristic implements IHeuristic{

    public  NOPointsHeuristic(){
    }

    @Override
    public int heuristicValue(Othello gameState, int player) {
        int result = 0;
        for (int i = 0; i < Othello.BOARD_SIZE; i++){
            for (int j = 0; j < Othello.BOARD_SIZE; j++){
                result += gameState.getBoard()[i][j] == 1 ? 1
                            : gameState.getBoard()[i][j] == 0 ? 0 : -1;
            }
        }
        return result;
    }
}
