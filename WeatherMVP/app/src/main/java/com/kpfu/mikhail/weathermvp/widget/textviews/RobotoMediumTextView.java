package com.kpfu.mikhail.weathermvp.widget.textviews;

import android.content.Context;
import android.util.AttributeSet;

import com.kpfu.mikhail.weathermvp.utils.FontUtils;


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
