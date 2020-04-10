package com.example.boardbattle;

class BoardParams {
    private int boardWidth;
    private int boardLength;
    private int boardHeight;

    BoardParams(int boardWidth, int boardLength, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardLength = boardLength;
        this.boardHeight = boardHeight;
    }

    int getBoardWidth() {
        return boardWidth;
    }

    int getBoardLength() {
        return boardLength;
    }

    int getBoardHeight() {
        return boardHeight;
    }

    void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    void setBoardLength(int boardLength) {
        this.boardLength = boardLength;
    }

    void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

}
