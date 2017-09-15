package com.fsep.sova.network.actions;

import android.content.Context;
import android.os.Parcelable;

public interface Action extends Parcelable {

    void execute();

    void setContext(Context context);

}
