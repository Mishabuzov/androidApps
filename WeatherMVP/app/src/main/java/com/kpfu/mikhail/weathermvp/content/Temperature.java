package com.kpfu.mikhail.weathermvp.content;


import io.realm.RealmObject;

public class Temperature extends RealmObject{
    private double day;
    private double min;
    private double max;

    public Temperature(double day, double min, double max) {
        this.day = day;
        this.min = min;
        this.max = max;
    }

    public double getDay() {
        return day;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public Temperature() {
    }

    public void setDay(double day) {
        this.day = day;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
