package com.fsep.sova.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsep.sova.App;
import com.fsep.sova.R;
import com.fsep.sova.activities.CommentsActivity;
import com.fsep.sova.activities.ResponderActivity;
import com.fsep.sova.activities.TaskDetailsActivity;
import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;
import com.fsep.sova.fragments.CommentsFragment;
import com.fsep.sova.fragments.NoteDetailsFragment;
import com.fsep.sova.models.FavouriteSendingModel;
import com.fsep.sova.models.Note;
import com.fsep.sova.models.Photo;
import com.fsep.sova.models.UserInfo;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionAddToFavourites;
import com.fsep.sova.network.actions.ActionDeleteFromFavourites;
import com.fsep.sova.network.actions.ActionPutLike;
import com.fsep.sova.utils.Constants;
import com.fsep.sova.utils.PrefUtils;
import com.fsep.sova.utils.UiUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NotesAdapter extends BaseRecyclerViewAdapter {

    protected List<Note> mNotes;
    protected Activity mActivity;
    private Note mFindPerformerTask;
    public Callback mCallback;
    //    private NotesViewHolder h;
//    private Note mNote;
    private boolean isFavouritesAdapter;
    private int mPosition;

    public NotesAdapter(@Nullable LoadData loadData) {
        super(loadData);
    }

    public NotesAdapter(Activity activity) {
        super(null);
        mNotes = new ArrayList<>();
        mActivity = activity;
    }

    public NotesAdapter(Activity activity, Callback callback) {
        super(null);
        mNotes = new ArrayList<>();
        mCallback = callback;
        mActivity = activity;
    }

    public void setData(List<Note> posts) {
        mNotes = posts;
        onNewDataAppeared();
    }

    public void updateData(List<Note> posts) {
        mNotes.addAll(posts);
        onNewDataAppeared();
    }

    public long getLasElementId() {
        if (!mNotes.isEmpty()) {
            return mNotes.get(mNotes.size() - 1).getId();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public List getData() {
        return mNotes;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType) {
        return new NotesViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, parent, false));
    }

    @NonNull
    @Override
    protected String defineTextForEmptyDataMessage() {
        return App.context.getString(R.string.notes_content_empty_message);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM_VIEW) {
            NotesViewHolder h = (NotesViewHolder) holder;
            Note note = getValueAt(position);
            initTitle(h, note);
            initDescription(h, note);
            h.mPlace.setText("");
            if (note.isPublished()) {
                if (note.getPlace() != null) h.mPlace.setText(note.getPlace().getPlaceName());
                h.mDate.setText(String.valueOf(UiUtils.getHumanReadableDate(note.getPublishedTime())));
            }
            h.mLikeButton.setText(String.valueOf(note.getLikesCount()));
            h.mCommentButton.setText(String.valueOf(note.getCommentsCount()));
            h.mAvatar.setImageResource(android.R.color.transparent);
            if (note.getAuthor() != null) {
                initUserLayout(h, note);
            } else {
                h.mUserLayout.setVisibility(View.GONE);
            }
            addTags(note.getTags(), h.mTagsScrollView);
            addCover(note.getCover(), h.mCoverIv);
            if (note.isFavorited()) {
                h.mFavouriteButton.setBackgroundResource(R.drawable.bookmark_active);
            } else {
                h.mFavouriteButton.setBackgroundResource(R.drawable.bookmark);
            }
            if (note.isLiked()) {
                h.mLikeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.redheart, 0, 0, 0);
            } else {
                h.mLikeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart, 0, 0, 0);
            }
            h.mCommentButton.setOnClickListener(view -> {
                openCommentsActivity(note);
            });
            h.mLikeButton.setOnClickListener(view -> {
                likeAction(note);
            });
            h.mFavouriteButton.setOnClickListener(view -> {
                onFavouriteButtonClicked(note);
            });
            h.mView.setOnClickListener(view -> {
                openDetailActivity(note);
            });
            recognizeNote(h, note);
        }
    }

    private void recognizeNote(NotesViewHolder h, Note note) {
        clearFields(h);
        h.mResponderButton.setOnClickListener(null);
        switch (note.getNoteType()) {
            case TASK:
                fillAndAddTaskFields(h, note);
                break;
            case EVENT:
                fillAndAddEventFields(h, note);
                break;
        }
    }

    private void clearFields(NotesViewHolder h) {
        h.mStatusLayout.setVisibility(View.GONE);
        h.mResponderButton.setVisibility(View.GONE);
        h.mDeadlineLayout.setVisibility(View.GONE);
    }

    private void fillAndAddEventFields(NotesViewHolder h, Note note) {
        setStatus(h, note);
        setDeadline(note.getEndingTime(), h);
        setRespondersCount(note.getParticipantsCount(), h);
        onClickResponderButton(h, note, true);
    }

    private void fillAndAddTaskFields(NotesViewHolder h, Note note) {
        setStatus(h, note);
        setDeadline(note.getDeadline(), h);
        setRespondersCount(note.getRespondersCount(), h);
        if (PrefUtils.getUserId(App.context) == note.getAuthor().getId()) {
            onClickResponderButton(h, note, false);
        }
    }

    private void onClickResponderButton(NotesViewHolder h, Note note, boolean isEvent) {
        h.mResponderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPerformersActivity(note, isEvent);
            }
        });
    }

    private void setRespondersCount(int respondersCount, NotesViewHolder h) {
        h.mResponderButton.setVisibility(View.VISIBLE);
        h.mResponderButton.setText(String.valueOf(respondersCount));
    }

    private void setDeadline(long deadline, NotesViewHolder h) {
        h.mDeadlineLayout.setVisibility(View.VISIBLE);
        h.mDeadLine.setText(String.valueOf(UiUtils.getHumanReadableDate(deadline)));
    }

    private void setStatus(NotesViewHolder h, Note note) {
        if (note.getStatus() != null) {
            h.mStatusLayout.setVisibility(View.VISIBLE);
            h.mTaskStatus.setText(note.getStatus().getHumanReadableValue());
            h.mTaskColor.setImageResource(note.getStatus().getStatusColor());
        }
    }

    private void openPerformersActivity(Note note, boolean isEvent) {
        Intent intent = new Intent(mActivity, ResponderActivity.class);
        intent.putExtra(Constants.NOTE_ID, note.getId());
        if (note.getPerformer() != null) {
            intent.putExtra(Constants.HAS_PERFORMER, true);
        }
        intent.putExtra(Constants.IS_EVENT, isEvent);
        mFindPerformerTask = note;
        mCallback.onChoosingPerformer(intent);
    }

    private void openDetailActivity(Note note) {
        //TODO: переход на детальный просмотр поста
        Intent intent = new Intent(mActivity, TaskDetailsActivity.class);
        intent.putExtra(NoteDetailsFragment.TASK_ID, note.getId());
        intent.putExtra(NoteDetailsFragment.TASK_TYPE, note.getNoteType());
        mActivity.startActivity(intent);
    }

    private Note getValueAt(int position) {
        return mNotes.get(position);
    }

    private void initTitle(NotesViewHolder h, Note note) {
        if (note.getTitle() == null || note.getTitle().equals("")) {
            h.mTitle.setVisibility(View.GONE);
        } else {
            h.mTitle.setVisibility(View.VISIBLE);
            h.mTitle.setText(note.getTitle());
        }
    }

    private void initDescription(NotesViewHolder h, Note note) {
        if (note.getText() == null || note.getText().equals("")) {
            h.mDescription.setVisibility(View.GONE);
        } else {
            h.mDescription.setVisibility(View.VISIBLE);
            h.mDescription.setText(note.getText());
        }
    }

    private void initUserLayout(NotesViewHolder h, Note note) {
        h.mUserLayout.setVisibility(View.VISIBLE);
        if (note.getAuthor().getAvatar() != null) {
            Picasso.with(recyclerView.getContext())
                    .load(String.valueOf(note.getAuthor().getAvatar().getOriginalUrl()))
                    .into(h.mAvatar);
        }
        h.mUserName.setText(String.format("%1$s %2$s", note.getAuthor().getFirstName(),
                note.getAuthor().getLastName()));
    }

    private void addTags(List<String> tagsList, LinearLayout tagsScrollView) {
        if (tagsScrollView.getChildCount() != 0) {
            tagsScrollView.removeAllViews();
            tagsScrollView.setVisibility(View.GONE);
        }
        if (!tagsList.isEmpty()) {
            String[] tags = tagsList.toArray(new String[tagsList.size()]);
            LayoutInflater inflater = mActivity.getLayoutInflater();
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

    private void addCover(Photo cover, ImageView coverView) {
        if (cover != null) {
            Picasso.with(mActivity.getApplicationContext())
                    .load(String.valueOf(cover.getOriginalUrl()))
                    .into(coverView);
            coverView.setVisibility(View.VISIBLE);
        } else {
            coverView.setVisibility(View.GONE);
        }
    }

    private void openCommentsActivity(Note note) {
        Intent intent = new Intent(mActivity, CommentsActivity.class);
        intent.putExtra(CommentsFragment.NOTE_ID, note.getId());
        mActivity.startActivity(intent);
    }

    private void likeAction(Note note) {
        Action likeAction = new ActionPutLike(note.getId());
        ServiceHelper.getInstance().startActionService(mActivity, likeAction);
    }

    private void onFavouriteButtonClicked(Note note) {
        Action favouriteAction;
        if (note.isFavorited()) {
            favouriteAction = new ActionDeleteFromFavourites(note.getId());
        } else {
            favouriteAction = new ActionAddToFavourites(new FavouriteSendingModel(note.getId()));
        }
        ServiceHelper.getInstance().startActionService(mActivity, favouriteAction);
    }

    public void setAdapterForFavourites() {
        isFavouritesAdapter = true;
    }

    public void updateFavouriteElement(long id) {
        mPosition = getPositionById(id, mNotes);
        Note note = mNotes.get(mPosition);
        if (note.isFavorited()) {
            note.setFavorited(false);
            if (isFavouritesAdapter) {
                mNotes.remove(mPosition);
                notifyItemRemoved(mPosition);
            }
        } else {
            note.setFavorited(true);
        }
        notifyItemChanged(mPosition);
    }

    public void refreshData(Note note) {
        mPosition = getPositionById(note.getId(), mNotes);
        mNotes.set(mPosition, note);
        notifyItemChanged(mPosition);
    }

    public void refreshingPerformingTask(UserInfo performer, int newResponderCount) {
        mPosition = getPositionById(mFindPerformerTask.getId(), mNotes);
        mFindPerformerTask.setPerformer(performer);
        mFindPerformerTask.setRespondersCount(newResponderCount);
        mNotes.set(mPosition, mFindPerformerTask);
        notifyItemChanged(mPosition);
    }

    public void refreshCommentsCount(long taskId, int newCommentsAmount) {
        mPosition = getPositionById(taskId, mNotes);
        Note note = mNotes.get(mPosition);
        note.setCommentsCount(note.getCommentsCount() + newCommentsAmount);
        mNotes.set(mPosition, note);
        notifyItemChanged(mPosition);
    }

    protected int getPositionById(long id, List<Note> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public void updateLikedElement(long id) {
        mPosition = getPositionById(id, mNotes);
        Note note = mNotes.get(mPosition);
        if (note.isLiked()) {
            note.setLiked(false);
            note.setLikesCount(note.getLikesCount() - 1);
        } else {
            note.setLiked(true);
            note.setLikesCount(note.getLikesCount() + 1);
        }
        mNotes.set(mPosition, note);
        notifyItemChanged(mPosition);
    }

    public long getTaskId() {
        return mFindPerformerTask.getId();
    }

    public interface Callback {
        void onChoosingPerformer(Intent intent);
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @Bind(R.id.tv_title) TextView mTitle;
        @Bind(R.id.tv_description) TextView mDescription;
        @Bind(R.id.tv_date) TextView mDate;
        @Bind(R.id.tv_place) TextView mPlace;
        @Bind(R.id.avatar) RoundedImageView mAvatar;
        @Bind(R.id.tv_name) TextView mUserName;
        @Bind(R.id.user_layout) View mUserLayout;
        @Bind(R.id.btn_like) Button mLikeButton;
        @Bind(R.id.btn_favourite) Button mFavouriteButton;
        @Bind(R.id.btn_comment) Button mCommentButton;
        @Bind(R.id.tags_scroll_view) LinearLayout mTagsScrollView;
        @Bind(R.id.iv_cover) ImageView mCoverIv;
        @Bind(R.id.task_status_color) ImageView mTaskColor;
        @Bind(R.id.tv_deadline) TextView mDeadLine;
        @Bind(R.id.tv_task_status) TextView mTaskStatus;
        @Bind(R.id.btn_responder) Button mResponderButton;
        @Bind(R.id.deadline_layout) LinearLayout mDeadlineLayout;
        @Bind(R.id.status_layout) LinearLayout mStatusLayout;

        public NotesViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
