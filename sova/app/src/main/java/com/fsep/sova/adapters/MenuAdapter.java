package com.fsep.sova.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fsep.sova.App;
import com.fsep.sova.R;
import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;
import com.fsep.sova.models.TaskListType;
import com.fsep.sova.utils.PrefUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

//import com.fsep.sova.network.actions.ActionGetUsers;

public class MenuAdapter extends BaseRecyclerViewAdapter {

    private List<TaskListType> mMenuItems;
    private Callback mCallback;
    private int mSelectedItem = 0;
    private TextView mNameTv;
    private TextView mCounterTv;
    private ImageView mMenuIv;
    private Activity mActivity;
    public static final int TYPE_ITEM_VIEW_HEADER = 5;

    public MenuAdapter(Callback callback, Activity activity) {
        super(null);
        addMenuItems();
        mCallback = callback;
        mActivity = activity;
    }

    private void addMenuItems() {
        mMenuItems = new ArrayList<>();
        mMenuItems.add(TaskListType.FEED);
        mMenuItems.add(TaskListType.FEED);
        mMenuItems.add(TaskListType.MY_TASKS);
        mMenuItems.add(TaskListType.MY_EVENTS);
        mMenuItems.add(TaskListType.NOTIFICATIONS);
        mMenuItems.add(TaskListType.FAVOURITES);
        mMenuItems.add(TaskListType.SEARCH);
    }

    @NonNull
    @Override
    public List getData() {
        return mMenuItems;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM_VIEW_HEADER) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_header, parent, false));
        } else {
            return new MenuViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item, parent, false));
        }
    }

    @NonNull
    @Override
    protected String defineTextForEmptyDataMessage() {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        int itemType = super.getItemViewType(position);
        if (itemType == TYPE_ITEM_VIEW) {
            if (position == 0) {
                return TYPE_ITEM_VIEW_HEADER;
            } else {
                return TYPE_ITEM_VIEW;
            }
        }
        return itemType;
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM_VIEW) {
            MenuViewHolder h = (MenuViewHolder) holder;
            TaskListType listType = mMenuItems.get(position);
            h.mMenuIv.setBackgroundResource(listType.getIcon());
            h.mNameTv.setText(listType.getName());
            h.mMenuItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unselectMenuItems(h.mMenuElementDefaultColor, h.mMenuCounterDefaultColor);
                    h.mNameTv.setTextColor(h.mMenuElementChosenColor);
                    h.mMenuIv.setBackgroundResource(listType.getActiveIcon());
                    mCallback.onItemSelected(h.mMenuIv, listType);
                    saveSelectedValues(h.mNameTv, h.mCounterTv, h.mMenuIv, position);
                }
            });
        } else if (holder.getItemViewType() == TYPE_ITEM_VIEW_HEADER) {
            HeaderViewHolder h = (HeaderViewHolder) holder;
            String userAvatar = PrefUtils.getUserAvatar(App.context);
            if(userAvatar != null
                    && !userAvatar.isEmpty()) {
                Picasso.with(h.mAvatarIv.getContext())
                        .load(userAvatar)
                        .into(h.mAvatarIv);
            } else {
                Picasso.with(h.mAvatarIv.getContext())
                        .load(R.drawable.add_user)
                        .into(h.mAvatarIv);
            }
            h.mUsernameTv.setText(String.format("%1$s %2$s", PrefUtils.getUserName(App.context), PrefUtils.getUserSurname(App.context)));
            h.mAvatarIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onItemSelected(null, TaskListType.AVATAR);
                }
            });
        }
    }


    private void saveSelectedValues(TextView nameTv, TextView counterTv, ImageView menuIv, int position) {
        mSelectedItem = position;
        mMenuIv = menuIv;
        mCounterTv = counterTv;
        mNameTv = nameTv;
    }

    private void unselectMenuItems(int menuElementColor, int menuCounterColor) {
        if (mSelectedItem != 0) {
            mMenuIv.setBackgroundResource(mMenuItems.get(mSelectedItem).getIcon());
            mCounterTv.setTextColor(menuCounterColor);
            mNameTv.setTextColor(menuElementColor);
        }
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        @BindColor(R.color.colorNavElementChosen) int mMenuElementChosenColor;
        @BindColor(R.color.menu_name_color) int mMenuElementDefaultColor;
        @BindColor(R.color.menu_counter_color) int mMenuCounterDefaultColor;
        @Bind(R.id.ic_menu_item) ImageView mMenuIv;
        @Bind(R.id.item_name) TextView mNameTv;
        @Bind(R.id.counter) TextView mCounterTv;
        @Bind(R.id.menu_item) FrameLayout mMenuItem;

        public MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.avatar) ImageView mAvatarIv;
        @Bind(R.id.username) TextView mUsernameTv;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface Callback {
        void onItemSelected(ImageView menuIv, TaskListType listType);
    }
}
