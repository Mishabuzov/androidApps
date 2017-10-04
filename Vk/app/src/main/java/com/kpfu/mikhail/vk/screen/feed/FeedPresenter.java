package com.kpfu.mikhail.vk.screen.feed;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.content.NewsLocal;
import com.kpfu.mikhail.vk.content.NewsResponse;
import com.kpfu.mikhail.vk.exceptions.IncorrectParsingDataException;
import com.kpfu.mikhail.vk.repository.VkProvider;
import com.kpfu.mikhail.vk.screen.base.BasePresenter;
import com.kpfu.mikhail.vk.utils.ConvertUtils;
import com.kpfu.mikhail.vk.utils.Function;
import com.kpfu.mikhail.vk.utils.PreferenceUtils;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class FeedPresenter extends BasePresenter<FeedView, NewsLocal> {

    private final Context mContext;

    private final FeedView mView;

    FeedPresenter(@NonNull FeedView view,
                  @NonNull Context context) {
        super(view);
        mView = view;
        mContext = context;
    }

    @Override
    public void connectData() {
        processRequest(VkProvider.provideVkRepository().getNewsFeed());
    }

    @Override
    protected void showData(@NonNull List<NewsLocal> data) {
        if (!data.isEmpty()) {
            mView.showFeed(data);
            mView.hideEmptyView();
        } else {
            mView.showEmptyView();
        }
        mView.hideLoading();
    }

    @Override
    protected void onRequestSuccess(VKResponse response) {
        try {
            NewsResponse newsResponse = parseNews(response.responseString);
            PreferenceUtils.saveNextFromValue(newsResponse.getResponse().getNextFrom());
            ArrayList<NewsLocal> news = ConvertUtils.convertResponseIntoAdapterModel(newsResponse.getResponse(), mContext);
            showData(news);
            mView.saveData(news);
        } catch (IncorrectParsingDataException e) {
            mView.handleError(e, this::connectData);
        }
    }

    private NewsResponse parseNews(@NonNull String feed) throws IncorrectParsingDataException {
        final ObjectMapper mapper = new ObjectMapper();
        final ObjectReader objectReader = mapper.readerFor(NewsResponse.class);
        NewsResponse newsResponse;
        try {
            newsResponse = objectReader
//                    .with(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
                    .with(JsonParser.Feature.IGNORE_UNDEFINED)
                    .readValue(feed);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IncorrectParsingDataException(
                    mContext.getString(R.string.incorrect_feed_json_exception));
        }
        return newsResponse;
    }

    @Override
    protected void onRequestError(@NonNull VKError error) {
        mView.hideLoading();
        mView.handleError(error.httpError, this::connectData);
    }

    void handleNetworkError(@NonNull Function reloadFunction,
                            boolean isDataEmpty) {
        if (isDataEmpty) {
            mView.handleNetworkErrorByErrorScreen(reloadFunction);
        } else {
            mView.showNetworkErrorMessage();
        }
    }

}
