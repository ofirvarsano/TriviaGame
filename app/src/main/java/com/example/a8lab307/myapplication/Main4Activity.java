package com.example.a8lab307.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.hit.communication.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Main4Activity extends AppCompatActivity {

    Player player;
    TextView scoretxt,history,txt5,txt6,datetxt;
    String txt,dates;
    ArrayList<Result> res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Intent intent = getIntent();
        player = (Player) intent.getSerializableExtra("player");

        scoretxt = (TextView)findViewById(R.id.scoretxt);
        txt5 = (TextView)findViewById(R.id.txt5);
        txt6 = (TextView)findViewById(R.id.txt6);
        history = (TextView)findViewById(R.id.history);
        datetxt =(TextView)findViewById(R.id.date);
        scoretxt.setText(String.valueOf(player.getScore()));

        Result newResult = new Result(player.getUser(), player.getScore(),player.getDiff(), new Date());
        sendResult(newResult);

        Player.results.add(newResult);
        Collections.reverse(Player.results);
        setResults();
        Collections.reverse(Player.results);

        scoretxt.setText(String.valueOf(player.getScore()));

        txt5.setText("Right Answers:  " + (String.valueOf(player.getScore())));
        txt6.setText("Wrong Answers:  " + (String.valueOf(5 - player.getScore())));
    }

    public void setResults()
    {
        if(!(Player.results.isEmpty()))
        {
            txt = "Score: \n";
            dates = "Date: \n";
            for (Result r : Player.results)
            {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                String dateString = format.format(r.getDate());
                txt += String.valueOf(r.getScore()) + " \n";
                dates += dateString + " \n";
            }
            history.setText(txt);
            datetxt.setText(dates);
        }
    }
    public void sendResult(final Result result) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try
                {
                    TCPClient.sendResult("40.118.43.255", result.getUser(), result.getScore(), player.getDiff());
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

                return null;
            }
            @Override
            protected void onPostExecute(Void nothing) {

            }
        }.execute();
    }

    public void newGame(View view)
    {
        try {
            Intent intent = new Intent(this, Main2Activity.class);
            intent.putExtra("player", player);

            startActivity(intent);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(),"ex",Toast.LENGTH_SHORT).show();
        }
    }
}
