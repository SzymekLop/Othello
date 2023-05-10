package Player.Heuristics;

import Logic.Othello;

public class TerritoryHeuristic implements IHeuristic{
    @Override
    public int heuristicValue(Othello gameState, int player) {
        int points = player == 1 ? gameState.getFirstPoints() : gameState.getSecondPoints();
        return gameState.getPossibleMoves(player).size() + points;
    }
}
