package com.kpfu.mikhail.vk.content;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kpfu.mikhail.vk.content.attachments.Attachment;
import com.kpfu.mikhail.vk.content.likes.Like;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsItem {

    @JsonProperty("source_id")
    private long sourceId;

    private long date;

    private String text;

    private List<Attachment> attachments;

    private Like likes;

//    private View views;

    public NewsItem() {
    }

    public NewsItem(long sourceId, long date,
                    String text, List<Attachment> attachments,
                    Like likes, View views) {
        this.sourceId = sourceId;
        this.date = date;
        this.text = text;
        this.attachments = attachments;
        this.likes = likes;
//        this.views = views;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Like getLikes() {
        return likes;
    }

    public void setLikes(Like likes) {
        this.likes = likes;
    }

/*
    public View getViews() {
        return views;
    }

    public void setViews(View views) {
        this.views = views;
    }
*/

}
