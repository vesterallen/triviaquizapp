package com.example.triviaapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Allen & Hari on 2/12/17.
 */
public class Trivia extends AppCompatActivity implements LoadQuestionPicture.getImageInterface {
    ArrayList<Questions> questions;
    Questions question;
    RadioGroup radioGroup;
    TextView questionText;
    ImageView quizPic;
    RadioButton radioButton;
    Button next;
    Button previous;
    RadioButton selectedButton;
    int i = 0;
    HashMap<Integer, String> selectedAnswer;
    ArrayList<String> correctAns;
    ArrayList<String> selectedAns;
    ArrayList<String> wrongQues;
    boolean firstTime = true;
    TextView quesId;
    TextView timeInSecs;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correctAns = new ArrayList<>();
        selectedAns = new ArrayList<>();
        wrongQues = new ArrayList<>();
        selectedAnswer = new HashMap<>();
        setContentView(R.layout.activity_trivia);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        questions = new ArrayList<>();
        timeInSecs = (TextView) findViewById(R.id.timeInSecs);
        quesId = (TextView) findViewById(R.id.questionNo);
        questions = getIntent().getParcelableArrayListExtra("questions");
        radioGroup = (RadioGroup) findViewById(R.id.ansRadioGroup);
        questionText = (TextView) findViewById(R.id.questionText);
        quizPic = (ImageView) findViewById(R.id.quizPicture);
        next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.previous);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGrp, int in) {
                if (i+1 == questions.size() && in != -1 &&questions.size() == selectedAnswer.size()) {
                    selectedButton = (RadioButton) findViewById(in);
                    selectedAnswer.put(i, (String) selectedButton.getText());
                    next.setText("Finish");
                }
            }
        });
        new CountDownTimer(120000, 1000) {

            @Override
            public void onTick(long l) {
                timeInSecs.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                for (int k = 0; k < questions.size(); k++) {
                    if (!selectedAnswer.containsKey(k)) {
                        selectedAnswer.put(k, "unattended");
                    }
                }
                complete();
            }
        }.start();
        populateData(questions, i);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i != 0 && radioGroup.getCheckedRadioButtonId() != -1) {
                    selectedButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                    selectedAnswer.put(i, (String) selectedButton.getText());
                    i--;
                    populateData(questions, i);
                } else if (i != 0 && radioGroup.getCheckedRadioButtonId() == -1) {
                    i--;
                    populateData(questions, i);
                } else {

                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i + 1 != questions.size() && radioGroup.getCheckedRadioButtonId() != -1) {
                    selectedButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                    selectedAnswer.put(i, (String) selectedButton.getText());
                    i++;
                    populateData(questions, i);
                } else if (i + 1 == questions.size() &&radioGroup.getCheckedRadioButtonId() != -1&& questions.size() == selectedAnswer.size()) {
                    selectedButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                    selectedAnswer.put(i, (String) selectedButton.getText());
                    next.setText("Finish");
                    complete();
                } else if (i + 1 != questions.size() && radioGroup.getCheckedRadioButtonId() == -1) {
                    i++;
                    populateData(questions, i);
                } else {

                }
            }
        });
    }

    public void complete() {
        for (int j = 0; j < questions.size(); j++) {
            if (!selectedAnswer.get(j).trim().equals(questions.get(j).getChoice()[questions.get(j).getAnswer() - 1].trim())) {
                selectedAns.add(selectedAnswer.get(j));
                correctAns.add(questions.get(j).getChoice()[questions.get(j).getAnswer() - 1]);
                wrongQues.add(questions.get(j).getText());
            }

        }
        Intent intentToStats = new Intent(Trivia.this, TriviaStats.class);
        intentToStats.putStringArrayListExtra("correctAns", correctAns);
        intentToStats.putStringArrayListExtra("selectedAns", selectedAns);
        intentToStats.putStringArrayListExtra("wrongQues", wrongQues);
        intentToStats.putExtra("totalSize", questions.size());
        finish();
        startActivity(intentToStats);
    }

    public void populateData(ArrayList questions, int index) {
        radioGroup.removeAllViews();
        question = (Questions) questions.get(index);
        new LoadQuestionPicture(this).execute(question.getImage());
        questionText.setText(question.getText());
        questionText.setTextSize(9);
        quesId.setText("Q" + ((Questions) questions.get(index)).getId());
        for (int i = 0; i < question.getChoice().length; i++) {
            radioButton = new RadioButton(this);
            radioButton.setId(i);
            radioButton.setText(question.getChoice()[i]);
            radioButton.setTextSize(9);
            radioGroup.addView(radioButton);
        }
        radioGroup.clearCheck();
        if (selectedAnswer.containsKey(index)) {
            String val = selectedAnswer.get(index);
            for (int h = 0; h < ((Questions) questions.get(index)).getChoice().length; h++) {
                if (val.trim().equals(((Questions) questions.get(index)).getChoice()[h].trim())) {
                    radioButton = (RadioButton) findViewById(h);
                }
            }
            radioButton.setChecked(true);
        }
    }

    @Override
    public void getImage(Bitmap image) {
        if (image != null)
            quizPic.setImageBitmap(image);
        else
            quizPic.setImageBitmap(null);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        quizPic.setImageBitmap(null);
    }

    @Override
    public void stopProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
