package com.fsep.sova.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fsep.sova.App;
import com.fsep.sova.R;
import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;
import com.fsep.sova.fragments.base.BaseRecyclerViewFragment;
import com.fsep.sova.models.Content;
import com.fsep.sova.models.Conversation;
import com.fsep.sova.models.Document;
import com.fsep.sova.models.Photo;
import com.fsep.sova.models.UserInfo;
import com.fsep.sova.models.Video;
import com.fsep.sova.utils.FileUtils;
import com.fsep.sova.utils.GridImageUtils;
import com.fsep.sova.utils.PrefUtils;
import com.fsep.sova.utils.UiUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

public class ChatAdapter extends BaseRecyclerViewAdapter {

    private List<Conversation> mConversations;
    private boolean mIsNeedLabelFunctional;
    private BaseRecyclerViewFragment mChatFragment;

    public void setConversations(List<Conversation> conversations) {
        mConversations = conversations;
        sortMessagesByTime();
        onNewDataAppeared();
    }

    public void addNewConversation(Conversation conversation) {
        mConversations.add(conversation);
        if (mConversations.size() == 1) {
            onNewDataAppeared();
        } else {
            notifyItemInserted(mConversations.size());
        }
    }

    /*private void sortMessagesByTime() {
        if (!mConversations.isEmpty()) {
            Collections.sort(mConversations, new Comparator<Conversation>() {
                @Override
                public int compare(Conversation conversation1, Conversation conversation2) {
                    if (conversation1.getDate() < conversation2.getDate()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
        }
    }*/

