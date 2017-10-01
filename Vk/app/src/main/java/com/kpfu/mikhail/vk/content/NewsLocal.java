package com.kpfu.mikhail.vk.content;

import android.os.Parcel;
import android.os.Parcelable;

import com.kpfu.mikhail.vk.content.attachments.Attachment;

import java.util.ArrayList;
import java.util.List;

public class NewsLocal implements Parcelable {

    private String mAuthorName;

    private String mAvatar;

    private String mDate;

    private String mText;

    private List<Attachment> mAttachments;

    private int mLikesCount;

    private boolean mIsLiked;

//    private int mViewsCount;

    public NewsLocal() {
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public String getDate() {
        return mDate;
    }

    public String getText() {
        return mText;
    }

    public List<Attachment> getAttachments() {
        return mAttachments;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public boolean isLiked() {
        return mIsLiked;
    }

/*    public int getViewsCount() {
//        return mViewsCount;
//    }*/

    public void setAuthorName(String authorName) {
        mAuthorName = authorName;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public void setText(String text) {
        mText = text;
    }

    public void setAttachments(List<Attachment> attachments) {
        mAttachments = attachments;
    }

    public void setLikesCount(int likesCount) {
        mLikesCount = likesCount;
    }

    public void setLiked(boolean liked) {
        mIsLiked = liked;
    }

  /*  public void setViewsCount(int viewsCount) {
        mViewsCount = viewsCount;
    }*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mAuthorName);
        dest.writeString(this.mAvatar);
        dest.writeString(this.mDate);
        dest.writeString(this.mText);
        dest.writeList(this.mAttachments);
        dest.writeInt(this.mLikesCount);
        dest.writeByte(this.mIsLiked ? (byte) 1 : (byte) 0);
//        dest.writeInt(this.mViewsCount);
    }

    protected NewsLocal(Parcel in) {
        this.mAuthorName = in.readString();
        this.mAvatar = in.readString();
        this.mDate = in.readString();
        this.mText = in.readString();
        this.mAttachments = new ArrayList<Attachment>();
        in.readList(this.mAttachments, Attachment.class.getClassLoader());
        this.mLikesCount = in.readInt();
        this.mIsLiked = in.readByte() != 0;
//        this.mViewsCount = in.readInt();
    }

    public static final Parcelable.Creator<NewsLocal> CREATOR = new Parcelable.Creator<NewsLocal>() {
        @Override
        public NewsLocal createFromParcel(Parcel source) {
            return new NewsLocal(source);
        }

        @Override
        public NewsLocal[] newArray(int size) {
            return new NewsLocal[size];
        }
    };
}
