package com.fsep.sova.network.events.get_ticket;

import com.fsep.sova.models.FileType;

public class GettingTicketIsSuccessEvent {
    private String mTicket;
    private String mFilePath;
    private FileType mFileType;

    public GettingTicketIsSuccessEvent(String ticket, String filePath, FileType fileType) {
        mTicket = ticket;
        mFilePath = filePath;
        mFileType = fileType;
    }

    public String getTicket() {
        return mTicket;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public FileType getFileType() {
        return mFileType;
    }
}
