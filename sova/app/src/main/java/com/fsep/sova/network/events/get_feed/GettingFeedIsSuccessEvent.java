package com.fsep.sova.network.events.get_feed;

import com.fsep.sova.models.Note;

import java.util.List;

public class GettingFeedIsSuccessEvent {
    private List<Note> mNotes;

    public GettingFeedIsSuccessEvent(List<Note> notes) {
        mNotes = notes;
    }

    public List<Note> getNotes() {
        return mNotes;
    }
}
