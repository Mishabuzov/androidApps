package com.fsep.sova.network.events.create_post;

import com.fsep.sova.models.Post;

public class CreatingPostIsSuccessEvent {
    private Post mPost;

    public CreatingPostIsSuccessEvent(Post post) {
        mPost = post;
    }

    public Post getPost() {
        return mPost;
    }
}
