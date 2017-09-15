package com.fsep.sova.network.events.get_favourite_tasks;

import com.fsep.sova.models.Note;

import java.util.List;

public class GettingFavouriteNotesIsSuccessEvent {
    private List<Note> mNotes;

    public GettingFavouriteNotesIsSuccessEvent(List<Note> tasks) {
        mNotes = tasks;
    }

    public List<Note> getNotes() {
        return mNotes;
    }
}
