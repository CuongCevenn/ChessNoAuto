package controller;

import model.ChessBoard;
import view.ChessGUI;

import javax.swing.*;

public class ChessGame {
    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard();
        ChessGUI chessBoardView = new ChessGUI(chessBoard);

        JFrame frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chessBoardView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
