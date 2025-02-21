package chess.Piece;

import chess.Color;

public abstract class Piece {
    Color color;
    int row;
    int col;

    public abstract boolean canMove(int row, int col);

    public void setPiece(int row, int col){
        this.row = row;
        this.col = col;
    }
}
