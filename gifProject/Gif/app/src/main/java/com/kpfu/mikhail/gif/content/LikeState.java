package com.kpfu.mikhail.gif.content;

public enum LikeState {

    STATE_ACTIVE(1),
    STATE_NON_ACTIVE(0);

    private int mLikeStatus;

    LikeState(int likeStatus) {
        mLikeStatus = likeStatus;
    }

    public int getLikeStatus() {
        return mLikeStatus;
    }
}
