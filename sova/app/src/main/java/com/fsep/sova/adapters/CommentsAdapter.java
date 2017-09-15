package com.fsep.sova.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fsep.sova.App;
import com.fsep.sova.R;
import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;
import com.fsep.sova.models.Comment;
import com.fsep.sova.utils.UiUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentsAdapter extends BaseRecyclerViewAdapter {

    private List<Comment> mComments;

    public CommentsAdapter() {
        super(null);
        mComments = new ArrayList<>();
    }

    public CommentsAdapter(@Nullable LoadData loadData) {
        super(loadData);
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
        sortCommentsByTime();
        onNewDataAppeared();
    }

    public void setEmptyScreen() {
        showEmptyDataView();
    }


    private void sortCommentsByTime() {
        if (mComments.size() > 0) {
            Collections.sort(mComments, new Comparator<Comment>() {
                @Override
                public int compare(final Comment comment1, final Comment comment2) {
                    if (comment1.getPublishedTime() < comment2.getPublishedTime()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
        }
    }

    public void addNewComment(Comment newComment) {
        mComments.add(newComment);
        onNewDataAppeared();
    }

    @NonNull
    @Override
    public List getData() {
        return mComments;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType) {
        return new CommentsViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false));
    }

    @NonNull
    @Override
    protected String defineTextForEmptyDataMessage() {
        return App.context.getString(R.string.comments_content_list_empty_message);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM_VIEW) {
            CommentsViewHolder h = (CommentsViewHolder) holder;
            final Comment comment = getValueAt(position);
            h.mTvDate.setText(UiUtils.getHumanReadableDate(comment.getPublishedTime()));
            h.mAvatar.setImageResource(android.R.color.transparent);
            if (comment.getAuthor() != null) {
                if (comment.getAuthor().getAvatar() != null) {
                    Picasso.with(recyclerView.getContext())
                            .load(String.valueOf(comment.getAuthor().getAvatar().getOriginalUrl()))
                            .into(h.mAvatar);
                }
                h.mTvName.setText(String.format("%1$s %2$s", comment.getAuthor().getFirstName(),
                        comment.getAuthor().getLastName()));
            } else {
                h.mUserLayout.setVisibility(View.GONE);
            }
            h.mTvDescription.setText(comment.getText());
        }
    }

    public Comment getValueAt(int position) {
        return mComments.get(position);
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_name) TextView mTvName;
        @Bind(R.id.tv_date) TextView mTvDate;
        @Bind(R.id.tv_description) TextView mTvDescription;
        @Bind(R.id.avatar) RoundedImageView mAvatar;
        @Bind(R.id.user_layout) View mUserLayout;

        public CommentsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
