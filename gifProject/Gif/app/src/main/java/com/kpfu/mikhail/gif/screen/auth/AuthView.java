package com.kpfu.mikhail.gif.screen.auth;

import com.kpfu.mikhail.gif.content.Token;
import com.kpfu.mikhail.gif.screen.general.LoadingView;

interface AuthView extends LoadingView {

    void onLoginError(String message);

    void onSuccessAuth(Token token);

}
