package Player.Heuristics;

import Logic.Othello;

public interface IHeuristic {

    public int heuristicValue(Othello gameState, int player);

}
