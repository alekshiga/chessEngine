package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class King extends Piece {
    private final static int[] CANDIDATE_MOVE_COORDINATE = { -9, -8, -7, -1, 1, 7, 8, 9};

    public King(final int piecePosition, final Alliance pieceAlliance) {
        super(PieceType.KING, piecePosition, pieceAlliance, true);
    }

    public King(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
        super(PieceType.KING, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if (isFirstColumnException(this.piecePosition, currentCandidateOffset) ||
                isEighthColumnException(this.piecePosition, currentCandidateOffset)) {
                continue;
            }
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isOccupied()) {                                               // if tile is empty then there is one more legal move
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));  // but you can't go on a tile that is under attack by another Piece
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));    // if tile is occupied you can only move there with Knight if tile is occupied with enemy piece
                    }
                }
            }
        }
        return legalMoves;
    }

    @Override
    public King movePiece(final Move move) {
        return new King(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }

    private static boolean isFirstColumnException(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 ||
                candidateOffset == 7);
    }

    private static boolean isEighthColumnException(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 ||
                candidateOffset == 9);
    }
}
