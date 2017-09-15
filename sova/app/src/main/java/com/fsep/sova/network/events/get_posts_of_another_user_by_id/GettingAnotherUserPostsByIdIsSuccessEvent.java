package com.fsep.sova.network.events.get_posts_of_another_user_by_id;

import com.fsep.sova.models.Post;

import java.util.List;

public class GettingAnotherUserPostsByIdIsSuccessEvent {
    private List<Post> mPosts;

    public GettingAnotherUserPostsByIdIsSuccessEvent(List<Post> posts) {
        mPosts = posts;
    }

    public List<Post> getPosts() {
        return mPosts;
    }
}
