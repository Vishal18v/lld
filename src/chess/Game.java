package chess;

import chess.Piece.Piece;

import java.util.List;

public class Game {
    static Game instance;
    List<Player> players;
    Board board;
    Player currentPlayer;

    public static Game getInstance(){
        if(instance==null)
            instance = new Game();
        return instance;
    }

    void startGame() {
        players.get(0).setName("vishal");
        players.get(0).setColor(Color.WHITE);

        players.get(1).setName("ankit");
        players.get(1).setColor(Color.BLACK);

        currentPlayer = players.get(0);

        while(!gameOver()) {
            int startRow = 0, startCol = 0, endRow = 2, endCol = 2;

            Piece piece = board.getPiece(startRow, startCol);

            currentPlayer.makeMove(board, piece, endRow, endCol);

            //change player turn

        }



    }

    boolean gameOver() {
        //check is checkmate OR stalemate
    }
}
