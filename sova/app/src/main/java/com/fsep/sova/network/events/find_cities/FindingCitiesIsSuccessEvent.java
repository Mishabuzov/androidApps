package com.fsep.sova.network.events.find_cities;

import com.fsep.sova.models.CountryCity;

import java.util.List;

public class FindingCitiesIsSuccessEvent {

    private List<CountryCity> mCities;

    public FindingCitiesIsSuccessEvent(List<CountryCity> cities) {
        mCities = cities;
    }

    public List<CountryCity> getCities() {
        return mCities;
    }
}
