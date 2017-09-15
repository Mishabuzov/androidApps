package com.kpfu.mikhail.gif.screen.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.kpfu.mikhail.gif.BuildConfig;
import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.content.LoginForm;
import com.kpfu.mikhail.gif.content.Token;
import com.kpfu.mikhail.gif.screen.base.navigation.NavigationActivity;
import com.kpfu.mikhail.gif.screen.general.LoadingDialog;
import com.kpfu.mikhail.gif.screen.general.LoadingView;
import com.kpfu.mikhail.gif.screen.register.RegistrationActivity;
import com.kpfu.mikhail.gif.utils.PreferenceUtils;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class AuthActivity extends AppCompatActivity implements AuthView {

    @BindDrawable(R.drawable.kek9) Drawable mErrorImage;

    @BindString(R.string.auth_save_token_bearer) String mTokenPrescription;

    @BindString(R.string.auth_save_token_format) String mTokenFormat;

    @BindString(R.string.login_error_edit_text) String mErrorLogin;

    @BindString(R.string.password_error_edit_text) String mErrorPassword;

    @BindString(R.string.login_form_grant_type) String mGrantType;

    @BindString(R.string.toast_error_format) String mToastErrorFormat;

    @BindString(R.string.toast_error) String mToastErrorMsg;

    @BindView(R.id.login_edit_text) EditText mLoginEditText;

    @BindView(R.id.password_edit_text) EditText mPasswordEditText;

    @BindView(R.id.login_text_input_layout) TextInputLayout mLoginTextInputLayout;

    @BindView(R.id.password_input_layout) TextInputLayout mPasswordTextInputLayout;

    @BindView(R.id.error_image) ImageView mErrorIv;

    private AuthPresenter mPresenter;

    private LoadingView mLoadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        initActivityElements();
    }

    private void initActivityElements() {
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mPresenter = new AuthPresenter(lifecycleHandler, this);
        mLoadingView = LoadingDialog.view(getSupportFragmentManager());
    }

    @OnClick(R.id.sign_in_button)
    public void onClickSignInButton() {
        String login = String.valueOf(mLoginEditText.getText()).trim();
        String password = String.valueOf(mPasswordEditText.getText()).trim();
        boolean hasError = false;

        if (login.length() == 0) {
            mLoginTextInputLayout.setErrorEnabled(true);
            mLoginTextInputLayout.setError(mErrorLogin);
            hasError = true;
        } else {
            mLoginTextInputLayout.setError(null);
            mLoginTextInputLayout.setErrorEnabled(false);
        }
        if (password.length() == 0) {
            mPasswordTextInputLayout.setErrorEnabled(true);
            mPasswordTextInputLayout.setError(mErrorPassword);
            hasError = true;
        } else {
            mPasswordTextInputLayout.setError(null);
            mPasswordTextInputLayout.setErrorEnabled(false);
        }
        if (!hasError) {
            auth(login, password);
        } else {
            setErrorPict();
        }
    }

    @OnLongClick(R.id.sign_in_button)
    public boolean onLongClickSignInButton() {
        if (BuildConfig.DEBUG) {
            final String[] logins = {"mishabuzov"};
            final String[] passwords = {"getiba19"};
            new MaterialDialog.Builder(this).items((CharSequence[]) logins)
                    .itemsCallback((dialog, view1, which, text) -> {
                        mLoginEditText.setText(logins[which]);
                        mPasswordEditText.setText(passwords[which]);
                    })
                    .show();
        }
        return true;
    }

    private void setErrorPict() {
        mErrorIv.setImageDrawable(mErrorImage);
    }

    @OnClick(R.id.sign_up_button)
    public void onClickRegisterButton() {
        startActivity(new Intent(this, RegistrationActivity.class));
    }

    @OnClick(R.id.lets_go_button)
    public void onClickLetsGoButton() {
        startActivity(new Intent(this, NavigationActivity.class));
    }

    private void auth(String login, String password) {
        mPresenter.sendAuthRequest(new LoginForm(mGrantType, login, password));
    }

    @Override
    public void showLoading() {
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hideLoading();
    }

    @Override
    public void onLoginError(String message) {
        setErrorPict();
        Toast.makeText(this, String.format(mToastErrorFormat, mToastErrorMsg, message), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessAuth(Token token) {
        PreferenceUtils.saveToken(String.format(mTokenFormat, mTokenPrescription, token.getStringToken()));
        startActivity(new Intent(this, NavigationActivity.class));
        finish();
    }
}
