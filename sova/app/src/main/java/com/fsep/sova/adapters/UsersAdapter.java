package com.fsep.sova.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fsep.sova.R;
import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;
import com.fsep.sova.fragments.base.BaseRecyclerViewFragment;
import com.fsep.sova.models.UserInfo;
import com.fsep.sova.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UsersAdapter extends BaseRecyclerViewAdapter {

    private List<UserInfo> mUserInfos;
    private Activity mActivity;
    private int mSelectedUserPosition = -1;
    private Button mSelectedButton;
    private boolean mIsResponderAdapter;
    private boolean mHasPerformer;
    private long mCheckedUserId;
    private Intent mIntent;
    private BaseRecyclerViewFragment mFragment;

    public UsersAdapter(BaseRecyclerViewFragment fragment, Activity activity, long checkedUserId) {
        super(null);
        mUserInfos = new ArrayList<UserInfo>();
        mActivity = activity;
        mFragment = fragment;
        mCheckedUserId = checkedUserId;
        mIntent = new Intent();
    }

    public UsersAdapter(@Nullable LoadData loadData, List<UserInfo> userInfos, Activity activity) {
        super(loadData);
        this.mUserInfos = userInfos;
        this.mActivity = activity;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        mUserInfos = userInfos;
        onNewDataAppeared();
    }

    public void setResponderAdapter() {
        mIsResponderAdapter = true;
    }

    @NonNull
    @Override
    public List getData() {
        return mUserInfos;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType) {
        return new UsersViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.users_list_item, parent, false));
    }

    @NonNull
    @Override
    protected String defineTextForEmptyDataMessage() {
        if (mIsResponderAdapter) {
            return mActivity.getString(R.string.select_user_activity_no_responders);
        } else {
            return mActivity.getString(R.string.select_user_activity_no_data);
        }
    }

    public void neededBlockingOnChoosingPerformer() {
        mHasPerformer = true;
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM_VIEW) {
            UsersViewHolder h = (UsersViewHolder) holder;
            UserInfo userInfo = mUserInfos.get(position);
            h.mTvUsername.setText(String.format("%s %s", userInfo.getFirstName(), userInfo.getLastName()));
            h.mIvAvatar.setImageResource(R.drawable.human);
            if (userInfo.getAvatar() != null) {
                Picasso.with(h.mIvAvatar.getContext()).load(userInfo.getAvatar().getOriginalUrl()).into(h.mIvAvatar);
            }
            h.mBtnSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCheckedUserId = userInfo.getId();
                    if (mSelectedButton == h.mBtnSelect) {
                        deselectButton();
                    } else {
                        if (mSelectedButton != null) {
                            deselectButton();
                        }
                        mActivity.setResult(Activity.RESULT_OK, mIntent);
                        mIntent.putExtra(Constants.EXTRA_USER, userInfo);
                        mSelectedButton = h.mBtnSelect;
                        initSelectedButton(h.mBtnSelect);
                    }
                }
            });
            if (mCheckedUserId == userInfo.getId()) {
                initSelectedButton(h.mBtnSelect);
                mSelectedButton = h.mBtnSelect;
            } else {
                initUnselectedButton(h.mBtnSelect);
            }
            if (mHasPerformer) {
                h.mBtnSelect.setEnabled(false);
                h.mBtnSelect.setVisibility(View.GONE);
            }
        }
    }

    private void deselectButton(){
        initUnselectedButton(mSelectedButton);
        mCheckedUserId = -1;
        mSelectedButton = null;
        mIntent.removeExtra(Constants.EXTRA_USER);
    }

    private void initSelectedButton(Button btn) {
        btn.setTextColor(ContextCompat.getColor(mActivity, R.color.btn_blue_bg));
        btn.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.btn_selected_user_bg));
        btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.blue_ok, 0, 0, 0);
        btn.setText(" принят");
        btn.setAlpha(0.9f);
    }

    private void initUnselectedButton(Button btn) {
        btn.setTextColor(ContextCompat.getColor(mActivity, android.R.color.white));
        btn.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.btn_blue_bg));
        btn.setAlpha(1f);
        btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        btn.setText(R.string.select_user_activity_select);
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_username) TextView mTvUsername;
        @Bind(R.id.iv_avatar) ImageView mIvAvatar;
        @Bind(R.id.btn_select) Button mBtnSelect;

        public UsersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
