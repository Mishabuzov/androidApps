package com.fsep.sova.network.events.find_tags;

import com.fsep.sova.models.Tag;

import java.util.List;

public class FindingTagsIsSuccessEvent {
    private List<Tag> mFoundedTags;

    public FindingTagsIsSuccessEvent(List<Tag> foundedTags) {
        mFoundedTags = foundedTags;
    }

    public List<Tag> getFoundedTags() {
        return mFoundedTags;
    }
}
