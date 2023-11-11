package com.chess.GUI;

import com.chess.engine.Alliance;
import com.chess.engine.player.Player;
import com.chess.engine.player.PlayerType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JDialog {

    private PlayerType whitePlayerType;
    private PlayerType blackPlayerType;

    private static final String HUMAN_BUTTON = "Human";
    private static final String WEAK_COMPUTER_BUTTON = "Weak computer";
    private static final String STRONG_COMPUTER_BUTTON = "Strong computer";

    Settings(final JFrame frame,
              final boolean modal) {
        super(frame, modal);
        final JPanel myPanel = new JPanel(new GridLayout(0, 1));
        final JRadioButton whiteHumanButton = new JRadioButton(HUMAN_BUTTON);
        final JRadioButton whiteWeakComputerButton = new JRadioButton(WEAK_COMPUTER_BUTTON);
        final JRadioButton whiteStrongComputerButton = new JRadioButton(STRONG_COMPUTER_BUTTON);
        final JRadioButton blackHumanButton = new JRadioButton(HUMAN_BUTTON);
        final JRadioButton blackWeakComputerButton = new JRadioButton(WEAK_COMPUTER_BUTTON);
        final JRadioButton blackStrongComputerButton = new JRadioButton(STRONG_COMPUTER_BUTTON);
        whiteHumanButton.setActionCommand(HUMAN_BUTTON);
        final ButtonGroup whiteGroup = new ButtonGroup();
        whiteGroup.add(whiteHumanButton);
        whiteGroup.add(whiteWeakComputerButton);
        whiteGroup.add(whiteStrongComputerButton);
        whiteHumanButton.setSelected(true);

        final ButtonGroup blackGroup = new ButtonGroup();
        blackGroup.add(blackHumanButton);
        blackGroup.add(blackWeakComputerButton);
        blackGroup.add(blackStrongComputerButton);
        blackHumanButton.setSelected(true);

        getContentPane().add(myPanel);
        myPanel.add(new JLabel("White"));
        myPanel.add(whiteHumanButton);
        myPanel.add(whiteWeakComputerButton);
        myPanel.add(whiteStrongComputerButton);
        myPanel.add(new JLabel("Black"));
        myPanel.add(blackHumanButton);
        myPanel.add(blackWeakComputerButton);
        myPanel.add(blackStrongComputerButton);

        final JButton okButton = new JButton("OK");
        final JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> {
            if (whiteWeakComputerButton.isSelected()) {
                whitePlayerType = PlayerType.WEAK_COMPUTER;
            } else if (whiteStrongComputerButton.isSelected()) {
                whitePlayerType = PlayerType.STRONG_COMPUTER;
            } else {
                whitePlayerType = PlayerType.HUMAN;
            }
            if (blackWeakComputerButton.isSelected()) {
                blackPlayerType = PlayerType.WEAK_COMPUTER;
            } else if (blackStrongComputerButton.isSelected()) {
                blackPlayerType = PlayerType.STRONG_COMPUTER;
            } else {
                blackPlayerType = PlayerType.HUMAN;
            }
            Settings.this.setVisible(false);
        });

        cancelButton.addActionListener(e -> {
            System.out.println("Cancel");
            Settings.this.setVisible(false);
        });

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
            return getWhitePlayerType() == PlayerType.WEAK_COMPUTER ||
                    getWhitePlayerType() == PlayerType.STRONG_COMPUTER;
        }
        return getBlackPlayerType() == PlayerType.WEAK_COMPUTER ||
                getBlackPlayerType() == PlayerType.STRONG_COMPUTER;
    }

    PlayerType getWhitePlayerType() {
        return this.whitePlayerType;
    }

    PlayerType getBlackPlayerType() {
        return this.blackPlayerType;
    }
}
