package com.chess.GUI;

import com.chess.engine.player.PlayerType;

import javax.swing.*;

public class Settings extends JDialog {

    private PlayerType whitePlayerType;
    private PlayerType blackPlayerType;

    private static final String HUMAN_CHECK = "Human";
    private static final String WEAK_COMPUTER = "Weak computer";
    private static final String STRONG_COMPUTER = "Strong computer";

    Settings(final JFrame frame, final boolean modal) {
        super(frame, modal);
    }

}
