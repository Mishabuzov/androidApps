package com.fsep.sova.network.events.loadfile;

import com.fsep.sova.network.events.BaseErrorEvent;

public class VideoFileUploadIsErrorEvent extends BaseErrorEvent {

    private String filePath;

    public VideoFileUploadIsErrorEvent(int responseCode, String filePath) {
        super(responseCode);
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
