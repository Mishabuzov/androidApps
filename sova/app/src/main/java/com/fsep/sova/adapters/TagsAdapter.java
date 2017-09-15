package com.fsep.sova.adapters;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fsep.sova.App;
import com.fsep.sova.R;
import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;
import com.fsep.sova.models.Tag;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;

public class TagsAdapter extends BaseRecyclerViewAdapter {

    private List<Tag> mFoundedTags;

    public TagsAdapter() {
        super(null);
        mFoundedTags = new ArrayList<>();
    }

    public TagsAdapter(@Nullable LoadData loadData) {
        super(loadData);
    }

    public String getLastItemId(){
        return mFoundedTags.get(mFoundedTags.size() - 1).getName();
    }

    public void updateData(List<Tag> tags){
        mFoundedTags.addAll(tags);
        onNewDataAppeared();
    }

    @NonNull
    @Override
    public List getData() {
        return mFoundedTags;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType) {
        return new TagsViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.tags_search_item, parent, false)
        );
    }

    public void setTags(List<Tag> tags) {
        mFoundedTags = tags;
        onNewDataAppeared();
    }

    @NonNull
    @Override
    protected String defineTextForEmptyDataMessage() {
        return App.context.getResources().getString(R.string.empty_tags_display);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM_VIEW) {
            TagsViewHolder h = (TagsViewHolder) holder;
            Tag currentTag = mFoundedTags.get(position);
            h.mTvTagName.setText(currentTag.getName());
            h.mTvPublishCount.setText(currentTag.getCountRecords() + " " + h.mTextForPublishes);
            final boolean[] isUserChosen = {false};
            h.mBtnSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isUserChosen[0]) {
                        unSelectChooseButton(h);
                        isUserChosen[0] = false;
                    } else {
                        selectChooseButton(h);
                        isUserChosen[0] = true;
                    }
                    //TODO: Обработчик выбора тега
                }
            });
        }
    }

    private void selectChooseButton(TagsViewHolder h) {
        h.mBtnSelect.setImageDrawable(h.mWhiteOkIcon);
        h.mBtnSelect.setBackgroundResource(R.color.searches_btn_chosen_color);
    }

    private void unSelectChooseButton(TagsViewHolder h) {
        h.mBtnSelect.setImageDrawable(h.mBluePlusIcon);
        h.mBtnSelect.setBackgroundResource(R.drawable.background_plus_btn_search);
    }

    public class TagsViewHolder extends RecyclerView.ViewHolder {

        @BindDrawable(R.drawable.white_ok) Drawable mWhiteOkIcon;
        @BindDrawable(R.drawable.blue_plus_plus) Drawable mBluePlusIcon;
        @BindString(R.string.publishes_count_adding) String mTextForPublishes;
        @Bind(R.id.tv_tag_name) TextView mTvTagName;
        @Bind(R.id.tv_publish_count) TextView mTvPublishCount;
        @Bind(R.id.btn_select) ImageButton mBtnSelect;

        public TagsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
