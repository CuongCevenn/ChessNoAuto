package model.piece;

import constant.ChessColor;
import model.Piece;

public class Knight extends Piece {
    public Knight(String name, ChessColor color, int x, int y, boolean topToBottom) {
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
        int[] dx = {2, 1, -1, -2, -2, -1, 1, 2};
        int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};
        for (int i = 0; i < 8; i++) {
            int newX = this.getX() + dx[i];
            int newY = this.getY() + dy[i];
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                if (board[newX][newY] == null || board[newX][newY].getColor() != this.getColor()) {
                    validMoves[index] = new int[]{newX, newY};
                    index++;
                }
            }
        }
        return validMoves;
    }

    @Override
    public String getImage() {
        return "resources/piece/" + (this.getColor() == ChessColor.WHITE ? "white" : "black") + "_knight.png";
    }
}
