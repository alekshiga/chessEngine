package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.concurrent.Future;

public class MoveTransition {
    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    public MoveTransition(final Board transitionBoard,
                          final Move move,
                          final MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public Board getTransitionBoard() {
        return this.transitionBoard;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
}