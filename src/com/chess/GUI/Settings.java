package com.chess.GUI;

import com.chess.engine.Alliance;
import com.chess.engine.player.Player;
import com.chess.engine.player.PlayerType;

import javax.swing.*;
import java.awt.*;

public class Settings extends JDialog {

    private PlayerType whitePlayerType;
    private PlayerType blackPlayerType;

    private static final String HUMAN_BUTTON = "Human";
    private static final String COMPUTER_BUTTON = "Computer";

    Settings(final JFrame frame,
              final boolean modal) {
        super(frame, modal);
        final JPanel myPanel = new JPanel(new GridLayout(0, 1));
        final JRadioButton whiteHumanButton = new JRadioButton(HUMAN_BUTTON);
        final JRadioButton whiteComputerButton = new JRadioButton(COMPUTER_BUTTON);
        final JRadioButton blackHumanButton = new JRadioButton(HUMAN_BUTTON);
        final JRadioButton blackComputerButton = new JRadioButton(COMPUTER_BUTTON);
        whiteHumanButton.setActionCommand(HUMAN_BUTTON);
        final ButtonGroup whiteGroup = new ButtonGroup();
        whiteGroup.add(whiteHumanButton);
        whiteGroup.add(whiteComputerButton);
        whiteHumanButton.setSelected(true);

        final ButtonGroup blackGroup = new ButtonGroup();
        blackGroup.add(blackHumanButton);
        blackGroup.add(blackComputerButton);
        blackHumanButton.setSelected(true);

        getContentPane().add(myPanel);
        myPanel.add(new JLabel("White"));
        myPanel.add(whiteHumanButton);
        myPanel.add(whiteComputerButton);
        myPanel.add(new JLabel("Black"));
        myPanel.add(blackHumanButton);
        myPanel.add(blackComputerButton);

        final JButton okButton = new JButton("OK");
        final JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> {
            if (whiteComputerButton.isSelected()) {
                whitePlayerType = PlayerType.COMPUTER;
            } else {
                whitePlayerType = PlayerType.HUMAN;
            }
            if (blackComputerButton.isSelected()) {
                blackPlayerType = PlayerType.COMPUTER;
            } else {
                blackPlayerType = PlayerType.HUMAN;
            }
            Settings.this.setVisible(false);
        });

        cancelButton.addActionListener(e -> Settings.this.setVisible(false));

        myPanel.add(cancelButton);
        myPanel.add(okButton);

        setLocationRelativeTo(frame);
        pack();
        setVisible(false);
    }

    void promptUser() {
        setVisible(true);
        repaint();
    }

    boolean isAI(final Player player) {
        if(player.getAlliance() == Alliance.WHITE) {
            return getWhitePlayerType() == PlayerType.COMPUTER;
        }
        return getBlackPlayerType() == PlayerType.COMPUTER;
    }

    PlayerType getWhitePlayerType() {
        return this.whitePlayerType;
    }

    PlayerType getBlackPlayerType() {
        return this.blackPlayerType;
    }
}
