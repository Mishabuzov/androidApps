package com.fsep.sova.network.events.put_like_to_note;

import com.fsep.sova.models.Note;

public class PuttingLikeIsSuccessEvent {
    private Note mNote;

    public PuttingLikeIsSuccessEvent(Note note) {
        mNote = note;
    }

    public Note getNote() {
        return mNote;
    }
}
