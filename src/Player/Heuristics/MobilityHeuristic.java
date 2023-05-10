package Player.Heuristics;

import Logic.Othello;

public class MobilityHeuristic implements IHeuristic{
    @Override
    public int heuristicValue(Othello gameState, int player) {
        return gameState.getPossibleMoves(player).size();
    }
}
