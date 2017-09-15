package com.fsep.sova.textviews;

import android.content.Context;
import android.util.AttributeSet;

import com.fsep.sova.utils.FontUtils;

public class RobotoItalicTextView extends BaseEditModeTextView {
    public RobotoItalicTextView(Context context) {
        super(context);
        setTypeface(FontUtils.TypefaceType.ROBOTO_ITALIC);
    }

    public RobotoItalicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(FontUtils.TypefaceType.ROBOTO_ITALIC);
    }

    public RobotoItalicTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(FontUtils.TypefaceType.ROBOTO_ITALIC);
    }
}
