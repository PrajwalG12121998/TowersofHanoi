package com.example.kenil.towersofhanoi;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.kenil.towersofhanoi.StackedLayout;

public class LetterTile extends android.support.v7.widget.AppCompatTextView {

    public static final int TILE_SIZE = 150;
    public Character letter;
    private boolean frozen;

    public Character getCharacter() {
        return letter;
    }

    public LetterTile(Context context, Character letter) {
        super(context);
        frozen = false;
        this.letter = letter;
        setText(letter.toString());
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setHeight(TILE_SIZE);
        setWidth(TILE_SIZE);
        setTextSize(30);
        setRotation(180);
        setBackgroundColor(Color.rgb(255, 255, 200));
    }

    public void moveToViewGroup(ViewGroup targetView) {
        ViewParent parent = getParent();
        if (parent instanceof StackedLayout) {
            StackedLayout owner = (StackedLayout) parent;
            owner.pop();
            ((StackedLayout) parent).removeView(this);
            ((StackedLayout)targetView).push(this);
//            targetView.addView(this, (targetView).getChildCount());
            setVisibility(View.VISIBLE);
        } else {
            ViewGroup owner = (ViewGroup) parent;
            owner.removeView(this);
            ((StackedLayout) targetView).push(this);
            //unfreeze();
        }
    }

    public void freeze() {
        frozen = true;
    }

    public void unfreeze() {
        frozen = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        if (!frozen &&  motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            return startDrag(ClipData.newPlainText("", ""),
                    new DragShadowBuilder(this), this, 0);

        }
        return super.onTouchEvent(motionEvent);
    }
}
