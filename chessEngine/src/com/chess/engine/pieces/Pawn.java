package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.majorMove;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATE = {7, 8, 9, 16 };

    public Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(PieceType.PAWN, piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
            final int candidateDestinationCoordinate = this.piecePosition + (this.getPieceAlliance().getDirection() * currentCandidateOffset);

            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }

            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isOccupied()) {

                // TODO PAWN PROMOTION!

                legalMoves.add(new majorMove(board, this, candidateDestinationCoordinate));
            } else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack() ||
                    BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite()) {
                final int behindCandidateDestinationCoordinate = this.piecePosition + this.pieceAlliance.getDirection() * 8;
                if (!board.getTile(behindCandidateDestinationCoordinate).isOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isOccupied()) {
                    legalMoves.add(new majorMove(board, this, candidateDestinationCoordinate));
                }
            } else if (  (currentCandidateOffset == 7) &&
                    !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                    (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))  ) {
                if (board.getTile(candidateDestinationCoordinate).isOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {

                        // TODO MORE MOVE SUBCLASSES

                        legalMoves.add(new majorMove(board, this, candidateDestinationCoordinate));
                    }
                }
            } else if (  (currentCandidateOffset == 9) &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                    (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))  ) {
                if (board.getTile(candidateDestinationCoordinate).isOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {

                        // TODO MORE MOVE SUBCLASSES

                        legalMoves.add(new majorMove(board, this, candidateDestinationCoordinate));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}









