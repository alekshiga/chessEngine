package com.chess.engine.AI;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.player.MoveTransition;

public class Minimax implements MoveStrategy {

    private final SituationEvaluator situationEvaluator;
    private final int depthSearch;

    public Minimax(int depthSearch) {
        this.situationEvaluator = new StartSituationEvaluator();
        this.depthSearch = depthSearch;
    }

    public String toString() {
        return "Minimax";
    }

    @Override
    public Move execute(Board board) {

        final long startTime = System.currentTimeMillis();

        Move bestMove = null;

        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;

        System.out.println(board.currentPlayer() + " depth = " + this.depthSearch);
        int numMoves = board.currentPlayer().getLegalMoves().size();

        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                if (board.currentPlayer().getAlliance().isWhite()) {
                    currentValue = min(moveTransition.getTransitionBoard(), this.depthSearch - 1);
                } else if (board.currentPlayer().getAlliance().isBlack()) {
                    currentValue = max(moveTransition.getTransitionBoard(), this.depthSearch - 1);
                } else {
                    throw new RuntimeException("Should not reach here!");
                }
                if (board.currentPlayer().getAlliance().isWhite() && currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                    bestMove = move;
                } else if (board.currentPlayer().getAlliance().isBlack() && currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                    bestMove = move;
                }
            }
        }
        final long executionTime = System.currentTimeMillis() - startTime;
        return bestMove;
    }

    public int max(final Board board,
                   final int depth) {
        if (depth == 0 || gameIsOver(board)) {
            return this.situationEvaluator.evaluate(board, depth);
        }
        int highestSeenValue = Integer.MIN_VALUE;
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currentValue = min(moveTransition.getTransitionBoard(), depth - 1);
                if (currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                }
            }
        }
        return highestSeenValue;
    }

    public int min(final Board board,
                   final int depth) {
        if (depth == 0 || gameIsOver(board)) {
            return this.situationEvaluator.evaluate(board, depth);
        }
        int lowestSeenValue = Integer.MAX_VALUE;
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currentValue = max(moveTransition.getTransitionBoard(), depth - 1);
                if (currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                }
            }
        }
        return lowestSeenValue;

    }

    private static boolean gameIsOver(final Board board) {
        return board.currentPlayer().isInCheckMate() ||
               board.currentPlayer().isInStaleMate();
    }

}
