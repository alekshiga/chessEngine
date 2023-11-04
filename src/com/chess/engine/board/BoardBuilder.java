package com.chess.engine.board;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public class BoardBuilder {

Map<Integer, Piece> boardConfig;
Alliance nextMoveMaker;
Pawn enPassantPawn;
Move transitionMove;

public BoardBuilder() {
    this.boardConfig = new HashMap<>();
}

public BoardBuilder setPiece(final Piece piece) {
    this.boardConfig.put(piece.getPiecePosition(), piece);
    return this;
}

public BoardBuilder setMoveMaker(final Alliance nextMoveMaker) {
    this.nextMoveMaker = nextMoveMaker;
    return this;
}

public Board build() {
    return new Board(this);
}

public BoardBuilder setEnPassantPawn(final Pawn enPassantPawn) {
    this.enPassantPawn = enPassantPawn;
    return this;
}

public void setMoveTransition(Move move) {
    this.transitionMove = move;
}
}