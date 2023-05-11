import Game.Game;
import Logic.Othello;
import Player.AIPlayer;
import Player.Heuristics.*;
import Player.Player;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {


        Othello game = new Othello();
        Othello game1 = new Othello();
        Othello game2 = new Othello();

        StaticValueHeuristic heu1 = new StaticValueHeuristic();
        TerritoryHeuristic heu2 = new TerritoryHeuristic();
        NOPointsHeuristic heu3 = new NOPointsHeuristic();
        MobilityHeuristic heu4 = new MobilityHeuristic();
        CornerHeuristic heu5 = new CornerHeuristic();
        LinearHeuristic heu6 = new LinearHeuristic();

        AdaptiveHeuristic heu7 = new AdaptiveHeuristic();

        AIPlayer pl1 = new AIPlayer(game1, 1, heu1, "minMx");
        AIPlayer pl2 = new AIPlayer(game2, 2, heu7, "minax");
        Player pl = new Player(1, game1);
        Game play = new Game(pl1, pl2, game);

        play.play();

    }
}