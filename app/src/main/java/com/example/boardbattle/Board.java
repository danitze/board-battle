package com.example.boardbattle;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.Arrays;

class Board {
    private BoardParams boardParams;
    private ImageView[][] board;
    private int[][][] boardScheme;

    Board(BoardParams boardParams, ImageView[][] board) {
        this.board = board;
        this.boardParams = boardParams;
        boardScheme = new int[boardParams.getBoardWidth()][boardParams.getBoardLength()][boardParams.getBoardHeight()];
        for (int i = 0; i < boardParams.getBoardWidth(); i++) {
            for (int j = 0; j < boardParams.getBoardLength(); j++) {
                Arrays.fill(boardScheme[i][j], 0);
            }
        }
    }

    int getBoardSchemeElement(int x, int y, int z) {
        return boardScheme[x][y][z];
    }

    void setBoardSchemeElement(int x, int y, int z, int value) {
        boardScheme[x][y][z] = value;
    }

    void setBoardElement(int x, int y, Drawable value){
        board[x][y].setBackground(value);
    }

    boolean isFull() {
        for(int i = 0; i < boardParams.getBoardWidth(); i++) {
            for (int j = 0; j < boardParams.getBoardLength(); j++) {
                if(getBoardSchemeElement(i, j, boardParams.getBoardHeight() - 1) == 0)
                    return false;
            }
        }
        return true;
    }

    BoardParams getBoardParams() {
        return boardParams;
    }

}
