package com.kpfu.mikhail.gif.screen.register;

import com.kpfu.mikhail.gif.screen.general.LoadingView;

interface RegisterView extends LoadingView {

    void onError(String message);

    void onSuccessRegistered();

}
