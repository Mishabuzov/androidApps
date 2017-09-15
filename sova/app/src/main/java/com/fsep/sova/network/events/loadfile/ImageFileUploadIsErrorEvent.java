package com.fsep.sova.network.events.loadfile;

import com.fsep.sova.network.events.BaseErrorEvent;

public class ImageFileUploadIsErrorEvent extends BaseErrorEvent {

    private String filePath;

    public ImageFileUploadIsErrorEvent(int responseCode, String filePath) {
        super(responseCode);
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
