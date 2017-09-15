package com.fsep.sova.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fsep.sova.R;
import com.fsep.sova.adapters.CommentsAdapter;
import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;
import com.fsep.sova.fragments.base.BaseRecyclerViewFragment;
import com.fsep.sova.local_events.RefreshingCommentsEvent;
import com.fsep.sova.models.CommentSendingModel;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionCreateComment;
import com.fsep.sova.network.actions.ActionGetComments;
import com.fsep.sova.network.events.create_comment.CreatingCommentIsSuccessEvent;
import com.fsep.sova.network.events.getcomments.GettingCommentsIsEmptyEvent;
import com.fsep.sova.network.events.getcomments.GettingCommentsIsSuccessEvent;
import com.fsep.sova.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentsFragment extends BaseRecyclerViewFragment {

  public static final String NOTE_ID = "NOTE_ID";

  public CommentsAdapter mAdapter;
  private long mTaskId;
  private int mNewCommentsAmount;

  @Bind(R.id.toolbar_edit_text) EditText mCommentEditText;

  @OnClick(R.id.back_btn) public void toBackActivity() {
    getActivity().finish();
  }

  @OnClick(R.id.send_btn)
  public void sendComment() {
    String comment = String.valueOf(mCommentEditText.getText()).trim();
    if (comment.equals("")) {
      UiUtils.showToast("Your comment is empty");
    } else {
      Action action = new ActionCreateComment(mTaskId, new CommentSendingModel(comment));
      ServiceHelper.getInstance().startActionService(getActivity(), action);
      showProgressBar();
    }
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    mAdapter = new CommentsAdapter();
    mNewCommentsAmount = 0;
    mTaskId = getArguments().getLong(NOTE_ID, 0);
    getRecyclerView().setAdapter(mAdapter);
    load();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_comments, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  protected void load() {
    showProgressBar();
    Action action = new ActionGetComments.Builder(mTaskId).build();
    ServiceHelper.getInstance().startActionService(getActivity(), action);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEvent(GettingCommentsIsSuccessEvent event) {
    mAdapter.setComments(event.getComments());
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEmptyEvent(GettingCommentsIsEmptyEvent event) {
    mAdapter.showEmptyDataView();
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onCommentCreated(CreatingCommentIsSuccessEvent event) {
    hideProgressBar();
    mAdapter.addNewComment(event.getNewComment());
    mCommentEditText.setText("");
    mNewCommentsAmount++;
  }

  @Override public void onStop() {
    if (mNewCommentsAmount != 0) {
      EventBus.getDefault().postSticky(new RefreshingCommentsEvent(mTaskId, mNewCommentsAmount));
    }
    super.onStop();
  }

  @Nullable @Override protected BaseRecyclerViewAdapter getAdapter() {
    return mAdapter;
  }

  @Override public void onRefresh() {

  }
}
