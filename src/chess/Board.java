package chess;

import chess.Piece.Piece;

public class Board {
    Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        initializeBoard();
    }

    void initializeBoard() {

    }

    Piece getPiece(int row, int col){

    }

    boolean validMove(Piece piece, int row, int col){

    }

    void setPiece(Piece piece, int row, int col){
        board[row][col] = piece;
    }




}