    private void sortMessagesByTime() {
        if (!mConversations.isEmpty()) {
            int n = mConversations.size();
            for(int i=0; i < n; i++){
                for(int j=1; j < (n-i); j++){

                    if(mConversations.get(j-1).getDate() > mConversations.get(j).getDate()){
                        //swap the elements!
                        Conversation conversation = mConversations.get(j-1);
                        mConversations.add(j-1, mConversations.get(j));
                        mConversations.add(j, conversation);
                    }

                }
            }

            /*for (Conversation conversation:mConversations){
                if(conversation.)
            }*/
            Collections.sort(mConversations, new Comparator<Conversation>() {
                @Override
                public int compare(Conversation conversation1, Conversation conversation2) {
                    if (conversation1.getDate() < conversation2.getDate()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
        }
    }

    public ChatAdapter(BaseRecyclerViewFragment chatFragment, boolean isNeedLabelFunctional) {
        super(null);
        mConversations = new ArrayList<>();
        mChatFragment = chatFragment;
        mIsNeedLabelFunctional = isNeedLabelFunctional;
    }

    public ChatAdapter(@Nullable LoadData loadData) {
        super(loadData);
    }

    @NonNull
    @Override
    public List getData() {
        return mConversations;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType) {
        return new ChatViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, parent, false));
    }

    @NonNull
    @Override
    protected String defineTextForEmptyDataMessage() {
        return App.context.getString(R.string.chat_empty_message);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM_VIEW) {
            ChatViewHolder h = (ChatViewHolder) holder;
            final Conversation conversation = getValueAt(position);
            final UserInfo author = conversation.getSender();
            h.mNameTv.setText(String.format("%1s %2s", author.getFirstName(), author.getLastName()));
            h.mAvatarIv.setImageResource(R.drawable.human);
            if (author.getAvatar() != null) {
                Picasso.with(recyclerView.getContext())
                        .load(author.getAvatar().getOriginalUrl())
                        .into(h.mAvatarIv);
            }
            h.mDateTv.setText(UiUtils.getHumanReadableDate(conversation.getDate()));
            h.mMessageTv.setVisibility(View.GONE);
            if (conversation.getText() != null && !conversation.getText().isEmpty()) {
                h.mMessageTv.setVisibility(View.VISIBLE);
                h.mMessageTv.setText(conversation.getText());
            }
            setContentIntoMosaicLayout(conversation, h.mGridForImages, h.mMosaicParentPadding, h.mMosaicImageMargins);
            addDocuments(conversation.getDocuments(), h.mDocumentsLayout, h.mChatDocumentsMarginTop);
            h.mStatusMenuBtn.setVisibility(View.GONE);
            if (mIsNeedLabelFunctional && conversation.getSender().getId() != PrefUtils.getUserId(App.context)) {
                h.mStatusMenuBtn.setVisibility(View.VISIBLE);
                h.mStatusMenuBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLabelMenu(v, h.mLabelColorIv, h.mStatusMenuBtn);
                    }
                });
            }
            h.mLabelColorIv.setVisibility(View.GONE);
            h.mLabelColorIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeLabelDialog(h.mLabelColorIv, h.mStatusMenuBtn);
                }
            });
        }
    }

    private void setContentIntoMosaicLayout(Conversation conversation,
                                            GridLayout mosaicLayout,
                                            int mosaicParentPadding,
                                            int mosaicImageMargins) {
        checkChildOfLayout(mosaicLayout);
        if (conversation.getImagesCount() != 0 || conversation.getVideosCount() != 0) {
            GridImageUtils gridUtils = new GridImageUtils(mChatFragment, mosaicLayout, getContentFromConversation(conversation));
            gridUtils.fillGridLayoutByMosaicAlgorithm(mosaicParentPadding, mosaicImageMargins);
            mosaicLayout.setVisibility(View.VISIBLE);
        }
    }

    private List<Content> getContentFromConversation(Conversation conversation) {
        List<Content> contentList = new ArrayList<>();
        List<Photo> photos = conversation.getImages();
        List<Video> videos = conversation.getVideos();
        for (Photo photo : photos) {
            contentList.add(new Content(photo));
        }
        for (Video video : videos) {
            contentList.add(new Content(video));
        }
        return contentList;
    }

    private void addDocuments(List<Document> documents, LinearLayout documentsLayout, int documentsMarginTop) {
        checkChildOfLayout(documentsLayout);
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

    private void checkChildOfLayout(ViewGroup parentLayout) {
        if (parentLayout.getChildCount() != 0) {
            parentLayout.removeAllViews();
            parentLayout.setVisibility(View.GONE);
        }
    }

    private void showLabelMenu(View v, RoundedImageView labelColorIv, ImageButton statusMenuBtn) {
        PopupMenu popupMenu = new PopupMenu(mChatFragment.getActivity(), v);
        popupMenu.inflate(R.menu.label_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.change_label_item:
                        changeLabelDialog(labelColorIv, statusMenuBtn);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    private void changeLabelDialog(RoundedImageView labelColorIv, ImageButton statusMenuBtn) {
        new MaterialDialog.Builder(mChatFragment.getActivity())
                .title(R.string.chat_change_label)
                .titleColor(ContextCompat.getColor(mChatFragment.getActivity(), R.color.btn_blue_bg))
                .items(R.array.chat_label_statuses)
                .itemsColor(ContextCompat.getColor(mChatFragment.getActivity(), R.color.black_text))
                .itemsCallback(new MaterialDialog.ListCallback() {
                    final int IN_PROGRESS = 0;
                    final int NEEDS_REVIEW = 1;
                    final int APPROVED = 2;
                    final int CANCEL = 3;

                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        switch (which) {
                            //TODO: Вставка запроса на изменение статуса в каждый кейс
                            case IN_PROGRESS:
                                setStatus(labelColorIv, statusMenuBtn, R.color.chat_in_progress_status);
                                break;
                            case APPROVED:
                                setStatus(labelColorIv, statusMenuBtn, R.color.chat_approved_status);
                                break;
                            case NEEDS_REVIEW:
                                setStatus(labelColorIv, statusMenuBtn, R.color.chat_needs_review_status);
                                break;
                            case CANCEL:
                                break;
                        }
                    }
                })
                .show();
    }

    private void setStatus(RoundedImageView labelColorIv, ImageButton statusMenuBtn, int colorRes) {
        statusMenuBtn.setVisibility(View.GONE);
        labelColorIv.setVisibility(View.VISIBLE);
        labelColorIv.setImageResource(colorRes);
    }

    private void setDocumentIntoMessage(LinearLayout documentsLayout, Document document, int chatDocumentMarginTop) {
        LayoutInflater inflater = mChatFragment.getActivity().getLayoutInflater();
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

    public Conversation getValueAt(int position) {
        return mConversations.get(position);
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        @BindDimen(R.dimen.mosaic_layout_parent_padding_size) int mMosaicParentPadding;
        @BindDimen(R.dimen.mosaic_layout_image_margins) int mMosaicImageMargins;
        @BindDimen(R.dimen.chat_documents_margin_top) int mChatDocumentsMarginTop;
        @Bind(R.id.avatar) RoundedImageView mAvatarIv;
        @Bind(R.id.tv_name) TextView mNameTv;
        @Bind(R.id.tv_date) TextView mDateTv;
        @Bind(R.id.status_menu) ImageButton mStatusMenuBtn;
        @Bind(R.id.tv_message) TextView mMessageTv;
        @Bind(R.id.grid_for_images) GridLayout mGridForImages;
        @Bind(R.id.documents_layout) LinearLayout mDocumentsLayout;
        @Bind(R.id.label_status_color) RoundedImageView mLabelColorIv;

        public ChatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
