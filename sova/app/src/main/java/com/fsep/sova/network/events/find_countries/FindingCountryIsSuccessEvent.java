package com.fsep.sova.network.events.find_countries;

import com.fsep.sova.models.CountryCity;

import java.util.List;

public class FindingCountryIsSuccessEvent {

    private List<CountryCity> mCountryList;

    public FindingCountryIsSuccessEvent(List<CountryCity> countryList) {
        mCountryList = countryList;
    }

    public List<CountryCity> getCountryList() {
        return mCountryList;
    }
}
