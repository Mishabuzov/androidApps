package com.fsep.sova.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.fsep.sova.R;
import com.fsep.sova.fragments.base.BaseLoadableFragment;
import com.fsep.sova.models.Content;
import com.fsep.sova.utils.Constants;
import com.fsep.sova.utils.GridImageUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GalleryFragment extends BaseLoadableFragment {

    @BindDimen(R.dimen.gallery_image_margins) int mGalleryMargins;
    @Bind(R.id.grid_for_images) GridLayout mGridForContent;
    private ArrayList<Content> mMediaContent;

    @OnClick(R.id.back_btn)
    public void onFinishWatchingGallery() {
        getActivity().finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMediaContent = getArguments().getParcelableArrayList(Constants.MEDIA_CONTENT);
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);
        createCells();
        return view;
    }

    private void createCells() {
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);

        int screenWidth = size.x - 4 * mGalleryMargins;
        int imageSize = screenWidth / 4;

        int i = 0;
        int rows = 0;
        int columns = 0;

        GridLayout.LayoutParams params;
        GridImageUtils gridUtils = new GridImageUtils(this, mGridForContent, mMediaContent);
        while (i < mMediaContent.size()) {
            params = new GridLayout.LayoutParams(GridLayout.spec(rows), GridLayout.spec(columns));
            params.setMargins(mGalleryMargins, 2 * mGalleryMargins, mGalleryMargins, 0);
            gridUtils.setParamsAndPicture(imageSize, imageSize, params, i);
            i++;
            columns++;
            if (columns == 4) {
                columns = 0;
                rows++;
            }
        }
    }
}
