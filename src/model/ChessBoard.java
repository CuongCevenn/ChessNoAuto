package model;

import constant.ChessColor;
import model.piece.*;

import javax.swing.*;
import java.util.Arrays;

public class ChessBoard {
    private final int size;
    private final Piece[][] board;
    private boolean isReversed;

    private boolean whiteIsChecked;
    private boolean blackIsChecked;

    private boolean whiteIsMoving;

    private final char[] columnLabels = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    private final char[] rowLabels = {'1', '2', '3', '4', '5', '6', '7', '8'};

    public ChessBoard() {
        this.size = 8;
        this.board = new Piece[size][size];
        this.isReversed = false;
        this.whiteIsChecked = false;
        this.blackIsChecked = false;
        initializeBoard();
    }

    public ChessBoard(boolean isReversed) {
        this.size = 8;
        this.board = new Piece[size][size];
        this.isReversed = isReversed;
        this.whiteIsChecked = false;
        this.blackIsChecked = false;
        initializeBoard();
    }

    public ChessBoard(int size) {
        this.size = size;
        this.board = new Piece[size][size];
    }

    private void invalidMove(Piece piece, int endX, int endY) {
        System.out.println("Invalid move " + piece.getColor().getName() + " " + piece.getName() + " to " + columnLabels[endY] + rowLabels[endX]);
    }

