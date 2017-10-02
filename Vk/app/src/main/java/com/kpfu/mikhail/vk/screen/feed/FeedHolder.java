package com.kpfu.mikhail.vk.screen.feed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.content.NewsLocal;
import com.kpfu.mikhail.vk.content.attachments.Attachment;
import com.kpfu.mikhail.vk.screen.feed.FeedAdapter.FeedCallback;
import com.kpfu.mikhail.vk.utils.GridImageUtils;
import com.kpfu.mikhail.vk.widget.textviews.RobotoMediumTextView;
import com.kpfu.mikhail.vk.widget.textviews.RobotoRegularTextView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class FeedHolder extends RecyclerView.ViewHolder implements FeedCallback {

    @BindView(R.id.avatar) RoundedImageView mAvatar;

    @BindView(R.id.tv_name) RobotoMediumTextView mTvName;

    @BindView(R.id.tv_date) RobotoRegularTextView mTvDate;

    @BindView(R.id.user_layout) RelativeLayout mUserLayout;

    @BindView(R.id.tv_description) RobotoRegularTextView mTvDescription;

    @BindView(R.id.grid_for_images) GridLayout mGridForImages;

    @BindView(R.id.btn_like) Button mBtnLike;

    @BindView(R.id.functional_layout) FrameLayout mFunctionalLayout;

    private Context mContext;

    FeedHolder(@NonNull View itemView,
               @NonNull Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
    }

    void bind(@NonNull NewsLocal newsLocal) {
        Picasso.with(mAvatar.getContext()).load(newsLocal.getAvatar())
                .into(mAvatar);
        mTvDate.setText(newsLocal.getDate());
        mTvName.setText(newsLocal.getAuthorName());
        mTvDescription.setText(newsLocal.getText());
        mBtnLike.setText(String.valueOf(newsLocal.getLikesCount()));
//        mBtnWatch.setText(String.valueOf(newsLocal.getViewsCount()));
        if (newsLocal.isLiked()) {
            mBtnLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.redheart, 0, 0, 0);
        } else {
            mBtnLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart, 0, 0, 0);
        }
        setAttachments(newsLocal.getAttachments());
    }

    private void setAttachments(List<Attachment> attachments) {
        /*if(attachments == null || attachments.isEmpty()){
//            Photo photo = new Photo();
            attachments = new ArrayList<>();
        }*/
        checkChildOfLayout(mGridForImages);
        if (attachments != null && !attachments.isEmpty()) {
            GridImageUtils gridImageUtils;
//        if(attachments != null && !attachments.isEmpty()){
            mGridForImages.setVisibility(View.VISIBLE);
            gridImageUtils = new GridImageUtils(mContext, mGridForImages, attachments);
            gridImageUtils.fillGridLayoutByMosaicAlgorithm();
        }
       /* } else {
            mGridForImages.setVisibility(View.GONE);
        }*/
    }

    private void checkChildOfLayout(ViewGroup parentLayout) {
        if (parentLayout.getChildCount() != 0) {
            parentLayout.removeAllViews();
            parentLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void enableTextViewHighLighting() {
        mTvDescription.setEnabled(false);
        mTvDescription.setEnabled(true);
    }

}