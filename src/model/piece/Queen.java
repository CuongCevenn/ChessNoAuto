package model.piece;

import constant.ChessColor;
import model.Piece;

public class Queen extends Piece {
    public Queen(String name, ChessColor color, int x, int y, boolean topToBottom) {
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
        int[][] validMoves = new int[27][2];
        for (int i = 0; i < validMoves.length; i++) {
            validMoves[i][0] = -1;
            validMoves[i][1] = -1;
        }
        int index = 0;
        for (int i = 1; this.getX() + i < 8 && this.getY() + i < 8; i++) {
            if (board[this.getX() + i][this.getY() + i] == null) {
                validMoves[index] = new int[]{this.getX() + i, this.getY() + i};
                index++;
            } else {
                if (board[this.getX() + i][this.getY() + i].getColor() != this.getColor()) {
                    validMoves[index] = new int[]{this.getX() + i, this.getY() + i};
                    index++;
                }
                break;
            }
        }

        for (int i = 1; this.getX() + i < 8 && this.getY() - i >= 0; i++) {
            if (board[this.getX() + i][this.getY() - i] == null) {
                validMoves[index] = new int[]{this.getX() + i, this.getY() - i};
                index++;
            } else {
                if (board[this.getX() + i][this.getY() - i].getColor() != this.getColor()) {
                    validMoves[index] = new int[]{this.getX() + i, this.getY() - i};
                    index++;
                }
                break;
            }
        }

        for (int i = 1; this.getX() - i >= 0 && this.getY() + i < 8; i++) {
            if (board[this.getX() - i][this.getY() + i] == null) {
                validMoves[index] = new int[]{this.getX() - i, this.getY() + i};
                index++;
            } else {
                if (board[this.getX() - i][this.getY() + i].getColor() != this.getColor()) {
                    validMoves[index] = new int[]{this.getX() - i, this.getY() + i};
                    index++;
                }
                break;
            }
        }

        for (int i = 1; this.getX() - i >= 0 && this.getY() - i >= 0; i++) {
            if (board[this.getX() - i][this.getY() - i] == null) {
                validMoves[index] = new int[]{this.getX() - i, this.getY() - i};
                index++;
            } else {
                if (board[this.getX() - i][this.getY() - i].getColor() != this.getColor()) {
                    validMoves[index] = new int[]{this.getX() - i, this.getY() - i};
                    index++;
                }
                break;
            }
        }

        for (int i = this.getX() + 1; i < 8; i++) {
            if (board[i][this.getY()] == null) {
                validMoves[index] = new int[]{i, this.getY()};
                index++;
            } else {
                if (board[i][this.getY()].getColor() != this.getColor()) {
                    validMoves[index] = new int[]{i, this.getY()};
                    index++;
                }
                break;
            }
        }

        for (int i = this.getX() - 1; i >= 0; i--) {
            if (board[i][this.getY()] == null) {
                validMoves[index] = new int[]{i, this.getY()};
                index++;
            } else {
                if (board[i][this.getY()].getColor() != this.getColor()) {
                    validMoves[index] = new int[]{i, this.getY()};
                    index++;
                }
                break;
            }
        }

        for (int i = this.getY() + 1; i < 8; i++) {
            if (board[this.getX()][i] == null) {
                validMoves[index] = new int[]{this.getX(), i};
                index++;
            } else {
                if (board[this.getX()][i].getColor() != this.getColor()) {
                    validMoves[index] = new int[]{this.getX(), i};
                    index++;
                }
                break;
            }
        }

        for (int i = this.getY() - 1; i >= 0; i--) {
            if (board[this.getX()][i] == null) {
                validMoves[index] = new int[]{this.getX(), i};
                index++;
            } else {
                if (board[this.getX()][i].getColor() != this.getColor()) {
                    validMoves[index] = new int[]{this.getX(), i};
                }
                break;
            }
        }

        return validMoves;
    }

    @Override
    public String getImage() {
        return "resources/piece/" + (this.getColor() == ChessColor.WHITE ? "white" : "black") + "_queen.png";
    }
}
