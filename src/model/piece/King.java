package model.piece;

import constant.ChessColor;
import model.Piece;

public class King extends Piece {
    public King(String name, ChessColor color, int x, int y, boolean topToBottom) {
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
        int[][] validMoves = new int[8][2];
        for (int i = 0; i < validMoves.length; i++) {
            validMoves[i][0] = -1;
            validMoves[i][1] = -1;
        }
        int index = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (this.getX() + i >= 0 && this.getX() + i < 8 && this.getY() + j >= 0 && this.getY() + j < 8) {
                    if (board[this.getX() + i][this.getY() + j] == null) {
                        validMoves[index] = new int[]{this.getX() + i, this.getY() + j};
                        index++;
                    } else {
                        if (board[this.getX() + i][this.getY() + j].getColor() != this.getColor()) {
                            validMoves[index] = new int[]{this.getX() + i, this.getY() + j};
                            index++;
                        }
                    }
                }
            }
        }
        return validMoves;
    }
}
