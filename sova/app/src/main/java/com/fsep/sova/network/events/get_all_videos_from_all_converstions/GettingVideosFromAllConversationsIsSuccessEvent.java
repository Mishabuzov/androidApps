package com.fsep.sova.network.events.get_all_videos_from_all_converstions;

import com.fsep.sova.models.Video;

import java.util.List;

public class GettingVideosFromAllConversationsIsSuccessEvent {
    private List<Video> mVideoList;

    public GettingVideosFromAllConversationsIsSuccessEvent(List<Video> videoList) {
        mVideoList = videoList;
    }

    public List<Video> getVideoList() {
        return mVideoList;
    }
}
