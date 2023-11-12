package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorAttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece {

    private static final int[] CANDIDATE_MOVE_COORDINATES = { -17, -15, -10, -6, 6, 10, 15, 17 };
    /*board has 64 tiles, a Knight has 8 moves in the best way, array the array contains possible moves relative to the current position of the knight*/

    public Knight(final int piecePosition, final Alliance pieceAlliance) {
        super(PieceType.KNIGHT, piecePosition, pieceAlliance, true);
    }

    public Knight(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
        super(PieceType.KNIGHT, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if (isFirstColumnException(this.piecePosition, currentCandidateOffset) ||
                isSecondColumnException(this.piecePosition, currentCandidateOffset) ||
                isSeventhColumnException(this.piecePosition, currentCandidateOffset) ||
                isEighthColumnException(this.piecePosition, currentCandidateOffset)) {
                    continue;
                }
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));  // if tile is empty then there is one more legal move
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
    public Knight movePiece(final Move move) {
        return new Knight(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }

    private static boolean isFirstColumnException(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
                candidateOffset == 6 || candidateOffset == 15);
    }

    private static boolean isSecondColumnException(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
    }

    private static boolean isSeventhColumnException(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);
    }

    private static boolean isEighthColumnException(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6 ||
                candidateOffset == 10 || candidateOffset == 17);
    }
 }

