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

    private final static int[] CANDIDATE_MOVE_COORDINATE = { -7, 7, 8, 16 };

    Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
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
                legalMoves.add(new majorMove(board, this, candidateDestinationCoordinate));
            } else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack() ||
                    BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite()) {
                final int behindCandidateDestinationCoordinate = this.piecePosition + this.pieceAlliance.getDirection() * 8;
                if (!board.getTile(behindCandidateDestinationCoordinate).isOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isOccupied()) {
                    legalMoves.add(new majorMove(board, this, candidateDestinationCoordinate));
                }
            } else if ((currentCandidateOffset == -7 || currentCandidateOffset == 7) &&
                    board.getTile(currentCandidateOffset).isOccupied() &&
                    board.getTile(currentCandidateOffset).getPiece().getPieceAlliance() != this.pieceAlliance) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                legalMoves.add(new Move.pawnAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));    // if tile is occupied you can only move there with Knight if tile is occupied with enemy piece
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
}
