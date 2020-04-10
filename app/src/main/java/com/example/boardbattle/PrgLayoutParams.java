package com.example.boardbattle;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

class PrgLayoutParams {
    private LinearLayout.LayoutParams mainLinearLayoutParams;
    private LinearLayout.LayoutParams linearLayoutParams;
    private LinearLayout.LayoutParams upAndDownLayoutParams;
    private LinearLayout.LayoutParams leftAndRightLayoutParams;
    private LinearLayout.LayoutParams turnAndAcceptLayoutParams;
    private ViewGroup.LayoutParams imageViewParams;
    PrgLayoutParams() {
        mainLinearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        imageViewParams = new ViewGroup.LayoutParams(GameActivity.cellSide, GameActivity.cellSide);
        linearLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;

        upAndDownLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        leftAndRightLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        turnAndAcceptLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        upAndDownLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        turnAndAcceptLayoutParams.gravity = Gravity.CENTER_VERTICAL;
    }

    LinearLayout.LayoutParams getMainLinearLayoutParams() {
        return mainLinearLayoutParams;
    }

    LinearLayout.LayoutParams getLinearLayoutParams() {
        return linearLayoutParams;
    }

    ViewGroup.LayoutParams getImageViewParams() {
        return imageViewParams;
    }

    LinearLayout.LayoutParams getUpAndDownLayoutParams() {
        return upAndDownLayoutParams;
    }

    LinearLayout.LayoutParams getLeftAndRightLayoutParams() {
        return leftAndRightLayoutParams;
    }

    LinearLayout.LayoutParams getTurnAndAcceptLayoutParams() {
        return turnAndAcceptLayoutParams;
    }

    void setUpAndDownLayoutParams(int width, int height) {
        upAndDownLayoutParams.width = width;
        upAndDownLayoutParams.height = height;
    }

    void setLeftAndRightLayoutParams(int width, int height) {
        leftAndRightLayoutParams.width = width;
        leftAndRightLayoutParams.height = height;
    }

    void setTurnAndAcceptLayoutParams(int diameter) {
        turnAndAcceptLayoutParams.width = diameter;
        turnAndAcceptLayoutParams.height = diameter;
    }

}
