package com.chess.engine.board;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import javax.management.ImmutableDescriptor;
import java.util.HashMap;
import java.util.Map;

import static com.chess.engine.board.BoardUtils.NUM_TILES;

public abstract class Tile {

    protected final int tileCoordinate; // tile coordinate set only once and never changes

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < NUM_TILES; ++i) {
            emptyTileMap.put(i, new EmptyTile(i));  // creating a Map of empty tiles, then return an Immutable copy of it
        }                                           // so nobody can change out map
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    private Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isOccupied();
                                                        // abstract methods that will have realisation in subclasses
    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile {

        private EmptyTile(int coordinate) {
            super(coordinate);
        }

        @Override
        public String toString() {
            return "-";
        }

        @Override
        public boolean isOccupied() {
            return false;   // returns false because it's a method of EmptyTile class
        }

        @Override
        public Piece getPiece() {
            return null;  // returns null because it's nothing on that tile
        }
    }

    public static final class OccupiedTile extends Tile {

        private final Piece pieceOnTile;

        private OccupiedTile(final int tileCoordinate, final Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public String toString() {
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
                    getPiece().toString();
        }

        @Override
        public boolean isOccupied() {
            return true; // returns true because there is a piece on that tile
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;    // returns Piece because there is something on that tile
        }
    }
}