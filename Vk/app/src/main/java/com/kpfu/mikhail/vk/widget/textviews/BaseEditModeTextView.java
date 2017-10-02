package com.kpfu.mikhail.vk.widget.textviews;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.kpfu.mikhail.vk.utils.FontUtils;

public abstract class BaseEditModeTextView extends AppCompatTextView {

    private boolean mEnabled;  //prevent text selection android bug

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

    @Override
    public void scrollTo(int x, int y) {
        //do nothing
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            if (!mEnabled) return;
            super.setEnabled(false);
            super.setEnabled(mEnabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
        super.setEnabled(enabled);
    }

}
