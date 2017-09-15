package com.fsep.sova.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fsep.sova.R;
import com.fsep.sova.activities.FullScreenImageActivity;
import com.fsep.sova.activities.GalleryActivity;
import com.fsep.sova.activities.PlayVideoActivity;
import com.fsep.sova.fragments.GalleryFragment;
import com.fsep.sova.fragments.base.BaseLoadableFragment;
import com.fsep.sova.models.Content;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GridImageUtils {

    private BaseLoadableFragment mFragment;
    private GridLayout mMosaicLayout;
    private List<Content> mMediaContent;
    private List<String> mPhotoUrls = new ArrayList<>();

    private View myView;
    private RelativeLayout videoLayout;
    private RelativeLayout.LayoutParams relativeParams;
    private boolean mIsMoreThan3Views;

    public GridImageUtils(BaseLoadableFragment fragment, GridLayout mosaicLayout, List<Content> mediaContent) {
        mFragment = fragment;
        mMosaicLayout = mosaicLayout;
        mMediaContent = mediaContent;
        getPhotoUrls();
    }

    private void getPhotoUrls() {
        for (Content content : mMediaContent) {
            if (content.isPhoto()) {
                mPhotoUrls.add(content.getPhoto().getOriginalUrl());
            }
        }
    }

    public void setParamsAndPicture(int width, int height, GridLayout.LayoutParams params, int contentNum) {
        params.width = width;
        params.height = height;
        mMosaicLayout.addView(createImageView(mMediaContent.get(contentNum), params), params);
    }

    public void fillGridLayoutByMosaicAlgorithm(int mosaicParentPadding, int mosaicImageMargins) {
        Point size = new Point();
        mFragment.getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x - 2 * mosaicParentPadding;
        int imageWidth = screenWidth / 3;
        int imageHeight = screenWidth / 4;

        int i = 0;
        int rows = 0;
        int columns = 0;

        GridLayout.LayoutParams params;
        switch (mMediaContent.size()) {
            case 1:
                //1
                params = new GridLayout.LayoutParams(GridLayout.spec(rows, 2), GridLayout.spec(columns, 2));
                params.setGravity(Gravity.CENTER_HORIZONTAL);
                params.setMargins(0, mosaicImageMargins, 0, 0);
                setParamsAndPicture(screenWidth, imageHeight * 2, params, i);
                break;
            case 2:
                //1
                params = new GridLayout.LayoutParams(GridLayout.spec(rows, 2), GridLayout.spec(columns));
                params.setMargins(0, mosaicImageMargins, mosaicImageMargins, 0);
                setParamsAndPicture(screenWidth / 2, imageHeight * 2, params, i);
                //2
                i++;
                params = new GridLayout.LayoutParams(GridLayout.spec(rows, 2), GridLayout.spec(columns + 1));
                params.setMargins(0, mosaicImageMargins, 0, 0);
                setParamsAndPicture(screenWidth / 2, imageHeight * 2, params, i);
                break;
            default:
                //1
                params = new GridLayout.LayoutParams(GridLayout.spec(rows, 2), GridLayout.spec(columns));
                params.setMargins(0, mosaicImageMargins, mosaicImageMargins, 0);
                setParamsAndPicture(imageWidth * 2, imageHeight * 2 + mosaicImageMargins, params, i);
                //2
                i++;
                params = new GridLayout.LayoutParams(GridLayout.spec(rows), GridLayout.spec(columns + 1));
                params.setMargins(0, mosaicImageMargins, 0, 0);
                setParamsAndPicture(imageWidth, imageHeight, params, i);
                //blurring
                if (mMediaContent.size() > 3) {
                    setMoreThan3ViewsFlag(true);
                }
                //3
                i++;
                params = new GridLayout.LayoutParams(GridLayout.spec(rows + 1), GridLayout.spec(columns + 1));
                params.setMargins(0, mosaicImageMargins, 0, 0);
                setParamsAndPicture(imageWidth, imageHeight, params, i);
        }
    }

    private RelativeLayout createImageView(Content content, GridLayout.LayoutParams params) {
        LayoutInflater inflater = (LayoutInflater) mFragment.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myView = inflater.inflate(R.layout.view_video_image, null);
        videoLayout = (RelativeLayout) myView.findViewById(R.id.video_layout);
        relativeParams = new RelativeLayout.LayoutParams(params);
        ImageView mosaicElement = (ImageView) myView.findViewById(R.id.video_image);
        setImageParams(mosaicElement, relativeParams, getContentImageUrl(content));
        if (mFragment.getClass().equals(GalleryFragment.class)) {
            setCaseForGalleryFragment(content);
        } else {
            setMosaicCase(content);
        }
        return videoLayout;
    }

    private String getContentImageUrl(Content content) {
        if (content.isPhoto()) {
            return content.getPhoto().getOriginalUrl();
        } else {
            return content.getVideo().getPreviewImage().getOriginalUrl();
        }
    }

    private void setMoreThan3ViewsFlag(boolean isMoreThan3Views) {
        mIsMoreThan3Views = isMoreThan3Views;
    }

    private void setMosaicCase(Content content) {
        if (content.isVideo()) {
            ImageView videoMark = (ImageView) myView.findViewById(R.id.video_mark);
            videoMark.setVisibility(View.VISIBLE);
        }
        if (mIsMoreThan3Views) {
            ImageView shadow = (ImageView) myView.findViewById(R.id.shadow);
            shadow.setLayoutParams(relativeParams);
            shadow.setVisibility(View.VISIBLE);

            TextView tvQuantityOfImage = (TextView) myView.findViewById(R.id.tv_video_amount);
            tvQuantityOfImage.setText("+" + String.valueOf(mMediaContent.size() - 2));
            setGoToGalleryListener(videoLayout);
        } else {
            setShowContentListener(videoLayout, content);
        }
    }

    private void setCaseForGalleryFragment(Content content) {
        setShowContentListener(videoLayout, content);
        if (content.isVideo()) {
            ImageView ivPlay = (ImageView) myView.findViewById(R.id.iv_play);
            ivPlay.setVisibility(View.VISIBLE);
            ImageView shadow = (ImageView) myView.findViewById(R.id.shadow);
            shadow.setVisibility(View.VISIBLE);
        }
    }

    private void setGoToGalleryListener(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mFragment.getActivity(), GalleryActivity.class);
                intent.putParcelableArrayListExtra(Constants.MEDIA_CONTENT, (ArrayList<Content>) mMediaContent);
                mFragment.startActivity(intent);
            }
        });
    }

    private void setImageParams(ImageView mosaicElement, ViewGroup.LayoutParams params, String url) {
        mosaicElement.setLayoutParams(params);
        mosaicElement.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(mFragment.getContext()).load(url).into(mosaicElement);
    }

    private void setShowContentListener(View mosaicElement, Content content) {
        mosaicElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: разобраться со свайпингом и маштабированием
                if (content.isPhoto()) {
                    Intent intent = new Intent(mFragment.getActivity(), FullScreenImageActivity.class);
                    intent.putStringArrayListExtra(Constants.URLS, (ArrayList<String>) mPhotoUrls);
                    mFragment.startActivity(intent);
                } else if (content.isVideo()) {
                    Intent intent = new Intent(mFragment.getActivity(), PlayVideoActivity.class);
                    intent.putExtra(Constants.VIDEO, content.getVideo());
                    mFragment.startActivity(intent);
                }
            }
        });
    }
}
