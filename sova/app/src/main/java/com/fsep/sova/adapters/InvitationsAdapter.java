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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fsep.sova.R;
import com.fsep.sova.activities.TaskDetailsActivity;
import com.fsep.sova.fragments.NoteDetailsFragment;
import com.fsep.sova.models.FavouriteSendingModel;
import com.fsep.sova.models.Note;
import com.fsep.sova.models.ReceivedInvitation;
import com.fsep.sova.models.TakingTaskSendingModel;
import com.fsep.sova.models.Task;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionAddToFavourites;
import com.fsep.sova.network.actions.ActionDeleteFromFavourites;
import com.fsep.sova.network.actions.ActionDeleteInvitation;
import com.fsep.sova.network.actions.ActionTakeTask;
import com.fsep.sova.utils.UiUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InvitationsAdapter extends NotesAdapter {

    public static final int TYPE_ITEM_VIEW_INVITATION = 5;

    private List<ReceivedInvitation> mReceivedInvitations;
    private Activity mActivity;
    private long clickedInvitationId;

    public InvitationsAdapter(Activity activity) {
        super(activity);
        mActivity = activity;
        mReceivedInvitations = new ArrayList<>();
        mNotes = new ArrayList<>();
    }

    public InvitationsAdapter(@Nullable LoadData loadData) {
        super(loadData);
    }

    public boolean isEmpty(){
        return mNotes.isEmpty() && mReceivedInvitations.isEmpty();
    }

    public long getLastTaskId(){
        return super.getLasElementId();
    }

    public long getLastInvitationId(){
        return mReceivedInvitations.get(mReceivedInvitations.size() - 1).getId();
    }

    public void setInvitations(List<ReceivedInvitation> sentInvitations) {
        if (sentInvitations != null) {
            mReceivedInvitations = sentInvitations;
        }
//        onNewDataAppeared();
    }

    public void onUpdateData(List<ReceivedInvitation> invitationTasks, List<Note> tasks) {
        mNotes.addAll(tasks);
        mReceivedInvitations.addAll(invitationTasks);
        onNewDataAppeared();
    }

    public boolean isLastInvitation(){
        return mNotes.isEmpty();
    }

    public void setData(List<Note> tasks) {
        if (tasks != null) {
            mNotes = tasks;
        }
        super.setData(tasks);
    }

    @Override
    public int getItemViewType(int position) {
        int itemType = super.getItemViewType(position);
        if (itemType == TYPE_ITEM_VIEW) {
            if (position < mReceivedInvitations.size()) {
                return TYPE_ITEM_VIEW_INVITATION;
            } else {
                return TYPE_ITEM_VIEW;
            }
        }
        return itemType;
    }

    @Override
    protected int getDefaultItemsCount() {
        return mReceivedInvitations.size() + mNotes.size();
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ITEM_VIEW_INVITATION:
                return new InvitationViewHolder(
                        LayoutInflater.from(parent.getContext()).
                                inflate(R.layout.invitation_list_item, parent, false));
            default:
                return super.onCreateDefaultViewHolder(parent, viewType);
        }
    }

    @NonNull
    @Override
    protected String defineTextForEmptyDataMessage() {
        return super.defineTextForEmptyDataMessage();
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_ITEM_VIEW:
                super.onBindDefaultViewHolder(holder, position - mReceivedInvitations.size());
                break;
            case TYPE_ITEM_VIEW_INVITATION:
                fillInvitationItem((InvitationViewHolder) holder, position);
                break;
        }
    }

    private void fillInvitationItem(InvitationViewHolder h, int position) {
        final ReceivedInvitation invitation = getValueAt(position);
        Task task = invitation.getTask();
        if (invitation.getTask().getAuthor() != null) {
            h.mUserLayout.setVisibility(View.VISIBLE);
            if (invitation.getTask().getAuthor().getAvatar() != null) {
                Picasso.with(recyclerView.getContext())
                        .load(String.valueOf(task.getAuthor().getAvatar().getOriginalUrl()))
                        .into(h.mAvatar);
            }
            h.mUserName.setText(String.format("%1$s %2$s", task.getAuthor().getFirstName(),
                    task.getAuthor().getLastName()));
        } else {
            h.mUserLayout.setVisibility(View.GONE);
        }
        if (task.isFavorited()) {
            h.mBtnFavourite.setBackgroundResource(R.drawable.bookmark_active);
        } else {
            h.mBtnFavourite.setBackgroundResource(R.drawable.bookmark);
        }
        h.mBtnFavourite.setOnClickListener(view -> {
            Action favouriteAction;
            if (task.isFavorited()) {
                favouriteAction = new ActionDeleteFromFavourites(task.getId());
            } else {
                favouriteAction = new ActionAddToFavourites(new FavouriteSendingModel(task.getId()));
            }
            ServiceHelper.getInstance().startActionService(mActivity, favouriteAction);
        });
        if (task.isPublished()) {
            if (task.getPlace() != null) h.mPlace.setText(task.getPlace().getPlaceName());
            h.mDate.setText(String.valueOf(UiUtils.getHumanReadableDate(task.getPublishedTime())));
        }
        h.mDeadLine.setText(String.valueOf(UiUtils.getHumanReadableDate(task.getDeadline())));
        h.mTaskTitle.setText(task.getTitle());
        h.mView.setOnClickListener(view -> {
            //Здесь обработчик нажатия на эл-т целитком
            Intent intent = new Intent(mActivity, TaskDetailsActivity.class);
            intent.putExtra(NoteDetailsFragment.class.getSimpleName(), task.getId());
            mActivity.startActivity(intent);
        });
        h.mBtnAcceptInvitation.setOnClickListener(view -> {
            //Обработчик принятия приглашения
            Action actionTakeTask = new ActionTakeTask(new TakingTaskSendingModel(task.getId()));
            ServiceHelper.getInstance().startActionService(mActivity, actionTakeTask);

        });
        h.mBtnDeclineInvitation.setOnClickListener(view -> {
            //Обработчик отказа от приглашения
            clickedInvitationId = invitation.getId();
            Action actionDeclineInvitation = new ActionDeleteInvitation(invitation.getId());
            ServiceHelper.getInstance().startActionService(mActivity, actionDeclineInvitation);
        });
    }

    private ReceivedInvitation getValueAt(int position) {
        return mReceivedInvitations.get(position);
    }

    @NonNull
    @Override
    public List getData() {
        List<Object> invitationsAndTasks = new ArrayList<>();
        invitationsAndTasks.addAll(mReceivedInvitations);
        invitationsAndTasks.addAll(mNotes);
        return invitationsAndTasks;
    }

    public void updateFavouriteElement(long id) {
        int position = getInvitationPositionByTaskId(id);
        Note task;
        if (position != -1) {
            task = mReceivedInvitations.get(position).getTask();
        } else {
            position = super.getPositionById(id, mNotes);
            task = mNotes.get(position);
            position += mReceivedInvitations.size();
        }
        if (task.isFavorited()) {
            task.setFavorited(false);
        } else {
            task.setFavorited(true);
        }
        notifyItemChanged(position);
    }

    private int getInvitationPositionById(long id) {
        for (int i = 0; i < mReceivedInvitations.size(); i++) {
            if (mReceivedInvitations.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private int getInvitationPositionByTaskId(long taskId) {
        for (int i = 0; i < mReceivedInvitations.size(); i++) {
            if (mReceivedInvitations.get(i).getTask().getId() == taskId) {
                return i;
            }
        }
        return -1;
    }

    public void takeTask(Task task) {
        int position = getInvitationPositionByTaskId(task.getId());
        mReceivedInvitations.remove(position);
        mNotes.add(0, task);
        notifyItemRemoved(position);
        if (mReceivedInvitations.size() != 0) {
            notifyItemChanged(mReceivedInvitations.size());
        }
    }

    public void deleteInvitation() {
        int position = getInvitationPositionById(clickedInvitationId);
        mReceivedInvitations.remove(position);
        notifyItemRemoved(position);
    }

    public void refreshData(Task task) {
        int position = super.getPositionById(task.getId(), mNotes);
        if (position != -1) {
            mNotes.set(position, task);
            position += mReceivedInvitations.size();
            notifyItemChanged(position);
        } else {
            position = getInvitationPositionByTaskId(task.getId());
            mReceivedInvitations.get(position).setTask(task);
            notifyItemChanged(position);
        }
    }

    public void refreshCommentsCount(long taskId, int newCommentsAmount) {
        int position = getPositionById(taskId, mNotes);
        Note task = mNotes.get(position);
        task.setCommentsCount(task.getCommentsCount() + newCommentsAmount);
        mNotes.set(position, task);
        notifyItemChanged(position + mReceivedInvitations.size());
    }

    public void updateLikedElement(long id) {
        int position = getPositionById(id, mNotes);
        Note task = mNotes.get(position);
        if (task.isLiked()) {
            task.setLiked(false);
            task.setLikesCount(task.getLikesCount() - 1);
        } else {
            task.setLiked(true);
            task.setLikesCount(task.getLikesCount() + 1);
        }
        mNotes.set(position, task);
        notifyItemChanged(position + mReceivedInvitations.size());
    }


    public class InvitationViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.avatar) RoundedImageView mAvatar;
        @Bind(R.id.tv_name) TextView mUserName;
        @Bind(R.id.tv_date) TextView mDate;
        @Bind(R.id.tv_place) TextView mPlace;
        @Bind(R.id.tv_deadline) TextView mDeadLine;
        @Bind(R.id.btn_favourite) Button mBtnFavourite;
        @Bind(R.id.tv_task_title) TextView mTaskTitle;
        @Bind(R.id.btn_accept) Button mBtnAcceptInvitation;
        @Bind(R.id.btn_decline) Button mBtnDeclineInvitation;
        @Bind(R.id.user_layout) RelativeLayout mUserLayout;

        public final View mView;

        public InvitationViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
