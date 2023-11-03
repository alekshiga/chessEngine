package com.chess.engine.AI;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class Minimax implements MoveStrategy {

    private final SituationEvaluator situationEvaluator;

    public Minimax() {
        this.situationEvaluator = null;
    }

    public String toString() {
        return "Minimax";
    }

    @Override
    public Move execute(Board board, int depth) {
        return null;
    }

}
