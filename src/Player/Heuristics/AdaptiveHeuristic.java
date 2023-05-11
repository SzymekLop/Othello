package Player.Heuristics;

import Logic.Othello;

public class AdaptiveHeuristic implements IHeuristic{

    private IHeuristic current;
    private final StaticValueHeuristic staticHeu = new StaticValueHeuristic();
    private final TerritoryHeuristic territoryHeu = new TerritoryHeuristic();
    private final NOPointsHeuristic pointsHeu = new NOPointsHeuristic();
    private final CornerHeuristic cornerHeu = new CornerHeuristic();
    private final LinearHeuristic linearHeu = new LinearHeuristic();
    public AdaptiveHeuristic(){
        current = staticHeu;
    }
    @Override
    public int heuristicValue(Othello gameState, int player) {
        int enemy = player == 1 ? 2 : 1;
        if(staticHeu.heuristicValue(gameState, enemy) > staticHeu.heuristicValue(gameState, player) * 1.6){
            current = linearHeu;
        }
        else if(gameState.getFirstPoints() + gameState.getSecondPoints() >= 32 &&
                pointsHeu.heuristicValue(gameState, player) * 1.6 < pointsHeu.heuristicValue(gameState, enemy)){
            current = territoryHeu;
        }
        return current.heuristicValue(gameState, player);
    }
}
