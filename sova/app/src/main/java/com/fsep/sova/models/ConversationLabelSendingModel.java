package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ConversationLabelSendingModel implements Parcelable {

    private Label label;

    public ConversationLabelSendingModel() {
    }

    public ConversationLabelSendingModel(Label label) {
        this.label = label;
    }

    protected ConversationLabelSendingModel(Parcel in) {
        label = Label.getEnum(in.readString());
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public static final Creator<ConversationLabelSendingModel> CREATOR = new Creator<ConversationLabelSendingModel>() {
        @Override
        public ConversationLabelSendingModel createFromParcel(Parcel source) {
            return new ConversationLabelSendingModel(source);
        }

        @Override
        public ConversationLabelSendingModel[] newArray(int size) {
            return new ConversationLabelSendingModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(label.toString());
    }
}
