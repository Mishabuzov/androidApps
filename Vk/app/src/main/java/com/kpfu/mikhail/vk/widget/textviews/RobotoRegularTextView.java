package com.kpfu.mikhail.vk.widget.textviews;

import android.content.Context;
import android.util.AttributeSet;

import com.kpfu.mikhail.vk.utils.FontUtils;

public class RobotoRegularTextView extends BaseEditModeTextView {

    public RobotoRegularTextView(Context context) {
        super(context);
        setTypeface(FontUtils.TypefaceType.ROBOTO_REGULAR);
    }

    public RobotoRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(FontUtils.TypefaceType.ROBOTO_REGULAR);
    }

    public RobotoRegularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(FontUtils.TypefaceType.ROBOTO_REGULAR);
    }
}
