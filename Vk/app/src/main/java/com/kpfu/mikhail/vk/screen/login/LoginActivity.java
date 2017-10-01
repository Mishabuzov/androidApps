package com.kpfu.mikhail.vk.screen.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.screen.base.activities.base_activity.BaseActivity;
import com.kpfu.mikhail.vk.screen.feed.FeedActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import butterknife.BindString;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginView {

    @BindString(R.string.login_permission_denied) String mLoginPermissionErrorMessage;

    @BindString(R.string.scope_friends) String mScopeFriends;

    @BindString(R.string.scope_wall) String mScopeWall;

    @BindString(R.string.scope_offline) String mScopeOffline;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenter(this, mLoginPermissionErrorMessage);
        VKSdk.login(this, mScopeWall, mScopeFriends, mScopeOffline);
    }

    @Override
    public void openFeedScreen() {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // Пользователь успешно авторизовался
                mPresenter.onLoginSuccess(res.accessToken);
            }

            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                mPresenter.onLoginError(error);
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
