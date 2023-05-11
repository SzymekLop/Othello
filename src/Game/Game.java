package Game;

import Logic.Move;
import Logic.Othello;
import Player.Player;

import java.util.ArrayList;

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
        ArrayList<Long> laps = new ArrayList<>();
        int turn = 1;
        int i = 0;

        while (!game.isOver()){
            System.out.println("Move " + i);
            game.printBoard();
            System.out.println();
            i++;
            laps.add(System.currentTimeMillis());
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
            }

            turn = turn == 1 ? 2 : 1;
            laps.add(System.currentTimeMillis());
        }
        System.out.println("Winner: Player" + game.getWinner());
        System.out.println("Player1 points: " + game.getFirstPoints() + ", Player2 points: " + game.getSecondPoints());
        long processingTime = 0;
        ArrayList<Long> times = new ArrayList<>();
        for(int j = 0; j <laps.size(); j += 2){
            long time = laps.get(j + 1) - laps.get(j);
            processingTime += time;
            times.add(time);
        }
        System.out.println("Total processing time: " + processingTime);
        for(long time : times){
            System.out.print(time + " ");
        }

    }

    private void printMenu(){
        System.out.println("Othello");
        System.out.println("1. player vs. player");
        System.out.println("2. player vs. ai");
        System.out.println("3. ai vs. ai");
    }
}
