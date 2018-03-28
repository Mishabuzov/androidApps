package com.example.tom.itistracker.screens.auth.login;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.tom.itistracker.repositories.MyRepository;

import javax.inject.Inject;

@InjectViewState
public class Presenter extends MvpPresenter<MyView> {

    private MyRepository mMyRepository;

    @Inject
    public Presenter(@NonNull final MyRepository myRepository) {
        mMyRepository = myRepository;
    }

    void doActionsAndShowText(@NonNull String text) {
        text = mMyRepository.workWithText(text);
        getViewState().changeText(text);
    }

}
