package view;

import model.ChessBoard;
import model.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ChessGUI extends JPanel {
    private ChessBoard chessBoard;
    private int selectedRow = -1, selectedCol = -1;
    private int dragRow = -1, dragCol = -1; // Vị trí đang kéo
    private Image draggingImage = null; // Ảnh của quân cờ đang được kéo

    public ChessGUI(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        setPreferredSize(new Dimension(400, 400));

        // Thêm MouseListener để xử lý sự kiện click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int squareSize = getWidth() / 8;
                int row = e.getY() / squareSize;
                int col = e.getX() / squareSize;

                if (selectedRow == -1 && selectedCol == -1) {
                    // Chọn ô bắt đầu
                    selectedRow = row;
                    selectedCol = col;
                } else {
                    // Chọn ô đến và di chuyển quân cờ
                    chessBoard.movePiece(selectedRow, selectedCol, row, col);
                    selectedRow = -1;
                    selectedCol = -1;
                    repaint(); // Cập nhật giao diện
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int squareSize = getWidth() / 8;
                int row = e.getY() / squareSize;
                int col = e.getX() / squareSize;

                Piece piece = chessBoard.getPieceAt(row, col);
                if (piece != null) {
                    selectedRow = row;
                    selectedCol = col;
                    draggingImage = new ImageIcon(piece.getImage()).getImage();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selectedRow != -1 && selectedCol != -1) {
                    int squareSize = getWidth() / 8;
                    int row = e.getY() / squareSize;
                    int col = e.getX() / squareSize;

                    chessBoard.movePiece(selectedRow, selectedCol, row, col);
                    selectedRow = -1;
                    selectedCol = -1;
                    draggingImage = null;
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedRow != -1 && selectedCol != -1) {
                    dragRow = e.getY();
                    dragCol = e.getX();
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawChessBoard(g);
        drawPieces(g);

        // Vẽ quân cờ đang được kéo
        if (draggingImage != null) {
            int squareSize = getWidth() / 8;
            g.drawImage(draggingImage, dragCol - squareSize / 2, dragRow - squareSize / 2, squareSize, squareSize, this);
        }
    }

    private void drawChessBoard(Graphics g) {
        int squareSize = getWidth() / 8;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 0) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(col * squareSize, row * squareSize, squareSize, squareSize);
            }
        }
    }

    private void drawPieces(Graphics g) {
        int squareSize = getWidth() / 8;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = chessBoard.getPieceAt(row, col);
                if (piece != null) {
                    ImageIcon icon = new ImageIcon(piece.getImage());
                    Image image = icon.getImage();
                    g.drawImage(image, col * squareSize, row * squareSize, squareSize, squareSize, this);
                }
            }
        }
    }
}
