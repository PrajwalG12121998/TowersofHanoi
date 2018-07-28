package com.example.kenil.towersofhanoi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private Button b2;
    private TextView t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        b2=findViewById(R.id.button2);
        Bundle bundle = getIntent().getExtras();
        String step = bundle.getString("steps");
        t2 = findViewById(R.id.tv2);
        t2.setText(step);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }



}
