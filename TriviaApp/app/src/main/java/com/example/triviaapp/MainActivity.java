package com.example.triviaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
/**
 * Created by Allen & Hari on 2/12/17.
 */
public class MainActivity extends AppCompatActivity implements LoadQuizContents.ToPassData {
    ImageView firstImage;
    ArrayList<Questions> questions;
    Button startTrivia;
    String url="http://dev.theappsdr.com/apis/trivia_json/index.php";
    ProgressBar progressBar;
    Button finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        finish=(Button)findViewById(R.id.mainExit);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        firstImage = (ImageView) findViewById(R.id.mainImage);
        questions=new ArrayList<>();
        startTrivia= (Button) findViewById(R.id.mainStart);
        progressBar= (ProgressBar) findViewById(R.id.progressBar2);
        new LoadQuizContents(this, firstImage).execute(url);
    }
    @Override
    public void getQuizCont(final ArrayList<Questions> questionsReceived) {
        startTrivia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Trivia.class);
                intent.putParcelableArrayListExtra("questions",questionsReceived);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        firstImage.setImageBitmap(null);
    }

    @Override
    public void stopProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
