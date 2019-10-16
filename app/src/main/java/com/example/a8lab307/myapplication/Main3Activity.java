package com.example.a8lab307.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hit.communication.Question;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    TextView textView3,qtext;
    Player player;
    Button ans1,ans2,ans3,ans4;
    ArrayList<Question> questionsarray;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent intent = getIntent();
        player = (Player) intent.getSerializableExtra("player");

        counter = 4;
        player.setScore(0);
        textView3 = (TextView)findViewById(R.id.textView3);
        qtext = (TextView)findViewById(R.id.qtext);
        textView3.setText(player.getUser());

        ans1 = (Button)findViewById(R.id.ans1);
        ans2 = (Button)findViewById(R.id.ans2);
        ans3 = (Button)findViewById(R.id.ans3);
        ans4 = (Button)findViewById(R.id.ans4);

        sendQuestionsRequest();
    }

    public void sendQuestionsRequest() {
        questionsarray = new ArrayList<Question>();
        new AsyncTask<Void, Void, Void>() {

            ArrayList<Question> serverRespond;

            @Override
            protected Void doInBackground(Void... params) {
                try
                {
                    serverRespond = TCPClient.sendQuestionsRequest("40.118.43.255", player.getDiff(), 5);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

                return null;
            }
            @Override
            protected void onPostExecute(Void nothing) {
                try
                {
                    for (Question question : serverRespond) {
                        questionsarray.add(new Question(question.getQuestion(),question.getAnswer(),new ArrayList<String>(question.getOptions())));//question.getOptions()));
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                setQuestions();
            }
        }.execute();
    }

    public void setQuestions()
    {
        try {
            ArrayList<String> op = new ArrayList<String>();
            if (counter >= 0) {
                op = questionsarray.get(counter).getOptions();

                ans1.setText(op.get(0));
                ans2.setText(op.get(1));
                ans3.setText(op.get(2));
                ans4.setText(op.get(3));

                ans1.setBackgroundColor(Color.BLACK);
                ans2.setBackgroundColor(Color.BLACK);
                ans3.setBackgroundColor(Color.BLACK);
                ans4.setBackgroundColor(Color.BLACK);

                ans1.setEnabled(true);
                ans2.setEnabled(true);
                ans3.setEnabled(true);
                ans4.setEnabled(true);

                qtext.setText(questionsarray.get(counter).getQuestion().toString());
            }
            else
            {
                Intent intent = new Intent(this , Main4Activity.class);
                intent.putExtra("player" , player);

                startActivity(intent);

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(),"ex2",Toast.LENGTH_SHORT).show();
        }
    }

    public void answerClick(View view)
    {
        counter--;
        Button b = (Button)view;
        ans1.setEnabled(false);
        ans2.setEnabled(false);
        ans3.setEnabled(false);
        ans4.setEnabled(false);

        boolean isRightAnswer = b.getText().toString().equalsIgnoreCase(questionsarray.get(counter+1).getAnswer());
        if(isRightAnswer)
        {
            player.setScore(player.getScore()+1);
            b.setBackgroundColor(Color.GREEN);
        }
        else
        {
            b.setBackgroundColor(Color.RED);
        }

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params)
            {
                android.os.SystemClock.sleep(1000);
                return null;
            }
            @Override
            protected void onPostExecute(Void nothing) {
                setQuestions();
            }
        }.execute();
    }
}
