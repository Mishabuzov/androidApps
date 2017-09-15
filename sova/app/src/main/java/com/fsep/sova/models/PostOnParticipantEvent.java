package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostOnParticipantEvent implements Parcelable {
    private long eventId;

    public PostOnParticipantEvent(long eventId) {
        this.eventId = eventId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.eventId);
    }

    public PostOnParticipantEvent() {
    }

    protected PostOnParticipantEvent(Parcel in) {
        this.eventId = in.readLong();
    }

    public static final Parcelable.Creator<PostOnParticipantEvent> CREATOR = new Parcelable.Creator<PostOnParticipantEvent>() {
        @Override
        public PostOnParticipantEvent createFromParcel(Parcel source) {
            return new PostOnParticipantEvent(source);
        }

        @Override
        public PostOnParticipantEvent[] newArray(int size) {
            return new PostOnParticipantEvent[size];
        }
    };

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }
}
