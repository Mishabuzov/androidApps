package com.fsep.sova.network.events.loadfile;

import com.fsep.sova.models.Document;
import com.fsep.sova.models.FileType;

public class FileUploadIsSuccessEvent {

    private String filePath;
    private Document file;
    private FileType fileType;

    public FileUploadIsSuccessEvent(String path, Document file, FileType fileType) {
        this.filePath = path;
        this.file = file;
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public Document getFile() {
        return file;
    }

    public FileType getFileType() {
        return fileType;
    }
}
