package com.fsep.sova.network.events.getcomments;

import com.fsep.sova.models.Comment;

import java.util.List;

public class GettingCommentsIsSuccessEvent {

    private List<Comment> mComments;

    public GettingCommentsIsSuccessEvent(List<Comment> comments) {
        mComments = comments;
    }

    public List<Comment> getComments() {
        return mComments;
    }
}
