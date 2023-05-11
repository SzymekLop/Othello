package Player;

import Logic.Move;
import Logic.Othello;
import Player.Heuristics.IHeuristic;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AIPlayer extends Player{

    private static final int DEPTH = 4;
    private final IHeuristic heuristic;
    private final DecisionTree decisionTree;
    private final String mode;

    public AIPlayer(Othello game, int player, IHeuristic heuristic, String mode){
        super(player, game);
        this.decisionTree = new DecisionTree(game, player);
        this.heuristic = heuristic;
        this.mode = mode;
    }

    public Move makeMove(){
        if(mode.equals("minMax"))
            return decisionTree.pickMoveMinMax(player);
        else
            return decisionTree.pickMoveAlphaBeta(player);
    }

    public void updateStatus(Move enemyMove){
        decisionTree.updateState(enemyMove);
    }
    public void updateState(Move move){
        decisionTree.updateState(move);
    }

    private class DecisionTree {

        private Node currState;
        private int player;

        public DecisionTree(Othello game, int player){
            this.currState = new Node(game, null, new Move(-1, -1, 2), 1);
            this.player = player;
        }

        public void updateState(Move move){
            if(currState.children.isEmpty()){
                currState.createChildren();
            }
            for(Node child : currState.children){
                boolean comp = child.change.equals(move);
                if(comp){
                    currState = child;
                    currState.parent = null;
                    return;
                }
            }
        }

        public Move pickMoveMinMax(int player){

            fillTree(currState, 0);
            minMax(currState, 0);
            if(currState.children.isEmpty() || currState.children.get(0).change.getPlayer() != player){
                return null;
            }
            int bestValue = currState.children.get(0).value;
            int bestIndex = 0;
            for(int i = 0; i < currState.children.size(); i++) {
                if (currState.children.get(i).value > bestValue) {
                    bestValue = currState.children.get(i).value;
                    bestIndex = i;
                }
            }
            currState = currState.children.get(bestIndex);
            currState.parent = null;
            return currState.change;
        }

        public Move pickMoveAlphaBeta(int player){

            fillTree(currState, 0);
            alphaBeta(currState, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if(currState.children.isEmpty()){
                return null;
            }
            int bestValue = currState.children.get(0).value;
            int bestIndex = 0;
            for(int i = 0; i < currState.children.size(); i++) {
                if (currState.children.get(i).value > bestValue) {
                    bestValue = currState.children.get(i).value;
                    bestIndex = i;
                }
            }
            currState = currState.children.get(bestIndex);
            currState.parent = null;
            return currState.change;
        }

        public void fillTree(Node curr, int depth){
            if(depth != DEPTH){
                if(curr.children.isEmpty() && !curr.state.isOver()){
                    curr.createChildren();
                }
                for (Node kid : curr.children){
                    fillTree(kid, depth + 1);
                }
            }
        }

        public int minMax(Node curr, int depth){

            if(depth >= DEPTH || curr.state.isOver()){
                curr.value = heuristic.heuristicValue(curr.state, this.player);
                return curr.value;
            }

            if(curr.turn == this.player){
                int maxEval = Integer.MIN_VALUE;
                for(Node kid : curr.children){
                    int eval = minMax(kid, depth + 1);
                    maxEval = Integer.max(maxEval, eval);
                }
                curr.value = maxEval;
                return maxEval;
            }
            else{
                int minEval = Integer.MAX_VALUE;
                for(Node kid : curr.children){
                    int eval = minMax(kid, depth + 1);
                    minEval = Integer.min(minEval, eval);
                }
                curr.value = minEval;
                return  minEval;
            }
        }

        public int alphaBeta(Node curr, int depth, int alpha, int beta){

            if(depth >= DEPTH || curr.state.isOver()){
                curr.value = heuristic.heuristicValue(curr.state, this.player);
                return curr.value;
            }
            if(curr.turn == this.player){
                int maxEval = Integer.MIN_VALUE;
                for(Node kid : curr.children){
                    int eval = alphaBeta(kid, depth + 1, alpha, beta);
                    maxEval = Integer.max(maxEval, eval);
                    alpha = Integer.max(alpha, maxEval);
                    if(beta <= alpha) {
                        curr.value = maxEval;
                        break;
                    }
                }
                curr.value = maxEval;
                return maxEval;
            }
            else{
                int minEval = Integer.MAX_VALUE;
                for(Node kid : curr.children){
                    int eval = alphaBeta(kid, depth + 1, alpha, beta);
                    minEval = Integer.min(minEval, eval);
                    beta = Integer.min(beta, minEval);
                    if(beta <= alpha) {
                        curr.value = minEval;
                        break;
                    }
                }
                curr.value = minEval;
                return minEval;
            }
        }

        private class Node {

            private final Othello state;
            private final Move change;
            private int value;
            private final int turn;
            private Node parent;
            private ArrayList<Node> children;

            public Node(Othello state, Node parent, Move change, int turn){
                this.parent = parent;
                this.state = state;
                this.change = change;
                this.turn = turn;
                this.value = 0;
                this.children = new ArrayList<>();
            }

            public void createChildren(){
                children = generateChildren();
            }
            private ArrayList<Node> generateChildren(){

                ArrayList<Move> possibleMoves = state.getPossibleMoves(turn);

                int nextTurn = turn == 1 ? 2 : 1;

                if(possibleMoves.isEmpty()){
                    possibleMoves = state.getPossibleMoves(nextTurn);
                    nextTurn = turn;
                }

                int finalNextTurn = nextTurn;
                return possibleMoves.stream()
                        .map(move -> new Node(state.makeMove(move), this, move, finalNextTurn))
                        .collect(Collectors.toCollection(ArrayList::new));
            }
        }
    }
}
