package com.fsep.sova.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fsep.sova.R;
import com.fsep.sova.activities.UsersActivity;
import com.fsep.sova.fragments.base.BaseLoadableFragment;
import com.fsep.sova.models.InvitationSendingModel;
import com.fsep.sova.models.Media;
import com.fsep.sova.models.Task;
import com.fsep.sova.models.TaskStatus;
import com.fsep.sova.models.TaskType;
import com.fsep.sova.models.UserInfo;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionCreateTask;
import com.fsep.sova.network.actions.ActionInviteUser;
import com.fsep.sova.network.events.createtask.CreatingTaskIsSuccessEvent;
import com.fsep.sova.network.events.get_ticket.GettingTicketErrorEvent;
import com.fsep.sova.network.events.get_ticket.GettingTicketIsSuccessEvent;
import com.fsep.sova.network.events.loadfile.FileUploadIsErrorEvent;
import com.fsep.sova.network.events.loadfile.FileUploadIsSuccessEvent;
import com.fsep.sova.network.events.loadfile.ImageFileUploadIsErrorEvent;
import com.fsep.sova.network.events.loadfile.ImageFileUploadSuccessEvent;
import com.fsep.sova.network.events.loadfile.VideoFileUploadIsErrorEvent;
import com.fsep.sova.network.events.loadfile.VideoFileUploadSuccessEvent;
import com.fsep.sova.utils.AttachmentsHelper;
import com.fsep.sova.utils.AttachmentsLoaderHelper;
import com.fsep.sova.utils.UiUtils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddTaskFragment extends BaseLoadableFragment {

    public static final String EXTRA_USER = "user_extra";
    private static final int REQUEST_GET_USER = 1;

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.et_title) EditText mEtTitle;
    @Bind(R.id.et_description) EditText mEtDescription;
    @Bind(R.id.tv_choose_date) TextView mTvChooseDate;
    @Bind(R.id.et_add_tag) EditText mAddTag;
    @Bind(R.id.tv_task_status) TextView mTvAddStatus;
    @Bind(R.id.iv_add_user) ImageView mIvAddUser;
    @Bind(R.id.iv_add_file) ImageView mIvAddFile;
    @Bind(R.id.iv_add_image) ImageView mIvAddImage;
    @Bind(R.id.images_scroll_view) LinearLayout mImageScrollView;
    @Bind(R.id.tags_scroll_view) LinearLayout mTagsScrollView;
    @Bind(R.id.iv_add_user_check) ImageView mIvAddUserCheck;
    @Bind(R.id.iv_add_image_check) ImageView mIvAddImageCheck;

    private Task mTask = new Task();
    private List<String> mTags;
    private UserInfo mPerformer;
    private DateTime mDeadLine;
    private AttachmentsHelper mAttachmentsHelper;
    private AttachmentsLoaderHelper mAttachmentsLoaderHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_add_task, container, false);
        ButterKnife.bind(this, v);
        initToolbar();
        initView();
        mAttachmentsHelper = new AttachmentsHelper(this, getActivity(), mImageScrollView, mIvAddImageCheck);
        mAttachmentsLoaderHelper = new AttachmentsLoaderHelper(getActivity(), mAttachmentsHelper);
        return v;
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationIcon(R.drawable.cancel);
    }

    private void initView() {
        mEtTitle.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf"));
        mEtDescription.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf"));
        mTvChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataChooseDialog();
            }
        });
        mAddTag.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf"));
        mAddTag.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                        && !mAddTag.getText().toString().trim().equals("")) {
                    if (mTags == null) {
                        mTags = new ArrayList<>();
                    }
                    addTag(mAddTag.getText().toString().trim());
                    mAddTag.setText("");
                    return true;
                }
                return false;
            }
        });
        mAddTag.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //filter that excepts calling onKey twice
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;
                if (event.getKeyCode() == KeyEvent.KEYCODE_DEL
                        && mAddTag.getText().toString().equals("")
                        && mTags.size() != 0) {
                    deleteLastTag();
                    return true;
                }
                return false;
            }
        });
        initBottomTabs();
    }

    private void initBottomTabs() {
        mIvAddFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAttachmentsHelper.showPopupMenu(v);
            }
        });
        mIvAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAttachmentsHelper.showAddPhotoDialog(true);
            }
        });
        mIvAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UsersActivity.class);
                if (mPerformer != null) {
                    intent.putExtra(UsersFragment.EXTRA_CHECKED_USER_ID, mPerformer.getId());
                }
                startActivityForResult(intent, REQUEST_GET_USER);
            }
        });
    }

    private void showDataChooseDialog() {
        DateTime mDateTimeNow = DateTime.now();
        DatePickerDialog dataDatePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mDeadLine = new DateTime();
                        mDeadLine = mDeadLine.withDate(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.ROOT);
                        mTvChooseDate.setText(sdf.format(mDeadLine.toDate()));
                        showTimeChoosingDialog();
                    }
                }, mDateTimeNow.getYear(), mDateTimeNow.getMonthOfYear() - 1, mDateTimeNow.getDayOfMonth());
        dataDatePickerDialog.show();
    }

    private void showTimeChoosingDialog() {
        DateTime mDateTimeNow = DateTime.now();
        TimePickerDialog mTimePicker = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mDeadLine = mDeadLine.withTime(selectedHour, selectedMinute, 0, 0);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ROOT);
                        mTvChooseDate.setText(sdf.format(mDeadLine.toDate()));
                    }
                }, mDateTimeNow.getHourOfDay(), mDateTimeNow.getMinuteOfHour(), true);//Yes 24 hour time
        mTimePicker.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_task, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.add_task_done:
                if (!mEtTitle.getText().toString().isEmpty()) {
                    if (mDeadLine != null) {
                        showProgressBar();
                        sendTask();
                    } else {
                        Toast.makeText(getActivity(), R.string.content_deadline_empty_error,
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.content_title_empty_error,
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GET_USER) {
                mPerformer = data.getParcelableExtra(EXTRA_USER);
                if (mPerformer != null) {
                    mIvAddUserCheck.setVisibility(View.VISIBLE);
                    mTvAddStatus.setText(R.string.add_task_closed_task);
                    if (mPerformer.getAvatar() != null) {
                        Picasso.with(mIvAddUser.getContext()).load(mPerformer.getAvatar().getOriginalUrl()).into(mIvAddUser);
                    } else {
                        mIvAddUser.setImageResource(R.drawable.add_user);
                    }
                } else {
                    mIvAddUser.setImageResource(R.drawable.add_user);
                    mIvAddUserCheck.setVisibility(View.GONE);
                    mTvAddStatus.setText(R.string.add_task_open_task);
                }
            } else {
                mAttachmentsHelper.processMediaResult(requestCode, data);
            }
        }
    }

