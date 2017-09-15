package com.fsep.sova.network.events.get_posts_created_by_me;

import com.fsep.sova.models.Post;

import java.util.List;

public class GettingPostsCreatedByMeIsSuccessEvent {
    private List<Post> mPosts;

    public GettingPostsCreatedByMeIsSuccessEvent(List<Post> posts) {
        mPosts = posts;
    }

    public List<Post> getPosts() {
        return mPosts;
    }
}
