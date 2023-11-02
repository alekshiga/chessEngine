package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.BoardUtils;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

enum PieceUtils {

    INSTANCE;

private final Table<Alliance, Integer, Queen> ALL_POSSIBLE_QUEENS = PieceUtils.createAllPossibleQueens();
private final Table<Alliance, Integer, Rook> ALL_POSSIBLE_ROOKS = PieceUtils.createAllPossibleRooks();
private final Table<Alliance, Integer, Knight> ALL_POSSIBLE_KNIGHTS = PieceUtils.createAllPossibleKnights();
private final Table<Alliance, Integer, Bishop> ALL_POSSIBLE_BISHOPS = PieceUtils.createAllPossibleBishops();
private final Table<Alliance, Integer, Pawn> ALL_POSSIBLE_PAWNS = PieceUtils.createAllPossibleMovedPawns();

Pawn getMovedPawn(final Alliance alliance,
                  final int destinationCoordinate) {
    return ALL_POSSIBLE_PAWNS.get(alliance, destinationCoordinate);
}

Knight getKnight(final Alliance alliance,
                      final int destinationCoordinate) {
    return ALL_POSSIBLE_KNIGHTS.get(alliance, destinationCoordinate);
}

Bishop getBishop(final Alliance alliance,
                      final int destinationCoordinate) {
    return ALL_POSSIBLE_BISHOPS.get(alliance, destinationCoordinate);
}

Rook getRook(final Alliance alliance,
                  final int destinationCoordinate) {
    return ALL_POSSIBLE_ROOKS.get(alliance, destinationCoordinate);
}

Queen getQueen(final Alliance alliance,
                    final int destinationCoordinate) {
    return ALL_POSSIBLE_QUEENS.get(alliance, destinationCoordinate);
}

private static Table<Alliance, Integer, Pawn> createAllPossibleMovedPawns() {
    final ImmutableTable.Builder<Alliance, Integer, Pawn> pieces = ImmutableTable.builder();
    for(final Alliance alliance : Alliance.values()) {
        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            pieces.put(alliance, i, new Pawn(i, alliance, false));
        }
    }
    return pieces.build();
}

private static Table<Alliance, Integer, Knight> createAllPossibleKnights() {
    final ImmutableTable.Builder<Alliance, Integer, Knight> pieces = ImmutableTable.builder();
    for(final Alliance alliance : Alliance.values()) {
        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            pieces.put(alliance, i, new Knight(i, alliance, false));
        }
    }
    return pieces.build();
}

private static Table<Alliance, Integer, Bishop> createAllPossibleBishops() {
    final ImmutableTable.Builder<Alliance, Integer, Bishop> pieces = ImmutableTable.builder();
    for(final Alliance alliance : Alliance.values()) {
        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            pieces.put(alliance, i, new Bishop(i, alliance, false));
        }
    }
    return pieces.build();
}

private static Table<Alliance, Integer, Rook> createAllPossibleRooks() {
    final ImmutableTable.Builder<Alliance, Integer, Rook> pieces = ImmutableTable.builder();
    for(final Alliance alliance : Alliance.values()) {
        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            pieces.put(alliance, i, new Rook(i, alliance, false));
        }
    }
    return pieces.build();
}

private static Table<Alliance, Integer, Queen> createAllPossibleQueens() {
    final ImmutableTable.Builder<Alliance, Integer, Queen> pieces = ImmutableTable.builder();
    for(final Alliance alliance : Alliance.values()) {
        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            pieces.put(alliance, i, new Queen(i, alliance, false));
        }
    }
    return pieces.build();
}

}
