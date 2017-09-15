package com.kpfu.mikhail.gif.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
/*import com.fsep.sova.R;
import com.fsep.sova.models.AttachmentDocument;
import com.fsep.sova.models.AttachmentImage;
import com.fsep.sova.models.AttachmentVideo;
import com.fsep.sova.models.Document;
import com.fsep.sova.models.FileType;
import com.fsep.sova.models.Photo;
import com.fsep.sova.models.Video;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionGetTicketForFileUploading;*/
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.kbeanie.multipicker.api.CameraImagePicker;
import com.kbeanie.multipicker.api.CameraVideoPicker;
import com.kbeanie.multipicker.api.FilePicker;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.VideoPicker;
import com.kbeanie.multipicker.api.callbacks.FilePickerCallback;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.callbacks.VideoPickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenFile;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.kbeanie.multipicker.api.entity.ChosenVideo;
import com.kbeanie.multipicker.utils.FileUtils;
import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.utils.logger.Logger;
import com.makeramen.roundedimageview.RoundedImageView;
//import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttachmentsHelper implements VideoPickerCallback {


    private Activity mActivity;
    private CameraImagePicker mCameraImagePicker;
    private CameraVideoPicker mCameraVideoPicker;
    private FilePicker mFilePicker;
    private Fragment mFragment;
    private ImagePicker mImagePicker;
    private LinearLayout mImageScrollView;
    private ImageView mIvAddImageCheck;
    private VideoPicker mVideoPicker;
    private boolean mIsCover;
    private ImageView mSingleImageView;
    /*
        private HashMap<String, AttachmentDocument> mDocumentAttachments;
        private HashMap<String, AttachmentImage> mImageAttachments;
        private HashMap<String, AttachmentVideo> mVideoAttachments;
        private AttachmentImage mCover;*/
    private String mOutputImagePath;
    private String mOutputVideoPath;
    private boolean mIsSingleImage;
    private View mSeparationView;


    public AttachmentsHelper(Fragment fragment, Activity activity,
                             LinearLayout imageScrollView,
                             ImageView imageViewAddCheck) {
        mFragment = fragment;
        mActivity = activity;
        mImageScrollView = imageScrollView;
        mIvAddImageCheck = imageViewAddCheck;
    }

    public AttachmentsHelper(Fragment fragment, Activity activity,
                             ImageView singeImage) {
        mFragment = fragment;
        mActivity = activity;
        mSingleImageView = singeImage;
        mIsSingleImage = true;
    }

    public AttachmentsHelper(Fragment fragment) {
        mFragment = fragment;
        mActivity = fragment.getActivity();
    }

   /* public void showAddPhotoDialog(boolean isCover) {
        mIsCover = isCover;
        new MaterialDialog.Builder(mActivity)
                .title(R.string.add_photo_dialog_title)
                .items(R.array.add_photo_dialog_list_items)
                .titleColor(ContextCompat.getColor(mActivity, R.color.btn_blue_bg))
                .itemsCallback(new MaterialDialog.ListCallback() {
                    final int ITEM_GALLERY = 0;
                    final int ITEM_CAMERA = 1;

                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        switch (which) {
                            case ITEM_GALLERY:
                                requestReadExternalStoragePermissionAndMakeAction(() -> AttachmentsHelper.this.initImagePicker());
                                break;
                            case ITEM_CAMERA:
                                requestCameraPermissionAndMakeAction(AttachmentsHelper.this::initCameraImagePicker);
                                break;
                        }
                    }
                })
                .show();
    }

    public void showChooseFile(){
        requestReadExternalStoragePermissionAndMakeAction(this::initFilePicker);
    }

    private void initCameraImagePicker(){
        mCameraImagePicker = new CameraImagePicker(mFragment);
        mCameraImagePicker.setImagePickerCallback(this);
        mOutputImagePath = mCameraImagePicker.pickImage();
    }
*/

    /*   private void initImagePicker() {
           mImagePicker = new ImagePicker(mFragment);
           mImagePicker.setImagePickerCallback(this);
           mImagePicker.pickImage();
       }

       private void initFilePicker(){
           mFilePicker = new FilePicker(mFragment);
           mFilePicker.setFilePickerCallback(this);
           mFilePicker.pickFile();
       }
   */
    public void showAddVideoDialog() {
        new MaterialDialog.Builder(mActivity)
                .title(R.string.add_video_dialog_title)
                .items(R.array.add_video_dialog_list_items)
                .titleColor(ContextCompat.getColor(mActivity, R.color.btn_blue_bg))
                .itemsCallback(new MaterialDialog.ListCallback() {
                    final int ITEM_CAMERA = 0;
                    final int ITEM_EXIST = 1;

                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        switch (which) {
                            case ITEM_CAMERA:
                                requestCameraPermissionAndMakeAction(AttachmentsHelper.this::initCameraVideoPicker);
                                break;
                            case ITEM_EXIST:
                                requestReadExternalStoragePermissionAndMakeAction(AttachmentsHelper.this::initVideoPicker);
                                break;
                        }
                    }
                })
                .show();
    }

    private void initCameraVideoPicker() {
        mCameraVideoPicker = new CameraVideoPicker(mFragment);
        mCameraVideoPicker.setVideoPickerCallback(this);
        mOutputVideoPath = mCameraVideoPicker.pickVideo();
    }

    private void initVideoPicker() {
        mVideoPicker = new VideoPicker(mFragment);
        mVideoPicker.setVideoPickerCallback(this);
        mVideoPicker.pickVideo();
    }
  /*  private float getRotationDegree(String orientationName) {
        if (orientationName.equals("ROTATE_90")) {
            return 90L;
        } else if (orientationName.equals("ROTATE_180")) {
            return 180L;
        } else if (orientationName.equals("ROTATE_270")) {
            return 270L;
        } else {
            return 0;
        }
    }*/

  /*  private void addVideo(String keyFilePath, ChosenVideo video) {
        String filePath = FileUtils.getPath(mActivity, Uri.parse(video.getQueryUri()));

//        createAndAddVideoToList(video);

        LayoutInflater inflater = mActivity.getLayoutInflater();
        View v = inflater.inflate(R.layout.view_video_layout, mImageScrollView, false);
        RoundedImageView iv = (RoundedImageView) v.findViewById(R.id.ticket_media);
        ImageView ivDelete = (ImageView) v.findViewById(R.id.ticket_image_close_button);

//        String filePath = FileUtils.getPath(getContext(), uri);
        if (filePath != null) {
            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.MICRO_KIND);
            iv.setImageBitmap(bitmap);
        }
        mImageScrollView.addView(v);
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageScrollView.removeView(v);
                mVideoAttachments.remove(keyFilePath);
                deleteSeparationView();
            }
        });
        AttachmentVideo attachment = new AttachmentVideo();
        attachment.setView(v);
        attachment.setRemoveButton(ivDelete);
        if(mVideoAttachments == null) {
            mVideoAttachments = new HashMap<>();
        }
        mVideoAttachments.put(keyFilePath, attachment);
    }*/