    private void initializeBoard() {
        ChessColor colorTop = isReversed ? ChessColor.WHITE : ChessColor.BLACK;
        ChessColor colorBottom = isReversed ? ChessColor.BLACK : ChessColor.WHITE;

        // Initialize pawns
        for (int i = 0; i < size; i++) {
            board[1][i] = new Pawn("Pawn", colorTop, 1, i, true);
            board[6][i] = new Pawn("Pawn", colorBottom, 6, i, false);
        }

        // Initialize rooks
        board[0][0] = new Rook("Rook", colorTop, 0, 0, true);
        board[0][7] = new Rook("Rook", colorTop, 0, 7, true);
        board[7][0] = new Rook("Rook", colorBottom, 7, 0, false);
        board[7][7] = new Rook("Rook", colorBottom, 7, 7, false);

        // Initialize knights
        board[0][1] = new Knight("Knight", colorTop, 0, 1, true);
        board[0][6] = new Knight("Knight", colorTop, 0, 6, true);
        board[7][1] = new Knight("Knight", colorBottom, 7, 1, false);
        board[7][6] = new Knight("Knight", colorBottom, 7, 6, false);

        // Initialize bishops
        board[0][2] = new Bishop("Bishop", colorTop, 0, 2, true);
        board[0][5] = new Bishop("Bishop", colorTop, 0, 5, true);
        board[7][2] = new Bishop("Bishop", colorBottom, 7, 2, false);
        board[7][5] = new Bishop("Bishop", colorBottom, 7, 5, false);

        // Initialize queens
        board[0][3] = new Queen("Queen", colorTop, 0, 3, true);
        board[7][3] = new Queen("Queen", colorBottom, 7, 3, false);

        // Initialize kings
        board[0][4] = new King("King", colorTop, 0, 4, true);
        board[7][4] = new King("King", colorBottom, 7, 4, false);

        this.whiteIsMoving = true;
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void movePiece(int startX, int startY, int endX, int endY) {
        resetCheck();
        if (board[startX][startY] == null) {
            System.out.println("No piece at the starting position");
            return;
        }
        if (startX == endX && startY == endY) {
            return;
        }
        if (board[startX][startY] != null && (board[startX][startY].getColor() == ChessColor.BLACK && whiteIsMoving) || (board[startX][startY].getColor() == ChessColor.WHITE && !whiteIsMoving)) {
            invalidMove(board[startX][startY], endX, endY);
            return;
        }
        if (board[startX][startY].isValidMove(endX, endY, board)) {
            ChessColor color = board[startX][startY].getColor();


            Piece temp = board[endX][endY];

            board[endX][endY] = board[startX][startY];
            board[endX][endY].move(endX, endY);
            board[startX][startY] = null;

            if (board[endX][endY].isChecked(board)) {
                invalidMove(board[endX][endY], endX, endY);
                board[startX][startY] = board[endX][endY];
                board[startX][startY].move(startX, startY);
                board[endX][endY] = temp;
                return;
            }

            if (isCrossMove(board[endX][endY] ,startX, startY, endX, endY)) {
                board[startX][endY] = null;
            }

            // Kiểm tra phong cấp
            if (board[endX][endY] instanceof Pawn && (endX == 0 || endX == 7)) {
                promotePawn(endX, endY);
            }

            if (board[endX][endY].check(board)) {
                if (color == ChessColor.WHITE) {
                    blackIsChecked = true;
                    System.out.println("Check");
                } else {
                    whiteIsChecked = true;
                    System.out.println("Check");
                }
            }
            expiredCrossMove();
            validateCross(board[endX][endY], startX, startY, endX, endY);

            whiteIsMoving = color != ChessColor.WHITE;

            if (isCheckMate() != 0) {
                getResults();
            }
        } else {
            invalidMove(board[startX][startY], endX, endY);
        }
    }

    private void resetCheck() {
        whiteIsChecked = false;
        blackIsChecked = false;
    }

    private void validateCross(Piece piece, int startX, int startY, int endX, int endY) {
        if (piece instanceof Pawn && (startX == 1 || startX == 6) && Math.abs(endX - startX) == 2) {
            int newX = (startX == 1) ? 3 : 4;
            if (startY > 0 && board[newX][startY - 1] != null && board[newX][startY - 1].getColor() != piece.getColor() && board[newX][startY - 1] instanceof Pawn) {
                ((Pawn) board[newX][startY - 1]).setCrossed(true);
            }
            if (startY < 7 && board[newX][startY + 1] != null && board[newX][startY + 1].getColor() != piece.getColor() && board[newX][startY + 1] instanceof Pawn) {
                ((Pawn) board[newX][startY + 1]).setCrossed(true);
            }
        }
    }

    private boolean isCrossMove(Piece piece, int startX, int startY, int endX, int endY) {
        return piece instanceof Pawn && board[startX][endY] != null && board[startX][endY] instanceof Pawn && board[startX][endY].getColor() != piece.getColor() && board[endX][endY] == null;
    }

    private void expiredCrossMove() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] instanceof Pawn) {
                    ((Pawn) board[i][j]).setCrossed(false);
                }
            }
        }
    }

    private void promotePawn(int row, int col) {
        // Hiển thị giao diện để người dùng chọn quân cờ phong cấp
        String[] options = {"Queen", "Rook", "Bishop", "Knight"};
        String choice = (String) JOptionPane.showInputDialog(
                null,
                "Choose a piece to promote the pawn:",
                "Promote Pawn",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        // Phong cấp quân tốt thành quân cờ được chọn
        if (choice != null) {
            Piece promotedPiece = createPromotedPiece(choice, board[row][col]);
            board[row][col] = promotedPiece;
        }
    }

    private Piece createPromotedPiece(String pieceType, Piece piece) {
        return switch (pieceType) {
            case "Queen" -> new Queen("Queen", piece.getColor(), piece.getX(), piece.getY(), piece.isTopToBottom());
            case "Rook" -> new Rook("Rook", piece.getColor(), piece.getX(), piece.getY(), piece.isTopToBottom());
            case "Bishop" -> new Bishop("Bishop", piece.getColor(), piece.getX(), piece.getY(), piece.isTopToBottom());
            case "Knight" -> new Knight("Knight", piece.getColor(), piece.getX(), piece.getY(), piece.isTopToBottom());
            default ->
                    new Queen("Queen", piece.getColor(), piece.getX(), piece.getY(), piece.isTopToBottom()); // Mặc định phong cấp thành hậu
        };
    }

    public int isCheckMate() {
        boolean isChecking = (whiteIsMoving && whiteIsChecked) || (!whiteIsMoving && blackIsChecked);
        ChessColor color = whiteIsMoving ? ChessColor.WHITE : ChessColor.BLACK;
        boolean legalMoves = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != null && board[i][j].getColor() == color) {
                    legalMoves = legalMoves || board[i][j].hasLegalMove(board);
                }
            }
        }
        if (isChecking && !legalMoves) {
            return 1;
        } else if (!isChecking && !legalMoves) {
            return 2;
        }

        return 0;
    }

    public void getResults() {
        int result = isCheckMate();
        if (result == 1) {
            System.out.println("Checkmate");
        } else if (result == 2) {
            System.out.println("Stalemate");
        }
    }

    public void setWhiteIsChecked(boolean whiteIsChecked) {
        this.whiteIsChecked = whiteIsChecked;
    }

    public void setBlackIsChecked(boolean blackIsChecked) {
        this.blackIsChecked = blackIsChecked;
    }

    public boolean isWhiteChecked() {
        return whiteIsChecked;
    }

    public boolean isBlackChecked() {
        return blackIsChecked;
    }

    public Piece getPieceAt(int x, int y) {
        return board[x][y];
    }
}
