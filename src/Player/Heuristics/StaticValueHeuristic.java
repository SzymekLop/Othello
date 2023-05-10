package Player.Heuristics;

import Logic.Othello;

public class StaticValueHeuristic implements IHeuristic{

    private int[][] staticValues;

    public  StaticValueHeuristic(){
        this.staticValues = new int[][]{{ 4, -3,  2,  2,  2,  2, -3,  4},
                                        {-3, -4, -1, -1, -1, -1, -4, -3},
                                        { 2, -1,  1,  0,  0,  1, -1,  2},
                                        { 2, -1,  0,  1,  1,  0, -1,  2},
                                        { 2, -1,  0,  1,  1,  0, -1,  2},
                                        { 2, -1,  1,  0,  0,  1, -1,  2},
                                        {-3, -4, -1, -1, -1, -1, -4, -3},
                                        { 4, -3,  2,  2,  2,  2, -3,  4}};
    }

    @Override
    public int heuristicValue(Othello gameState, int player) {
        int result = 0;
        for (int i = 0; i < Othello.BOARD_SIZE; i++){
            for (int j = 0; j < Othello.BOARD_SIZE; j++){
                if(gameState.getBoard()[i][j] == player){
                    result += staticValues[i][j];
                }
                else if(gameState.getBoard()[i][j] != 0){
                    result -= staticValues[i][j];
                }
            }
        }
        return result;
    }
}
