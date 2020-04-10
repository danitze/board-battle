package com.example.boardbattle;

class Move {
    private int width;
    private int length;
    private int cordX;
    private int cordY;
    private Board board;
    private Player player;
    private Images images;

    Move(Board board, Player player, Images images, int maxSide) {
        this.board = board;
        this.player = player;
        this.images = images;
        int firstSide, secondSide;
        firstSide = (int) (Math.random() * maxSide + 1);
        secondSide = (int) (Math.random() * maxSide + 1);
        if (firstSide > secondSide) {
            width = secondSide;
            length = firstSide;
        } else {
            width = firstSide;
            length = secondSide;
        }
        cordX = (board.getBoardParams().getBoardWidth() - width) / 2;
        cordY = (board.getBoardParams().getBoardLength() - length) / 2;
        setFigure();
        if (isPlaceForMove())
            drawMove();
    }

    private void setFigure() {
        for (int i = 0; i < board.getBoardParams().getBoardWidth(); i++) {
            for (int j = 0; j < board.getBoardParams().getBoardLength(); j++) {
                if (i >= cordX && i < cordX + width && j >= cordY && j < cordY + length) {
                    board.setBoardSchemeElement(i, j, 0, player.getGameNumber());
                } else board.setBoardSchemeElement(i, j, 0, 0);
            }
        }
    }

    private void swapSides() {
        int temp = width;
        width = length;
        length = temp;
    }

    private void drawMove() {
        for (int i = 0; i < board.getBoardParams().getBoardWidth(); i++) {
            for (int j = 0; j < board.getBoardParams().getBoardLength(); j++) {
                if (i >= cordX && i < cordX + width && j >= cordY && j < cordY + length && board.getBoardSchemeElement(i, j, 0) != 0) {
                    if (canAccept())
                        board.setBoardElement(i, j, images.getCanAccept());
                    else
                        board.setBoardElement(i, j, images.getCanNotAccept());
                } else
                    board.setBoardElement(i, j, images.getPlayerImage(board.getBoardSchemeElement(i, j, board.getBoardParams().getBoardHeight() - 1)));
            }
        }
    }

    private boolean canExist() {
        if (cordX >= 0 && cordY >= 0 && cordX + width <= board.getBoardParams().getBoardWidth() && cordY + length <= board.getBoardParams().getBoardLength())
            return true;
        else return false;
    }

    Player getPlayer() {
        return player;
    }

    Board getBoard() {
        return board;
    }

    int getCordX() {
        return cordX;
    }

    int getCordY() {
        return cordY;
    }

    void setCordX(int cordX) {
        this.cordX = cordX;
    }

    void setCordY(int cordY) {
        this.cordY = cordY;
    }

    boolean isOnTheRightEdge() {
        return (cordX + width == board.getBoardParams().getBoardWidth());
    }

    boolean isOnTheBottomEdge() {
        return (cordY + length == board.getBoardParams().getBoardLength());
    }

    boolean isOnTheLeftEdge() {
        return (cordX == 0);
    }

    boolean isOnTheTopEdge() {
        return (cordY == 0);
    }

    void upMove() {
        cordY--;
        setFigure();
        drawMove();
    }

    void downMove() {
        cordY++;
        setFigure();
        drawMove();
    }

    void leftMove() {
        cordX--;
        setFigure();
        drawMove();
    }

    void rightMove() {
        cordX++;
        setFigure();
        drawMove();
    }


    void turn() {
        cordX = cordX + (width - length) / 2;
        cordY = cordY + (length - width) / 2;
        swapSides();
        setFigure();
        drawMove();
    }

    boolean canTurn() {
        int newX = cordX + (width - length) / 2;
        int newY = cordY + (length - width) / 2;
        return (newX >= 0 && newY >= 0 && newX + length <= board.getBoardParams().getBoardWidth() && newY + width <= board.getBoardParams().getBoardLength());
    }

    boolean canAccept() {
        for (int i = cordX; i < cordX + width; i++) {
            for (int j = cordY; j < cordY + length; j++) {
                if (board.getBoardSchemeElement(i, j, board.getBoardParams().getBoardHeight() - 1) != 0)
                    return false;
            }
        }
        if (GameActivity.counter == 1) {
            if ((cordX + width) == board.getBoardParams().getBoardWidth() && (cordY + length) == board.getBoardParams().getBoardLength())
                return true;
            else return false;
        } else if (GameActivity.counter == 2) {
            if (cordX == 0 && cordY == 0)
                return true;
            else return false;
        }

        if (!isOnTheTopEdge()) {
            for (int i = cordX; i < cordX + width; i++) {
                if (board.getBoardSchemeElement(i, cordY - 1, board.getBoardParams().getBoardHeight() - 1) == player.getGameNumber())
                    return true;
            }
        }

        if (!isOnTheBottomEdge()) {
            for (int i = cordX; i < cordX + width; i++) {
                if (board.getBoardSchemeElement(i, cordY + length, board.getBoardParams().getBoardHeight() - 1) == player.getGameNumber())
                    return true;
            }
        }

        if (!isOnTheLeftEdge()) {
            for (int i = cordY; i < cordY + length; i++) {
                if (board.getBoardSchemeElement(cordX - 1, i, board.getBoardParams().getBoardHeight() - 1) == player.getGameNumber())
                    return true;
            }
        }

        if (!isOnTheRightEdge()) {
            for (int i = cordY; i < cordY + length; i++) {
                if (board.getBoardSchemeElement(cordX + width, i, board.getBoardParams().getBoardHeight() - 1) == player.getGameNumber())
                    return true;
            }
        }

        return false;

    }

    void accept() {
        for (int i = cordX; i < cordX + width; i++) {
            for (int j = cordY; j < cordY + length; j++) {
                board.setBoardSchemeElement(i, j, board.getBoardParams().getBoardHeight() - 1, board.getBoardSchemeElement(i, j, 0));
                board.setBoardSchemeElement(i, j, 0, 0);
            }
        }
        drawMove();
        player.setScore(player.getScore() + length * width);
        ++GameActivity.counter;
    }

    boolean isPlaceForMove() {
        int x = cordX;
        int y = cordY;
        for (int i = 0; i < board.getBoardParams().getBoardWidth(); i++) {
            for (int j = 0; j < board.getBoardParams().getBoardLength(); j++) {
                cordX = i;
                cordY = j;
                if (canExist()) {
                    if (canAccept()) {
                        cordX = x;
                        cordY = y;
                        return true;
                    }
                }
                swapSides();
                if (canExist()) {
                    if (canAccept()) {
                        cordX = x;
                        cordY = y;
                        swapSides();
                        return true;
                    }
                }
                swapSides();
            }
        }
        return false;
    }
}
