package Game;

import Logic.Move;
import Logic.Othello;
import Player.Player;

import java.util.Scanner;

public class Game {

    private Player player1;
    private Player player2;

    private Othello game;

    public Game(Player player1, Player player2, Othello game) {
        this.player1 = player1;
        this.player2 = player2;
        this.game = game;
    }

    public void play(){
        int turn = 1;
        int i = 0;
        Scanner scn = new Scanner(System.in);
        while (!game.isOver()){
            System.out.println("iteration " + i);
            game.printBoard();
            System.out.println();
            i++;
            Move toProcess = null;
            if(turn == 1){
                toProcess = player1.makeMove();
                player2.updateStatus(toProcess);
            }
            else{
                toProcess = player2.makeMove();
                player1.updateStatus(toProcess);
            }
            if(toProcess == null){
                if (turn == 1) {
                    game.setOverOne(true);
                } else {
                    game.setOverTwo(true);
                }
            }
            else {
                if (turn == 1) {
                    game.setOverOne(false);
                    player2.updateStatus(toProcess);
                } else {
                    game.setOverTwo(false);
                    player1.updateStatus(toProcess);
                }
                game = game.makeMove(toProcess);
                //scn.next();
            }

            turn = turn == 1 ? 2 : 1;
        }
        System.out.println(game.getWinner());

    }

    private void printMenu(){
        System.out.println("Othello");
        System.out.println("1. player vs. player");
        System.out.println("2. player vs. ai");
        System.out.println("3. ai vs. ai");
    }
}
