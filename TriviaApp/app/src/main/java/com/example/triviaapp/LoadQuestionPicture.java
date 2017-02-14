package com.example.triviaapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Allen & Hari on 2/12/17.
 */

public class LoadQuestionPicture extends AsyncTask<String, Void, Bitmap>{
    Bitmap image;
    Trivia trivia;


    public LoadQuestionPicture(Trivia trivia) {
        this.trivia = trivia;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        trivia.stopProgress();
        trivia.getImage(bitmap);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        trivia.showProgress();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            if (strings[0] != null) {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == connection.HTTP_OK) {
                    image = BitmapFactory.decodeStream(connection.getInputStream());
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image!=null)
            return image;
        else
            return null;
    }
    public interface getImageInterface{
        public void getImage(Bitmap image);
        public void showProgress();
        public void stopProgress();
    }
}
