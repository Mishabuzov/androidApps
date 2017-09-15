package com.fsep.sova.network.events.refresh_post;

import com.fsep.sova.models.Post;

public class RefreshingPostIsSuccessEvent {
    private Post mPost;

    public RefreshingPostIsSuccessEvent(Post post) {
        mPost = post;
    }

    public Post getPost() {
        return mPost;
    }
}