/*    private void addImage(String keyFilePath, String filePath, boolean isCover, String orientationName) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View v;
        if (isCover) {
            v = inflater.inflate(R.layout.view_photo_cover_layout, mImageScrollView, false);
            mIvAddImageCheck.setVisibility(View.VISIBLE);
        } else {
            v = inflater.inflate(R.layout.view_photo_layout, mImageScrollView, false);
        }
        RoundedImageView iv = (RoundedImageView) v.findViewById(R.id.ticket_media);
        ImageView ivDelete = (ImageView) v.findViewById(R.id.ticket_image_close_button);
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageScrollView.removeView(v);
                if (mCover!=null && mCover.getView() == v) {
                    mCover = null;
                    mIvAddImageCheck.setVisibility(View.GONE);
                } else {
                    mImageAttachments.remove(keyFilePath);
                }
                deleteSeparationView();
            }
        });
        Picasso.with(mActivity).load(filePath).rotate(getRotationDegree(orientationName)).into(iv);
        mImageScrollView.addView(v);
        if (isCover) {
            if(mCover == null) {
                mCover = new AttachmentImage();
            } else {
                mImageScrollView.removeView(mCover.getView());
            }
            mCover.setView(v);
            mCover.setRemoveButton(ivDelete);
        } else {
            AttachmentImage attachment = new AttachmentImage();
            attachment.setView(v);
            attachment.setView(ivDelete);
            if (mImageAttachments == null) {
                mImageAttachments = new HashMap<>();
            }
            mImageAttachments.put(keyFilePath, attachment);
        }
    }*/

/*    private void getTicketForFileUploading(String filePath, FileType fileType) {
        Action action = new ActionGetTicketForFileUploading(
                ActionGetTicketForFileUploading.Reason.TO_UPLOAD.toString(),
                filePath,
                fileType);
        ServiceHelper.getInstance().startActionService(mActivity, action);
    }*/

