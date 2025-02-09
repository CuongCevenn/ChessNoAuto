package model;

import constant.ChessColor;
import model.piece.*;

public class ChessBoard {
    private final int size;
    private final Piece[][] board;
    private boolean isReversed;

    private boolean whiteIsChecked;
    private boolean blackIsChecked;

    private boolean whiteIsMoving;

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
        if (board[startX][startY] == null) {
            System.out.println("No piece at the starting position");
            return;
        }
        if (board[startX][startY] != null && (board[startX][startY].getColor() == ChessColor.BLACK && whiteIsMoving) || (board[startX][startY].getColor() == ChessColor.WHITE && !whiteIsMoving)) {
            System.out.println("Invalid move");
            return;
        }
        if (board[startX][startY].isValidMove(endX, endY, board)) {
            ChessColor color = board[startX][startY].getColor();

            if (color == ChessColor.WHITE && !whiteIsChecked || color == ChessColor.BLACK && !blackIsChecked) {
                board[endX][endY] = board[startX][startY];
                board[endX][endY].move(endX, endY);
                board[startX][startY] = null;

                if (board[endX][endY].check(board)) {
                    if (color == ChessColor.WHITE) {
                        blackIsChecked = true;
                        System.out.println("Check");
                    } else {
                        whiteIsChecked = true;
                        System.out.println("Check");
                    }
                }
            } else {
                Piece temp = board[endX][endY];

                board[endX][endY] = board[startX][startY];
                board[endX][endY].move(endX, endY);
                board[startX][startY] = null;

                if (board[endX][endY].isChecked(board)) {
                    System.out.println("Invalid move");
                    board[startX][startY] = board[endX][endY];
                    board[startX][startY].move(startX, startY);
                    board[endX][endY] = temp;
                    return;
                }
            }

            whiteIsMoving = color != ChessColor.WHITE;
        } else {
            System.out.println("Invalid move");
        }
    }

    public int isCheckMate() {
        boolean isChecking = (whiteIsMoving && whiteIsChecked) || (!whiteIsMoving && blackIsChecked);
        ChessColor color = whiteIsMoving ? ChessColor.WHITE : ChessColor.BLACK;
        int legalMoves = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != null && board[i][j].getColor() == color) {
                    int[][] validMoves = board[i][j].getValidMoves(board);
                    for (int[] validMove : validMoves) {
                        if (validMove[0] != -1 && validMove[1] != -1) {
                            legalMoves++;
                        }
                    }
                }
            }
        }
        if (isChecking && legalMoves == 0) {
            return 1;
        } else if (!isChecking && legalMoves == 0) {
            return 2;
        }

        return 0;
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
