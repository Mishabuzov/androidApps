package com.example.tom.itistracker.widgets.fonts.textviews;

import android.content.Context;
import android.util.AttributeSet;

import com.example.tom.itistracker.widgets.fonts.FontUtils;

public class RobotoMediumTextView extends BaseEditModeTextView {

    public RobotoMediumTextView(Context context) {
        super(context);
        setTypeface(FontUtils.TypefaceType.ROBOTO_MEDIUM);
    }

    public RobotoMediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(FontUtils.TypefaceType.ROBOTO_MEDIUM);
    }

    public RobotoMediumTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(FontUtils.TypefaceType.ROBOTO_MEDIUM);
    }
}
