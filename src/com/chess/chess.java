package com.chess;
import com.chess.GUI.Table;

import com.chess.engine.board.Board;

public class chess {

    public static void main(String[] args) {

        Board board = Board.createStandartBoard();
        Table.get().show();
    }

}
