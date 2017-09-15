package com.fsep.sova.local_events;

import com.fsep.sova.models.Resume;

public class RefreshingProfileEvent {
    private Resume mResume;

    public RefreshingProfileEvent(Resume resume) {
        mResume = resume;
    }

    public Resume getResume() {
        return mResume;
    }
}
