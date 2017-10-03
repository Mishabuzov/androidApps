package com.kpfu.mikhail.vk.screen.base.fragments.base_fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;

import com.kpfu.mikhail.vk.screen.base.BasePresenter;
import com.kpfu.mikhail.vk.screen.base.activities.single_fragment_activity.SingleFragmentActivity;
import com.kpfu.mikhail.vk.screen.base.fragments.error_fragment.ErrorFragment;
import com.kpfu.mikhail.vk.utils.AndroidUtils;
import com.kpfu.mikhail.vk.widget.progressbar.LoadingDialog;
import com.kpfu.mikhail.vk.widget.progressbar.LoadingView;

import java.util.ArrayList;

public abstract class BaseFragment<Data extends Parcelable, V extends BaseFragmentView<Data>,
        P extends BasePresenter<V, Data>>
        extends ErrorFragment implements BaseFragmentView<Data> {

    private static final String DATA = "mData"; //Для сохранения данных

    private LoadingView mLoadingView;

    private ArrayList<Data> mData;

    private P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mData = savedInstanceState.getParcelableArrayList(DATA);
        }
        super.onCreate(savedInstanceState);
        mLoadingView = LoadingDialog.view(getActivity()
                .getSupportFragmentManager());
        mPresenter = initPresenter();
    }

    /*@SuppressWarnings("unchecked")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore some state that needs to happen after the Activity was created
            //
            // Note #1: Our views haven't had their states restored yet
            // This could be a good place to restore a ListView's contents (and it's your last
            // opportunity if you want your scroll position to be restored properly)
            //
            // Note #2:
            // The following line will cause an unchecked type cast compiler warning
            // It's impossible to actually check the type because of Java's type erasure:
            //      At runtime all generic types become Object
            // So the best you can do is add the @SuppressWarnings("unchecked") annotation
            // and understand that you must make sure to not use a different type anywhere
            mData = savedInstanceState.getParcelableArrayList(DATA);
        }
    }*/

    public abstract P initPresenter();

    @NonNull
    public P getPresenter() {
        AndroidUtils.checkNotNull(mPresenter);
        return mPresenter;
    }

//    private Bundle mSavedInstanceState;

    public void getData(@Nullable Bundle savedInstanceState) {
      /*  if (savedInstanceState != null) {
//            mSavedInstanceState = savedInstanceState;
            mData = savedInstanceState.getParcelableArrayList(DATA); //Восстанавливаем данные если они есть
        }*/
        mPresenter.processData(mData);
    }

    public void saveData(ArrayList<Data> data) {
        mData = data;
        /*if (data != null && !data.isEmpty() && mSavedInstanceState != null) {
            mSavedInstanceState.putParcelableArrayList(DATA, (ArrayList<? extends Parcelable>) data);
        }*/
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

    /* public interface ToolbarCallback{

        void setToolbarBehavior(RecyclerView recyclerView);

        void setToolbarBehavior(ScrollView scrollView);

    }*/

   /* @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ViewGroup viewGroup = (ViewGroup) getView();
        AndroidUtils.checkNotNull(viewGroup);

*//*        viewGroup.removeAllViewsInLayout();
        View view = onCreateView(getActivity().getLayoutInflater(), viewGroup, null);*//*
//        viewGroup.addView(view);
    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mData != null) {
            Log.d("my", "Данные сохранены");
            outState.putParcelableArrayList(DATA, mData);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().isFinishing()) {
            destroyData();
        }
    }

    /*
    метод isFinishing вернет true только если мы явно вызвали метод finish() в активити,
    либо же ОС сама уничтожила активити из-за нехватки памяти.
    В этих случаях нам нет необходимости сохранять данные.
     */
    private void destroyData() {
        if (mData != null) {
            mData = null;
            Log.d("my", "Данные уничтожены");
        }
    }


}
