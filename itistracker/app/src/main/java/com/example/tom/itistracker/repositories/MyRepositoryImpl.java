package com.example.tom.itistracker.repositories;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.network.ServiceApi;

public class MyRepositoryImpl implements MyRepository {

    @NonNull
    private final ServiceApi mServiceApi;

    public MyRepositoryImpl(@NonNull final ServiceApi serviceApi) {
        mServiceApi = serviceApi;
    }

    @NonNull
    @Override
    public String workWithText(@NonNull final String text) {
        return text + " Text after working";
    }
}
