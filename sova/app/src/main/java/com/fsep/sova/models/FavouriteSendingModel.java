package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FavouriteSendingModel implements Parcelable {

    private long noteId;

    public FavouriteSendingModel() {
    }

    public FavouriteSendingModel(long noteId) {
        this.noteId = noteId;
    }

    protected FavouriteSendingModel(Parcel in) {
        noteId = in.readLong();
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public static final Creator<FavouriteSendingModel> CREATOR = new Creator<FavouriteSendingModel>() {
        @Override
        public FavouriteSendingModel createFromParcel(Parcel source) {
            return new FavouriteSendingModel(source);
        }

        @Override
        public FavouriteSendingModel[] newArray(int size) {
            return new FavouriteSendingModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(noteId);
    }
}