/*
    public void cleanAttachments() {
        if (mImageAttachments != null) {
            mImageAttachments.clear();
        }
        if (mVideoAttachments != null) {
            mVideoAttachments.clear();
        }
        if (mDocumentAttachments != null) {
            mDocumentAttachments.clear();
        }
    }
*/

   /* public ArrayList<Photo> getPhotos() {
        ArrayList<Photo> mPhotos = new ArrayList<>();
        if(mImageAttachments != null && !mImageAttachments.isEmpty()) {
            for (Map.Entry<String, AttachmentImage> entry : mImageAttachments.entrySet()) {
                AttachmentImage value = entry.getValue();
                mPhotos.add(value.getPhoto());
            }
        }
        return mPhotos;
    }*/

   /* public ArrayList<Video> getVideos() {
        ArrayList<Video> mVideos = new ArrayList<>();
        if(mVideoAttachments != null && !mVideoAttachments.isEmpty()) {
            for (Map.Entry<String, AttachmentVideo> entry : mVideoAttachments.entrySet()) {
                AttachmentVideo value = entry.getValue();
                mVideos.add(value.getVideo());
            }
        }
        return mVideos;
    }

    public ArrayList<Document> getDocuments() {
        ArrayList<Document> mDocuments = new ArrayList<>();
        if(mDocumentAttachments != null && !mDocumentAttachments.isEmpty()) {
            for (Map.Entry<String, AttachmentDocument> entry : mDocumentAttachments.entrySet()) {
                AttachmentDocument value = entry.getValue();
                mDocuments.add(value.getDocument());
            }
        }
        return mDocuments;
    }*/

  /*  public void processGalleryImagePickerActivityResult(Intent data) {
        if (mImagePicker == null) {
            mImagePicker = new ImagePicker(mFragment);
            mImagePicker.setImagePickerCallback(this);
        }
        mImagePicker.submit(data);
    }*/

    public void processGalleryVideoPickerActivityResult(Intent data) {
        if (mVideoPicker == null) {
            mVideoPicker = new VideoPicker(mFragment);
            mVideoPicker.setVideoPickerCallback(this);
        }
        mVideoPicker.submit(data);
    }

   /* public void processCameraImagePickerActivityResult(Intent data) {
        if (mCameraImagePicker == null) {
            mCameraImagePicker = new CameraImagePicker(mFragment);
            mCameraImagePicker.reinitialize(mOutputImagePath);
            mCameraImagePicker.setImagePickerCallback(this);
        }
        mCameraImagePicker.submit(data);
    }*/

    public void processCameraVideoPickerActivityResult(Intent data) {
        if (mCameraVideoPicker == null) {
            mCameraVideoPicker = new CameraVideoPicker(mFragment);
            mCameraVideoPicker.reinitialize(mOutputVideoPath);
            mCameraVideoPicker.setVideoPickerCallback(this);
        }
        mCameraVideoPicker.submit(data);
    }

