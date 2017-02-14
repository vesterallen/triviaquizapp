package com.example.triviaapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Allen & Hari on 2/12/17.
 */

class Questions implements Parcelable{
    public int id;
    public String image;
    public String text;
    public int answer;
    public String[] choice;

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public int getAnswer() {
        return answer;
    }

    public String[] getChoice() {
        return choice;
    }

    public Questions(int id, String image, String text, int answer, String[] choice) {
        this.id=id;
        this.image=image;
        this.text=text;
        this.answer=answer;
        this.choice=choice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(image);
        parcel.writeString(text);
        parcel.writeInt(answer);
        parcel.writeStringArray(choice);
    }
    public static final Parcelable.Creator<Questions> CREATOR
            = new Parcelable.Creator<Questions>() {
        public Questions createFromParcel(Parcel in) {
            return new Questions(in);
        }

        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };

    public Questions(Parcel in) {
        this.id=in.readInt();
        this.image=in.readString();
        this.text=in.readString();
        this.answer=in.readInt();
        this.choice= in.createStringArray();
    }
}
