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
import com.fsep.sova.models.UserInfo;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;

public class PeopleSearchAdapter extends BaseRecyclerViewAdapter {

    private List<UserInfo> mFoundedPeopleList;

    public PeopleSearchAdapter() {
        super(null);
        mFoundedPeopleList = new ArrayList<>();
    }

    public PeopleSearchAdapter(@Nullable LoadData loadData) {
        super(loadData);
    }

    public void setPeople(List<UserInfo> foundedPeopleList) {
        mFoundedPeopleList = foundedPeopleList;
        onNewDataAppeared();
    }

    @NonNull
    @Override
    public List getData() {
        return mFoundedPeopleList;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType) {
        return new PeopleViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.people_search_item, parent, false)
        );
    }

    @NonNull
    @Override
    protected String defineTextForEmptyDataMessage() {
        return App.context.getString(R.string.empty_people_display);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM_VIEW) {
            PeopleViewHolder h = (PeopleViewHolder) holder;
            UserInfo currentUser = mFoundedPeopleList.get(position);
            h.mIvAvatar.setImageResource(android.R.color.transparent);
            if (currentUser.getAvatar() != null) {
                Picasso.with(recyclerView.getContext())
                        .load(String.valueOf(currentUser.getAvatar().getOriginalUrl()))
                        .into(h.mIvAvatar);
            } else {
                Picasso.with(recyclerView.getContext())
                        .load(R.drawable.human)
                        .into(h.mIvAvatar);
            }
            h.mTvUsername.setText(String.format(h.mNameFormat, currentUser.getFirstName(), currentUser.getLastName()));
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
                    //TODO: Обработчик выбора пользователя
                }
            });
        }
    }

    private void selectChooseButton(PeopleViewHolder h) {
        h.mBtnSelect.setImageDrawable(h.mWhiteOkIcon);
        h.mBtnSelect.setBackgroundResource(R.color.searches_btn_chosen_color);
    }

    private void unSelectChooseButton(PeopleViewHolder h) {
        h.mBtnSelect.setImageDrawable(h.mBluePlusIcon);
        h.mBtnSelect.setBackgroundResource(R.drawable.background_plus_btn_search);
    }

    public class PeopleViewHolder extends RecyclerView.ViewHolder {

        @BindString(R.string.name_surname_format) String mNameFormat;
        @BindDrawable(R.drawable.white_ok) Drawable mWhiteOkIcon;
        @BindDrawable(R.drawable.blue_plus_plus) Drawable mBluePlusIcon;
        @Bind(R.id.iv_avatar) RoundedImageView mIvAvatar;
        @Bind(R.id.tv_username) TextView mTvUsername;
        @Bind(R.id.btn_select) ImageButton mBtnSelect;

        public PeopleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
