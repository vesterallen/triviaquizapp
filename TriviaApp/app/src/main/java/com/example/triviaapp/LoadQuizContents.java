package com.example.triviaapp;

import android.os.AsyncTask;
import android.widget.ImageView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Allen on 2/11/17.
 */

public class LoadQuizContents extends AsyncTask<String, Void, ArrayList<Questions>> {
    public MainActivity mainActivity;
    public ImageView picImage;
    public StringBuilder sb;
    public ArrayList<Questions> questions;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mainActivity.showProgressBar();
    }

    public LoadQuizContents(MainActivity mainActivityReceived, ImageView imageView) {
        this.mainActivity=mainActivityReceived;
        picImage=imageView;
    }

    @Override
    protected void onPostExecute(ArrayList<Questions> questionses) {
        super.onPostExecute(questionses);
        mainActivity.stopProgressBar();
        picImage.setImageResource(R.drawable.trivia);
        mainActivity.getQuizCont(questionses);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected ArrayList<Questions> doInBackground(String... strings) {
        try {
            URL url=new URL(strings[0]);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.connect();
            int statusCode=connection.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK){
                BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                sb=new StringBuilder();
                String line=br.readLine();
                while(line!=null){
                    sb.append(line);
                    line=br.readLine();
                }
            }
            questions=new ArrayList<>();
            questions=QuestionsUtil.QuestionsJSONParser.parseQuestions(sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public interface ToPassData{
        public void getQuizCont(ArrayList<Questions> questions);
        public void showProgressBar();
        public void stopProgressBar();
    }
}
