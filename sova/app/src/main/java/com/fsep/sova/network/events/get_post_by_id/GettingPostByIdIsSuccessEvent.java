package com.fsep.sova.network.events.get_post_by_id;

import com.fsep.sova.models.Post;

public class GettingPostByIdIsSuccessEvent {
    private Post mPost;

    public GettingPostByIdIsSuccessEvent(Post post) {
        mPost = post;
    }

    public Post getPost() {
        return mPost;
    }
}
