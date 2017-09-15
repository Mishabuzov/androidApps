package com.fsep.sova.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fsep.sova.R;
import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;
import com.fsep.sova.fragments.EditProfileFragment;
import com.fsep.sova.models.CountryCity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CountryCityAdapter extends BaseRecyclerViewAdapter{

    private List<CountryCity> mCountryCities;
    private String mSelectedCountryCity;
    private Intent mIntent = new Intent();
    private Activity mActivity;

    public CountryCityAdapter(Activity activity) {
        super(null);
        mActivity = activity;
        mCountryCities = new ArrayList<>();
    }

    public CountryCityAdapter(@Nullable LoadData loadData, String selectedCountryCity, List<CountryCity> countryCities) {
        super(loadData);
        mSelectedCountryCity = selectedCountryCity;
        mCountryCities = countryCities;
    }

    public CountryCityAdapter(@Nullable LoadData loadData) {
        super(loadData);
    }

    public void setCountryCities(List<CountryCity> countryCities) {
        mCountryCities = countryCities;
        onNewDataAppeared();
    }

    public void updateData(List<CountryCity> countryCities){
        mCountryCities.addAll(countryCities);
        onNewDataAppeared();
    }

    public long getLastItemId(){
        return mCountryCities.get(mCountryCities.size() - 1).getId();
    }

    @NonNull
    @Override
    public List getData() {
        return mCountryCities;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType) {
        return new CountryCityViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country_city, parent, false));
    }

    @NonNull
    @Override
    protected String defineTextForEmptyDataMessage() {
        return null;
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM_VIEW) {
            CountryCityViewHolder h = (CountryCityViewHolder) holder;
            String countryCity = mCountryCities.get(position).getTitle();
            h.mTvCountryCity.setText(countryCity);
            if(isSelectedCountryCity(countryCity)){
                h.mIvCountryCity.setVisibility(View.VISIBLE);
            } else {
                h.mIvCountryCity.setVisibility(View.INVISIBLE);
            }
            h.mCountryCityItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.setResult(Activity.RESULT_OK, mIntent);
                  //  mIntent.putExtra(EditProfileFragment.COUNTRY_CITY, mCountryCities.get(position));
                    mActivity.finish();
                }
            });
        }
    }

    public boolean isSelectedCountryCity(String countryCity){
        return countryCity.equals(mSelectedCountryCity);
    }

    public class CountryCityViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv_city_country_name) TextView mTvCountryCity;
        @Bind(R.id.iv_country_city) ImageView mIvCountryCity;
        @Bind(R.id.country_city_item) RelativeLayout mCountryCityItem;

        public CountryCityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
