package com.chess.test;

import com.chess.engine.AI.Minimax;
import com.chess.engine.AI.MoveStrategy;
import com.chess.engine.board.Board;
import com.chess.engine.*;
import com.chess.engine.board.BoardBuilder;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.player.MoveStatus;
import com.chess.engine.player.MoveTransition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    public void initialBoard() {
        final Board board = Board.createStandartBoard();
        assertEquals(board.currentPlayer().getLegalMoves().size(), 20);
        assertEquals(board.currentPlayer().getOpponent().getLegalMoves().size(), 20);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().isCastled());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isCastled());
        assertTrue(board.whitePlayer().toString().equals("White"));
        assertTrue(board.blackPlayer().toString().equals("Black"));
    }

    @Test
    public void testFoolsMate() {

        final Board board = Board.createStandartBoard();

        final MoveTransition firstWhite = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board,
                                                                                                     BoardUtils.getCoordinateAtPosition("f2"),
                                                                                                     BoardUtils.getCoordinateAtPosition("f3")));
        assertTrue(firstWhite.getMoveStatus().isDone());

        final MoveTransition firstBlack = firstWhite.getTransitionBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(firstWhite.getTransitionBoard(),
                                                                                                   BoardUtils.getCoordinateAtPosition("e7"),
                                                                                                   BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(firstBlack.getMoveStatus().isDone());

        final MoveTransition secondWhite = firstBlack.getTransitionBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(firstBlack.getTransitionBoard(),
                                                                                                    BoardUtils.getCoordinateAtPosition("g2"),
                                                                                                    BoardUtils.getCoordinateAtPosition("g4")));

        assertTrue(secondWhite.getMoveStatus().isDone());

        final MoveStrategy strategy = new Minimax(4);

        final Move bestMove = Move.MoveFactory.createMove(secondWhite.getTransitionBoard(),
                BoardUtils.getCoordinateAtPosition("d8"), BoardUtils.getCoordinateAtPosition("h4"));

        final Move AIMove = strategy.execute(secondWhite.getTransitionBoard());

        assertEquals(AIMove, bestMove);

    }

}









































