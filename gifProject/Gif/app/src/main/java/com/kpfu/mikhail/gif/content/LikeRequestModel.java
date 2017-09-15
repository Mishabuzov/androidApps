package com.kpfu.mikhail.gif.content;

import android.support.annotation.NonNull;

public class LikeRequestModel {

    private String value;

    public LikeRequestModel(String value) {
        this.value = value;
    }

    @NonNull
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
