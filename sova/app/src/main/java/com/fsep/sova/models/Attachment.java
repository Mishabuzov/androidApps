package com.fsep.sova.models;

import android.view.View;

public class Attachment {

    private View mView;
    private View mRemoveButton;

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        mView = view;
    }

    public View getRemoveButton() {
        return mRemoveButton;
    }

    public void setRemoveButton(View removeButton) {
        mRemoveButton = removeButton;
    }
}
