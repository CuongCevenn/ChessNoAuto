package model.piece;

import constant.ChessColor;
import model.Piece;

public class Pawn extends Piece {
    public Pawn(String name, ChessColor color, int x, int y, boolean topToBottom) {
        super(name, color, x, y, topToBottom);
    }

    @Override
    public boolean isValidMove(int endX, int endY, Piece[][] board) {
        int[][] validMoves = this.getValidMoves(board);
        for (int[] validMove : validMoves) {
            if (validMove[0] == endX && validMove[1] == endY) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int[][] getValidMoves(Piece[][] board) {
        int direction = this.isTopToBottom() ? 1 : -1;
        int startRow = this.isTopToBottom() ? 1 : 6;

        int[][] validMoves = new int[4][2];
        for (int i = 0; i < validMoves.length; i++) {
            validMoves[i][0] = -1;
            validMoves[i][1] = -1;
        }
        int index = 0;
        if (board[this.getX() + direction][this.getY()] == null) {
            validMoves[index] = new int[]{this.getX() + direction, this.getY()};
            index++;
        }

        if (this.getX() == startRow && board[this.getX() + 2 * direction][this.getY()] == null) {
            validMoves[index] = new int[]{this.getX() + 2 * direction, this.getY()};
            index++;
        }

        if (this.getY() > 0 && board[this.getX() + direction][this.getY() - 1] != null && board[this.getX() + direction][this.getY() - 1].getColor() != this.getColor()) {
            validMoves[index] = new int[]{this.getX() + direction, this.getY() - 1};
            index++;
        }

        if (this.getY() < 7 && board[this.getX() + direction][this.getY() + 1] != null && board[this.getX() + direction][this.getY() + 1].getColor() != this.getColor()) {
            validMoves[index] = new int[]{this.getX() + direction, this.getY() + 1};
        }

        return validMoves;
    }
}