package com.fsep.sova.activities.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseActivity extends BaseLifecycleActivity {

    private Window mWindow;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void setupWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mWindow = getWindow();
            mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void setupWindow(@ColorRes int statusBarColor){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            setupWindow();
            setStatusBarColor(statusBarColor);
        }
    }


    protected void setStatusBarColor(@ColorRes int statusBarColor){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mWindow.setStatusBarColor(ContextCompat.getColor(BaseActivity.this, statusBarColor));
        }
    }

}
