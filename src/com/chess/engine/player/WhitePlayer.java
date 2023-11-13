package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.LongSideCastleMove;
import com.chess.engine.board.Move.ShortSideCastleMove;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import java.util.ArrayList;
import java.util.Collection;

public class WhitePlayer extends Player {
    public WhitePlayer(final Board board,
                       final Collection<Move> whiteStandartLegalMoves,
                       final Collection<Move> blackStandartLegalMoves) {
        super(board, whiteStandartLegalMoves, blackStandartLegalMoves);

    }

    @Override
    public Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                    final Collection<Move> opponentLegals) {
        final Collection<Move> kingCastles = new ArrayList<>();
        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            if (!this.board.getTile(61).isOccupied() && !this.board.getTile(62).isOccupied()) {
                final Tile rookTile = this.board.getTile(63);
                if (rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(61, opponentLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(62, opponentLegals).isEmpty()) {
                            kingCastles.add(new ShortSideCastleMove(this.board,
                                                                    this.playerKing,
                                                                    62,
                                                                    (Rook) rookTile.getPiece(),
                                                                    rookTile.getTileCoordinate(),
                                                                    61));
                    }
                }
            }
            if (!this.board.getTile(57).isOccupied() &&
               !this.board.getTile(58).isOccupied() &&
               !this.board.getTile(59).isOccupied()) {

                final Tile rookTile = this.board.getTile(56);
                if (rookTile.getPiece().isFirstMove() &&
                    Player.calculateAttacksOnTile(57, opponentLegals).isEmpty() &&
                    Player.calculateAttacksOnTile(58, opponentLegals).isEmpty() &&
                    Player.calculateAttacksOnTile(59, opponentLegals).isEmpty()) {
                        kingCastles.add(new LongSideCastleMove(this.board,
                                                                this.playerKing,
                                                                58,
                                                                (Rook) rookTile.getPiece(),
                                                                rookTile.getTileCoordinate(),
                                                                59));
                }
            }
        }
    return kingCastles;
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    public String toString() {
        return "White";
    }
}
