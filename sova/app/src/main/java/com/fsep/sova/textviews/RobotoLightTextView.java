package com.fsep.sova.textviews;

import android.content.Context;
import android.util.AttributeSet;

import com.fsep.sova.utils.FontUtils;


public class RobotoLightTextView extends BaseEditModeTextView {

    public RobotoLightTextView(Context context) {
        super(context);
        setTypeface(FontUtils.TypefaceType.ROBOTO_LIGHT);
    }

    public RobotoLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(FontUtils.TypefaceType.ROBOTO_LIGHT);
    }

    public RobotoLightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(FontUtils.TypefaceType.ROBOTO_LIGHT);
    }
}
