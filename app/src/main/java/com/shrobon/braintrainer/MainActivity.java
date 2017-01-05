package com.shrobon.braintrainer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    Button button0;
    Button button1;
    Button button2;
    Button button3;

    ArrayList<Integer> answers= new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score = 0;
    TextView resultTextView;
    TextView pointsTextView;
    int numberOfQuestions = 0;
    TextView sumTextView;
    TextView timerTextView;
    Button playAgainButton;
    RelativeLayout gameRelativelayout;

    public void playAgain(View v)
    {
        generateQuestion();
        score = 0;
        numberOfQuestions =0;
        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
        new CountDownTimer(30000+100,1000) {
            @Override
            public void onTick(long millisUntilFinished)
            {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                resultTextView.setText("Final Score:"+ pointsTextView.getText());
                
                MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(),R.raw.horn);//this here will refer to countdown timer and not application context
                mplayer.start();
                Toast.makeText(getApplicationContext(),"Game over: Timeout!!",Toast.LENGTH_LONG).show();
            }
        }.start();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.startButton);
        sumTextView = (TextView)findViewById(R.id.sumTextView);
        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        resultTextView = (TextView)findViewById(R.id.resultTextView) ;
        pointsTextView = (TextView)findViewById(R.id.pointsTextView);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        playAgainButton=(Button)findViewById(R.id.playAgainButton);
        gameRelativelayout = (RelativeLayout)findViewById(R.id.gameRelativelayout);

        //playAgain(findViewById(R.id.playAgainButton));


    }


    public void generateQuestion()
    {
        //we would also require some logic to perform different computation
        Random rand = new Random();

        int a = rand.nextInt(21);// Number 1:: Generates a random number between 0 and 20
        int b = rand.nextInt(21);// Number 2
        int logicRandom = rand.nextInt(3); //chooses whether it will be + , - , *

        locationOfCorrectAnswer = rand.nextInt(4); // will give random between 0-3

        //**can cause a problem if not cleared :: Good ! :D
        answers.clear(); //clear the arraylist each time we answer the question
        int incorrectAnswer;



        if(logicRandom == 0) //case of addition
        {
            sumTextView.setText(Integer.toString(a)+"+"+Integer.toString(b));
            for(int i = 0 ; i <4 ; i++)
            {
                if( i == locationOfCorrectAnswer)
                {
                    answers.add(a + b);
                }
                else
                {
                    incorrectAnswer= (rand.nextInt(41));
                    while(incorrectAnswer == a + b)
                    {
                        incorrectAnswer= (rand.nextInt(41));
                    }
                    answers.add(incorrectAnswer);
                }
            }
        }
        else if(logicRandom == 1) // case of minus
        {
            sumTextView.setText(Integer.toString(a)+"-"+Integer.toString(b));
            for(int i = 0 ; i <4 ; i++)
            {
                if( i == locationOfCorrectAnswer)
                {
                    answers.add(a - b);
                }
                else
                {
                    incorrectAnswer= (rand.nextInt(41));
                    while(incorrectAnswer == a - b)
                    {
                        incorrectAnswer= (rand.nextInt(41));
                    }
                    answers.add(incorrectAnswer);
                }
            }
        }

        else // case of multiply
        {
            sumTextView.setText(Integer.toString(a)+"*"+Integer.toString(b));
            for(int i = 0 ; i <4 ; i++)
            {
                if( i == locationOfCorrectAnswer)
                {
                    answers.add(a * b);
                }
                else
                {
                    incorrectAnswer= (rand.nextInt(41));
                    while(incorrectAnswer == a * b)
                    {
                        incorrectAnswer= (rand.nextInt(41));
                    }
                    answers.add(incorrectAnswer);
                }
            }
        }



        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }

    public void start(View v)
    {
        startButton.setVisibility(View.INVISIBLE);
        gameRelativelayout.setVisibility(RelativeLayout.VISIBLE);
        playAgain(findViewById(R.id.playAgainButton));
        //
    }

    public void chooseAnswer(View v)
    {
        if(v.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer)))
        {
            score++;
            resultTextView.setText("Correct!");
        }
        else
        {
            resultTextView.setText("Wrong");
        }
        numberOfQuestions++;
        pointsTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        generateQuestion();
    }
}
