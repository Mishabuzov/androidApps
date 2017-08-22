package com.kpfu.mikhail.weathermvp.screen.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kpfu.mikhail.weathermvp.R;
import com.kpfu.mikhail.weathermvp.content.FullWeatherInfo;
import com.kpfu.mikhail.weathermvp.screen.general.LoadingDialog;
import com.kpfu.mikhail.weathermvp.screen.general.LoadingView;
import com.kpfu.mikhail.weathermvp.widget.EmptyRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class WeatherActivity extends AppCompatActivity implements WeatherView {

    public static final String CITY_NAME_KEY = "city_name";

    @BindView(R.id.recyclerView) EmptyRecyclerView mRecyclerView;
    @BindView(R.id.empty) View mEmptyView;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    private WeatherAdapter mWeatherAdapter;
    private WeatherPresenter mPresenter;
    private LoadingView mLoadingView;
    private String mCityName;

    public static void start(@NonNull Activity activity, @NonNull String cityName) {
        Intent intent = new Intent(activity, WeatherActivity.class);
        intent.putExtra(CITY_NAME_KEY, cityName);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        mCityName = getIntent().getStringExtra(CITY_NAME_KEY);
        initToolbar();
        mLoadingView = LoadingDialog.view(getSupportFragmentManager());
        initAdapter();
        setupRecyclerView();

        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mPresenter = new WeatherPresenter(lifecycleHandler, this, mCityName);
        mPresenter.init();
    }

    private void initAdapter(){
        mWeatherAdapter = new WeatherAdapter(new ArrayList<>());
        mWeatherAdapter.attachToRecyclerView(mRecyclerView);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(mCityName);
    }

    private void setupRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(mWeatherAdapter);
        mRecyclerView.setEmptyView(mEmptyView);
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
    public void showFullWeatherList(FullWeatherInfo fullWeatherInfo) {
        mWeatherAdapter.changeDataSet(fullWeatherInfo.getWeatherInfos());
    }

    @Override
    public void showError() {
        mWeatherAdapter.clear();
    }
}
