package com.kpfu.mikhail.gif.content;

import android.support.annotation.NonNull;

public class GifInfo {

    public GifInfo() {
    }

    private String gfyId;

    private int likeState;

    private int bookmarkState;

    public GifInfo(String gfyId, int likeState, int bookmarkState) {
        this.gfyId = gfyId;
        this.likeState = likeState;
        this.bookmarkState = bookmarkState;
    }

    public int getLikeState() {
        return likeState;
    }

    public void setLikeState(int likeState) {
        this.likeState = likeState;
    }

    public int getBookmarkState() {
        return bookmarkState;
    }

    public void setBookmarkState(int bookmarkState) {
        this.bookmarkState = bookmarkState;
    }

    @NonNull
    public String getGfyId() {
        return gfyId;
    }

    public void setGfyId(String gfyId) {
        this.gfyId = gfyId;
    }
}
