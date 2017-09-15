package com.fsep.sova.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fsep.sova.BuildConfig;
import com.fsep.sova.R;
import com.fsep.sova.activities.NotesActivity;
import com.fsep.sova.activities.RegistrationPhoneActivity;
import com.fsep.sova.activities.SearchActivity;
import com.fsep.sova.fragments.base.BaseLoadableFragment;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionLogin;
import com.fsep.sova.network.events.login.AuthIsSuccess;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class AuthorizationFragment extends BaseLoadableFragment {

    @BindString(R.string.login_error_edit_text) String mErrorLogin;
    @BindString(R.string.password_error_edit_text) String mErrorPassword;
    @Bind(R.id.login_edit_text) EditText mLoginEditText;
    @Bind(R.id.password_edit_text) EditText mPasswordEditText;
    @Bind(R.id.login_text_input_layout) TextInputLayout mLoginTextInputLayout;
    @Bind(R.id.password_input_layout) TextInputLayout mPasswordTextInputLayout;

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
        }
    }

    @OnClick(R.id.register_button)
    public void onClickRegisterButton() {
        startActivity(new Intent(getActivity(), RegistrationPhoneActivity.class));
        getActivity().finish();
    }

    @OnClick(R.id.forget_button)
    public void onClickForgetButton() {
//        UiUtils.showToast("Вы забыли пароль?");
    }

    @OnLongClick(R.id.sign_in_button)
    public boolean onLongClickSignInButton() {
        if (BuildConfig.DEBUG) {
            final String[] logins = {"marsel", "ilseyar_alimova"};
            final String[] passwords = {"qwerty007", "qwertyuiop"};
            new MaterialDialog.Builder(getContext()).items(logins)
                    .itemsCallback((dialog, view1, which, text) -> {
                        mLoginEditText.setText(logins[which]);
                        mPasswordEditText.setText(passwords[which]);
                    })
                    .show();
        }
        return true;
    }

    private void auth(String login, String password) {
        showProgressBar();
        Action action = new ActionLogin(login, password);
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup conatiner,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authorization, conatiner, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Subscribe
    public void onEvent(AuthIsSuccess event) {
        hideProgressBar();
        Intent intent = new Intent(getActivity(), NotesActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