//    private void setPhotoBitmap(Bitmap bitmap, boolean isCover) {
//        RoundedImageView iv = getImageView(isCover);
//        iv.setImageBitmap(bitmap);
//        mImageScrollView.addView(photoLayout);
//    }

//    public void addVideo(String filePath, ChosenVideo video) {
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View v = inflater.inflate(R.layout.view_video_layout, mImageScrollView, false);
//        RelativeLayout videoLayout = (RelativeLayout) v.findViewById(R.id.video_layout);
//        RoundedImageView iv = (RoundedImageView) v.findViewById(R.id.ticket_media);
//        ImageView ivDelete = (ImageView) v.findViewById(R.id.ticket_image_close_button);
////        if(video != null) {
////            if (video.getPreviewImage() != null) {
////                Picasso.with(getActivity()).load(new File(video.getPreviewImage())).into(iv);
////            } else {
////                Picasso.with(getActivity()).load(Uri.parse(video.getOriginalPath())).into(iv);
////            }
////        } else {
//            if (filePath != null) {
//                Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath, Thumbnails.MICRO_KIND);
//                iv.setImageBitmap(bitmap);
////            }
////        }
//        mPhotoList.add(iv);
//        mImageScrollView.addView(videoLayout);
//        ivDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mImageScrollView.removeViewAt(mPhotoList.indexOf(iv));
//                mPhotoList.remove(mPhotoList.indexOf(iv));
//                //TODO delete by fileName
//            }
//        });
//    }


    private void addTag(String text) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.view_tag_layout, mTagsScrollView, false);
        LinearLayout tagLayout = (LinearLayout) v.findViewById(R.id.tag_layout);
        TextView tv = (TextView) v.findViewById(R.id.tv_tag);
        tv.setText(text);
        mTags.add(text);
        mTagsScrollView.addView(tagLayout, mTags.size() - 1);
    }

    private void deleteLastTag() {
        mTagsScrollView.removeViewAt(mTags.size() - 1);
        mTags.remove(mTags.size() - 1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingTicketIsSuccessEvent event) {
        mAttachmentsLoaderHelper.onSuccessGettingTicket(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingTicketErrorEvent event){
        hideProgressBar();
        UiUtils.showToast(R.string.content_dialog_error_add_task);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FileUploadIsSuccessEvent event) {
        mAttachmentsLoaderHelper.onSuccessFileUploading(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FileUploadIsErrorEvent event) {
        showErrorUploadingFileToast();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ImageFileUploadSuccessEvent event) {
        mAttachmentsLoaderHelper.onSuccessImageFileUploading(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(VideoFileUploadSuccessEvent event) {
        mAttachmentsLoaderHelper.onSuccessVideoFileUploading(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(VideoFileUploadIsErrorEvent event) {
        showErrorUploadingFileToast();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ImageFileUploadIsErrorEvent event) {
        showErrorUploadingFileToast();
    }

    private void showErrorUploadingFileToast() {
        UiUtils.showToast("Error upload file");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CreatingTaskIsSuccessEvent event) {
        hideProgressBar();
        if (mPerformer != null) {
            inviteUser(event.getTask());
        }
        getActivity().finish();
    }

    private void inviteUser(Task task) {
        InvitationSendingModel invitationSendingModel = new InvitationSendingModel();
        invitationSendingModel.setTaskId(task.getId());
        ActionInviteUser action = new ActionInviteUser(mPerformer.getId(), invitationSendingModel);
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }

    private void fillTask() {
        Media media = new Media();
        media.setImages(mAttachmentsHelper.getPhotos());
        media.setVideos(mAttachmentsHelper.getVideos());
        media.setDocuments(mAttachmentsHelper.getDocuments());
        mTask.setMedia(media);
        mTask.setTitle(mEtTitle.getText().toString());
        mTask.setText(mEtDescription.getText().toString());
        mTask.setDeadline(mDeadLine.getMillis() / 1000);
        if (mPerformer != null) {
            mTask.setType(TaskType.PRIVATE);
        } else {
            mTask.setType(TaskType.PUBLIC);
        }
        mTask.setCover(mAttachmentsHelper.getCover());
        mTask.setTags(mTags);
        mTask.setStatus(TaskStatus.HUNTING);
        mTask.setPublished(true);
    }

    public void sendTask() {
        fillTask();
        Action action = new ActionCreateTask(mTask);
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }
}
