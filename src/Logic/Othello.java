package Logic;

import java.util.ArrayList;
import java.util.HashSet;

public class Othello {

    //0 = empty
    //1 = 1st player
    //2 = 2nd player

    public static final int BOARD_SIZE = 8;

    private final int[][] board;
    private boolean isOverOne;
    private boolean isOverTwo;
    private int firstPoints;
    private int secondPoints;

    public Othello(){

        this.board = new int[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){

                board[i][j] = 0;
            }
        }
        board[3][3] = 1;
        board[3][4] = 2;
        board[4][3] = 2;
        board[4][4] = 1;

        this.isOverOne = false;
        this.isOverTwo = false;
        this.firstPoints = 2;
        this.secondPoints = 2;
    }

    public Othello(Othello toCopy){

        this.board = new int[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++){
            System.arraycopy(toCopy.board[i], 0, board[i], 0, BOARD_SIZE);
        }
        this.isOverOne = false;
        this.isOverTwo = false;
        this.firstPoints = toCopy.firstPoints;
        this.secondPoints = toCopy.secondPoints;
    }

    public Othello(int[][] board){
        this.board = board;
        this.firstPoints = 0;
        this.secondPoints = 0;
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++) {
                if(board[i][j] == 1){
                    firstPoints++;
                }
                if (board[i][j] == 2){
                    secondPoints++;
                }
            }
        }
        this.isOverOne = false;
        this.isOverTwo = false;

    }

    public ArrayList<Move> getPossibleMoves(int player){

        HashSet<Move> possibleMoves = new HashSet<>();
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j <BOARD_SIZE; j++){
                if(board[i][j] != 0 && board[i][j] != player){
                    for(int xOffset = -1; xOffset <= 1; xOffset++){
                        for(int yOffset = -1; yOffset <= 1; yOffset++){
                            int possibleX = i+xOffset;
                            int possibleY = j+yOffset;
                            if(possibleX >= 0 && possibleY >= 0 && possibleX < BOARD_SIZE && possibleY < BOARD_SIZE) {
                                Move possibleMove = new Move(possibleX, possibleY, player);
                                if (checkMove(possibleMove)) {
                                    possibleMoves.add(possibleMove);
                                }
                            }
                        }
                    }
                }
            }
        }
        if(player == 1){
            isOverOne = possibleMoves.isEmpty();
        }
        else{
            isOverTwo = possibleMoves.isEmpty();
        }
        return new ArrayList<>(possibleMoves);
    }

    public boolean checkDirection(Move move, int xDirection, int yDirection){

        int currX = move.getX();
        int currY = move.getY();
        boolean doesBound = false;
        while (true){
            currX += xDirection;
            currY += yDirection;

            if(currX == BOARD_SIZE || currY == BOARD_SIZE || currX == -1 || currY == -1){
                return false;
            }
            if(board[currX][currY] == 0){
                return false;
            }
            if(board[currX][currY] == move.getPlayer()){
                return doesBound;
            }
            if(board[currX][currY] != move.getPlayer()){
                doesBound = true;
            }
        }
    }

    public boolean checkMove(Move move){
        if(board[move.getX()][move.getY()] == 0) {
            for (int xDirection = -1; xDirection <= 1; xDirection++) {
                for (int yDirection = -1; yDirection <= 1; yDirection++) {
                    if (checkDirection(move, xDirection, yDirection)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void processMove(Move move){
        switch (move.getPlayer()) {
            case 1 -> firstPoints++;
            case 2 -> secondPoints++;
        }

        if(board[move.getX()][move.getY()] != 0){
            System.out.println("Cheater!!!!!!!!!!!!!!!!!!!!!!!!");
        }

        board[move.getX()][move.getY()] = move.getPlayer();

        for (int xDirection = -1; xDirection <= 1; xDirection++) {
            for (int yDirection = -1; yDirection <= 1; yDirection++) {
                if((xDirection != 0 || yDirection != 0) && checkDirection(move, xDirection, yDirection)){
                    processMoveDirection(move, xDirection, yDirection);
                }
            }
        }
    }

    public Othello makeMove(Move move){

        Othello copy = new Othello(this);
        copy.processMove(move);
        return copy;
    }
    public void processMoveDirection(Move move, int xDirection, int yDirection){
        int currX = move.getX() + xDirection;
        int currY = move.getY() + yDirection;

        while (currX >= 0 && currY >= 0 && currX < BOARD_SIZE && currY < BOARD_SIZE){

            if(board[currX][currY] != move.getPlayer() && board[currX][currY] != 0){
                board[currX][currY] = move.getPlayer();
                switch (move.getPlayer()) {
                    case 1:
                        firstPoints++;
                        secondPoints--;
                        break;
                    case 2:
                        secondPoints++;
                        firstPoints--;
                        break;
                }
            }
            else{
                break;
            }

            currX += xDirection;
            currY += yDirection;
        }
    }

    public int getWinner(){
        return firstPoints > secondPoints ? 1 : secondPoints > firstPoints ? 2 : 0;
    }

    public void printBoard(){

        System.out.println("  0-1-2-3-4-5-6-7-");
        for (int i = 0; i < BOARD_SIZE; i++){
            System.out.print(i +" ");
            for(int j = 0; j < BOARD_SIZE; j++){
                System.out.print(board[i][j] + "|");
            }
            System.out.println();
        }
        for(int i = 0; i < 2 * BOARD_SIZE + 2; i++){
            System.out.print("-");
        }
    }

    public boolean isOver(){
        return isOverOne && isOverTwo;
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean isOverOne() {
        return isOverOne;
    }

    public void setOverOne(boolean over) {
        isOverOne = over;
    }

    public boolean isOverTwo() {
        return isOverTwo;
    }

    public void setOverTwo(boolean over) {
        isOverTwo = over;
    }
    public int getFirstPoints() {
        return firstPoints;
    }

    public void setFirstPoints(int firstPoints) {
        this.firstPoints = firstPoints;
    }

    public int getSecondPoints() {
        return secondPoints;
    }

    public void setSecondPoints(int secondPoints) {
        this.secondPoints = secondPoints;
    }
}
