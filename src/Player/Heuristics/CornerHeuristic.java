package Player.Heuristics;

import Logic.Othello;

public class CornerHeuristic implements IHeuristic{

    public CornerHeuristic(){

    }
    @Override
    public int heuristicValue(Othello gameState, int player) {
        int score = 0;
        final int size = gameState.getBoard().length;
        if (gameState.getBoard()[0][0] == player) {
            score += 10;
        }
        else if(gameState.getBoard()[0][0] != 0){
            score -= 10;
        }
        if (gameState.getBoard()[0][size - 1] == player) {
            score += 10;
        }
        else if(gameState.getBoard()[0][size - 1] != 0){
            score -= 10;
        }
        if (gameState.getBoard()[size - 1][0] == player) {
            score += 10;
        }
        else if(gameState.getBoard()[size - 1][0] != 0){
            score -= 10;
        }
        if (gameState.getBoard()[size - 1][size - 1] == player) {
            score += 10;
        }
        else if(gameState.getBoard()[size - 1][size - 1] != 0){
            score -= 10;
        }

        return score + (gameState.getFirstPoints() - gameState.getSecondPoints()) * player == 1 ? 1 : -1;
    }
}
