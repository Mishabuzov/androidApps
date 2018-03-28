package com.example.tom.itistracker.widgets.fonts.textviews;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.example.tom.itistracker.widgets.fonts.FontUtils;

public abstract class BaseEditModeTextView extends AppCompatTextView {
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
