package com.fsep.sova.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fsep.sova.App;
import com.fsep.sova.R;
import com.fsep.sova.activities.AddTaskActivity;
import com.fsep.sova.activities.ChatActivity;
import com.fsep.sova.activities.CommentsActivity;
import com.fsep.sova.activities.ResponderActivity;
import com.fsep.sova.fragments.base.BaseLoadableFragment;
import com.fsep.sova.local_events.RefreshingCommentsEvent;
import com.fsep.sova.local_events.RefreshingDataEvent;
import com.fsep.sova.models.AssignTaskSendingModel;
import com.fsep.sova.models.Content;
import com.fsep.sova.models.Document;
import com.fsep.sova.models.FavouriteSendingModel;
import com.fsep.sova.models.Note;
import com.fsep.sova.models.NoteType;
import com.fsep.sova.models.Photo;
import com.fsep.sova.models.PostOnParticipantEvent;
import com.fsep.sova.models.Respond;
import com.fsep.sova.models.ResponseOnTaskInTask;
import com.fsep.sova.models.Url;
import com.fsep.sova.models.UserInfo;
import com.fsep.sova.models.Video;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionAddResponse;
import com.fsep.sova.network.actions.ActionAddToFavourites;
import com.fsep.sova.network.actions.ActionAssignTask;
import com.fsep.sova.network.actions.ActionCancelDecisionToParticipateInTheEvent;
import com.fsep.sova.network.actions.ActionDeclineResponse;
import com.fsep.sova.network.actions.ActionDeleteFromFavourites;
import com.fsep.sova.network.actions.ActionGetEventById;
import com.fsep.sova.network.actions.ActionGetPostById;
import com.fsep.sova.network.actions.ActionGetTaskById;
import com.fsep.sova.network.actions.ActionPutLike;
import com.fsep.sova.network.actions.ActionTakeParticipationInTheEvent;
import com.fsep.sova.network.events.add_response.AddingResponseIsSuccessEvent;
import com.fsep.sova.network.events.add_to_favourites.AddingToFavouritesIsSuccessEvent;
import com.fsep.sova.network.events.assign_task.AssignTaskErrorEvent;
import com.fsep.sova.network.events.assign_task.AssignTaskIsSuccessEvent;
import com.fsep.sova.network.events.cancel_decision_to_participate_in_the_event.CancelingDecisionToParticipateInTheEventIsSuccessEvent;
import com.fsep.sova.network.events.decline_response.DeclineResponseIsSuccessEvent;
import com.fsep.sova.network.events.delete_from_favourites.DeletingFromFavouritesIsSuccessEvent;
import com.fsep.sova.network.events.delete_task.DeletingTaskIsSuccessEvent;
import com.fsep.sova.network.events.get_event_by_id.GettingEventByIdIsSuccessEvent;
import com.fsep.sova.network.events.get_post_by_id.GettingPostByIdIsSuccessEvent;
import com.fsep.sova.network.events.gettaskbyid.GettingTaskByIdIsSuccess;
import com.fsep.sova.network.events.put_like_to_note.PuttingLikeIsSuccessEvent;
import com.fsep.sova.network.events.take_participation_in_the_event.TakingParticipationInTheEventIsSuccessEvent;
import com.fsep.sova.utils.Constants;
import com.fsep.sova.utils.FileUtils;
import com.fsep.sova.utils.GridImageUtils;
import com.fsep.sova.utils.PrefUtils;
import com.fsep.sova.utils.UiUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteDetailsFragment extends BaseLoadableFragment {

    @BindDimen(R.dimen.mosaic_layout_parent_padding_size) int mMosaicParentPadding;
    @BindDimen(R.dimen.mosaic_layout_image_margins) int mMosaicImageMargins;
    @BindDimen(R.dimen.documents_layout_margin_top) int mDocumentsMarginTop;
    @Bind(R.id.task_status_color) RoundedImageView mTaskColor;
    @Bind(R.id.tv_title) TextView mTitle;
    @Bind(R.id.tv_description) TextView mDescription;
    @Bind(R.id.tv_date) TextView mDate;
    @Bind(R.id.tv_place) TextView mPlace;
    @Bind(R.id.tv_deadline) TextView mDeadline;
    @Bind(R.id.avatar) RoundedImageView mAvatar;
    @Bind(R.id.tv_name) TextView mUserName;
    @Bind(R.id.tv_task_status) TextView mTaskStatus;
    @Bind(R.id.user_layout) View mUserLayout;
    @Bind(R.id.grid_for_images) GridLayout mMosaicLayout;
    @Bind(R.id.time_layout) LinearLayout mTimeLayout;
    @Bind(R.id.black_line) View mBlackLine;
    @Bind(R.id.functional_layout) FrameLayout mFunctionalLayout;
    @Bind(R.id.additional_func_layout) RelativeLayout mMainLayout;
    @Bind(R.id.btn_favourite) Button mFavouriteButton;
    @Bind(R.id.btn_like) Button mLikeButton;
    @Bind(R.id.btn_comment) Button mCommentButton;
    @Bind(R.id.btn_responder) Button mResponderButton;
    @Bind(R.id.tags_scroll_view) LinearLayout mTagsScrollView;
    @Bind(R.id.iv_cover) ImageView mCoverIv;
    @Bind(R.id.toolbar_top) Toolbar mToolbar;
    @Bind(R.id.status_layout) LinearLayout mStatusLayout;
    @Bind(R.id.documents_layout) LinearLayout mDocumentsLayout;
    public static final int REQUEST_CHOOSING_PERFORMER = 1;
    private Note mNote;
    private List<Url> mContentUrls;
    private List<Content> mMediaContent;
    private long mNoteId;
    private LayoutInflater inflater;
    private View myView;
    private UserStatus mUserStatus;
    private UserInfo mUserInfo;
    private long mUserId;
    private boolean mIsDeletingFromFavouritesEvent;
    private int mNewAmountOfResponder;
    private NoteType mNoteType;
    public static final String TASK_ID = "NOTE_ID";
    public static final String TASK_TYPE = "TASK_TYPE";

    @OnClick(R.id.btn_comment)
    public void onClickCommentsButton() {
        Intent intent = new Intent(getActivity(), CommentsActivity.class);
        intent.putExtra(CommentsFragment.NOTE_ID, mNote.getId());
        startActivity(intent);
    }

    @OnClick(R.id.back_btn)
    public void backToFeed() {
        getActivity().finish();
    }

    @OnClick(R.id.btn_favourite)
    public void addToFavouriteOrDeleteFromFavourite() {
        Action favouriteAction;
        if (mNote.isFavorited()) {
            favouriteAction = new ActionDeleteFromFavourites(mNoteId);
        } else {
            favouriteAction = new ActionAddToFavourites(new FavouriteSendingModel(mNoteId));
        }
        ServiceHelper.getInstance().startActionService(getActivity(), favouriteAction);
    }

    @OnClick(R.id.btn_like)
    public void likeOrDislikeTask() {
        Action likeAction = new ActionPutLike(mNoteId);
        ServiceHelper.getInstance().startActionService(getActivity(), likeAction);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessLikesUpdate(PuttingLikeIsSuccessEvent event) {
        if (mNote.isLiked()) {
            mNote.setLiked(false);
            mNote.setLikesCount(mNote.getLikesCount() - 1);
            mLikeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart, 0, 0, 0);
            mLikeButton.setText(String.valueOf(mNote.getLikesCount()));
        } else {
            mNote.setLiked(true);
            mNote.setLikesCount(mNote.getLikesCount() + 1);
            mLikeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.redheart, 0, 0, 0);
            mLikeButton.setText(String.valueOf(mNote.getLikesCount()));
        }
        refreshDataInFeed();
    }

    private void refreshDataInFeed() {
        RefreshingDataEvent event = new RefreshingDataEvent(mNote);
        if (mIsDeletingFromFavouritesEvent) {
            event.setDeletingFromFavouritesEvent();
        }
        EventBus.getDefault().postSticky(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessAddedToFavourite(AddingToFavouritesIsSuccessEvent event) {
        mNote.setFavorited(true);
        mFavouriteButton.setBackgroundResource(R.drawable.bookmark_active);
        refreshDataInFeed();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessRemovingFromFavourites(DeletingFromFavouritesIsSuccessEvent event) {
        mIsDeletingFromFavouritesEvent = true;
        mNote.setFavorited(false);
        mFavouriteButton.setBackgroundResource(R.drawable.bookmark);
        refreshDataInFeed();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void refreshCommentsCount(RefreshingCommentsEvent event) {
        mNote.setCommentsCount(mNote.getCommentsCount() + event.getNewCommentsAmount());
        mCommentButton.setText(String.valueOf(mNote.getCommentsCount()));
        EventBus.getDefault().removeStickyEvent(event);
        refreshDataInFeed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mNoteId = getArguments().getLong(TASK_ID, 0);
        mNoteType = (NoteType) getArguments().getSerializable(TASK_TYPE);
        View view = inflater.inflate(R.layout.fragment_task_details, container, false);
        ButterKnife.bind(this, view);
        load();
        return view;
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_edit_note:
                Intent i = new Intent(getActivity(), AddTaskActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessDeletingTask(DeletingTaskIsSuccessEvent event) {
        getActivity().finish();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.task_details_menu, menu);
        MenuItem itemEditNote = menu.findItem(R.id.item_edit_note);
        MenuItem itemDeleteNote = menu.findItem(R.id.item_delete_note);
        switch (mNoteType) {
            case EVENT:
                itemEditNote.setTitle(R.string.task_details_edit_event);
                menu.removeItem(R.id.item_finish_note);
                itemDeleteNote.setTitle(R.string.task_details_remove_event);
                break;
            case POST:
                itemEditNote.setTitle(R.string.task_details_edit_post);
                menu.removeItem(R.id.item_finish_note);
                itemDeleteNote.setTitle(R.string.task_details_remove_post);
                break;
        }
    }

    private UserStatus identifyUser() {
        long userId = mUserId;
        if (mNote.getAuthor() != null && userId == mNote.getAuthor().getId()) {
            return UserStatus.AUTHOR;
        } else if (mNote.isResponsed() && mNote.getPerformer() == null || mNote.isParticipates()) {
            mUserStatus = UserStatus.RESPONDER;
            return UserStatus.RESPONDER;
        } else if (mNote.getPerformer() != null && userId == mNote.getPerformer().getId()) {
            return UserStatus.PERFORMER;
        } else {
            mUserStatus = UserStatus.USER;
            return UserStatus.USER;
        }
    }

    private void createMosaicLayoutWithContent() {
        GridImageUtils gridUtils = new GridImageUtils(this, mMosaicLayout, mMediaContent);
        gridUtils.fillGridLayoutByMosaicAlgorithm(mMosaicParentPadding, mMosaicImageMargins);
    }

    private void setDifferentParams() {
        long deadline;
        int respondersCount;
        switch (mNoteType) {
            case TASK:
                deadline = mNote.getDeadline();
                respondersCount = mNote.getRespondersCount();
                setDeadlineAndRespondersCountAndStatuses(deadline, respondersCount);
                break;
            case EVENT:
                deadline = mNote.getEndingTime();
                respondersCount = mNote.getParticipantsCount();
                setDeadlineAndRespondersCountAndStatuses(deadline, respondersCount);
                setOnClickResponderBtnForEvent();
                break;
            case POST:
                mResponderButton.setVisibility(View.GONE);
                mStatusLayout.setVisibility(View.GONE);
                break;
        }
    }

    private void setOnClickResponderBtnForEvent() {
        mResponderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ResponderActivity.class);
                intent.putExtra(Constants.NOTE_ID, mNote.getId());
                intent.putExtra(Constants.IS_EVENT, true);
                startActivity(intent);
            }
        });
    }

    private void setDeadlineAndRespondersCountAndStatuses(long deadline, int respondersCount) {
        mTimeLayout.setVisibility(View.VISIBLE);
        mDeadline.setText(String.valueOf(UiUtils.getHumanReadableDate(deadline)));
        mResponderButton.setText(String.valueOf(respondersCount));
        if (mNote.getStatus() != null) {
            mTaskStatus.setText(mNote.getStatus().getHumanReadableValue());
            mTaskColor.setImageResource(mNote.getStatus().getStatusColor());
        }
    }

    private void setInfoFromTaskToViews() {
        if (mNote.getTitle() == null || mNote.getTitle().equals("")) {
            mTitle.setVisibility(View.GONE);
        } else {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(mNote.getTitle());
        }
        if (mNote.getText() == null || mNote.getText().equals("")) {
            mDescription.setVisibility(View.GONE);
        } else {
            mDescription.setVisibility(View.VISIBLE);
            mDescription.setText(mNote.getText());
        }
        if (mNote.isPublished()) {
            if (mNote.getPlace() != null) mPlace.setText(". " + mNote.getPlace().getPlaceName());
            mDate.setText(String.valueOf(UiUtils.getHumanReadableDate(mNote.getPublishedTime())));
        }
        setDifferentParams();
        setLikes();
        if (mNote.isFavorited()) {
            mFavouriteButton.setBackgroundResource(R.drawable.bookmark_active);
        }
        mCommentButton.setText(String.valueOf(mNote.getCommentsCount()));
        if (mNote.getAuthor() != null) {
            mUserLayout.setVisibility(View.VISIBLE);
            if (mNote.getAuthor().getAvatar() != null) {
                Picasso.with(getContext())
                        .load(String.valueOf(mNote.getAuthor().getAvatar().getOriginalUrl()))
                        .into(mAvatar);
            }
            mUserName.setText(String.format("%1$s %2$s", mNote.getAuthor().getFirstName(),
                    mNote.getAuthor().getLastName()));
        } else {
            mUserLayout.setVisibility(View.GONE);
        }
        initMediaUrl(mNote);
        if (!mMediaContent.isEmpty()) {
            createMosaicLayoutWithContent();
        } else {
            mMosaicLayout.setVisibility(View.GONE);
        }
        addDocuments(mNote.getMedia().getDocuments(), mDocumentsLayout, mDocumentsMarginTop);
        addTags(mNote.getTags(), mTagsScrollView);
        addCover(mNote.getCover(), mCoverIv);
        mBlackLine.setVisibility(View.VISIBLE);
        mFunctionalLayout.setVisibility(View.VISIBLE);
    }

    private void addDocuments(List<Document> documents, LinearLayout documentsLayout, int documentsMarginTop) {
        if (!documents.isEmpty()) {
            for (Document document : documents) {
                setDocumentIntoMessage(
                        documentsLayout,
                        document,
                        documentsMarginTop);
            }
            documentsLayout.setVisibility(View.VISIBLE);
        }
    }

    private void setDocumentIntoMessage(LinearLayout documentsLayout, Document document, int chatDocumentMarginTop) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.document_layout, documentsLayout, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, chatDocumentMarginTop, 0, 0);
        v.setLayoutParams(params);
        TextView docNameTv = (TextView) v.findViewById(R.id.document_name);
        docNameTv.setText(document.getName());
        TextView docParamsTv = (TextView) v.findViewById(R.id.document_params);
        docParamsTv.setText(String.format("%1s,%2s", document.getExtension(), FileUtils.getRoundedFileSizeInMb(document.getSize())));
        documentsLayout.addView(v);
    }

    private void addCover(Photo cover, ImageView coverView) {
        if (cover != null) {
            Picasso.with(getActivity().getApplicationContext())
                    .load(String.valueOf(cover.getOriginalUrl()))
                    .into(coverView);
            coverView.setVisibility(View.VISIBLE);
        } else {
            coverView.setVisibility(View.GONE);
        }
    }

    private void addTags(List<String> tagsList, LinearLayout tagsScrollView) {
        if (!tagsList.isEmpty()) {
            if (tagsScrollView.getChildCount() != 0) {
                tagsScrollView.removeAllViews();
                tagsScrollView.setVisibility(View.GONE);
            }
            String[] tags = tagsList.toArray(new String[tagsList.size()]);
            LayoutInflater inflater = getActivity().getLayoutInflater();
            for (int i = 0; i < tags.length; i++) {
                View v = inflater.inflate(R.layout.view_tag_layout, tagsScrollView, false);
                LinearLayout tagLayout = (LinearLayout) v.findViewById(R.id.tag_layout);
                TextView tv = (TextView) v.findViewById(R.id.tv_tag);
                tv.setText(tags[i]);
                tagsScrollView.addView(tagLayout, i);
            }
            tagsScrollView.setVisibility(View.VISIBLE);
        }
    }

    private void setLikes() {
        if (mNote.isLiked()) {
            mLikeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.redheart, 0, 0, 0);
        }
        mLikeButton.setText(String.valueOf(mNote.getLikesCount()));
    }

    private void initMediaUrl(Note note) {
        mMediaContent = new ArrayList<>();
        List<Photo> photos = note.getMedia().getImages();
        List<Video> videos = note.getMedia().getVideos();
        for (Photo photo : photos) {
            mMediaContent.add(new Content(photo));
        }
        for (Video video : videos) {
            mMediaContent.add(new Content(video));
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected void load() {
        showProgressBar();
        Action action = null;
        switch (mNoteType) {
            case TASK:
                action = new ActionGetTaskById(mNoteId);
                break;
            case EVENT:
                action = new ActionGetEventById(mNoteId);
                break;
            case POST:
                action = new ActionGetPostById(mNoteId);
                break;
        }
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }

    private void addResponse() {
        showProgressBar();
        Action action = null;
        switch (mNoteType) {
            case TASK:
                action = new ActionAddResponse(new Respond(mNoteId, "string"));
                break;
            case EVENT:
                action = new ActionTakeParticipationInTheEvent(new PostOnParticipantEvent(mNoteId));
        }
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }

    private void declineResponse() {
        showProgressBar();
        Action action = null;
        switch (mNoteType) {
            case TASK:
                action = new ActionDeclineResponse(mNote.getResponse().getId());
                break;
            case EVENT:
                action = new ActionCancelDecisionToParticipateInTheEvent(mNoteId);
        }
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CancelingDecisionToParticipateInTheEventIsSuccessEvent event) {
        mNote.setParticipates(false);
        mNote.setParticipantsCount(mNote.getParticipantsCount() - 1);
        refreshing();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessDeclineResponseEvent(DeclineResponseIsSuccessEvent event) {
        mNote.setResponsed(false);
        mNote.setRespondersCount(mNote.getRespondersCount() - 1);
        mNote.setResponse(null);
        refreshing();
    }

    private void refreshFragment() {
        mResponderButton.setText(String.valueOf(mNote.getRespondersCount()));
        mMainLayout.removeAllViews();
        switch (mUserStatus) {
            case RESPONDER:
                addUserLayoutForHuntingTask();
                mUserStatus = UserStatus.USER;
                break;
            case USER:
                addResponderLayout();
                mUserStatus = UserStatus.RESPONDER;
                break;
        }
        hideProgressBar();
    }

    private void refreshing() {
        refreshDataInFeed();
        refreshFragment();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessAddingResponseEvent(AddingResponseIsSuccessEvent event) {
        mNote.setResponsed(true);
        mNote.setRespondersCount(mNote.getRespondersCount() + 1);
        mNote.setResponse(new ResponseOnTaskInTask(event.getResponseOnTask().getId(),
                event.getResponseOnTask().getMessage()));
        refreshing();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TakingParticipationInTheEventIsSuccessEvent event) {
        mNote.setParticipates(true);
        mNote.setParticipantsCount(mNote.getParticipantsCount() + 1);
        refreshing();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingTaskByIdIsSuccess event) {
        mNote = event.getTask();
        fillFragment();
    }

    private void fillFragment() {
        mUserId = PrefUtils.getUserId(App.context);
        if (mNote.getAuthor().getId() == mUserId) {
            initToolbar();
        }
        setInfoFromTaskToViews();
        if (mNoteType != NoteType.POST) {
            addUserOrResponderFunctional();
        }
        hideProgressBar();
        mFunctionalLayout.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingEventByIdIsSuccessEvent event) {
        mNote = event.getEvent();
        fillFragment();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingPostByIdIsSuccessEvent event) {
        mNote = event.getPost();
        fillFragment();
    }

    private void addUserLayoutForHuntingTask() {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myView = inflater.inflate(R.layout.fragment_task_details_user, null);
        LinearLayout userFrame = (LinearLayout) myView.findViewById(R.id.user_layout);
        Button responseBtn = (Button) myView.findViewById(R.id.response_button);
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        responseBtn.setWidth(size.x);
        if (mNoteType == NoteType.EVENT) {
            responseBtn.setText(R.string.task_details_participate);
        }
        responseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Обработчик отклика
                addResponse();
            }
        });
        mMainLayout.addView(userFrame);
    }

    private void addUserLayoutForWorkingTask() {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myView = inflater.inflate(R.layout.fragment_task_details_performer, null);
        LinearLayout performerLayout = (LinearLayout) myView.findViewById(R.id.founded_performer_layout);
        RoundedImageView avatarView = (RoundedImageView) myView.findViewById(R.id.avatar);
        if (mNote.getPerformer().getAvatar() != null) {
            Picasso.with(getContext()).load(mNote.getPerformer().getAvatar().getOriginalUrl()).into(avatarView);
        } else {
            Picasso.with(getContext()).load(R.drawable.add_user).into(avatarView);
        }
        avatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:Обработчик нажатия на аватар исполнителя
            }
        });
        mMainLayout.addView(performerLayout);
    }

    private void addResponderLayout() {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myView = inflater.inflate(R.layout.fragment_task_details_response, null);
        LinearLayout responseLayout = (LinearLayout) myView.findViewById(R.id.response_layout);
        Button responseCancelBtn = (Button) myView.findViewById(R.id.response_cancel_button);
        if (mNoteType == NoteType.EVENT) {
            TextView tvParticipation = (TextView) myView.findViewById(R.id.tv_participation);
            tvParticipation.setText(R.string.task_details_tv_participation);
            responseCancelBtn.setText(R.string.task_details_decline_participation);
        }
        responseCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Обработчик отмены отклика
                declineResponse();
            }
        });
        mMainLayout.addView(responseLayout);
    }

    private void addUserOrResponderFunctional() {
        Photo avatar;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (identifyUser()) {
            case RESPONDER:
                addResponderLayout();
                break;
            case USER:
                if (mNote.getPerformer() == null) {
                    addUserLayoutForHuntingTask();
                } else {
                    addUserLayoutForWorkingTask();
                }
                break;
            case AUTHOR:
                addAuthorFunctionalForResponderBtn();
                if (mNote.getPerformer() != null) {
                    avatar = mNote.getPerformer().getAvatar();
                    addPerformerLayout(avatar, true);
                }
                break;
            case PERFORMER:
                avatar = mNote.getAuthor().getAvatar();
                addPerformerLayout(avatar, false);
                break;
        }
    }

    private void addAuthorFunctionalForResponderBtn() {
        mResponderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ResponderActivity.class);
                i.putExtra(Constants.NOTE_ID, mNoteId);
                if (mNote.getPerformer() != null) {
                    i.putExtra(Constants.HAS_PERFORMER, true);
                }
                if (mNoteType == NoteType.EVENT) {
                    i.putExtra(Constants.IS_EVENT, true);
                }
                startActivityForResult(i, REQUEST_CHOOSING_PERFORMER);
            }
        });
    }

    private void addPerformerLayout(@Nullable Photo avatar, boolean isAuthor) {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myView = inflater.inflate(R.layout.fragment_task_details_my_performer, null);
        RelativeLayout performerLayout = (RelativeLayout) myView.findViewById(R.id.my_responder_layout);
        RoundedImageView avatarView = (RoundedImageView) myView.findViewById(R.id.avatar);
        if (avatar != null) {
            Picasso.with(getContext()).load(avatar.getOriginalUrl()).into(avatarView);
        } else {
            Picasso.with(getContext()).load(R.drawable.add_user).into(avatarView);
        }
        avatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:Обработчик нажатия на аватар исполнителя/автора задачи
            }
        });
        performerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(ChatFragment.TASK_ID, mNoteId);
                if (isAuthor) {
                    intent.putExtra(ChatFragment.NEED_LABEL_FUNCTIONAL, true);
                }
                startActivity(intent);
            }
        });
        mMainLayout.addView(performerLayout);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            showProgressBar();
            switch (requestCode) {
                case REQUEST_CHOOSING_PERFORMER:
                    mUserInfo = data.getParcelableExtra(Constants.EXTRA_USER);
                    mNewAmountOfResponder = data.getIntExtra(Constants.AMOUNT_OF_RESPONDERS, 0);
                    Action action = new ActionAssignTask(mUserInfo.getId(), new AssignTaskSendingModel(mNoteId));
                    ServiceHelper.getInstance().startActionService(getActivity(), action);
                    break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessAssigningOnTask(AssignTaskIsSuccessEvent event) {
        mNote.setPerformer(mUserInfo);
        mNote.setRespondersCount(mNewAmountOfResponder);
        mResponderButton.setText(String.valueOf(mNewAmountOfResponder));
        addPerformerLayout(mUserInfo.getAvatar(), false);
        refreshDataInFeed();
        hideProgressBar();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorAssigningOnTask(AssignTaskErrorEvent event) {
        UiUtils.showToast("Не удалось назначить выбранного исполнителя");
    }

    public enum UserStatus {
        AUTHOR,
        USER,
        RESPONDER,
        PERFORMER
    }
}
