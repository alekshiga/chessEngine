package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {

private final static int[] CANDIDATE_MOVE_COORDINATES = {8, 16, 7, 9};

public Pawn(final int piecePosition, final Alliance pieceAlliance) {
    super(PieceType.PAWN, piecePosition, pieceAlliance, true);
}

public Pawn(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
    super(PieceType.PAWN, piecePosition, pieceAlliance, isFirstMove);
}

@Override
public Collection<Move> calculateLegalMoves(final Board board) {
    final List<Move> legalMoves = new ArrayList<>();
    for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
        int candidateDestinationCoordinate =
                this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);
        if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
            continue;
        }
        if (currentCandidateOffset == 8 && board.getTile(candidateDestinationCoordinate).getPiece() == null) {
            if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                legalMoves.add(new PawnPromotionMove(
                        new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getQueen(this.pieceAlliance, candidateDestinationCoordinate)));
                legalMoves.add(new PawnPromotionMove(
                        new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getRook(this.pieceAlliance, candidateDestinationCoordinate)));
                legalMoves.add(new PawnPromotionMove(
                        new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getBishop(this.pieceAlliance, candidateDestinationCoordinate)));
                legalMoves.add(new PawnPromotionMove(
                        new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getKnight(this.pieceAlliance, candidateDestinationCoordinate)));
            } else {
                legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
            }
        } else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                ((BoardUtils.SECOND_RANK[this.piecePosition] && this.pieceAlliance.isBlack()) ||
                        (BoardUtils.SEVENTH_RANK[this.piecePosition] && this.pieceAlliance.isWhite()))) {
            final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
            if (board.getTile(candidateDestinationCoordinate).getPiece() == null &&
                    board.getTile(behindCandidateDestinationCoordinate).getPiece() == null) {
                legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
            }
        } else if (currentCandidateOffset == 7 &&
                !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                        (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
            if (board.getTile(candidateDestinationCoordinate).isOccupied()) {
                final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                    if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                        legalMoves.add(new PawnPromotionMove(
                                new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getQueen(this.pieceAlliance, candidateDestinationCoordinate)));
                        legalMoves.add(new PawnPromotionMove(
                                new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getRook(this.pieceAlliance, candidateDestinationCoordinate)));
                        legalMoves.add(new PawnPromotionMove(
                                new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getBishop(this.pieceAlliance, candidateDestinationCoordinate)));
                        legalMoves.add(new PawnPromotionMove(
                                new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getKnight(this.pieceAlliance, candidateDestinationCoordinate)));
                    } else {
                        legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                }
            } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                    (this.piecePosition + (this.pieceAlliance.getOppositeDirection()))) {
                final Piece pieceOnCandidate = board.getEnPassantPawn();
                if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                    legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                }
            }
        } else if (currentCandidateOffset == 9 &&
                !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                        (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
            if (board.getTile(candidateDestinationCoordinate).isOccupied()) {
                final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                if (this.pieceAlliance != board.getTile(candidateDestinationCoordinate).getPiece().getPieceAlliance()) {
                    if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                        legalMoves.add(new PawnPromotionMove(
                                new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getQueen(this.pieceAlliance, candidateDestinationCoordinate)));
                        legalMoves.add(new PawnPromotionMove(
                                new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getRook(this.pieceAlliance, candidateDestinationCoordinate)));
                        legalMoves.add(new PawnPromotionMove(
                                new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getBishop(this.pieceAlliance, candidateDestinationCoordinate)));
                        legalMoves.add(new PawnPromotionMove(
                                new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getKnight(this.pieceAlliance, candidateDestinationCoordinate)));
                    } else {
                        legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, board.getTile(candidateDestinationCoordinate).getPiece()));
                    }
                }
            } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                    (this.piecePosition - (this.pieceAlliance.getOppositeDirection()))) {
                final Piece pieceOnCandidate = board.getEnPassantPawn();
                if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                    legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                }
            }
        }
    }
    return legalMoves;
}


    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }

    public Piece getPromotionPiece() {
        return new Queen(this.piecePosition, this.pieceAlliance, false);
    }
}