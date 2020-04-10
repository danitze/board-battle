package com.example.boardbattle;

import android.graphics.drawable.Drawable;

class Player {
    private int gameNumber;
    private int score;
    private Drawable image;

    Player(int gameNumber, Drawable image) {
        this.gameNumber = gameNumber;
        this.image = image;
        score = 0;
    }

    int getGameNumber() {
        return gameNumber;
    }

    int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = score;
    }

    Drawable getImage() {
        return image;
    }
}
