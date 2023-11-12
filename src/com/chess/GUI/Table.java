package com.chess.GUI;

import com.chess.engine.AI.Minimax;
import com.chess.engine.AI.MoveStrategy;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.MoveTransition;
import com.chess.engine.player.PlayerType;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.chess.engine.board.Move.MoveFactory;
import static javax.swing.SwingUtilities.*;

public class Table extends Observable {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final MoveLog moveLog;
    private final Settings gameSettings;
    private Move computerMove;

    private Board chessBoard;

    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;
    private BoardDirection boardDirection;
    public static boolean highlightLegalMoves;

    private static final String pieceIconPath = "C:\\Users\\User\\IdeaProjects\\chessEngine\\artIdea\\";

    private final Color lightTileColor = Color.decode("#f1f0e6");
    private final Color darkTileColor = Color.decode("#606060");
    private final Color legalMoveLightTileColor = Color.decode("#9BFC8C");
    private final Color legalMoveDarkTileColor = Color.decode("#70D560");

    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(710, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 400);

    private static final Table INSTANCE = new Table();

private Table() {
        this.gameFrame = new JFrame("chess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.chessBoard = Board.createStandartBoard();
        this.boardDirection = BoardDirection.NORMAL;
        highlightLegalMoves = false;
        this.addObserver(new AIObserver());
        this.gameHistoryPanel = new GameHistoryPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.moveLog = new MoveLog();
        this.gameSettings = new Settings(this.gameFrame, true);

        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        gameFrame.repaint();
        gameFrame.setVisible(true);
    }

    public static Table get() {
        return INSTANCE;
    }

    private Settings getSettings() {
        return this.gameSettings;
    }

    private Board getCurrentBoard() {
        return this.chessBoard;
    }

    private void update(final Settings settings) {
        setChanged();
        notifyObservers(gameSettings);
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        tableMenuBar.add(createSettingsMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem openPGN = new JMenuItem("Open PGN file");

        openPGN.addActionListener(e -> System.out.println("open up PGN file"));
        fileMenu.add(openPGN);

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    private JMenu createPreferencesMenu() {
        final JMenu preferencesMenu = new JMenu("Preferences");
        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip board");
        flipBoardMenuItem.addActionListener(e -> {
            boardDirection = boardDirection.opposite();
            boardPanel.drawBoard(chessBoard);
        });
        preferencesMenu.add(flipBoardMenuItem);
        final JCheckBoxMenuItem legalMoveHighlighterCheckbox = new JCheckBoxMenuItem("Show legal moves", false);
        legalMoveHighlighterCheckbox.addActionListener(e -> highlightLegalMoves = legalMoveHighlighterCheckbox.isSelected());

        preferencesMenu.add(legalMoveHighlighterCheckbox);

        return preferencesMenu;
    }

    private JMenu createSettingsMenu() {
        final JMenu settingsMenu = new JMenu("Settings");
        final JMenuItem setupGameMenuItem = new JMenuItem("Setup game");
        setupGameMenuItem.addActionListener(e -> {
            Table.get().getSettings().promptUser();
            Table.get().update(Table.get().getSettings());
        });

        settingsMenu.add(setupGameMenuItem);

        return settingsMenu;
    }

    public void show() {
        invokeLater(() -> {
            Table.get().getGameHistoryPanel().reDo(chessBoard, Table.get().getMoveLog());
            Table.get().getBoardPanel().drawBoard(Table.get().getCurrentBoard());
        });
    }

    public static class AIObserver implements Observer {

        @Override
        public void update(final Observable o, final Object arg) {
            if (Table.get().getSettings().isAI(Table.get().getCurrentBoard().currentPlayer()) &&
            !Table.get().getCurrentBoard().currentPlayer().isInCheckMate() &&
            !Table.get().getCurrentBoard().currentPlayer.isInStaleMate()) {
                //todo AI move
                final DepthThinkTank depthTank = new DepthThinkTank();
                depthTank.execute();
            }
            if (Table.get().getCurrentBoard().currentPlayer.isInStaleMate()) {
                System.out.println("Game over with " + Table.get().getCurrentBoard().currentPlayer + "is in stalemate");
            }
            if (Table.get().getCurrentBoard().currentPlayer.isInCheckMate()) {
                System.out.println("Game over with " + Table.get().getCurrentBoard().currentPlayer + "is in checkmate");
            }
        }
    }

    private static class DepthThinkTank extends SwingWorker<Move, String> {

        private DepthThinkTank() {

        }

        @Override
        protected Move doInBackground() throws Exception {
            final MoveStrategy miniMax = new Minimax(4);

            final Move bestMove = miniMax.execute(Table.get().getCurrentBoard());

            return bestMove;
        }

        @Override
        public void done() {
            try {
                final Move bestMove = get();

                Table.get().updateComputerMove(bestMove);
                Table.get().updateBoard(Table.get().getCurrentBoard().currentPlayer().makeMove(bestMove).getTransitionBoard());
                Table.get().getMoveLog().addMove(bestMove);
                Table.get().getGameHistoryPanel().reDo(Table.get().getCurrentBoard(), Table.get().getMoveLog());
                Table.get().getTakenPiecesPanel().reDo(Table.get().moveLog);
                Table.get().getBoardPanel().drawBoard(Table.get().getCurrentBoard());
                Table.get().madeMoveUpdate(PlayerType.COMPUTER);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void madeMoveUpdate(final PlayerType playerType) {
        setChanged();
        notifyObservers(playerType);
    }

    private BoardPanel getBoardPanel() {
        return this.boardPanel;
    }

    private TakenPiecesPanel getTakenPiecesPanel() {
        return this.takenPiecesPanel;
    }

    private GameHistoryPanel getGameHistoryPanel() {
        return this.gameHistoryPanel;
    }

    private MoveLog getMoveLog() {
        return this.moveLog;
    }

    public void updateBoard(final Board board) {
        this.chessBoard = board;
    }

    public void updateComputerMove(final Move move) {
        this.computerMove = move;
    }

public enum BoardDirection {

        NORMAL {
            @Override
            List<TilePanel> traverse (final List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTile) {
                return Lists.reverse(boardTile);
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };
        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
        abstract BoardDirection opposite();
    }

    private class BoardPanel extends JPanel {

        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; ++i) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            validate();
        }

        public void drawBoard(final Board board) {
            removeAll();
            for(final TilePanel tilePanel : boardDirection.traverse(boardTiles)) {
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    private class TilePanel extends JPanel {

        private final int tileId;

        TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent event) {
                    if(isRightMouseButton(event)) {
                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;
                    } else if(isLeftMouseButton(event)) {
                        if(sourceTile == null) {
                            sourceTile = chessBoard.getTile(tileId) ;
                            humanMovedPiece = sourceTile.getPiece();
                            if (humanMovedPiece == null) {
                                sourceTile = null;
                            }
                        } else {
                            destinationTile = chessBoard.getTile(tileId);
                            final Move move = MoveFactory.createMove(chessBoard,sourceTile.getTileCoordinate(),destinationTile.getTileCoordinate());
                            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                            if(transition.getMoveStatus().isDone()) {
                                chessBoard = transition.getTransitionBoard();
                                moveLog.addMove(move);
                            }
                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;
                        }
                        invokeLater(() -> {
                            gameHistoryPanel.reDo(chessBoard, moveLog);
                            takenPiecesPanel.reDo(moveLog);

                            if (gameSettings.isAI(chessBoard.currentPlayer)) {
                                Table.get().madeMoveUpdate(PlayerType.HUMAN);
                            }

                            boardPanel.drawBoard(chessBoard);
                            gameFrame.setVisible(true);
                            validate();
                        });
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });
            validate();
        }

        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if(board.getTile(this.tileId).isOccupied()) {
                try {
                    final BufferedImage image = ImageIO.read(new File(pieceIconPath + board.getTile(this.tileId).getPiece().getPieceAlliance().toString().charAt(0) +
                            board.getTile(this.tileId).getPiece().toString() + ".png"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void showLegalMoves(final Board board) {
            if (Table.getHighlightLegalMoves()) {
                for (final Move move : pieceLegalMoves(board)) {
                    if (move.getDestinationCoordinate() == this.tileId) {
                        try {
                            if (BoardUtils.EIGHTH_RANK[this.tileId] ||
                                    BoardUtils.SIXTH_RANK[this.tileId] ||
                                    BoardUtils.FOURTH_RANK[this.tileId] ||
                                    BoardUtils.SECOND_RANK[this.tileId]) {
                                setBackground(this.tileId % 2 == 0 ? legalMoveLightTileColor : legalMoveDarkTileColor);
                            } else if (BoardUtils.SEVENTH_RANK[this.tileId] ||
                                    BoardUtils.FIFTH_RANK[this.tileId] ||
                                    BoardUtils.THIRD_RANK[this.tileId] ||
                                    BoardUtils.FIRST_RANK[this.tileId]) {
                                setBackground(this.tileId % 2 != 0 ? legalMoveLightTileColor : legalMoveDarkTileColor);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(final Board board) {
            if (humanMovedPiece != null && humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance()) {
                return humanMovedPiece.calculateLegalMoves(board);
            } else {
                return Collections.emptyList();
            }
        }

        private void assignTileColor() {
            if (BoardUtils.EIGHTH_RANK[this.tileId] ||
                    BoardUtils.SIXTH_RANK[this.tileId] ||
                    BoardUtils.FOURTH_RANK[this.tileId] ||
                    BoardUtils.SECOND_RANK[this.tileId]) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if (BoardUtils.SEVENTH_RANK[this.tileId] ||
                    BoardUtils.FIFTH_RANK[this.tileId] ||
                    BoardUtils.THIRD_RANK[this.tileId] ||
                    BoardUtils.FIRST_RANK[this.tileId]) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }

        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            showLegalMoves(board);
            validate();
            repaint();
        }
    }

    private static boolean getHighlightLegalMoves() {
        return highlightLegalMoves;
    }

    public static class MoveLog {
        private final List<Move> moves;

        MoveLog() {
            this.moves = new ArrayList<>();
        }

        public List<Move> getMoves() {
            return this.moves;
        }

        public void addMove(Move move) {
            this.moves.add(move);
        }

        public int size() {
            return this.moves.size();
        }
    }
}
