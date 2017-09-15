package com.fsep.sova.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.fsep.sova.R;
import com.fsep.sova.fragments.base.BaseLoadableFragment;
import com.fsep.sova.models.Video;
import com.fsep.sova.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayVideoFragment extends BaseLoadableFragment {

    @Bind(R.id.cinema) VideoView mCinema;
    private Video mVideo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mVideo = getArguments().getParcelable(Constants.VIDEO);
        View view = inflater.inflate(R.layout.video_view, container, false);
        ButterKnife.bind(this, view);
        setVideoSettingsAndPlay();
        return view;
    }

    private void setVideoSettingsAndPlay() {
        mCinema.requestFocus();
        mCinema.setVideoURI(Uri.parse(mVideo.getUrl()));
        mCinema.setMediaController(new MediaController(getActivity()));
        mCinema.start();
    }
}
