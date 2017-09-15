package com.fsep.sova.network.events.create_comment;

import com.fsep.sova.models.Comment;

public class CreatingCommentIsSuccessEvent {
    private Comment mNewComment;

    public CreatingCommentIsSuccessEvent(Comment comment) {
        mNewComment = comment;
    }

    public Comment getNewComment() {
        return mNewComment;
    }
}
