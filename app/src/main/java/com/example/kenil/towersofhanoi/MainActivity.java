package com.example.kenil.towersofhanoi;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.tv.TvContentRating;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private String S;

    private Button b1;
    private LinearLayout vl;
    private TextView t1;
    private StackedLayout sl1, sl2, sl3;
    private Stack<LetterTile> p1, p2, p3;
    private ArrayList<LetterTile> ring;
    public static final int LIGHT_BLUE = Color.rgb(176, 200, 255);
    public static final int LIGHT_GREEN = Color.rgb(200, 255, 200);
    private static  Integer SIZE = 3;
    private static Integer MAX_SIZE = 7;
    int smiley = 0x1F60A;
    int partypooper = 0x1F389;
    int thumbsup = 0x1F44D;
    private static Integer moves=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = findViewById(R.id.message_box);
        b1 = findViewById(R.id.giveup);
        startGame();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResult(SIZE,'A','B','C');
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra("steps", S);
                startActivity(i);
                finish();
            }
        });

        sl1.setOnDragListener(new DragListener());
        sl2.setOnDragListener(new DragListener());
        sl3.setOnDragListener(new DragListener());

    }


    private class DragListener implements View.OnDragListener {

        public boolean onDrag(View v, DragEvent event) {

            int action = event.getAction();

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    v.setBackgroundColor(LIGHT_BLUE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(LIGHT_GREEN);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(LIGHT_BLUE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(Color.WHITE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign Tile to the target Layout
                    LetterTile tile = (LetterTile) event.getLocalState();


                    //  StackedLayout temp = (StackedLayout) v;

                    //  if (temp.empty()) {
                    TextView messageBox = (TextView) findViewById(R.id.message_box);
                    //messageBox.setText("Answer: "+ word1 + " " + word2);
                    //Button undo = findViewById(R.id.button);
                    //undo.setEnabled(false);
                    //    }

                    //temp.push(tile);

                    ViewParent parent = tile.getParent();
                    if (parent instanceof StackedLayout) {
                        if(((StackedLayout) parent).getId() != v.getId()) {
                            moves++;
                        }
                        switch (((StackedLayout) parent).getId()) {
                            case R.id.pole1:
                                if (!p1.isEmpty()) {
                                    p1.pop();
                                }
                                break;
                            case R.id.pole2:
                                if (!p2.isEmpty()) {
                                    p2.pop();
                                }
                                break;
                            case R.id.pole3:
                                if (!p3.isEmpty()) {
                                    p3.pop();
                                }
                                break;

                        }
                    }

                    switch (v.getId()) {
                        case R.id.pole1:
                            if (!p1.isEmpty()) {
                                if (tile.getCharacter() < p1.peek().getCharacter()) {
                                    tile.moveToViewGroup((ViewGroup) v);
                                    p1.push(tile);
                                }
                            } else {
                                tile.moveToViewGroup((ViewGroup) v);
                                p1.push(tile);
                            }

                            break;

                        case R.id.pole2:
                            if (!p2.isEmpty()) {
                                if (tile.getCharacter() < p2.peek().getCharacter()) {
                                    tile.moveToViewGroup((ViewGroup) v);
                                    p2.push(tile);
                                }
                            } else {
                                tile.moveToViewGroup((ViewGroup) v);
                                p2.push(tile);
                            }
                            break;
                        case R.id.pole3:
                            if (!p3.isEmpty()) {
                                if (tile.getCharacter() < p3.peek().getCharacter()) {
                                    tile.moveToViewGroup((ViewGroup) v);
                                    p3.push(tile);
                                }
                            } else {
                                tile.moveToViewGroup((ViewGroup) v);
                                p3.push(tile);
                            }
                            break;
                    }

                    t1.setText("Moves: " + moves);

                    if (win()) {
                        if(SIZE == MAX_SIZE) {
                            createAlertBox2("You completed the game" + getEmojiByUnicode(smiley),"Congratulations");
                        } else {
                            createAlertBox("You Won"+getEmojiByUnicode(thumbsup), getEmojiByUnicode(partypooper)+"Congratulations!!"+getEmojiByUnicode(partypooper));
                        }
                    }

                    return true;
            }
            return false;
        }
    }

    public boolean win() {
        if (p3.size() == SIZE) {
            return true;
        } else
            return false;
    }

    private void createAlertBox(String msg, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SIZE = 3;
                startGame();
            }
        });
        builder.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SIZE++;
                startGame();
            }
        });
        builder.show();
    }

    public void createAlertBox2(String msg, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setNegativeButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SIZE = 3;
                startGame();
            }
        });
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }



    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    public void createTiles() {
        ring = new ArrayList<LetterTile>();
        for(Integer i=1;i<=SIZE;i++) {
            ring.add(new LetterTile(this,i.toString().charAt(0)));
        }

    }

    public void pushInPole() {
        for(Integer i=SIZE-1;i>=0;i--) {
            p1.push(ring.get(i));
        }
    }

    public void pushInSll() {
        for(Integer i=SIZE-1;i>=0;i--) {
            sl1.push(ring.get(i));
        }
    }

    public void startGame() {

        S="";
        moves=0;
        t1.setText("Moves: 0");

        sl1 = findViewById(R.id.pole1);
        sl2 = findViewById(R.id.pole2);
        sl3 = findViewById(R.id.pole3);



        p1 = new Stack<LetterTile>();
        p2 = new Stack<LetterTile>();
        p3 = new Stack<LetterTile>();

        sl3.removeAllViews();

        createTiles();
        pushInPole();
        pushInSll();
    }


    public void showResult(int topN, char from, char inter, char to) {


        if (topN == 1) {
            S += "Disk 1 from " + from + " to " + to+'\n';
        } else {
            showResult(topN - 1, from, to, inter);
            S += "Disk " + topN + " from " + from + " to " + to+'\n';
            showResult(topN - 1, inter, from, to);
        }
    }


}



