package com.kpfu.mikhail.weathermvp.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WeatherUtils {

    public static String getRoundedValue(double doubleValue) {
        return String.valueOf(new BigDecimal(doubleValue).setScale(0, RoundingMode.HALF_UP).intValue());
    }

}
