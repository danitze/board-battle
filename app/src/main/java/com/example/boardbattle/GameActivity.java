package com.example.boardbattle;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.CoreComponentFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class GameActivity extends AppCompatActivity {
    private LinearLayout mainLinearLayout;
    private LinearLayout boardLinearLayout;

    private TextView playerNumber;

    private ImageButton up, down, left, right;

    private ImageButton turn, accept;

    private Player player1, player2;

    private ImageView[][] imageViews;

    private BoardParams boardParams;

    private PrgLayoutParams layoutParams;

    private Board board;

    private Move move;

    private Images images;

    static int layoutWidth;
    static int layoutHeight;
    static int cellSide;
    static int counter;

    private int boardWidth;
    private int boardLength;
    private int boardHeight;
    private int maxSide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSides();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        layoutParams = new PrgLayoutParams();
        mainLinearLayout = new LinearLayout(this);
        mainLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mainLinearLayout.setBackground(getDrawable(R.drawable.background));
        setContentView(mainLinearLayout, layoutParams.getMainLinearLayoutParams());
        createGame();
    }

    private void createGame() {
        calculateBoardAndMoveParams();
        addTable();
        addPlayerNumberView();
        addManagementButtons();
        startGame();
    }

    private void getSides() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        layoutWidth = size.x;
        layoutHeight = size.y;
        cellSide = layoutWidth / 12;

    }


    private void calculateBoardAndMoveParams() {
        boardHeight = 2;
        boardWidth = 10;
        boardLength = (int) ((boardWidth + 2) * (3.0 / 4) * (layoutHeight * 1.0 / layoutWidth)) - 2;
        maxSide = 4;
    }

    private void addPlayerNumberView() {
        playerNumber = new TextView(this);
        playerNumber.setLayoutParams(layoutParams.getLinearLayoutParams());
        playerNumber.setTextSize(32);
        mainLinearLayout.addView(playerNumber);
    }

    private void addTable() {
        boardParams = new BoardParams(boardWidth, boardLength, boardHeight);
        boardLinearLayout = new LinearLayout(this);
        boardLinearLayout.setOrientation(LinearLayout.VERTICAL);
        boardLinearLayout.setLayoutParams(layoutParams.getLinearLayoutParams());
        imageViews = new ImageView[boardParams.getBoardWidth()][boardParams.getBoardLength()];
        boardLinearLayout.addView(generateFrameLine());
        for (int i = 0; i < boardParams.getBoardLength(); i++) {
            LinearLayout line = new LinearLayout(this);
            line.setOrientation(LinearLayout.HORIZONTAL);
            line.addView(generateFrameView());
            for (int j = 0; j < boardParams.getBoardWidth(); j++) {
                imageViews[j][i] = new ImageView(this);
                imageViews[j][i].setLayoutParams(layoutParams.getImageViewParams());
                imageViews[j][i].setBackground(getDrawable(R.drawable.standart));
                line.addView(imageViews[j][i]);
            }
            line.addView(generateFrameView());
            boardLinearLayout.addView(line);
        }
        boardLinearLayout.addView(generateFrameLine());
        mainLinearLayout.addView(boardLinearLayout);
        board = new Board(boardParams, imageViews);
    }

    private void addManagementButtons() {
        up = new ImageButton(this);
        up.setBackground(getDrawable(R.drawable.up_move_dark));
        up.setLayoutParams(layoutParams.getUpAndDownLayoutParams());
        up.setAdjustViewBounds(true);
        up.setEnabled(false);

        down = new ImageButton(this);
        down.setBackground(getDrawable(R.drawable.down_move_dark));
        down.setLayoutParams(layoutParams.getUpAndDownLayoutParams());
        down.setAdjustViewBounds(true);
        down.setEnabled(false);

        left = new ImageButton(this);
        left.setBackground(getDrawable(R.drawable.left_move_dark));
        left.setLayoutParams(layoutParams.getLeftAndRightLayoutParams());
        left.setAdjustViewBounds(true);
        left.setEnabled(false);

        right = new ImageButton(this);
        right.setBackground(getDrawable(R.drawable.right_move_dark));
        right.setLayoutParams(layoutParams.getLeftAndRightLayoutParams());
        right.setAdjustViewBounds(true);
        right.setEnabled(false);

        turn = new ImageButton(this);
        turn.setBackground(getDrawable(R.drawable.turn_dark));
        turn.setLayoutParams(layoutParams.getTurnAndAcceptLayoutParams());
        turn.setAdjustViewBounds(true);
        turn.setEnabled(false);

        accept = new ImageButton(this);
        accept.setBackground(getDrawable(R.drawable.accept_dark));
        accept.setLayoutParams(layoutParams.getTurnAndAcceptLayoutParams());
        accept.setAdjustViewBounds(true);
        accept.setEnabled(false);

        double coefficient = (double) left.getLayoutParams().height / left.getLayoutParams().width;

        int buttonWidth = (layoutHeight - (boardLength + 2) * cellSide - playerNumber.getLayoutParams().height) / 4;

        int diameter = (int) (buttonWidth * 1.5);

        layoutParams.setLeftAndRightLayoutParams(buttonWidth, (int) (buttonWidth * coefficient));
        left.setLayoutParams(layoutParams.getLeftAndRightLayoutParams());
        right.setLayoutParams(layoutParams.getLeftAndRightLayoutParams());

        layoutParams.setUpAndDownLayoutParams((int) (buttonWidth * coefficient), buttonWidth);
        up.setLayoutParams(layoutParams.getUpAndDownLayoutParams());
        down.setLayoutParams(layoutParams.getUpAndDownLayoutParams());

        layoutParams.setTurnAndAcceptLayoutParams(diameter);
        turn.setLayoutParams(layoutParams.getTurnAndAcceptLayoutParams());
        accept.setLayoutParams(layoutParams.getTurnAndAcceptLayoutParams());

        LinearLayout buttonsLinearLayout = new LinearLayout(this);
        buttonsLinearLayout.setLayoutParams(layoutParams.getLinearLayoutParams());
        buttonsLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout gamePad = new LinearLayout(this);
        gamePad.setLayoutParams(layoutParams.getLinearLayoutParams());
        gamePad.setOrientation(LinearLayout.VERTICAL);

        LinearLayout line1 = new LinearLayout(this);
        line1.setLayoutParams(layoutParams.getLinearLayoutParams());
        line1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout line2 = new LinearLayout(this);
        line2.setLayoutParams(layoutParams.getLinearLayoutParams());
        line2.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout line3 = new LinearLayout(this);
        line3.setLayoutParams(layoutParams.getLinearLayoutParams());
        line3.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout smallIndent = new LinearLayout(this);
        smallIndent.setLayoutParams(new LinearLayout.LayoutParams(up.getLayoutParams().width, left.getLayoutParams().height));

        LinearLayout.LayoutParams upAndDownParams = (LinearLayout.LayoutParams) up.getLayoutParams();
        upAndDownParams.leftMargin = (layoutWidth - gamePad.getLayoutParams().width - 2 * turn.getLayoutParams().width) / 24;
        up.setLayoutParams(upAndDownParams);
        down.setLayoutParams(upAndDownParams);

        LinearLayout.LayoutParams leftParams = (LinearLayout.LayoutParams) left.getLayoutParams();
        leftParams.leftMargin = (layoutWidth - gamePad.getLayoutParams().width - 2 * turn.getLayoutParams().width) / 24;
        left.setLayoutParams(leftParams);

        line1.addView(up);

        line2.addView(left);
        line2.addView(smallIndent);
        line2.addView(right);

        line3.addView(down);

        gamePad.addView(line1);
        gamePad.addView(line2);
        gamePad.addView(line3);

        LinearLayout.LayoutParams turnParams = (LinearLayout.LayoutParams) turn.getLayoutParams();
        turnParams.leftMargin = (layoutWidth - gamePad.getLayoutParams().width - 2 * turn.getLayoutParams().width) / 12;
        turn.setLayoutParams(turnParams);

        LinearLayout.LayoutParams acceptParams = (LinearLayout.LayoutParams) accept.getLayoutParams();
        acceptParams.leftMargin = (layoutWidth - gamePad.getLayoutParams().width - 2 * turn.getLayoutParams().width) / 12;
        accept.setLayoutParams(acceptParams);

        buttonsLinearLayout.addView(gamePad);

        buttonsLinearLayout.addView(turn);

        buttonsLinearLayout.addView(accept);

        mainLinearLayout.addView(buttonsLinearLayout);
    }

    private void startGame() {
        counter = 1;
        player1 = new Player(1, getDrawable(R.drawable.player1));
        player2 = new Player(2, getDrawable(R.drawable.player2));
        Drawable canAccept = getDrawable(R.drawable.green_square);
        Drawable canNotAccept = getDrawable(R.drawable.red_square);
        final Map<Integer, Drawable> playerImage = new HashMap<>();
        playerImage.put(0, getDrawable(R.drawable.standart));
        playerImage.put(player1.getGameNumber(), getDrawable(R.drawable.player1));
        playerImage.put(player2.getGameNumber(), getDrawable(R.drawable.player2));
        images = new Images(playerImage, canAccept, canNotAccept);
        while (!generateMove())
            generateMove();
        setPlayerNumberText();
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move.upMove();
                checkFigurePosition();
            }
        });

        up.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                while (!move.isOnTheTopEdge())
                    move.upMove();
                checkFigurePosition();
                return false;
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move.downMove();
                checkFigurePosition();
            }
        });

        down.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                while (!move.isOnTheBottomEdge())
                    move.downMove();
                checkFigurePosition();
                return false;
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move.leftMove();
                checkFigurePosition();
            }
        });

        left.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                while (!move.isOnTheLeftEdge())
                    move.leftMove();
                checkFigurePosition();
                return false;
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move.rightMove();
                checkFigurePosition();
            }
        });

        right.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                while (!move.isOnTheRightEdge())
                    move.rightMove();
                checkFigurePosition();
                return false;
            }
        });

        turn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move.turn();
                checkFigurePosition();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move.accept();
                board = move.getBoard();
                if (counter % 2 == 0)
                    player1 = move.getPlayer();
                else player2 = move.getPlayer();
                int formerPlayer = counter - 1;
                up.setEnabled(false);
                down.setEnabled(false);
                left.setEnabled(false);
                right.setEnabled(false);
                turn.setEnabled(false);
                accept.setEnabled(false);
                if (board.isFull()) {
                    showResults();
                    return;
                }
                while (!generateMove())
                    counter++;
                if((counter - formerPlayer) % 2 == 0) {
                    if(counter % 2 == 0) {
                        Toast.makeText(GameActivity.this, R.string.skip_player_1, Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(GameActivity.this, R.string.skip_player_2, Toast.LENGTH_SHORT).show();
                }
                setPlayerNumberText();
            }
        });
    }

    private void checkFigurePosition() {
        if (move.isOnTheBottomEdge())
            down.setEnabled(false);
        else down.setEnabled(true);
        if (move.isOnTheTopEdge())
            up.setEnabled(false);
        else up.setEnabled(true);
        if (move.isOnTheLeftEdge())
            left.setEnabled(false);
        else left.setEnabled(true);
        if (move.isOnTheRightEdge())
            right.setEnabled(false);
        else right.setEnabled(true);
        if (move.canTurn())
            turn.setEnabled(true);
        else turn.setEnabled(false);
        if (move.canAccept())
            accept.setEnabled(true);
        else accept.setEnabled(false);

    }

    private boolean generateMove() {
        move = new Move(board, counter % 2 != 0 ? player1 : player2, images, maxSide);
        if (move.isPlaceForMove()) {
            checkFigurePosition();
            return true;
        }
        return false;
    }

    private void setPlayerNumberText() {
        switch (move.getPlayer().getGameNumber()) {
            case 1:
                playerNumber.setText(R.string.player_1_move);
                break;
            case 2:
                playerNumber.setText(R.string.player_2_move);
                break;
        }
    }

    private LinearLayout generateFrameLine() {
        LinearLayout frame = new LinearLayout(this);
        frame.setLayoutParams(layoutParams.getLinearLayoutParams());
        for (int i = 0; i < boardWidth + 2; i++) {
            frame.addView(generateFrameView());
        }
        return frame;
    }

    private ImageView generateFrameView() {
        ImageView frame = new ImageView(this);
        frame.setLayoutParams(layoutParams.getImageViewParams());
        frame.setBackground(getDrawable(R.drawable.frame));
        return frame;
    }

    private void showResults() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.results);
        if (player1.getScore() > player2.getScore())
            builder.setMessage(R.string.player_1_wins);
        else if (player2.getScore() > player1.getScore())
            builder.setMessage(R.string.player_2_wins);
        else builder.setMessage(R.string.draw);
        builder.setCancelable(false)
                .setPositiveButton(R.string.restart, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(GameActivity.this, GameActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.to_menu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

        AlertDialog gameEnd = builder.create();
        gameEnd.show();
    }

    private void showExitDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.do_you_want_to_exit_to_menu)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setCancelable(true);

        AlertDialog exitDialog = builder.create();
        exitDialog.show();
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }
}
