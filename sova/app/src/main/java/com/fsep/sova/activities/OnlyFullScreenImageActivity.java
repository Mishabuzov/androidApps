package com.fsep.sova.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.fsep.sova.R;
import com.fsep.sova.activities.base.BaseActivity;

import ua.zabelnikov.swipelayout.layout.frame.SwipeableLayout;
import ua.zabelnikov.swipelayout.layout.listener.LayoutShiftListener;
import ua.zabelnikov.swipelayout.layout.listener.OnLayoutPercentageChangeListener;
import ua.zabelnikov.swipelayout.layout.listener.OnLayoutSwipedListener;
import uk.co.senab.photoview.PhotoViewAttacher;

public class OnlyFullScreenImageActivity extends BaseActivity {
    private SwipeableLayout swipeableLayout;
    private View colorFrame;
    private ImageView mImageView;
    private PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        swipeableLayout = (SwipeableLayout) findViewById(R.id.swipeableLayout);
        colorFrame = findViewById(R.id.colorContainer);
        mImageView = (ImageView) findViewById(R.id.full_screen_image);


        swipeableLayout.setOnLayoutPercentageChangeListener(new OnLayoutPercentageChangeListener() {
            private float lastAlpha = 1.0f;

            @Override
            public void percentageY(float percentage) {
                float alphaCorrector = percentage / 3;
                AlphaAnimation alphaAnimation = new AlphaAnimation(lastAlpha, 1 - alphaCorrector);
                alphaAnimation.setDuration(300);
                colorFrame.startAnimation(alphaAnimation);
                lastAlpha = 1 - alphaCorrector;
            }
        });
        swipeableLayout.setOnSwipedListener(new OnLayoutSwipedListener() {
            @Override
            public void onLayoutSwiped() {
                OnlyFullScreenImageActivity.this.finish();
            }
        });

        swipeableLayout.setLayoutShiftListener(new LayoutShiftListener() {
            @Override
            public void onLayoutShifted(float positionX, float positionY, boolean isTouched) {
                Log.e("Swipelayout", "isTouched = " + isTouched);
            }
        });
        mAttacher = new PhotoViewAttacher(mImageView);
    }
}
