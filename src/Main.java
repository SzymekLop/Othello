import Game.Game;
import Logic.Othello;
import Player.AIPlayer;
import Player.Heuristics.StaticValueHeuristic;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {

        Othello game = new Othello();
        Othello game1 = new Othello();
        Othello game2 = new Othello();

        StaticValueHeuristic heu1 = new StaticValueHeuristic();
        StaticValueHeuristic heu2 = new StaticValueHeuristic();

        AIPlayer pl1 = new AIPlayer(game1, 1, heu1, "minMax");
        AIPlayer pl2 = new AIPlayer(game2, 2, heu2, "minMax");

        Game play = new Game(pl1, pl2, game);

        play.play();

    }
}