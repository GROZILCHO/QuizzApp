package com.example.roskata.quizzapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView countLabel;
    private TextView questionLabel;
    private RadioGroup rg;
    private RadioButton answerBtn1;
    private RadioButton answerBtn2;
    private RadioButton answerBtn3;
    private RadioButton answerBtn4;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private  int quizCount = 1;
    static final private int QUIZ_COUNT = 5;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    String quizData [][] = {
            //"Question", "Right Answer", "Choice1", "Choice2", "Choice3"
            {"How many strings on a standard guitar?", "6", "5", "7", "4"},
            {"Which is the thickest string?", "6th", "3th", "5th", "2nd"},
            {"What notes are the guitar strings tuned to in standard tuning from 6th to 1st strings?",
                    "E A D G B E", "E A G D B E", "E B G D A E", "E A D G B D"},
            {"What is a fastest BPM (beats per minute) tempo?", "120", "80", "110", "60"},
            {"What are the notes of music?", "A B C D E F G", "A B C D E F G H", "A B C D E F ", "A B C D E E G"},
            {"What is down/up picking called?", "Alternate", "Alternative", "Alternator", "Altering"},
            {"Which chords are the sad sounding chords?", " Minor", "Major", "Mojo", "Progressive"},
            {"How many beats in a bar 4/4?", "4", "8", "12", "16"},
            {"What is an arpeggio?", "The notes of a chord played individually", "A type of scale",
                    "A style of Italian opera", "A new kids fashion"},
            {"How many different notes are there in a major scale?", "7", "8", "10", "9"},
            {"Is a sharp note up or down one fret on the guitar?", "down", "up", "left", "right"},
            {"How many notes apart is one octave note from another in the steps of a major scale?",
                    "8", "10", "12", "1"},
            {"What are 3 common lead guitar techniques?", " Hammer-ons, bends, slides", "Hammer-ons, bonds, slides",
                    "Hammer-offs, bends, slides", "Hammer-ons, bends, slades"},
            {"How many different notes are there in a minor pentatonic scale?", "5", "7", "10", "12"}
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countLabel = (TextView)findViewById(R.id.countLabel);
        questionLabel = (TextView) findViewById(R.id.questionLabel);
        rg = (RadioGroup)findViewById(R.id.radioGroup);
        answerBtn1 = (RadioButton) findViewById(R.id.answerBtn1);
        answerBtn2 = (RadioButton) findViewById(R.id.answerBtn2);
        answerBtn3 = (RadioButton) findViewById(R.id.answerBtn3);
        answerBtn4 = (RadioButton) findViewById(R.id.answerBtn4);

        //Create quizArray from quizData
        for (int i = 0; i < quizData.length; i++){
            //Prepare Array
            ArrayList<String> tmpArray = new ArrayList<>();
            tmpArray.add(quizData[i][0]);  //"Question"
            tmpArray.add(quizData[i][1]);  //"Right Answer"
            tmpArray.add(quizData[i][2]);  //"Choice1"
            tmpArray.add(quizData[i][3]);  //"Choice2"
            tmpArray.add(quizData[i][4]);  //"Choice3"

            //Add tmpArray to quizArray
            quizArray.add(tmpArray);
        }

        showNextQuiz();
    }

    public void showNextQuiz() {
        //Update quizCountLabel
        countLabel.setText("Q" + quizCount);

        //Generate a random number between 0 and 4 (quizArray's size -1)
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        //Pick one quiz set
        ArrayList<String> quiz = quizArray.get(randomNum);

        //Set question and right answer
        //Array format: "Question", "Right Answer", "Choice1", "Choice2", "Choice3"
        questionLabel.setText(quiz.get(0));
        rightAnswer = quiz.get(1);

        //Remove Question from quiz and shuffle choices
        quiz.remove(0);
        Collections.shuffle(quiz);

        //Set choices
        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));

        //Clear checked radio button
        rg.clearCheck();

        //Remove this quiz from quizArray
        quizArray.remove(randomNum);

    }

    public void checkAnswer(View view) {
        // get selected radio button from radioGroup
        int selectedId = rg.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        Button answerBtn = (Button)findViewById(selectedId);
        String btnText = answerBtn.getText().toString();

        if (btnText.equals(rightAnswer)) {
            //Correct
            rightAnswerCount++;
            Toast.makeText(getApplicationContext(), "Correct!" , Toast.LENGTH_SHORT).show();
        } else {
            //Wrong
            Toast.makeText(getApplicationContext(), "Wrong! The right answer is " + rightAnswer, Toast.LENGTH_SHORT).show();
        }

        if (quizCount == QUIZ_COUNT) {
                    //Show Result
           Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
           intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
           startActivity(intent);
        } else {
                quizCount++;
                showNextQuiz();
        }
    }
}



