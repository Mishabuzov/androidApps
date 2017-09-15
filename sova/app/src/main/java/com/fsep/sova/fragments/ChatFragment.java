package com.fsep.sova.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsep.sova.App;
import com.fsep.sova.Config;
import com.fsep.sova.R;
import com.fsep.sova.adapters.ChatAdapter;
import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;
import com.fsep.sova.fragments.base.BaseRecyclerViewFragment;
import com.fsep.sova.models.Conversation;
import com.fsep.sova.models.Document;
import com.fsep.sova.models.Label;
import com.fsep.sova.models.Photo;
import com.fsep.sova.models.Video;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionGetConversations;
import com.fsep.sova.network.actions.ActionSendMessage;
import com.fsep.sova.network.events.get_conversations.GettingConversationsErrorEvent;
import com.fsep.sova.network.events.get_conversations.GettingConversationsIsEmptyEvent;
import com.fsep.sova.network.events.get_conversations.GettingConversationsIsSuccessEvent;
import com.fsep.sova.network.events.get_ticket.GettingTicketErrorEvent;
import com.fsep.sova.network.events.get_ticket.GettingTicketIsSuccessEvent;
import com.fsep.sova.network.events.get_user_by_id.GettingUserByIdErrorEvent;
import com.fsep.sova.network.events.loadfile.FileUploadIsErrorEvent;
import com.fsep.sova.network.events.loadfile.FileUploadIsSuccessEvent;
import com.fsep.sova.network.events.loadfile.ImageFileUploadIsErrorEvent;
import com.fsep.sova.network.events.loadfile.ImageFileUploadSuccessEvent;
import com.fsep.sova.network.events.loadfile.VideoFileUploadIsErrorEvent;
import com.fsep.sova.network.events.loadfile.VideoFileUploadSuccessEvent;
import com.fsep.sova.network.events.send_conversation.SendingConversationErrorEvent;
import com.fsep.sova.network.events.send_conversation.SendingConversationIsSuccessEvent;
import com.fsep.sova.utils.AttachmentsHelper;
import com.fsep.sova.utils.AttachmentsLoaderHelper;
import com.fsep.sova.utils.PrefUtils;
import com.fsep.sova.utils.UiUtils;
import com.fsep.sova.utils.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.java_websocket.WebSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompHeader;
import ua.naiksoftware.stomp.client.StompClient;

public class ChatFragment extends BaseRecyclerViewFragment {

    private AttachmentsHelper mAttachmentsHelper;
    private AttachmentsLoaderHelper mAttachmentsLoaderHelper;
    private long mTaskId;
    private ChatAdapter mAdapter;
    private String mTextMessage;
    private Conversation mNewConversation;
    private boolean mIsNeedLabelFunctional;
    private StompClient mStompClient;
    private ObjectMapper mJSONMapper = new ObjectMapper();
    public static final String TASK_ID = "TASK_ID";
    public static final String NEED_LABEL_FUNCTIONAL = "NEED_LABEL_FUNCTIONAL";
    @Bind(R.id.toolbar_edit_text) EditText mMessageEditText;
    @Bind(R.id.images_scroll_view) LinearLayout mImageScrollView;
    @Bind(R.id.separation_view) View mSeparationView;

    @OnClick(R.id.back_btn)
    public void toBackActivity() {
        getActivity().finish();
    }

    @OnClick(R.id.send_btn)
    public void sendMessage() {
        mTextMessage = String.valueOf(mMessageEditText.getText()).trim();
        if (!mTextMessage.isEmpty() || mImageScrollView.getChildCount() != 0) {
            createNewConversation(
                    mAttachmentsHelper.getPhotos(),
                    mAttachmentsHelper.getVideos(),
                    mAttachmentsHelper.getDocuments());
            sendMessageThrowWebsocket();
        }
    }

    private void sendMessageThrowWebsocket() {
        Action action = new ActionSendMessage(mTaskId, mNewConversation);
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessSendingMessage(SendingConversationIsSuccessEvent event) {
        //mAdapter.addNewConversation(event.getNewMessage());
        cleanEditsAndMedia();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorSendingMessage(SendingConversationErrorEvent errorEvent) {
        Logger.d(String.format("%1$s -- %2$s", errorEvent.getMessage(), errorEvent.getResponseCode()));
    }

    private void subscribeToChat() {
        mStompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Logger.d("Stomp connection opened");
                    break;
                case ERROR:
                    Logger.d("Error", lifecycleEvent.getException());
                    break;
                case CLOSED:
                    Logger.d(lifecycleEvent.getMessage());
                    Logger.d("Stomp connection closed");
                    break;

            }
        });

