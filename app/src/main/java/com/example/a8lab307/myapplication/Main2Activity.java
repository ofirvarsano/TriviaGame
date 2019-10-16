package com.example.a8lab307.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    int diff = 0;
    Player player;
    TextView textView4;
    Button easy,normal,hard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        player = (Player) intent.getSerializableExtra("player");

        textView4 = (TextView)findViewById(R.id.textView4);
        textView4.setText(player.getUser());
    }

    public void diffFunc(View view)
    {
        Button b = (Button)view;
        switch ((b.getText().toString().toLowerCase()))
        {
            case "easy": diff = 1; break;
            case "normal": diff = 2; break;
            case "hard" : diff = 3; break;
        }
        player.setDiff(diff);

        Intent intent = new Intent(this , Main3Activity.class);
        intent.putExtra("player" , player);

        startActivity(intent);
    }
}
