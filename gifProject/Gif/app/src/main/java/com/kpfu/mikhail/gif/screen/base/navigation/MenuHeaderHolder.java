package com.kpfu.mikhail.gif.screen.base.navigation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.utils.PreferenceUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

class MenuHeaderHolder extends RecyclerView.ViewHolder {

    @BindString(R.string.menu_header_welcome_msg) String mWelcomeMsg;

    @BindString(R.string.menu_header_welcome_msg_end) String mWelcomeMsgEnd;

    @BindString(R.string.menu_header_welcome_msg_format) String mWelcomeMsgFormat;

    @BindView(R.id.username_tv) TextView mUsernameTv;

    MenuHeaderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(){
        if(PreferenceUtils.isCurrentUserAuthorized()){
            String username = PreferenceUtils.getUserName();
            mUsernameTv.setText(String.format(mWelcomeMsgFormat, mWelcomeMsg, username, mWelcomeMsgEnd));
            mUsernameTv.setVisibility(View.VISIBLE);
        }
    }
}
