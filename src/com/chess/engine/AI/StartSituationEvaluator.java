package com.chess.engine.AI;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;

public final class StartSituationEvaluator implements SituationEvaluator {

    private static final int BONUS_FOR_CHECK = 50;
    private static final int BONUS_FOR_CHECKMATE = 10000;
    private static final int BONUS_DEPTH = 100;
    private static final int BONUS_FOR_CASTLE = 60;

    @Override
    public int evaluate(final Board board,
                        final int depth) {
        return scorePlayer(board, board.whitePlayer(), depth) - scorePlayer(board, board.blackPlayer(), depth);
    }

    private int scorePlayer(final Board board,
                            final Player player,
                            final int depth) {

        return pieceValue(player) +
               mobility(player) +
               check(player) +
               checkMate(player, depth) +
               castled(player);
    }

    private static int pieceValue(final Player player) {
        int pieceValueScore = 0;
        for (final Piece piece : player.getActivePieces()) {
            pieceValueScore += piece.getPieceValue();
        }
        return pieceValueScore;
    }

    private static int mobility(final Player player) {
        return player.getLegalMoves().size();
    }

    private static int check(final Player player) {
        return player.getOpponent().isInCheck() ? BONUS_FOR_CHECK : 0;
    }

    private int checkMate(final Player player,
                                 final int depth) {
        return player.getOpponent().isInCheckMate() ? BONUS_FOR_CHECKMATE * depthBonus(depth) : 0;
    }

    private static int depthBonus(int depth) {
        if (depth == 0) {
            return 1;
        } else {
            return BONUS_DEPTH * depth;
        }
    }

    private static int castled(Player player) {
        if (player.isCastled()) {
            return 0;
        } else {
            return BONUS_FOR_CASTLE;
        }
    }

}