        mStompClient.topic("/tasks/" + mTaskId + "/conversations").subscribe(topicMessage -> {
            Logger.d(topicMessage.getPayload());
            Conversation receivedMessage = null;
            try {
                receivedMessage = mJSONMapper.readValue(topicMessage.getPayload(), Conversation.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            final Conversation finalReceivedMessage = receivedMessage;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.addNewConversation(finalReceivedMessage);
                }
            });
        });
    }

    private void createStompClientWithHeadersAndConnect() {
        mStompClient = Stomp.over(WebSocket.class, Config.SOVA_WEBSOCKET_ENDPOINT);
        StompHeader taskIDHeader = new StompHeader("task-id", String.valueOf(mTaskId));
        StompHeader authTokenHeader = new StompHeader("Auth-Token", PrefUtils.getAuthToken(App.context));
        List<StompHeader> headers = new ArrayList<>();
        headers.add(taskIDHeader);
        headers.add(authTokenHeader);
        mStompClient.connect(headers);
        subscribeToChat();
    }

    @OnClick(R.id.add_file_btn)
    public void addFileToMessage(View v) {
        mAttachmentsHelper.showPopupMenu(v);
    }

    private void createNewConversation(
            List<Photo> photos,
            List<Video> videos,
            List<Document> documents) {

        mNewConversation = new Conversation();
        mNewConversation.setText(mTextMessage);
        mNewConversation.setLabel(Label.EMPTY);
        mNewConversation.setImagesCount(photos.size());
        mNewConversation.setVideosCount(videos.size());
        mNewConversation.setDocumentsCount(documents.size());
        mNewConversation.setImages(photos);
        mNewConversation.setVideos(videos);
        mNewConversation.setDocuments(documents);
    }

    private void cleanEditsAndMedia() {
        mSeparationView.setVisibility(View.GONE);
        mMessageEditText.setText("");
        mImageScrollView.removeAllViews();
        mAttachmentsHelper.cleanAttachments();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingUserByIdErrorEvent errorEvent) {
        UiUtils.showToast("Не удалось загрузить данные с сервера");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, v);
        mAttachmentsHelper = new AttachmentsHelper(this, getActivity(), mImageScrollView, null);
        mAttachmentsHelper.setSeparationViewForChat(mSeparationView);
        mAttachmentsLoaderHelper = new AttachmentsLoaderHelper(getActivity(), mAttachmentsHelper);
        getArgs();
        createStompClientWithHeadersAndConnect();
        mAdapter = new ChatAdapter(this, mIsNeedLabelFunctional);
        getRecyclerView().setAdapter(mAdapter);
        load();
        return v;
    }

    private void getArgs() {
        mTaskId = getArguments().getLong(TASK_ID, 0);
        mIsNeedLabelFunctional = getArguments().getBoolean(NEED_LABEL_FUNCTIONAL);
    }

    private void load() {
        showProgressBar();
        Action action = new ActionGetConversations.Builder(mTaskId).build();
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mSeparationView.setVisibility(View.VISIBLE);
            mAttachmentsHelper.processMediaResult(requestCode, data);
        }
    }

    @Nullable
    @Override
    protected BaseRecyclerViewAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onRefresh() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingConversationsIsSuccessEvent event) {
        hideProgressBar();
        mAdapter.setConversations(event.getConversations());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingConversationsIsEmptyEvent emptyEvent) {
        hideProgressBar();
        mAdapter.showEmptyDataView();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingTicketIsSuccessEvent event) {
        mAttachmentsLoaderHelper.onSuccessGettingTicket(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingConversationsErrorEvent errorEvent) {
        hideProgressBar();
        UiUtils.showToast(R.string.chat_error_loading_messages);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingTicketErrorEvent event) {
        hideProgressBar();
        UiUtils.showToast(R.string.chat_error_loading_messages);
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
}
