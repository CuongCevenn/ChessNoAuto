package model;

import constant.ChessColor;
import model.piece.King;

public abstract class Piece {
    private final String name;
    private final ChessColor color;
    private int x;
    private int y;
    private boolean isCaptured = false;

    // True: line 0 is at the top, line 7 is at the bottom
    private boolean topToBottom;

    public Piece(String name, ChessColor color, int x, int y, boolean topToBottom) {
        this.name = name;
        this.color = color;
        this.x = x;
        this.y = y;
        this.topToBottom = topToBottom;
    }

    public abstract boolean isValidMove(int endX, int endY, Piece[][] board);

    public abstract int[][] getValidMoves(Piece[][] board);

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean check(Piece[][] board) {
        Piece[] allyPieces = new Piece[16];
        int index = 0;
        Piece enemyKing = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece instanceof King && piece.getColor() != this.getColor()) {
                    enemyKing = piece;
                } else if (piece != null && piece.getColor() == this.getColor()) {
                    allyPieces[index] = piece;
                    index++;
                }
            }
        }
        if (enemyKing == null) {
            throw new RuntimeException("Enemy king not found");
        }
        for (Piece allyPiece : allyPieces) {
            if (allyPiece != null) {
                int[][] validMoves = allyPiece.getValidMoves(board);
                for (int[] validMove : validMoves) {
                    if (validMove[0] == enemyKing.getX() && validMove[1] == enemyKing.getY()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isChecked(Piece[][] board) {
        Piece[] enemyPieces = new Piece[16];
        int index = 0;
        Piece allyKing = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece instanceof King && piece.getColor() == this.getColor()) {
                    allyKing = piece;
                } else if (piece != null && piece.getColor() != this.getColor()) {
                    enemyPieces[index] = piece;
                    index++;
                }
            }
        }
        if (allyKing == null) {
            throw new RuntimeException("Ally king not found");
        }
        for (Piece enemyPiece : enemyPieces) {
            if (enemyPiece != null) {
                int[][] validMoves = enemyPiece.getValidMoves(board);
                for (int[] validMove : validMoves) {
                    if (validMove[0] == allyKing.getX() && validMove[1] == allyKing.getY()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean hasLegalMove(Piece[][] board) {
        int[][] validMoves = this.getValidMoves(board);

        int oldX = this.getX();
        int oldY = this.getY();

        for (int[] validMove : validMoves) {
            if (validMove[0] == -1 || validMove[1] == -1) {
                continue;
            }
            Piece[][] newBoard = new Piece[8][8];
            for (int i = 0; i < 8; i++) {
                System.arraycopy(board[i], 0, newBoard[i], 0, 8);
            }
            newBoard[validMove[0]][validMove[1]] = this;
            newBoard[this.getX()][this.getY()] = null;
            this.move(validMove[0], validMove[1]);
            if (!this.isChecked(newBoard)) {
                this.move(oldX, oldY);
                return true;
            }
        }
        this.move(oldX, oldY);
        return false;
    }

    public String getName() {
        return name;
    }

    public ChessColor getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public void setCaptured(boolean captured) {
        isCaptured = captured;
    }

    public boolean isTopToBottom() {
        return topToBottom;
    }

    public void setTopToBottom(boolean topToBottom) {
        this.topToBottom = topToBottom;
    }

    public String getImage() {
        return "resources/piece/" + (this.getColor() == ChessColor.WHITE ? "white" : "black") + "_pawn.png";
    }
}
