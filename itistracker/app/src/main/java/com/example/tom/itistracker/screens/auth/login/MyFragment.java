package com.example.tom.itistracker.screens.auth.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.tom.itistracker.App;
import com.example.tom.itistracker.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyFragment extends MvpFragment implements MyView {

    @BindView(R.id.edittext_login)
    EditText mLoginEditText;

    @BindView(R.id.text_view)
    TextView mTextView;

    @Inject
    @InjectPresenter
    Presenter mPresenter;

    @ProvidePresenter
    Presenter providePresenter() {
        return mPresenter;
    }

    @OnClick(R.id.action_btn)
    public void onClickLoginBtn() {
        mPresenter.doActionsAndShowText(mLoginEditText.getText().toString());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        doInject();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    protected void doInject() {
        ((App) getActivity().getApplication()).getComponent().inject(this);
    }

    @Override
    public void changeText(String text) {
        mTextView.setText(text);
    }

}
