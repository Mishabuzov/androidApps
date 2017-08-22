package com.kpfu.mikhail.weathermvp.screen.city_list;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kpfu.mikhail.weathermvp.R;
import com.kpfu.mikhail.weathermvp.content.CurrentWeatherInfo;
import com.kpfu.mikhail.weathermvp.screen.general.LoadingDialog;
import com.kpfu.mikhail.weathermvp.screen.general.LoadingView;
import com.kpfu.mikhail.weathermvp.screen.weather.WeatherActivity;
import com.kpfu.mikhail.weathermvp.widget.BaseAdapter;
import com.kpfu.mikhail.weathermvp.widget.DividerItemDecoration;
import com.kpfu.mikhail.weathermvp.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class CityActivity extends AppCompatActivity implements CityView, BaseAdapter.OnItemClickListener<CurrentWeatherInfo> {

    @BindView(R.id.recyclerView) EmptyRecyclerView mRecyclerView;
    @BindView(R.id.empty) View mEmptyView;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    private CityAdapter mCityAdapter;
    private CityPresenter mPresenter;
    private LoadingView mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);

        initToolbar();
        mLoadingView = LoadingDialog.view(getSupportFragmentManager());
        initAdapter();
        setupRecyclerView();
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mPresenter = new CityPresenter(lifecycleHandler, this);
        mPresenter.dispatchCreate();

    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
    }

    private void initAdapter(){
        mCityAdapter = new CityAdapter(new ArrayList<>());
        mCityAdapter.attachToRecyclerView(mRecyclerView);
        mCityAdapter.setOnItemClickListener(this);
    }

    private void setupRecyclerView() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mCityAdapter);
        mRecyclerView.setEmptyView(mEmptyView);
    }

    @Override
    public void showWeather(@NonNull List<CurrentWeatherInfo> currentWeatherInfos) {
        mCityAdapter.changeDataSet(currentWeatherInfos);
    }

    @Override
    public void goToFullWeatherScreen(@NonNull String cityName) {
        WeatherActivity.start(this, cityName);
    }

    @Override
    public void showError() {
        mCityAdapter.clear();
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
    public void onItemClick(@NonNull CurrentWeatherInfo item) {
        mPresenter.onItemClick(item.getName());
    }
}
