package com.example.triviaapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * Created by Allen & Hari on 2/12/17.
 */
public class TriviaStats extends AppCompatActivity {
    ArrayList<String> correctAns;
    ArrayList<String> selectedAns;
    ArrayList<String> wrongQues;
    LinearLayout linearLayout;
    float totalSize;
    float correctAnsSize;
    double performance;
    TextView performanceView;
    ProgressBar progressBar;
    Button finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_stats);
        linearLayout= (LinearLayout) findViewById(R.id.linearLayout);
        performanceView= (TextView) findViewById(R.id.progressPercent);
        correctAns=new ArrayList<>();
        selectedAns=new ArrayList<>();
        wrongQues=new ArrayList<>();
        finish= (Button) findViewById(R.id.finish);
        correctAns=getIntent().getStringArrayListExtra("correctAns");
        selectedAns=getIntent().getStringArrayListExtra("selectedAns");
        wrongQues=getIntent().getStringArrayListExtra("wrongQues");
        totalSize=getIntent().getIntExtra("totalSize",15);
        correctAnsSize=correctAns.size();
        performance=((totalSize-correctAnsSize)/totalSize)*100;
        performanceView.setText( String.valueOf(performance)+"%");
        progressBar= (ProgressBar) findViewById(R.id.progress);
        progressBar.setMax(100);
        progressBar.setProgress((int)performance);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i=0;i<correctAns.size();i++){
            TextView question=new TextView(TriviaStats.this);
            TextView corAns=new TextView(TriviaStats.this);
            TextView selAns=new TextView(TriviaStats.this);
            question.setText(wrongQues.get(i));
            selAns.setText(selectedAns.get(i));
            corAns.setText(correctAns.get(i));
            question.setLayoutParams(params);
            corAns.setLayoutParams(params);
            selAns.setLayoutParams(params);
            selAns.setBackgroundColor(Color.RED);
            linearLayout.addView(question);
            linearLayout.addView(selAns);
            linearLayout.addView(corAns);
        }
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
