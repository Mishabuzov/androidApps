package com.kpfu.mikhail.weathermvp.screen.city_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kpfu.mikhail.weathermvp.R;
import com.kpfu.mikhail.weathermvp.content.CurrentWeatherInfo;
import com.kpfu.mikhail.weathermvp.utils.WeatherUtils;
import com.kpfu.mikhail.weathermvp.widget.BaseAdapter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CityAdapter extends BaseAdapter<CityAdapter.CityViewHolder, CurrentWeatherInfo> {

    public CityAdapter(@NonNull List<CurrentWeatherInfo> items) {
        super(items);
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CityViewHolder h, int position) {
        super.onBindViewHolder(h, position);
        CurrentWeatherInfo currentWeatherInfo = getItem(position);
        String temp = WeatherUtils.getRoundedValue(currentWeatherInfo.getCurrentWeather().getTemp());
        h.mCityTempTv.setText(String.format("%1$s %2$s", temp, h.mCelsiusSymbol));
        h.mCityNameTv.setText(currentWeatherInfo.getName());
    }


    public class CityViewHolder extends RecyclerView.ViewHolder {
        @BindString(R.string.celsius) String mCelsiusSymbol;
        @BindView(R.id.city_name_tv) TextView mCityNameTv;
        @BindView(R.id.city_temp_tv) TextView mCityTempTv;

        public CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
