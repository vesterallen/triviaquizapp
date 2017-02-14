package com.example.triviaapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Allen & Hari on 2/12/17.
 */

public class QuestionsUtil {
    public static class QuestionsJSONParser{
        public static ArrayList<Questions> parseQuestions(String input) throws JSONException{
            ArrayList<Questions> questions=new ArrayList<>();
            JSONObject root=new JSONObject(input);
            JSONArray questionsJSONArray=root.getJSONArray("questions");
            for (int i=0;i<questionsJSONArray.length();i++){
                Questions question;
                JSONObject quesJson=questionsJSONArray.getJSONObject(i);
                JSONObject choiceJson=quesJson.getJSONObject("choices");
                JSONArray choiceJSONArray=choiceJson.getJSONArray("choice");
                String[] choiceArray=new String[choiceJSONArray.length()];
                for (int j=0;j<choiceJSONArray.length();j++){
                    choiceArray[j]=choiceJSONArray.getString(j);
                }
                if(!quesJson.has("image")) {
                    question = new Questions(quesJson.getInt("id"), null,
                            quesJson.getString("text"), choiceJson.getInt("answer"), choiceArray);
                }else {
                    question = new Questions(quesJson.getInt("id"), quesJson.getString("image"),
                            quesJson.getString("text"), choiceJson.getInt("answer"), choiceArray);
                }
                questions.add(question);
            }
            return questions;
        }
    }
}
