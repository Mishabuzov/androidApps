package com.kpfu.mikhail.vk.content;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vk.sdk.api.model.VKApiModel;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class News extends VKApiModel {

    private List<NewsItem> items;

    private List<Profile> profiles;

    private List<Group> groups;

    @JsonProperty("next_from")
    private String nextFrom;

    public News() {
    }

    public List<NewsItem> getItems() {
        return items;
    }

    public void setItems(List<NewsItem> items) {
        this.items = items;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public String getNextFrom() {
        return nextFrom;
    }

    public void setNextFrom(String nextFrom) {
        this.nextFrom = nextFrom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.items);
        dest.writeTypedList(this.profiles);
        dest.writeList(this.groups);
        dest.writeString(this.nextFrom);
    }

    protected News(Parcel in) {
        this.items = new ArrayList<NewsItem>();
        in.readList(this.items, NewsItem.class.getClassLoader());
        this.profiles = in.createTypedArrayList(Profile.CREATOR);
        this.groups = new ArrayList<Group>();
        in.readList(this.groups, Group.class.getClassLoader());
        this.nextFrom = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
