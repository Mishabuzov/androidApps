package com.fsep.sova.network.events.loadfile;

import com.fsep.sova.models.FileType;
import com.fsep.sova.models.Photo;

public class ImageFileUploadSuccessEvent {

    private String filePath;
    private Photo photo;
    private FileType fileType;

    public ImageFileUploadSuccessEvent(String path, Photo photo, FileType fileType) {
        this.filePath = path;
        this.photo = photo;
        this.fileType = fileType;
    }

    public Photo getPhoto() {
        return photo;
    }

    public FileType getFileType() {
        return fileType;
    }

    public String getFilePath() {
        return filePath;
    }
}
