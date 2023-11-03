package com.chess.engine.AI;

import com.chess.engine.board.Board;

public interface SituationEvaluator {

    int evaluate(Board board, int depth);

}
