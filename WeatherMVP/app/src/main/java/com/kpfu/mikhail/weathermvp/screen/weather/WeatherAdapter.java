package com.kpfu.mikhail.weathermvp.screen.weather;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kpfu.mikhail.weathermvp.R;
import com.kpfu.mikhail.weathermvp.content.WeatherInfo;
import com.kpfu.mikhail.weathermvp.utils.WeatherUtils;
import com.kpfu.mikhail.weathermvp.widget.BaseAdapter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherAdapter extends BaseAdapter<WeatherAdapter.WeatherViewHolder, WeatherInfo> {

    public WeatherAdapter(@NonNull List<WeatherInfo> weatherInfos) {
        super(weatherInfos);
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WeatherViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder h, int position) {
        super.onBindViewHolder(h, position);
        WeatherInfo weatherInfo = getItem(position);
        h.mDateTv.setText(weatherInfo.getHumanReadableDate());
        h.mMainTv.setText(weatherInfo.getWeather().get(0).getMain());
        String temp = WeatherUtils.getRoundedValue(weatherInfo.getTemperature().getDay());
        h.mTempTv.setText(String.format("%1$s%2$s", temp, h.mCelsiusSymbol));
        String pressure = WeatherUtils.getRoundedValue(weatherInfo.getPressure() * 0.75);
        h.mPressureTv.setText(String.format("%1$s %2$s", pressure, h.mPressSymbol)); // Выносить типы форматов в ресурсы!
        h.mHumidityTv.setText(String.format("%1$s%2$s", weatherInfo.getHumidity(), h.mHumiditySymbol));
        h.mWindSpeedTv.setText(String.format("%1$s %2$s", WeatherUtils.getRoundedValue(weatherInfo.getSpeed()), h.mWindSymbol));
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        @BindString(R.string.celsius) String mCelsiusSymbol;
        @BindString(R.string.press_symbol) String mPressSymbol;
        @BindString(R.string.wind_speed_symbol) String mWindSymbol;
        @BindString(R.string.humidity_symbol) String mHumiditySymbol;
        @BindView(R.id.date) TextView mDateTv;
        @BindView(R.id.main) TextView mMainTv;
        @BindView(R.id.temp) TextView mTempTv;
        @BindView(R.id.press) TextView mPressureTv;
        @BindView(R.id.humidity) TextView mHumidityTv;
        @BindView(R.id.wind_speed) TextView mWindSpeedTv;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
