package com.example.a8lab307.myapplication;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.hit.communication.AuthRespond;
import com.hit.communication.Result;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText userNameText,passwordText;
    Button s;
    String username,password;
    Player player;
    ImageView image;
    ArrayList<Result> res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameText = (EditText)findViewById(R.id.userNameText);
        passwordText = (EditText)findViewById(R.id.passwordText);
        image = (ImageView)findViewById(R.id.imageView);
        image.setImageResource(R.drawable.animm);

        AnimationDrawable antonyms = (AnimationDrawable) image.getDrawable();
        antonyms.start();
    }

    public void sendAuthRequest(View view) {
        username = userNameText.getText().toString().toLowerCase();
        password = passwordText.getText().toString().toLowerCase();

        new AsyncTask<Void, Void, Void>() {

            AuthRespond serverRespond;

            @Override
            protected Void doInBackground(Void... params) {
                try
                {
                    serverRespond = TCPClient.sendAuthRequest("40.118.43.255", username, password);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

                return null;
            }
            @Override
            protected void onPostExecute(Void nothing) {
                if(serverRespond.getStatus())
                {
                    player = new Player(username,password,0,0);
                    Player.results = serverRespond.getResults();
                    startGame();
                }
                else
                {
                    passwordText.setText("");
                    Toast.makeText(getApplicationContext(),serverRespond.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public void startGame()
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
