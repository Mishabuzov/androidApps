package com.kpfu.mikhail.weathermvp.widget.textviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kpfu.mikhail.weathermvp.utils.FontUtils;


public abstract class BaseEditModeTextView extends TextView {
    public BaseEditModeTextView(Context context) {
        super(context);
    }

    public BaseEditModeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseEditModeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void setTypeface(FontUtils.TypefaceType typefaceType) {
        if (!isInEditMode()) {
            super.setTypeface(FontUtils.getTypeface(getContext(), typefaceType));
        }
    }
}
