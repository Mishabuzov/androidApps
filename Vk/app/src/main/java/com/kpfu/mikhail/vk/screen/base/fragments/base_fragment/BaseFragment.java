package com.kpfu.mikhail.vk.screen.base.fragments.base_fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.kpfu.mikhail.vk.screen.base.BasePresenter;
import com.kpfu.mikhail.vk.screen.base.activities.single_fragment_activity.SingleFragmentActivity;
import com.kpfu.mikhail.vk.screen.base.fragments.error_fragment.ErrorFragment;
import com.kpfu.mikhail.vk.utils.AndroidUtils;
import com.kpfu.mikhail.vk.widget.progressbar.LoadingDialog;
import com.kpfu.mikhail.vk.widget.progressbar.LoadingView;

import java.util.List;

public abstract class BaseFragment<T extends Parcelable, P extends BasePresenter<LoadingView, T>>
        extends ErrorFragment implements BaseFragmentView {

    private static final String DATA = "mData"; //Для сохранения данных

    private LoadingView mLoadingView;

    private List<T> mData;

    private P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingView = LoadingDialog.view(getActivity()
                .getSupportFragmentManager());
        mPresenter = initPresenter();
    }

    public abstract P initPresenter();

    @NonNull
    public P getPresenter() {
        AndroidUtils.checkNotNull(mPresenter);
        return mPresenter;
    }

    public void getData(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mData = savedInstanceState.getParcelableArrayList(DATA); //Восстанавливаем данные если они есть
        }
        mPresenter.processData(mData);
    }

    @Override
    public void setToolbarTitle(String title) {
        if (title != null && !title.isEmpty()) {
            ((SingleFragmentActivity) getActivity()).setToolbarTitle(title);
        }
    }

    public void setToolbarTitle(@StringRes int title) {
        if (title != 0) {
            ((SingleFragmentActivity) getActivity()).setToolbarTitle(title);
        }
    }

    @Override
    public void showLoading() {
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hideLoading();
    }

  /*  @Override
    public void showErrorPage(String errorMessage) {
        //TODO: Exceptions handling
        UiUtils.showToast("Error! " + errorMessage, getActivity());
    }*/

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /* public interface ToolbarCallback{

        void setToolbarBehavior(RecyclerView recyclerView);

        void setToolbarBehavior(ScrollView scrollView);

    }*/

}
