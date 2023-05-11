package Player;

import Logic.Move;
import Logic.Othello;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    protected final int player;
    protected Othello state;

    public Player(int player, Othello state){
        this.player = player;
        this.state = state;
    }

    public Move makeMove(){
        ArrayList<Move> possibleMoves = state.getPossibleMoves(player);
        if(possibleMoves.isEmpty()){
            return null;
        }
        System.out.println("Pick one of possible moves");
        for(Move possible : possibleMoves){
            System.out.println("X: " + possible.getX() + ", Y: " + possible.getY());
        }
        Scanner scn = new Scanner(System.in);
        System.out.print("Pass horizontal coord: ");
        int x = scn.nextInt();
        System.out.print("Pass vertical coord: ");
        int y = scn.nextInt();
        Move decision = null;
        for(Move possible : possibleMoves){
            if(possible.getX() == x && possible.getY() == y){
                decision = possible;
            }
        }
        if(decision != null){
            state = state.makeMove(decision);
            return decision;
        }
        else{
            System.out.println("Pick one of possible moves!");
            for(Move possible : possibleMoves){
                System.out.println("X: " + possible.getX() + ", Y: " + possible.getY());
            }
            return makeMove();
        }
    }

    public void updateStatus(Move enemyMove){
        state = state.makeMove(enemyMove);
    }
}
