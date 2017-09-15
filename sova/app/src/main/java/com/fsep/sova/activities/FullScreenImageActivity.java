package com.fsep.sova.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.fsep.sova.R;
import com.fsep.sova.activities.base.BaseActivity;
import com.fsep.sova.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.zabelnikov.swipelayout.layout.frame.SwipeableLayout;
import ua.zabelnikov.swipelayout.layout.listener.OnLayoutPercentageChangeListener;
import ua.zabelnikov.swipelayout.layout.listener.OnLayoutSwipedListener;
import uk.co.senab.photoview.PhotoViewAttacher;

public class FullScreenImageActivity extends BaseActivity implements OnLayoutSwipedListener {

    @Bind(R.id.viewPager) ViewPager mViewPager;
    private PhotoViewPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        ButterKnife.bind(this);
        setupWindow(R.color.colorAuthorizationDark);
        initPagerAdapter();
        mViewPager.setAdapter(mPagerAdapter);
    }

    public void initPagerAdapter() {

        Intent intent = getIntent();
        List<String> urls = intent.getStringArrayListExtra(Constants.URLS);

        mPagerAdapter = new PhotoViewPagerAdapter(this, urls);
        mPagerAdapter.setOnLayoutSwipedListener(this);
    }

    @Override
    public void onLayoutSwiped() {
        finish();
    }


    public class PhotoViewPagerAdapter extends PagerAdapter {

        @Bind(R.id.full_screen_image) ImageView mFullScreenImage;
        //   @Bind(R.id.base_layout) LinearLayout mBaseLayout;
        @Bind(R.id.colorContainer) FrameLayout mBaseLayout;
        @Bind(R.id.swipeableLayout) SwipeableLayout mSwipeableLayout;
        private PhotoViewAttacher mAttacher;
        private final List<String> items;
        private final Context context;
        private OnLayoutSwipedListener onLayoutSwipedListener;
        private View mView;

        public PhotoViewPagerAdapter(Context context, List<String> photos) {
            //     ButterKnife.bind(context, mView);
            this.context = context;
            items = photos;
        }

        public void setOnLayoutSwipedListener(OnLayoutSwipedListener onLayoutSwipedListener) {
            this.onLayoutSwipedListener = onLayoutSwipedListener;
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            String link = items.get(position);

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            mView = inflater.inflate(R.layout.activity_image_full_screen_2, null);
            ButterKnife.bind(this, mView);

            Picasso.with(context).load(link).into(mFullScreenImage);
            mAttacher = new PhotoViewAttacher(mFullScreenImage);

            mSwipeableLayout.setOnLayoutPercentageChangeListener(new OnLayoutPercentageChangeListener() {
                private float lastAlpha = 1.0f;

                @Override
                public void percentageY(float percentage) {
                    float alphaCorrector = percentage / 2;
                    AlphaAnimation alphaAnimation = new AlphaAnimation(lastAlpha, 1 - alphaCorrector);
                    alphaAnimation.setDuration(300);
                    mBaseLayout.startAnimation(alphaAnimation);
                    lastAlpha = 1 - alphaCorrector;
                }
            });
            mSwipeableLayout.setOnSwipedListener(new OnLayoutSwipedListener() {
                @Override
                public void onLayoutSwiped() {
                    if (onLayoutSwipedListener != null) {
                        onLayoutSwipedListener.onLayoutSwiped();
                    }
                }
            });


            container.addView(mView);

            return mView;
        }

        @Override
        public int getCount() {
            return items.size();
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

    }

}