/*    public void processFilePickerActivityResult(Intent data){
        if(mFilePicker == null) {
            mFilePicker = new FilePicker(mFragment);
            mFilePicker.setFilePickerCallback(this);
        }
        mFilePicker.submit(data);
    }*/


 /*   @Override
    public void onImagesChosen(List<ChosenImage> images) {
        if(images != null && !images.isEmpty()) {
            ChosenImage image = images.get(0);
            String filePath = image.getOriginalPath();
            if (mIsSingleImage) {
                Picasso.with(mActivity).load(filePath).into(mSingleImageView);
            } else {
                addImage(filePath, image.getQueryUri(), mIsCover, image.getOrientationName());
            }
            if (mIsCover) {
                getTicketForFileUploading(filePath, FileType.COVER);
            } else {
                getTicketForFileUploading(filePath, FileType.IMAGE);
            }
        }
    }*/

    @Override
    public void onError(String s) {

    }

    private String mVideoPath = null;

    public String getPickedVideoPath() {
        return mVideoPath;
    }

    @Override
    public void onVideosChosen(List<ChosenVideo> list) {
        if (list != null && !list.isEmpty()) {
            ChosenVideo chosenVideo = list.get(0);
            processVideoPath(chosenVideo.getOriginalPath());
//            addVideo(chosenVideo.getOriginalPath(), chosenVideo);
//            getTicketForFileUploading(chosenVideo.getOriginalPath(), FileType.VIDEO);
        }
    }

    private void processVideoPath(String videoPath) {
        if (videoPath != null) {
            String[] cmd = {"-i"
                    , videoPath
                    , "Image.gif"};
            conversion(cmd);
        }
    }


    private void conversion(String[] cmd) {
        FFmpeg ffmpeg = FFmpeg.getInstance(mActivity);
        try {
            // to execute "ffmpeg -version" command you just need to pass "-version"
            ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {
                    Logger.d("Start");
                }

                @Override
                public void onProgress(String message) {
                    Logger.d("Progress");
                }

                @Override
                public void onFailure(String message) {
                    Logger.e(String.format("%1$s -- %2$s", "error!", message));
                }

                @Override
                public void onSuccess(String message) {
                    Logger.d("Done!");
                }

                @Override
                public void onFinish() {
                    Toast.makeText(mActivity, "Finish", Toast.LENGTH_LONG).show();
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // Handle if FFmpeg is already running
            e.printStackTrace();
        }
    }


  /*  @Override
    public void onFilesChosen(List<ChosenFile> list) {
        if(list != null && !list.isEmpty()) {
            ChosenFile chosenFile = list.get(0);
            addFile(chosenFile.getOriginalPath(), chosenFile);
            getTicketForFileUploading(chosenFile.getOriginalPath(), FileType.FILE);
        }
    }*/

    public void setSeparationViewForChat(View separationView) {
        mSeparationView = separationView;
    }

    private void deleteSeparationView() {
        if (mSeparationView != null && mImageScrollView.getChildCount() == 0) {
            mSeparationView.setVisibility(View.GONE);
        }
    }

   /* private void addFile(String keyFilePath, ChosenFile chosenFile) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View v = inflater.inflate(R.layout.view_document_layout, mImageScrollView, false);
        TextView tvExtention = (TextView) v.findViewById(R.id.tv_file_extension);
        TextView tvFileName = (TextView) v.findViewById(R.id.tv_file_name);
        tvExtention.setText(FileUtils.getExtension(chosenFile.getOriginalPath()));
        tvFileName.setText(chosenFile.getDisplayName());
        ImageView ivDelete = (ImageView) v.findViewById(R.id.ticket_image_close_button);
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageScrollView.removeView(v);
                mDocumentAttachments.remove(keyFilePath);
                deleteSeparationView();
            }
        });
        mImageScrollView.addView(v);
        AttachmentDocument attachment = new AttachmentDocument();
        attachment.setView(v);
        attachment.setRemoveButton(ivDelete);
        if(mDocumentAttachments == null) {
            mDocumentAttachments = new HashMap<>();
        }
        mDocumentAttachments.put(keyFilePath, attachment);
    }*/

   /* public void setPhoto(String filePath, Photo photo){
        mImageAttachments.get(filePath).setPhoto(photo);
    }

    public void setVideo(String fileName, Video video){
        mVideoAttachments.get(fileName).setVideo(video);
    }

    public void setFile(String fileName, Document file) {
        mDocumentAttachments.get(fileName).setDocument(file);
    }

    public void setCover(Photo photo){
        mCover.setPhoto(photo);
    }

    public Photo getCover(){
        if(mCover != null) {
            return mCover.getPhoto();
        } else {
            return null;
        }
    }*/

    private void requestReadExternalStoragePermissionAndMakeAction(Function function) {
        if (Dexter.isRequestOngoing()) return;
        Dexter.checkPermission(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                function.action();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                //
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
                                                           PermissionToken token) {
                PermissionsUtils.showPermissionRationale(token, mActivity,
                        R.string.attachment_permission_dialog_title_read_file,
                        R.string.attachment_permission_dialog_content_read_file);
            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private void requestCameraPermissionAndMakeAction(Function function) {
        if (Dexter.isRequestOngoing()) return;
        Dexter.checkPermissions(new MultiplePermissionsListener() {
                                    @Override
                                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                                        function.action();
                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                                                   PermissionToken token) {
                                        PermissionsUtils.showPermissionRationale(token, mActivity,
                                                R.string.attachment_permission_dialog_title_camera,
                                                R.string.attachment_permission_dialog_content_camera);
                                    }
                                }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void processMediaResult(int requestCode, Intent data) {
        if (requestCode == Picker.PICK_IMAGE_DEVICE) {
//            processGalleryImagePickerActivityResult(data);
        } else if (requestCode == Picker.PICK_VIDEO_DEVICE) {
            processGalleryVideoPickerActivityResult(data);
        } else if (requestCode == Picker.PICK_IMAGE_CAMERA) {
//            processCameraImagePickerActivityResult(data);
        } else if (requestCode == Picker.PICK_VIDEO_CAMERA) {
            processCameraVideoPickerActivityResult(data);
        } else if (requestCode == Picker.PICK_FILE) {
//            processFilePickerActivityResult(data);
        }
    }

  /*  public void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(mActivity, v);
        popupMenu.inflate(R.menu.add_file);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_add_photo:
                        showAddPhotoDialog(false);
                        return true;
                    case R.id.item_add_video:
                        showAddVideoDialog();
                        return true;
                    case R.id.item_add_doc:
                        showChooseFile();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }*/
}
