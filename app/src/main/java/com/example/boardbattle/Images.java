package com.example.boardbattle;

import android.graphics.drawable.Drawable;

import java.util.Map;

class Images {
    private Map<Integer, Drawable> playerImage;
    private Drawable canAccept;
    private Drawable canNotAccept;
    Images(Map<Integer, Drawable> playerImage, Drawable canAccept, Drawable canNotAccept) {
        this.playerImage = playerImage;
        this.canAccept = canAccept;
        this.canNotAccept = canNotAccept;
    }
    Drawable getPlayerImage(int key) {
        return playerImage.get(key);
    }

    Drawable getCanAccept() {
        return canAccept;
    }

    Drawable getCanNotAccept() {
        return canNotAccept;
    }
}
