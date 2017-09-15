package com.fsep.sova.utils;

import android.app.Activity;

import com.fsep.sova.models.Photo;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionUploadFile;
import com.fsep.sova.network.actions.ActionUploadImageFile;
import com.fsep.sova.network.actions.ActionUploadVideoFile;
import com.fsep.sova.network.events.get_ticket.GettingTicketIsSuccessEvent;
import com.fsep.sova.network.events.loadfile.FileUploadIsSuccessEvent;
import com.fsep.sova.network.events.loadfile.ImageFileUploadSuccessEvent;
import com.fsep.sova.network.events.loadfile.VideoFileUploadSuccessEvent;

public class AttachmentsLoaderHelper {

    private Activity mActivity;
    private AttachmentsHelper mAttachmentsHelper;

    public AttachmentsLoaderHelper(Activity activity, AttachmentsHelper attachmentsHelper) {
        mActivity = activity;
        mAttachmentsHelper = attachmentsHelper;
    }

    public void onSuccessGettingTicket(GettingTicketIsSuccessEvent event) {
        Action action = null;
        switch (event.getFileType()) {
            case IMAGE:
                action = new ActionUploadImageFile(event.getTicket(), event.getFilePath(), event.getFileType());
                break;
            case COVER:
                action = new ActionUploadImageFile(event.getTicket(), event.getFilePath(), event.getFileType());
                break;
            case VIDEO:
                action = new ActionUploadVideoFile(event.getTicket(), event.getFilePath(), event.getFileType());
                break;
            case FILE:
                action = new ActionUploadFile(event.getTicket(), event.getFilePath(), event.getFileType());
                break;
        }
        ServiceHelper.getInstance().startActionService(mActivity, action);
    }

    public void onSuccessFileUploading(FileUploadIsSuccessEvent event) {
        showOnUploadingFileToast();
        mAttachmentsHelper.setFile(event.getFilePath(), event.getFile());
    }

    public void onSuccessImageFileUploading(ImageFileUploadSuccessEvent event) {
        switch (event.getFileType()) {
            case IMAGE:
                mAttachmentsHelper.setPhoto(event.getFilePath(), event.getPhoto());
                break;
            case COVER:
                Photo cover = event.getPhoto();
                mAttachmentsHelper.setCover(cover);
                break;
        }
        showOnUploadingFileToast();
    }

    public void onSuccessVideoFileUploading(VideoFileUploadSuccessEvent event) {
        showOnUploadingFileToast();
        mAttachmentsHelper.setVideo(event.getFilePath(), event.getVideo());
    }

    public void showOnUploadingFileToast() {
        UiUtils.showToast("File uploaded");
    }
}
