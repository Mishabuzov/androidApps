package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentSendingModel implements Parcelable {

    private String text;

    public CommentSendingModel() {
    }

    public CommentSendingModel(String text) {
        this.text = text;
    }

    protected CommentSendingModel(Parcel in) {
        text = in.readString();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static final Creator<CommentSendingModel> CREATOR = new Creator<CommentSendingModel>() {
        @Override
        public CommentSendingModel createFromParcel(Parcel source) {
            return new CommentSendingModel(source);
        }

        @Override
        public CommentSendingModel[] newArray(int size) {
            return new CommentSendingModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
    }
}
