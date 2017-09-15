package com.fsep.sova.network.events.loadfile;

import com.fsep.sova.models.FileType;
import com.fsep.sova.models.Video;

public class VideoFileUploadSuccessEvent {

    private String filePath;
    private Video video;
    private FileType fileType;

    public VideoFileUploadSuccessEvent(String path, Video video, FileType fileType) {
        this.filePath = path;
        this.video = video;
        this.fileType = fileType;
    }

    public Video getVideo() {
        return video;
    }

    public String getFilePath() {
        return filePath;
    }


}
