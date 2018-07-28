package com.example.kenil.towersofhanoi;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Stack;


public class StackedLayout extends LinearLayout {
    private Stack<View> tiles = new Stack<>();
    public StackedLayout(Context context) {
        super(context);
    }
    public StackedLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void push(View tile) {
        if (!empty()) {
            ((LetterTile)peek()).freeze();
        }
        tiles.push(tile);
        addView(tile);
        ((LetterTile)peek()).unfreeze();
        invalidate();
    }

    public View pop() {
        View popped = null;
        if (!empty()) {
            popped = tiles.pop();
            removeView(popped);
        }
        invalidate();
        if (!empty()) {
            ((LetterTile)peek()).unfreeze();

        }

        return popped;
    }

    public View peek() {
        return tiles.peek();
    }

    public boolean empty() {
        return tiles.empty();
    }

    public void clear() {
        if (!tiles.isEmpty()) {
            tiles.clear();
            removeAllViews();
        }
    }
}
