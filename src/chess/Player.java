package chess;

import chess.Piece.Piece;

public class Player {
    String name;
    Color color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void makeMove(Board board, Piece piece, int endRow, int endCol){
        if(board.validMove() && piece.canMove()) {
            board.setPiece(piece, endRow, endCol);
            piece.setPiece(endRow, endRow);
        }
    }
}
