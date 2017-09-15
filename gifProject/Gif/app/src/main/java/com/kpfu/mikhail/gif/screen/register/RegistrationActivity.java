package com.kpfu.mikhail.gif.screen.register;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.content.RegistrationForm;
import com.kpfu.mikhail.gif.screen.base.navigation.NavigationActivity;
import com.kpfu.mikhail.gif.screen.general.LoadingDialog;
import com.kpfu.mikhail.gif.screen.general.LoadingView;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class RegistrationActivity extends AppCompatActivity implements RegisterView {

    @BindDrawable(R.drawable.kek9) Drawable mErrorImage;

    @BindString(R.string.auth_save_token_bearer) String mTokenPrescription;

    @BindString(R.string.auth_save_token_format) String mTokenFormat;

    @BindString(R.string.login_error_edit_text) String mErrorLogin;

    @BindString(R.string.email_error_edit_text) String mErrorPassword;

    @BindString(R.string.password_error_edit_text) String mErrorEmail;

    @BindString(R.string.toast_error_format) String mToastErrorFormat;

    @BindString(R.string.toast_error) String mToastErrorMsg;

    @BindView(R.id.text_login) EditText mLoginEt;

    @BindView(R.id.text_email) EditText mEmailEt;

    @BindView(R.id.text_password) EditText mPasswordEt;

    @BindView(R.id.register_login) TextInputLayout mLoginInputLayout;

    @BindView(R.id.register_email) TextInputLayout mEmailInputLayout;

    @BindView(R.id.register_password) TextInputLayout mPasswordInputLayout;

    @BindView(R.id.error_image_reg) ImageView mErrorIv;

    private RegistrationPresenter mPresenter;

    private LoadingView mLoadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initActivityElements();
    }

    private void initActivityElements() {
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mPresenter = new RegistrationPresenter(lifecycleHandler, this);
        mLoadingView = LoadingDialog.view(getSupportFragmentManager());
    }

    @OnClick(R.id.finish_button)
    public void onClickSignInButton() {
        String login = String.valueOf(mLoginEt.getText()).trim();
        String email = String.valueOf(mEmailEt.getText()).trim();
        String password = String.valueOf(mPasswordEt.getText()).trim();
        boolean hasError = false;

        if (login.length() == 0) {
            mLoginInputLayout.setErrorEnabled(true);
            mLoginInputLayout.setError(mErrorLogin);
            hasError = true;
        } else {
            mLoginInputLayout.setError(null);
            mLoginInputLayout.setErrorEnabled(false);
        }

        if (password.length() == 0) {
            mPasswordInputLayout.setErrorEnabled(true);
            mPasswordInputLayout.setError(mErrorPassword);
            hasError = true;
        } else {
            mPasswordInputLayout.setError(null);
            mPasswordInputLayout.setErrorEnabled(false);
        }

        if (email.length() == 0) {
            mEmailInputLayout.setErrorEnabled(true);
            mEmailInputLayout.setError(mErrorEmail);
            hasError = true;
        } else {
            mEmailInputLayout.setError(null);
            mEmailInputLayout.setErrorEnabled(false);
        }
        if (!hasError) {
            register(login, email, password);
        } else {
            setErrorPict();
        }
    }

    private void setErrorPict() {
        mErrorIv.setImageDrawable(mErrorImage);
    }

    private void register(String login, String email, String password) {
        mPresenter.sendRegisterRequest(new RegistrationForm(login, password, email), mTokenFormat, mTokenPrescription);
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
    public void onError(String message) {
        setErrorPict();
        Toast.makeText(this, String.format(mToastErrorFormat, mToastErrorMsg, message), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessRegistered() {
        startActivity(new Intent(this, NavigationActivity.class));
        Toast.makeText(this, "You have successfully registered!", Toast.LENGTH_LONG).show();
        finish();
    }
}
